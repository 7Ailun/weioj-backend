package com.wei.weioj.judge;

import cn.hutool.json.JSONUtil;
import com.wei.weioj.common.ErrorCode;
import com.wei.weioj.exception.BusinessException;
import com.wei.weioj.judge.codesandbox.CodeSandboxFactory;
import com.wei.weioj.judge.codesandbox.CodeSandboxProxy;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.wei.weioj.judge.strategy.JudgeContext;
import com.wei.weioj.judge.strategy.JudgeManager;
import com.wei.weioj.judge.strategy.JudgeStrategy;
import com.wei.weioj.judge.strategy.impl.DefaultJudgeStrategy;
import com.wei.weioj.model.dto.question.JudgeCase;
import com.wei.weioj.model.dto.questionsubmit.JudgeInfo;
import com.wei.weioj.model.entity.Question;
import com.wei.weioj.model.entity.QuestionSubmit;
import com.wei.weioj.model.enums.QuestionSubmitStatusEnum;
import com.wei.weioj.service.QuestionService;
import com.wei.weioj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题服务
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Value("${code-sandbox.type}")
    private String type;
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private JudgeManager judgeManager;
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1. 校验 传入题目的提交 id，获取到对应的题目、提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();

        Question question = questionService.getById(questionId);
        if(question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }
        // 2. 如果题目提交状态不为等待中，就不用重复执行了
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            // 如果不是等待中，则不能进行判题
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }

        // 3. 更改判题（题目提交）的状态为“判题中：，防止重复执行，也能让用户即使看到判题状态
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean b = questionSubmitService.updateById(updateQuestionSubmit);
        if(!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新判题状态失败");
        }
        // 4. 调用代码沙箱，获取执行结果
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(judgeCase -> judgeCase.getInput()).collect(Collectors.toList());
        // 获取代码沙箱，执行代码
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(CodeSandboxFactory.newInstance(type));
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(code)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        // 构造 JudgeContext 上下文
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setJudgeInfo(judgeInfo);
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        // 根据不同的语言选择不同的策略
        JudgeStrategy judgeStrategy = judgeManager.doJudge(questionSubmit);
        JudgeInfo judgeInfoResponse = judgeStrategy.doJudge(judgeContext);
        // 修改数据库判题结果
        QuestionSubmit update = new QuestionSubmit();
        update.setId(questionSubmitId);
        update.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoResponse));
        update.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        // 更新状态
        boolean updateById = questionSubmitService.updateById(update);
        if(!updateById) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新判题状态失败");
        }
        // 返回最新更新后的数据
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);

        return questionSubmitResult;
    }
}

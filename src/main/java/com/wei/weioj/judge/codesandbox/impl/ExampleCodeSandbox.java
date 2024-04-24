package com.wei.weioj.judge.codesandbox.impl;

import com.wei.weioj.judge.codesandbox.CodeSandbox;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeResponse;
import com.wei.weioj.model.dto.questionsubmit.JudgeInfo;
import com.wei.weioj.model.enums.JudgeInfoMessageEnum;
import com.wei.weioj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱实现
 */
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage("测试执行成功");
        judgeInfo.setTime(1000);
        judgeInfo.setMemory(1000);

        executeCodeResponse.setJudgeInfo(judgeInfo);
        executeCodeResponse.setMessage("测试信息");
        executeCodeResponse.setStatus(JudgeInfoMessageEnum.ACCEPTED.getValue());
        executeCodeResponse.setOutputList(inputList);

        return executeCodeResponse;
    }
}

package com.wei.weioj.judge.strategy.impl;

import cn.hutool.json.JSONUtil;
import com.wei.weioj.judge.strategy.JudgeContext;
import com.wei.weioj.judge.strategy.JudgeStrategy;
import com.wei.weioj.model.dto.question.JudgeCase;
import com.wei.weioj.model.dto.question.JudgeConfig;
import com.wei.weioj.model.dto.questionsubmit.JudgeInfo;
import com.wei.weioj.model.entity.Question;
import com.wei.weioj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {

    /**
     * 执行判题
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        Question question = judgeContext.getQuestion();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        long time = judgeInfo.getTime();
        long memory = judgeInfo.getMemory();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setMemory(memory);
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;


        // 判断沙箱执行数量是否和预期数量一致
        if(outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.RUNTIME_ERROR;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        // 依次判断每一项输出和预期输出是否相
        for (int i = 0; i < outputList.size(); i++) {
            String output = outputList.get(i);
            String exceptOutput = judgeCaseList.get(i).getOutput();
            if(!output.equals(exceptOutput)) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.RUNTIME_ERROR;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }


        // 要求的判题配置
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        long needTimeLimit = judgeConfig.getTimeLimit();
        long needMemoryLimit = judgeConfig.getMemoryLimit();
        if(time > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if(memory > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}

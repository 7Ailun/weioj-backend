package com.wei.weioj.judge.strategy;

import com.wei.weioj.model.dto.question.JudgeCase;
import com.wei.weioj.model.dto.questionsubmit.JudgeInfo;
import com.wei.weioj.model.entity.Question;
import com.wei.weioj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {
    private Question question;
    private QuestionSubmit questionSubmit;
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
}

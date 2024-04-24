package com.wei.weioj.judge;

import com.wei.weioj.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}

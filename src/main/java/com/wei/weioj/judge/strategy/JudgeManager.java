package com.wei.weioj.judge.strategy;

import com.wei.weioj.judge.strategy.impl.DefaultJudgeStrategy;
import com.wei.weioj.judge.strategy.impl.JavaLanguageJudgeStrategy;
import com.wei.weioj.model.entity.QuestionSubmit;
import com.wei.weioj.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 判题管理类
 */
@Service
public class JudgeManager {
    /**
     * 根据不同的语言选择不同的策略
     * @param questionSubmit
     * @return
     */
    public JudgeStrategy doJudge(QuestionSubmit questionSubmit) {
        String language = questionSubmit.getLanguage();
        if(QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)) {
            return new JavaLanguageJudgeStrategy();
        }

        return new DefaultJudgeStrategy();
    }
}

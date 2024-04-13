package com.wei.weioj.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wei.weioj.model.dto.question.JudgeCase;
import com.wei.weioj.model.dto.question.JudgeConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class QuestionSubmitAddRequest implements Serializable {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户提交代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

package com.wei.weioj.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wei.weioj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
public class QuestionSubmitQueryRequest extends PageRequest {
    /**
     * 编程语言
     */
    private String language;


    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、 3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}

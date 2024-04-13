package com.wei.weioj.model.dto.questionsubmit;

import lombok.Data;

@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗的时间
     */
    private long time;
    /**
     * 使用的内存
     */
    private long memory;

}

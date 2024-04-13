package com.wei.weioj.model.dto.question;

import lombok.Data;

@Data
public class JudgeConfig {
    /**
     * 时间限制 ms
     */
    private long timeLimit;
    /**
     * 内存限制 kb
     */
    private long memoryLimit;
    /**
     * 栈内存 占内存
     */
    private long stackLimit;

}

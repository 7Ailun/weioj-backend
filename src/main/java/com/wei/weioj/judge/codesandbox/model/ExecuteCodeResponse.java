package com.wei.weioj.judge.codesandbox.model;

import com.wei.weioj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteCodeResponse {
    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;
    /**
     * 信息
     */
    private String message;

    /**
     * 执行状态
     */
    private String status;
    /**
     * 输出用例
     */
    private List<String> outputList;

}

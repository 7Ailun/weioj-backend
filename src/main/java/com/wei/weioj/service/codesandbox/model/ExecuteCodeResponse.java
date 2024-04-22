package com.wei.weioj.service.codesandbox.model;

import com.wei.weioj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

package com.wei.weioj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteCodeRequest {
    /**
     * 输入用例
     */
    List<String> inputList;
    /**
     * 代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;

}

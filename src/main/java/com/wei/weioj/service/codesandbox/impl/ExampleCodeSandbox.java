package com.wei.weioj.service.codesandbox.impl;

import com.wei.weioj.service.codesandbox.CodeSandbox;
import com.wei.weioj.service.codesandbox.model.ExecuteCodeRequest;
import com.wei.weioj.service.codesandbox.model.ExecuteCodeResponse;

/**
 * 示例代码沙箱实现
 */
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        return null;
    }
}

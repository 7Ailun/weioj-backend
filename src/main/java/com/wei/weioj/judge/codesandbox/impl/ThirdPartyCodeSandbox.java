package com.wei.weioj.judge.codesandbox.impl;

import com.wei.weioj.judge.codesandbox.CodeSandbox;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱实现
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}

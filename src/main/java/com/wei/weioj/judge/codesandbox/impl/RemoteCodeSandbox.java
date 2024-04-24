package com.wei.weioj.judge.codesandbox.impl;

import com.wei.weioj.judge.codesandbox.CodeSandbox;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱实现
 */
public class RemoteCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}

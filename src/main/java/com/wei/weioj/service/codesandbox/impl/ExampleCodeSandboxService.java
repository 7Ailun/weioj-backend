package com.wei.weioj.service.codesandbox.impl;

import com.wei.weioj.service.codesandbox.CodeSandboxService;
import com.wei.weioj.service.codesandbox.model.CodeSandboxRequest;
import com.wei.weioj.service.codesandbox.model.CodeSandboxResponse;

/**
 * 示例代码沙箱实现
 */
public class ExampleCodeSandboxService implements CodeSandboxService {

    @Override
    public CodeSandboxResponse executeCode(CodeSandboxRequest codeSandboxRequest) {
        System.out.println("示例代码沙箱");
        return null;
    }
}

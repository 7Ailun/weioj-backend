package com.wei.weioj.service.codesandbox;

import com.wei.weioj.service.codesandbox.model.CodeSandboxRequest;
import com.wei.weioj.service.codesandbox.model.CodeSandboxResponse;

/**
 * 代码沙箱公共接口
 */
public interface CodeSandboxService {
    /**
     * 执行目标代码
     * @param codeSandboxRequest
     * @return
     */
     CodeSandboxResponse executeCode(CodeSandboxRequest codeSandboxRequest);
}

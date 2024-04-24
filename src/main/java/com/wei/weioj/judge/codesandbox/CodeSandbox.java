package com.wei.weioj.judge.codesandbox;

import com.wei.weioj.judge.codesandbox.model.ExecuteCodeRequest;
import com.wei.weioj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱公共接口
 */
public interface CodeSandbox {
    /**
     * 执行目标代码
     * @param executeCodeRequest
     * @return
     */
     ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

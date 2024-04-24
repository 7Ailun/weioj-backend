package com.wei.weioj.judge.codesandbox;

import com.wei.weioj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.wei.weioj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.wei.weioj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 沙箱工厂
 */
public class CodeSandboxFactory {
    /**
     * 创建代码沙箱示例
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}

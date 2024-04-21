package com.wei.weioj.service.codesandbox;

import com.wei.weioj.service.codesandbox.impl.ExampleCodeSandboxService;
import com.wei.weioj.service.codesandbox.impl.RemoteCodeSandboxService;
import com.wei.weioj.service.codesandbox.impl.ThirdPartyCodeSandboxService;

/**
 * 沙箱工厂
 */
public class CodeSandboxFactory {
    public static CodeSandboxService newInstance(String type) {
        CodeSandboxService codeSandboxService = null;
        switch (type) {
            case "example":
                codeSandboxService = new ExampleCodeSandboxService();
                break;
            case "remote":
                codeSandboxService = new RemoteCodeSandboxService();
            case "thirdParty":
                codeSandboxService = new ThirdPartyCodeSandboxService();
                break;
            default:
                codeSandboxService = new ExampleCodeSandboxService();
                break;
        }
        return codeSandboxService;
    }
}

package com.wei.weioj.service.codesandbox;

import com.wei.weioj.service.codesandbox.impl.ExampleCodeSandbox;
import com.wei.weioj.service.codesandbox.impl.RemoteCodeSandbox;
import com.wei.weioj.service.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 沙箱工厂
 */
public class CodeSandboxFactory {
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

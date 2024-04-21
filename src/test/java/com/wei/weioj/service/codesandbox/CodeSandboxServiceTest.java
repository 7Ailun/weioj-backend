package com.wei.weioj.service.codesandbox;

import cn.hutool.core.lang.Assert;
import com.wei.weioj.service.codesandbox.impl.ExampleCodeSandboxService;
import com.wei.weioj.service.codesandbox.model.CodeSandboxRequest;
import com.wei.weioj.service.codesandbox.model.CodeSandboxResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class CodeSandboxServiceTest {

    @Test
    void executeCode() {
        CodeSandboxService sandboxService = new ExampleCodeSandboxService();

        new CodeSandboxRequest();
        CodeSandboxRequest codeSandboxRequest = CodeSandboxRequest.builder()
                .inputList(Arrays.asList("1 2", "2 3"))
                .code("public class Main { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }")
                .language("java")
                .build();
        CodeSandboxResponse codeSandboxResponse = sandboxService.executeCode(codeSandboxRequest);
        Assert.isNull(codeSandboxResponse);
    }
}
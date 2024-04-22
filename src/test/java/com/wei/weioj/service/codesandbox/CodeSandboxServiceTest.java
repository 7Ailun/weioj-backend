package com.wei.weioj.service.codesandbox;

import cn.hutool.core.lang.Assert;
import com.wei.weioj.service.codesandbox.impl.ExampleCodeSandbox;
import com.wei.weioj.service.codesandbox.model.ExecuteCodeRequest;
import com.wei.weioj.service.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Slf4j
@SpringBootTest
class CodeSandboxServiceTest {
    @Value("${code-sandbox.type}")
    private String type;

    @Test
    void executeCode() {
        CodeSandbox sandboxService = new ExampleCodeSandbox();

        new ExecuteCodeRequest();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(Arrays.asList("1 2", "2 3"))
                .code("public class Main { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }")
                .language("java")
                .build();
        ExecuteCodeResponse executeCodeResponse = sandboxService.executeCode(executeCodeRequest);
        Assert.isNull(executeCodeResponse);
    }
    @Test
    void sandboxFactory() {
//        String type = "remote"; // 通过配置进行获取
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        log.info(type);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(Arrays.asList("1 2", "2 3"))
                .code("public class Main { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }")
                .language("java")
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assert.isNull(executeCodeResponse);
    }
}
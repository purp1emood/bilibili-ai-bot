package com.toryz.biligpt.util;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ztr.wuning
 * @Date: 2023/8/3 17:08
 */
@Component
public class GptSdkUtil {

    @Value("${openai.token}")
    static String token;

    public static String chatForSum(String txt){
        OpenAiService service = new OpenAiService(token);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(txt)
                .maxTokens(3500)
                .model("gpt-3.5-turbo")
                .build();
        CompletionResult completion = service.createCompletion(completionRequest);
        List<CompletionChoice> chatMessage = completion.getChoices();
        return chatMessage.get(0).getText();
    }


}

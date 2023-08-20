package com.toryz.biligpt.util;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ztr.wuning
 * @Date: 2023/8/3 17:08
 */
@Component
@Slf4j
public class GptSdkUtil {

    @Value("${openAI.token}")
    String token;

    public String chatForSum(StringBuilder txt){
        OpenAiService service = new OpenAiService(token, Duration.ofSeconds(40));
        List<ChatMessage> messages = new ArrayList<>();

        List<String> splitContent = splitContent(txt);
        messages.add(new ChatMessage(ChatMessageRole.USER.value(),"现在你是一个视频内容编辑，请用专业但不失俏皮的语言，总结这个视频的内容,总字数控制在400字左右。视频的字幕内容如下"));
        //messages.add(new ChatMessage(ChatMessageRole.USER.value(),"随便背一首李白的诗吧"));
        for(String s: splitContent){
            ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(),s);
            messages.add(userMessage);
        }
        for (ChatMessage s: messages){
            log.info("messages:{}",s.getContent());
        }
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .maxTokens(5000)
                .model("gpt-3.5-turbo-16k-0613")
                .build();

        //CompletionResult completion = service.createCompletion(completionRequest);
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        String s = responseMessage.getContent();
        log.info("responseMessage:{}",s);
        return s;
    }

    private List<String> splitContent(StringBuilder txt){
        List<String> resultList = new ArrayList<>();
        int length = txt.length();
        int startIndex = 0;

        while (startIndex < length) {
            int endIndex = Math.min(startIndex + 500, length);
            String substring = txt.substring(startIndex, endIndex);
            resultList.add(substring);

            startIndex = endIndex;
        }
        return resultList;
    }
}

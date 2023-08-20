package com.toryz.biligpt.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.toryz.biligpt.service.SummaryService;
import com.toryz.biligpt.util.BiliSdkUtil;
import com.toryz.biligpt.util.GptSdkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ztr.wuning
 * @Date: 2023/7/30 20:29
 */
@Slf4j
@Service
public class SummaryServiceImpl implements SummaryService {

    @Value("${openAI.token}")
    String token;
    @Autowired
    GptSdkUtil gptSdkUtil;

    @Override
    public String getGptSummary(String bvid) {
        List<String> cidList = BiliSdkUtil.getPartCidList(bvid);
        for(String s : cidList){
            log.info("cidList: {}",s);
        }
        List<String> summaryList = null;

        for(String cid: cidList){
            List<String> subtitleUrlList = BiliSdkUtil.getSubtitleUrlList(bvid, cid);
            for(String subtitleUrl: subtitleUrlList){
                log.info("subtitleUrl: {}",subtitleUrl);
            }
            for(String subtitleUrl: subtitleUrlList){
                List<String> contentList = BiliSdkUtil.parseSubtitle(subtitleUrl);
                for(String content: contentList){
                    log.info("content: {}",content);
                }
                for(String content: contentList){
                    summaryList.add(gptSdkUtil.chatForSum(content));
                }
            }
        }
        return summaryList.get(0);
    }

    @Override
    public String getGptModel(){
        System.out.println(token);
        OpenAiService service = new OpenAiService(token);
        System.out.println(service.listModels());
        return service.listModels().toString();
    }

    @Override
    public String getGptSummary() {
       // String s = GptSdkUtil.chatForSum("给我背一遍《静夜思》");
        OpenAiService service = new OpenAiService(token);
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(),"给我背一遍《静夜思》");
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .maxTokens(3500)
                .model("gpt-3.5-turbo")
                .build();

        //CompletionResult completion = service.createCompletion(completionRequest);
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        return responseMessage.getContent();
    }
}

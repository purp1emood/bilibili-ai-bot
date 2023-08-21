package com.toryz.biligpt.service.impl;

import com.alibaba.fastjson.JSON;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.toryz.biligpt.entity.response.GetGptSummaryResponse;
import com.toryz.biligpt.service.SummaryService;
import com.toryz.biligpt.util.BiliSdkUtil;
import com.toryz.biligpt.util.GptSdkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        log.info("bvid: {}",bvid);
        GetGptSummaryResponse getGptSummaryResponse = new GetGptSummaryResponse();

        List<String> cidList = BiliSdkUtil.getPartCidList(bvid);
        for(String s : cidList){
            log.info("cidList: {}",s);
        }

        StringBuilder AllContent = new StringBuilder();

        for(String cid: cidList){
            List<String> subtitleUrlList = BiliSdkUtil.getSubtitleUrlList(bvid, cid);
            for(String subtitleUrl: subtitleUrlList){
                log.info("subtitleUrl: {}",subtitleUrl);
            }
            if(subtitleUrlList.size() == 0){

                getGptSummaryResponse.setMessage("暂不支持该视频的总结功能捏，感谢支持，小乌龟正在升级中...");
                getGptSummaryResponse.setCode(201);
                return JSON.toJSONString(getGptSummaryResponse);
            }
            for(String subtitleUrl: subtitleUrlList){
                List<String> contentList = BiliSdkUtil.parseSubtitle(subtitleUrl);
                for(String content: contentList){
                    log.info("content: {}",content);
                }
                for(String content: contentList){
                    AllContent.append(content);
                }
            }
        }
        String s = null;
        try{
            s = gptSdkUtil.chatForSum(AllContent);
        }catch (Exception e){
            log.info("gpt连接超时...");
            getGptSummaryResponse.setMessage("gpt连接超时...");
            getGptSummaryResponse.setCode(400);
        }

        if(s != null){
            getGptSummaryResponse.setMessage(s);
            getGptSummaryResponse.setCode(200);
        }
        return JSON.toJSONString(getGptSummaryResponse);
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
        OpenAiService service = new OpenAiService(token, Duration.ofSeconds(90));
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(),"随便背一首李白的诗吧");
                messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .maxTokens(500)
                .model("gpt-3.5-turbo-16k-0613")
                .build();

        //CompletionResult completion = service.createCompletion(completionRequest);
        ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        GetGptSummaryResponse getGptSummaryResponse = new GetGptSummaryResponse();
        getGptSummaryResponse.setMessage(responseMessage.getContent());
        getGptSummaryResponse.setCode(200);
        return JSON.toJSONString(getGptSummaryResponse);
    }
}

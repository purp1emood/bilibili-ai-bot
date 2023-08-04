package com.toryz.biligpt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.theokanning.openai.service.OpenAiService;
import com.toryz.biligpt.entity.response.GetGptSummaryResponse;
import com.toryz.biligpt.service.SummaryService;
import com.toryz.biligpt.util.BiliSdkUtil;
import com.toryz.biligpt.util.GptSdkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @Author: ztr.wuning
 * @Date: 2023/7/30 20:29
 */
@Service
public class SummaryServiceImpl implements SummaryService {

    @Value("${openai.token}")
    String token;

    @Override
    public GetGptSummaryResponse getGptSummary(String bvid) {
        List<String> cidList = BiliSdkUtil.getPartCidList(bvid);
        List<String> summaryList = null;
        for(String cid: cidList){
            List<String> subtitleUrlList = BiliSdkUtil.getSubtitleUrlList(bvid, cid);
            for(String subtitleUrl: subtitleUrlList){
                List<String> contentList = BiliSdkUtil.parseSubtitle(subtitleUrl);
                for(String content: contentList){
                    summaryList.add(GptSdkUtil.chatForSum(content));
                }
            }
        }


        return null;
    }

    @Override
    public String getGptModel(){
        OpenAiService service = new OpenAiService(token);
        System.out.println(service.listModels());
        return service.listModels().toString();
    }

}

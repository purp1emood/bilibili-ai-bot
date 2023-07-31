package com.toryz.biligpt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toryz.biligpt.entity.response.GetGptSummaryResponse;
import com.toryz.biligpt.service.SummaryService;
import com.toryz.biligpt.util.BiliSdkUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public GetGptSummaryResponse getGptSummary(String bvid) {
        List<String> cidList = BiliSdkUtil.getPartCidList(bvid);
        for(String cid: cidList){
            List<String> subtitleUrlList = BiliSdkUtil.getSubtitleUrlList(bvid, cid);
            for(String subtitleUrl: subtitleUrlList){
                List<String> contentList = BiliSdkUtil.parseSubtitle(subtitleUrl);


            }
        }





        return null;
    }


}

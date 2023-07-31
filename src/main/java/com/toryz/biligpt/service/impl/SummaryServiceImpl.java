package com.toryz.biligpt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toryz.biligpt.entity.response.GetGptSummaryResponse;
import com.toryz.biligpt.service.SummaryService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author: ztr.wuning
 * @Date: 2023/7/30 20:29
 */
@Service
public class SummaryServiceImpl implements SummaryService {

    @Override
    public GetGptSummaryResponse getGptSummary(String bvid) {
        String bv = "BV1ZB4y1G7fd"; // bv号，可以替换成任意bv号
        String url = "https://api.bilibili.com/x/web-interface/view?bvid=" + bv;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObj = JSON.parseObject(response.toString()).getJSONObject("data");
            // 视频标题
            String title = jsonObj.getString("title");
            // 视频描述
            String desc = jsonObj.getString("desc");
            // 视频时长（秒）
            int duration = jsonObj.getInteger("duration");
            // 观看次数
            int view = jsonObj.getJSONObject("stat").getInteger("view");
            // 点赞数
            int like = jsonObj.getJSONObject("stat").getInteger("like");
            System.out.println("标题：" + title);
            System.out.println("描述：" + desc);
            System.out.println("时长：" + duration + "秒");
            System.out.println("观看次数：" + view);
            System.out.println("点赞数：" + like);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

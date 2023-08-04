package com.toryz.biligpt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ztr.wuning
 * @Date: 2023/7/30 21:30
 */
@Component
@Slf4j
public class BiliSdkUtil {

    public static String getCid(String bv) {
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
            String cid = jsonObj.getString("cid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getPartCidList(String bv){
        String url = "https://api.bilibili.com/x/player/pagelist?bvid=" + bv;
        List<String> cidList = new ArrayList<>();
        try{
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
            JSONArray jsonArray = JSON.parseObject(response.toString()).getJSONArray("data");
            for(Object data : jsonArray){
                JSONObject jsondata = (JSONObject) JSON.toJSON(data);
                cidList.add(jsondata.getString("cid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cidList;
    }

    public static List<String> getSubtitleUrlList(String bv, String cid){
        String url = "https://api.bilibili.com/x/player/v2?bvid=" + bv + "&cid=" + cid;
        List<String> subtitlesList = new ArrayList<>();
        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            setHeader(con);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONArray subtitlesArray = JSON.parseObject(response.toString()).getJSONObject("data").getJSONObject("subtitle").getJSONArray("subtitles");
            for(Object subtitles : subtitlesArray){
                JSONObject subtitlesObj = (JSONObject) JSON.toJSON(subtitles);
                subtitlesList.add(subtitlesObj.getString("subtitle_url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subtitlesList;
    }

    public static List<String> parseSubtitle(String url) {
        String subtitle = "https:" + url;
        List<String> contentList = new ArrayList<>();
        try {
            URL obj = new URL(subtitle);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            setHeader(con);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONArray body = JSON.parseObject(response.toString()).getJSONArray("body");
            List<String> subtitleList = new ArrayList<>();
            for (Object data : body) {
                JSONObject jsondata = (JSONObject) JSON.toJSON(data);
                subtitleList.add(jsondata.getString("content"));
            }
            StringBuilder concatenatedText = new StringBuilder();
            for (String content : subtitleList) {
                concatenatedText.append(content+" ");
                if(concatenatedText.length() > 3000){
                    contentList.add(concatenatedText.toString());
                    concatenatedText = new StringBuilder();
                }
            }
            contentList.add(concatenatedText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentList;
    }



    private static void setHeader(HttpURLConnection con) throws ProtocolException {
        con.setRequestMethod("GET");
        con.setRequestProperty("authority", "api.bilibili.com");
        con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        con.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        con.setRequestProperty("origin", "https://www.bilibili.com");
        con.setRequestProperty("referer", "https://www.bilibili.com/");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Mobile Safari/537.36");
        con.setRequestProperty("cookie","buvid3=69AB4D84-0360-4E40-9445-C3D28DDF280634778infoc; LIVE_BUVID=AUTO7616228919520923; CURRENT_BLACKGAP=0; blackside_state=0; buvid_fp_plain=undefined; DedeUserID=274844512; DedeUserID__ckMd5=7e000fc6ab1616d2; go_old_video=-1; b_nut=100; buvid4=161D9AE5-9340-0F56-93C2-81D416BE265952261-022012418-VdL1Gs%2F2d6pQ5pEiC51oBg%3D%3D; hit-new-style-dyn=0; i-wanna-go-feeds=-1; rpdid=|(J~RY|lYmRm0J'uYY)l~mY)u; is-2022-channel=1; nostalgia_conf=-1; CURRENT_PID=631c9a50-ca40-11ed-86e5-ad24e64584b4; i-wanna-go-back=-1; header_theme_version=CLOSE; hit-dyn-v2=1; FEED_LIVE_VERSION=V8; _uuid=14A108F101-B2C3-E1EE-263C-F831011B5EA6479883infoc; bp_article_offset_274844512=817068076077940700; fingerprint=b112a405d4b6a7747db434f5d87c2bd8; PVID=1; CURRENT_FNVAL=4048; CURRENT_QUALITY=80; SESSDATA=e4fa35e2%2C1706328160%2C36a0d%2A71RTBX33xttErojT7AMq8p9hi0a8jfb-JzQIZ8iFQLEkJAv0Ykt6EsMvsOVHWgzErNQJl_nQAADAA; bili_jct=ab873be542c185d7e43631de4d58686b; sid=7meg3r3u; bp_video_offset_274844512=824403588161732633; b_lsid=F798F69C_189AAC26026; buvid_fp=69AB4D84-0360-4E40-9445-C3D28DDF280634778infoc");

    }


}

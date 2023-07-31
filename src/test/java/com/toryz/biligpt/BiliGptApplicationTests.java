package com.toryz.biligpt;

import com.toryz.biligpt.service.impl.SummaryServiceImpl;
import com.toryz.biligpt.util.BiliSdkUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BiliGptApplicationTests {

    @Autowired
    SummaryServiceImpl summaryService;
    @Autowired
    BiliSdkUtil biliSdkUtil;

    @Test
    void contextLoads() {
        summaryService.getGptSummary("BV1QK4y1C7ZB");
    }

    @Test
    void testGetCid() {
        List<String> partList = biliSdkUtil.getPartCidList("BV12h4y1L7gX");
        for (String s : partList) {
            System.out.println(s);
        }
    }

    @Test
    void testGetSubtitle() {
        List<String> subtitle = biliSdkUtil.getSubtitleUrlList("BV12h4y1L7gX", "1152551551");
        for (String s : subtitle) {
            System.out.println(s);
        }
    }

    @Test
    void testParseSubtitle() {
        biliSdkUtil.parseSubtitle("//aisubtitle.hdslb.com/bfs/ai_subtitle/prod/359085168121618675019e8292075781017582af6dc483231e6?auth_key=1690812170-93ea77f900b64f2c806b076344122969-0-6243f351e7e99bab1699f5c5f425d4f6");

    }
}

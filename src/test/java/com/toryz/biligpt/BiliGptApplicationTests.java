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

//    @Test
//    void contextLoads() {
//        summaryService.getGptSummary("BV1QK4y1C7ZB");
//    }
//
//    @Test
//    void testGetCid() {
//        List<String> partList = biliSdkUtil.getPartCidList("BV1oX4y1x7Ho");
//        for (String s : partList) {
//            System.out.println(s);
//        }
//    }
//
//    @Test
//    void testGetSubtitle() {
//        List<String> subtitle = biliSdkUtil.getSubtitleUrlList("BV1oX4y1x7Ho", "1239238098");
//        for (String s : subtitle) {
//            System.out.println(s);
//            System.out.println("-----");
//        }
//        System.out.println("-----");
//    }
//
//    @Test
//    void testParseSubtitle() {
//        //biliSdkUtil.parseSubtitle("//aisubtitle.hdslb.com/bfs/ai_subtitle/prod/40197385111525515512ed6627aab493fa3c02fbbd3c9657627?auth_key=1690887920-07441b0fa2d04fd0b06ddfbf7cdf21cf-0-95c47ff78af56528a5121da9374ff987\n");
//
//    }
//
//    @Test
//    void testOpenAi(){
//        //OpenAiService service = new OpenAiService("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");
//        //System.out.println(service.listModels());
//    }
}

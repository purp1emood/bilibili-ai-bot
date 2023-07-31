package com.toryz.biligpt;

import com.toryz.biligpt.service.impl.SummaryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BiliGptApplicationTests {

    @Autowired
    SummaryServiceImpl summaryService;

    @Test
    void contextLoads() {
        summaryService.getGptSummary("BV1QK4y1C7ZB");
    }

}

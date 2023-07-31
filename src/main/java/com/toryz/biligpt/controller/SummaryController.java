package com.toryz.biligpt.controller;

import com.toryz.biligpt.entity.response.GetGptSummaryResponse;
import com.toryz.biligpt.service.impl.SummaryServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ztr.wuning
 * @Date: 2023/7/30 20:08
 */
@RestController
@RequestMapping("/summary")
public class SummaryController {
    final SummaryServiceImpl summaryService;

    public SummaryController(SummaryServiceImpl summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/gpt/{bvid}")
    public GetGptSummaryResponse getGptSummary(String bvid) {

        return getGptSummary(bvid);
    }
}

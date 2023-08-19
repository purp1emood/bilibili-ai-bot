package com.toryz.biligpt.controller;

import com.toryz.biligpt.entity.response.GetGptSummaryResponse;
import com.toryz.biligpt.service.impl.SummaryServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ztr.wuning
 * @Date: 2023/7/30 20:08
 */
@RestController
@RequestMapping("/gpt")
public class SummaryController {
    final SummaryServiceImpl summaryService;

    public SummaryController(SummaryServiceImpl summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/{bvid}")
    public String getGptSummary(@PathVariable String bvid) {

        return summaryService.getGptSummary(bvid);
    }

    @GetMapping("/model")
    public String getGptModel() {
        return summaryService.getGptModel();
    }

    @GetMapping("/summary")
    public String getGptSummary() {
        return summaryService.getGptSummary();
    }
}

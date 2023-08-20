package com.toryz.biligpt.controller;

import com.google.common.hash.BloomFilter;
import com.toryz.biligpt.constant.BloomFilterConstant;
import com.toryz.biligpt.service.impl.SummaryServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: ztr.wuning
 * @Date: 2023/7/30 20:08
 */
@RestController
@RequestMapping("/gpt")
public class SummaryController {
    final SummaryServiceImpl summaryService;

    @Resource
    @Qualifier(BloomFilterConstant.NAME_BV)
    BloomFilter<String> bloomFilter;

    public SummaryController(SummaryServiceImpl summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/{bvid}")
    public String getGptSummary(@PathVariable String bvid) {
        if (bloomFilter.mightContain(bvid)) {
            return "该视频已有回答";
        } else {
            bloomFilter.put(bvid);
        }
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

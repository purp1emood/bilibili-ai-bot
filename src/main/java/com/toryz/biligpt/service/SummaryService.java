package com.toryz.biligpt.service;

import com.toryz.biligpt.entity.response.GetGptSummaryResponse;
import org.springframework.stereotype.Service;

/**
 * @Author: ztr.wuning
 * @Date: 2023/7/30 20:28
 */

public interface SummaryService {
    GetGptSummaryResponse getGptSummary(String bvid);
}

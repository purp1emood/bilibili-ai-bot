package com.toryz.biligpt.config;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.toryz.biligpt.constant.BloomFilterConstant;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

@EnableCaching
@Configuration
public class BvConfig {

    @Bean(BloomFilterConstant.NAME_BV)
    public BloomFilter<String> bvBloomFilter(){
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),
                BloomFilterConstant.EXPECTED_INSERTIONS_BV, BloomFilterConstant.FALSE_POSITIVERATE_BV);
        return bloomFilter;
    }
}

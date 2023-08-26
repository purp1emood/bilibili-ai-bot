package com.toryz.biligpt.config;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.toryz.biligpt.constant.BloomFilterConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
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

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}

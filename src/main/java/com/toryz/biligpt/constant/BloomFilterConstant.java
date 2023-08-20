package com.toryz.biligpt.constant;

public interface BloomFilterConstant {
    String NAME_BV = "bvBloomFilter";
    // 预期数据量
    Long EXPECTED_INSERTIONS_BV = 100_000L;
    // 误判率
    Double FALSE_POSITIVERATE_BV = 0.01;
}

package com.toryz.biligpt;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.toryz.biligpt.constant.BloomFilterConstant;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

public class BloomTests {

    @Test
    void testBloom() {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()),
                BloomFilterConstant.EXPECTED_INSERTIONS_BV, BloomFilterConstant.FALSE_POSITIVERATE_BV);
        bloomFilter.put("BV123");
        bloomFilter.put("BV1234");
        System.out.println(bloomFilter.mightContain("bv123"));
        System.out.println(bloomFilter.mightContain("BV123"));
    }
}

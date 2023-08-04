package com.toryz.biligpt;

import com.toryz.biligpt.util.BloomFilter;
import org.junit.jupiter.api.Test;

public class BloomTests {

    @Test
    void testBloom() {
        BloomFilter filter=new BloomFilter();
        filter.addValue("1234");
        System.out.println(filter.existsValue("1234"));
        System.out.println(filter.existsValue("12341"));
    }
}

package com.toryz.biligpt.util;

import java.util.BitSet;


/**
 * 布隆过滤器
 */
public class BloomFilter {

    /**
     * 构建hash函数的关键字，总共7个
     */
    private static final int[] HashSeeds = new int[]{3, 5, 7, 11, 13, 17, 19};

    /**
     * Hash工具类的数组
     */
    private static final BloomHash[] HashList = new BloomHash[HashSeeds.length];

    /**
     * BloomFilter的长度，最好为插入数量的10倍，目前为2的20次方，大约100万个
     */
    private static final int BloomLength = 1 << 20;

    /**
     * 对位的操作类，java自带的BitSet，共BloomLength个bit
     */
    private final BitSet bitSet = new BitSet(BloomLength);


    public BloomFilter() {
        //初始化Hash工具类的数组,每个hash工具类的hash函数都不同
        for (int i = 0; i < HashSeeds.length; i++) {
            HashList[i] = new BloomHash(BloomLength, HashSeeds[i]);
        }
    }

    /**
     * 在布隆过滤器中加入值value，在多个hash函数生成的hashcode对应的位置上，置1
     */
    public void addValue(String value) {
        for (int i = 0; i < HashSeeds.length; i++) {
            //根据对应的hash函数得到hashcode
            int hashcode = HashList[i].hashCode(value);
            //在位图中，将对应的位，设置为1
            bitSet.set(hashcode);
        }
    }

    /**
     * 在布隆过滤器中,检验是否可能有值value
     * @return 如果返回false，则一定没有BV 如果返回true，就代表有可能有
     */
    public boolean existsValue(String value) {
        boolean result = true;
        for (int i = 0; i < HashSeeds.length; i++) {
            //根据对应的hash函数得到hashcode
            int hashcode = HashList[i].hashCode(value);
            //将result与对应位置上的0或1 做与运算
            //如果全为1，则result最后为1
            //如果有一个位置上为0，则最后result为0
            result = result & bitSet.get(hashcode);
        }
        return result;

    }


}
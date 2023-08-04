package com.toryz.biligpt.util;

/**
 * BloomFilter的Hash工具类，用来对对应的value生成Hashcode
 */
public class BloomHash {

    /**
     * Hash工具类返回的hashcode的最大长度<br>
     * maxLength为2的n次方，返回的hashcode为[0,2^n-1]
     */
    public int maxLength;

    /**
     * Hash函数生成哈希码的关键字
     */
    public int seed;

    public BloomHash(int maxLength, int seed) {
        this.maxLength = maxLength;
        this.seed = seed;
    }

    /**
     * 返回字符串string的hashcode，大小为[0,maxLength-1]
     */
    public int hashCode(String string){
        int result=0;
        //这个构建hashcode的方式类似于java的string的hashcode方法
        //只是我这里是可以设置的seed，它那里是31
        for(int i=0;i<string.length();i++){
            result=result+seed*string.charAt(i);
        }
        //maxLength-1=111111,相当于result mod (maxLength-1)
        //保证结果在[0,maxLength-1]
        return (maxLength-1)&result;
    }



}
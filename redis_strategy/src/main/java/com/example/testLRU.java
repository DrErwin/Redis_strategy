/*
 * @Author: Yuhong Wu
 * @Date: 2024-10-01 12:45:20
 * @LastEditors: Yuhong Wu
 * @LastEditTime: 2024-10-01 14:03:17
 * @Description: 
 */
package com.example;

import org.apache.lucene.util.RamUsageEstimator;


public class testLRU {
    
    public static void main(String[] args) {
        LRU<String, String> LruInstance = new LRU<>(5000);
        System.out.println("map init value is " + RamUsageEstimator.sizeOf("123"));
        LruInstance.put("1", "A");
        System.out.println(LruInstance);
        LruInstance.put("2", "B");
        System.out.println(LruInstance);


    }
    
}

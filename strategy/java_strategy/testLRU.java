package java_strategy;
/*
 * @Author: Yuhong Wu
 * @Date: 2024-10-01 12:45:20
 * @LastEditors: Yuhong Wu
 * @LastEditTime: 2024-10-01 13:05:21
 * @Description: 
 */

import java_strategy.LRUCache;


public class testLRU {
    
    public static void main(String[] args) {
        System.setProperty("java.vm.name","Java HotSpot(TM) ");
        LRUCache<String, String> LruInstance = new LRUCache<>(2);
        // System.out.println(ObjectSizeCalculator.getObjectSize(LruInstance));
        LruInstance.put("1", "A");

        LruInstance.put("2", "B");

        System.out.println(LruInstance);

    }
    
}

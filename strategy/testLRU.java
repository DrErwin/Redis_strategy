/*
 * @Author: Yuhong Wu
 * @Date: 2024-10-01 12:45:20
 * @LastEditors: Yuhong Wu
 * @LastEditTime: 2024-10-01 13:05:21
 * @Description: 
 */

import strategy.LRU;


public class testLRU {
    
    public static void main(String[] args) {
        System.setProperty("java.vm.name","Java HotSpot(TM) ");
        LRU<String, String> LruInstance = new LRU<>(2);
        System.out.println(ObjectSizeCalculator.getObjectSize(LruInstance));
        LruInstance.put("1", "A");
        System.out.println(ObjectSizeFetcher.getObjectSize(LruInstance));
        LruInstance.put("2", "B");
        System.out.println(ObjectSizeFetcher.getObjectSize(LruInstance));
        System.out.println(LruInstance);

    }
    
}

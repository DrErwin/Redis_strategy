/*
 * @Author: Yuhong Wu
 * @Date: 2024-10-03 01:41:35
 * @LastEditors: Yuhong Wu
 * @LastEditTime: 2024-10-04 00:45:57
 * @Description: 
 */
package java_strategy;

import java.util.Arrays;

public class LFUCacheTest {
    private static int SIZE_EACH_NODE = 112;

    public static void main(String[] args) {
        DataReader dataReader = new DataReader("data/local_visit.txt");
        int[] storage = {256*1024, 512*1024, 1024*1024, 2*1024*1024,
                            4*1024*1024, 8*1024*1024, 16*1024*1024};
        int[] capacities = new int[storage.length];
        for (int i=0 ; i < storage.length; i++){
            capacities[i] = storage[i] / LFUCacheTest.SIZE_EACH_NODE;
        }

        // int[] capacities = {50,100,200,400,800,1600,3200,6400};

        System.out.println(Arrays.toString(capacities));

        for (int capacity : capacities) {
            LFUCacheSimulator simulator = new LFUCacheSimulator(capacity);
            double curTime = System.currentTimeMillis();
            simulator.accessData(dataReader.getData());
            System.out.println("Capacity: " + capacity + ", Hit Rate: " + simulator.getHitRate());
            System.out.println("APS: "+1000000/((System.currentTimeMillis()-curTime)));
        }
    }


        

}

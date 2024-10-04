/*
 * @Author: Yuhong Wu
 * @Date: 2024-10-03 01:41:35
 * @LastEditors: Yuhong Wu
 * @LastEditTime: 2024-10-04 00:45:57
 * @Description: 
 */
package java_strategy;

public class LRUCacheTest {

    public static void main(String[] args) {
        DataReader dataReader = new DataReader("data/cyclic_visit.txt");
        int[] capacities = {5, 10, 15};

        for (int capacity : capacities) {
            LRUCacheSimulator simulator = new LRUCacheSimulator(capacity);
            simulator.accessData(dataReader.getData());
            System.out.println("Capacity: " + capacity + ", Hit Rate: " + simulator.getHitRate());
        }
    }


        

}

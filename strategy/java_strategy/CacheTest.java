package java_strategy;

import org.apache.commons.csv.*;

public class CacheTest {
    public static void main(String[] args) {
        int[] keys = {1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5};
        String[] strKeys = new String[keys.length];
        for(int i = 0; i<keys.length; i++){
            strKeys[i] = Integer.toString(keys[i]);
        }
        int[] capacities = {5, 10, 15};

        for (int capacity : capacities) {
            LRUCacheSimulator simulator = new LRUCacheSimulator(capacity);
            simulator.accessData(strKeys);
            System.out.println("Capacity: " + capacity + ", Hit Rate: " + simulator.getHitRate());
        }
    }
        

}

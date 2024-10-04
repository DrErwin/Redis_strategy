package java_strategy;

public class LRUCacheSimulator {
    private LRUCache<String, String> cache;
    private int hitCount;
    private int missCount;

    public LRUCacheSimulator(int capacity) {
        cache = new LRUCache<>(capacity);
        hitCount = 0;
        missCount = 0;
    }

    public void accessData(String[] keys) {
        for (String key : keys) {
            if (cache.get(key) != null) {
                // System.out.println("Hit: " + key);
                hitCount++;
            } else {
                // System.out.println("Miss: " + key);
                missCount++;
                cache.put(key, "Value-" + key);
            }
        }
    }

    public double getHitRate() {
        return (double) hitCount / (hitCount + missCount);
    }
}
package java_strategy;

public class LTUCacheSimulator {
    private LTUCache<String, String> cache;
    public int hitCount;
    public int missCount;

    public LTUCacheSimulator(int capacity) {
        cache = new LTUCache<>(capacity);
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
                cache.put(key, key);
            }
        }
    }

    public double getHitRate() {
        return (double) hitCount / (hitCount + missCount);
    }

    // public void clearData(){
    //     hitCount = 0;
    //     missCount = 0;
    //     this.cache.clear();
    // }
}
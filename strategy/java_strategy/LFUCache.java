package java_strategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.PriorityQueue;

public class LFUCache<K, V> {

  Map<K, Node> cache;
  Queue<Node> queue;
  int capacity;
  int size;
  int idx = 0;

  public LFUCache(int capacity) {
      cache = new HashMap<>(capacity);
      if (capacity > 0) {
          queue = new PriorityQueue<>(capacity);
      }
      this.capacity = capacity;
  }
  
  public V get(K key) {
      Node node = cache.get(key);
      if (node == null) {
          return null;
      }
      node.freq++;
      node.idx = idx++;
      queue.remove(node);
      queue.offer(node);
      return node.value;

  }
  
  public void put(K key, V value) {
      if (capacity == 0) {
          return;
      }
      Node node = cache.get(key);
      if (node != null) {
          node.value = value;
          node.freq++;
          node.idx = idx++;
          queue.remove(node);
          queue.offer(node);
      } else {
          if (size == capacity) {
              cache.remove(queue.peek().key);
              queue.poll();
              size--;
          } 
          Node newNode = new Node(key, value, idx++);
          cache.put(key, newNode);
          queue.offer(newNode);
          size++;
      }
  }

  class Node implements Comparable<Node> {
    K key;
    V value;
    int freq;
    int idx;
  
    public Node() {}
  
    public Node(K key, V value, int idx) {
        this.key = key;
        this.value = value;
        freq = 1;
        this.idx = idx;
    }
  
    public int compareTo(Node node) {
    int diff = freq - node.freq;
        return diff != 0? diff: idx - node.idx;
    }
  }
}


// public class LFUCache<k, v> {
//     private final int capcity;
   
//     private Map<k, v> cache = new HashMap<>();
   
//     private Map<k, HitRate> count = new HashMap<>();
   
//     public LFUCache(int capcity) {
//       this.capcity = capcity;
//     }
   
//     public void put(k key, v value) {
//       v v = cache.get(key);
//       if (v == null) {
//         if (cache.size() == capcity) {
//           removeElement();
//         }
//         count.put(key, new HitRate(key, 1, System.nanoTime()));
//       } else {
//         addHitCount(key);
//       }
//       cache.put(key, value);
//     }
   
//     public v get(k key) {
//       v value = cache.get(key);
//       if (value != null) {
//         addHitCount(key);
//         return value;
//       }
//       return null;
//     }

//     public void clear(){
//       this.cache.clear();
//       this.count.clear();
//     }
   
//     //移除元素
//     private void removeElement() {
//       HitRate hr = Collections.min(count.values());
//       cache.remove(hr.key);
//       count.remove(hr.key);
//     }
   
//     //更新访问元素状态
//     private void addHitCount(k key) {
//       HitRate hitRate = count.get(key);
//       hitRate.hitCount = hitRate.hitCount + 1;
//       hitRate.lastTime = System.nanoTime();
//     }
   
//     //内部类
//     class HitRate implements Comparable<HitRate> {
//       private k key;
//       private int hitCount;
//       private long lastTime;
   
//       private HitRate(k key, int hitCount, long lastTime) {
//         this.key = key;
//         this.hitCount = hitCount;
//         this.lastTime = lastTime;
//       }
   
//       @Override
//       public int compareTo(HitRate o) {
//         int compare = Integer.compare(this.hitCount, o.hitCount);
//         return compare == 0 ? Long.compare(this.lastTime, o.lastTime) : compare;
//       }
//     }
//   }
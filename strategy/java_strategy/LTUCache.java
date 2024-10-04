package java_strategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.arch.Processor;

import KMeans.MyMethod.KMeans;
import java.io.*;


import java.util.PriorityQueue;

public class LTUCache<K, V> {

  Map<K, Node> cache;
  Queue<Node> queue;
  int capacity;
  int size;
  int idx = 0;
  ExponentialDecayCalculator decayCalculator = new ExponentialDecayCalculator(0.2);
  KMeans kMeans;

  public LTUCache(int capacity) {
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
      node.freq = decayCalculator.calculate(node.freq, node.lastTime) + 1;
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
          node.freq = decayCalculator.calculate(node.freq, node.lastTime) + 1;
          node.idx = idx++;
          queue.remove(node);
          queue.offer(node);
      } else {
            if (size == capacity) {
                // 对Node的温度进行Kmeans聚类，在cache和queue中删除小的一类，调整size
                int removeAmount = 500;

                // double sum = 0;
                // int count = 0;
                // for(Node cur : cache.values()){
                //     sum += cur.freq;
                // }
                // sum /= cache.size();
                // for(Node cur : cache.values()){
                //     count = cur.freq > sum ? count+1 : count;
                // }
                // int removeAmount = count;

                // writeData();
                // System.out.println(removeAmount);
                // System.out.println("************");
                for(int i = 0; i < removeAmount; i++){
                    cache.remove(queue.peek().key);
                    queue.poll();
                    size--;
                }
            } 
            Node newNode = new Node(key, value, idx++);
            cache.put(key, newNode);
            queue.offer(newNode);
            size++;
      }
  }

  private int getKMeansTemp(){
    kMeans = new KMeans(2);
    for(Node cur : cache.values()){
        kMeans.addData(cur.freq);
    }
    kMeans.initial();
    return kMeans.doKMeans();
  }

  private void writeData(){
    try{
        FileWriter writer = new FileWriter("data.txt");
        BufferedWriter bw = new BufferedWriter(writer);
        for(Node cur : cache.values()){
            bw.write(cur.freq+"\n");
        }
        bw.close();
        writer.close();
    }catch(IOException e){
        e.printStackTrace();
    }
  }

  private int getKMeansResult(){
    Process proc;
    try{
        String[] command = {"python", "python_files/kmeans.py"};
        proc = Runtime.getRuntime().exec(command);
        InputStream is = proc.getInputStream();
        BufferedReader d = new BufferedReader(new InputStreamReader(is));
        String str = d.readLine();
        proc.waitFor();
        // System.out.println(str);
        return Integer.parseInt(str);
    }
    catch(IOException e){
        e.printStackTrace();
    }
    catch(InterruptedException e){
        e.printStackTrace();
    }
    return 0;
  }

  class Node implements Comparable<Node> {
    K key;
    V value;
    double freq;
    int idx;
    long lastTime;
  
    public Node(K key, V value, int idx) {
        this.key = key;
        this.value = value;
        freq = 1;
        this.idx = idx;
        this.lastTime = System.currentTimeMillis();
    }
  
    public int compareTo(Node node) {
    int diff = Double.compare(this.freq, node.freq);
        return diff != 0 ? diff: idx - node.idx;
    }
  }
}


// public class LTUCache<k, v> {
//     private final int capcity;
   
//     private Map<k, v> cache = new HashMap<>();
   
//     private Map<k, HitRate> count = new HashMap<>();

//     private ExponentialDecayCalculator decayCalculator = new ExponentialDecayCalculator(0.2);
   
//     public LTUCache(int capcity) {
//       this.capcity = capcity;
//     }
   
//     public void put(k key, v value) {
//       v v = cache.get(key);
//       if (v == null) {
//         if (cache.size() == capcity) {
//           removeElement();
//         }
//         count.put(key, new HitRate(key, 1, System.currentTimeMillis()));
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
//       hitRate.hitTemperture = decayCalculator.calculate(hitRate.hitTemperture, hitRate.lastTime)  + 1;
//       hitRate.lastTime = System.currentTimeMillis();
//     }
   
//     //内部类
//     class HitRate implements Comparable<HitRate> {
//       private k key;
//       private double hitTemperture;
//       private long lastTime;
   
//       private HitRate(k key, int hitTemperture, long lastTime) {
//         this.key = key;
//         this.hitTemperture = hitTemperture;
//         this.lastTime = lastTime;
//       }
   
//       @Override
//       public int compareTo(HitRate o) {
//         int compare = Double.compare(this.hitTemperture, o.hitTemperture);
//         return compare == 0 ? Long.compare(this.lastTime, o.lastTime) : compare;
//       }
//     }
//   }
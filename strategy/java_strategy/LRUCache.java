package java_strategy;

import java.util.HashMap;
import java.util.Map;

// 在Java中，对象的内存占用取决于对象本身的大小以及JVM的实现。对于给定的Node类，每个节点包含两个字符串字段（key和value），一个Node类型的前驱指针（pre）和一个Node类型的后继指针（next）。我们可以估算每个部分的占用空间：
// 对象头：在64位JVM中，对象头通常是16字节，包括类的类型信息、哈希码、GC信息等。
// 字段：
// 两个字符串字段（key和value）：每个字符串都是长度为15的字符串。字符串对象的开销大约是40字节（对象头8字节 + 类型信息和哈希码4字节 + 长度字段4字节 + 指向字符数组的引用12字节 + 16个字符32字节），因此两个字符串总共大约是80字节。
// 两个Node类型的指针（pre和next）：在64位JVM中，每个指针占用8字节，因此两个指针总共是16字节。
// 对齐填充：Java对象的大小通常需要按照8字节进行对齐。因此，可能需要额外的填充字节来确保对象的大小是8的倍数。

// 综合以上因素，我们可以估算一个Node节点的大致内存占用：
// 对象头：16字节
// 两个字符串字段：80字节
// 两个Node类型指针：16字节
// 对齐填充：可能是0字节，因为16 + 80 + 16 = 112字节，已经是8的倍数。
// 因此，一个Node节点的总大小大约是112字节。这是一个粗略的估计，实际的内存占用可能会因JVM的具体实现和配置而有所不同。

public class LRUCache<K, V> {

    /**
     * 头结点
     */
    private Node head;
    /**
     * 尾结点
     */
    private Node tail;
    /**
     * 容量限制
     */
    private int capacity;
    /**
     * key和数据的映射
     */
    private Map<K, Node> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    public V put(K key, V value) {
        Node node = map.get(key);
        // 数据存在，将节点移动到队尾
        if (node != null) {
            V oldValue = node.value;
            //更新数据
            node.value = value;
            moveToTail(node);
            return oldValue;
        } else {
            Node newNode = new Node(key, value);
            // 数据不存在，判断链表是否满
            if (map.size() == capacity) {
                // 如果满，则删除队首节点，更新哈希表
                map.remove(removeHead().key);
            }
            // 放入队尾节点
            addToTail(newNode);
            map.put(key, newNode);
            return null;
        }
    }

    public V get(K key) {
        Node node = map.get(key);
        if (node != null) {
            moveToTail(node);
            return node.value;
        }
        return null;
    }

    public void clear(){
        this.map.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LruCache{");
        Node curr = this.head;
        while (curr != null) {
            if(curr != this.head){
                sb.append(',').append(' ');
            }
            sb.append(curr.key);
            sb.append('=');
            sb.append(curr.value);
            curr = curr.next;
        }
        return sb.append('}').toString();
    }

    private void addToTail(Node newNode) {
        if (newNode == null) {
            return;
        }
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            //连接新节点
            tail.next = newNode;
            newNode.pre = tail;
            //更新尾节点指针为新节点
            tail = newNode;
        }
    }

    private void moveToTail(Node node) {
        if (tail == node) {
            return;
        }
        if (head == node) {
            head = node.next;
            head.pre = null;
        } else {
            //调整双向链表指针
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        node.pre = tail;
        node.next = null;
        tail.next = node;
        tail = node;
    }

    private Node removeHead() {
        if (head == null) {
            return null;
        }
        Node res = head;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = res.next;
            head.pre = null;
            res.next = null;
        }
        return res;
    }

    class Node {
        K key;
        V value;
        Node pre;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
package strategy;

import java.util.HashMap;
import java.util.Map;

public class LRU<K, V> {

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

    public LRU(int capacity) {
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
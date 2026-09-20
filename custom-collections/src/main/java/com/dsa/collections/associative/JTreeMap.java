package com.dsa.collections.associative;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import com.dsa.collections.interfaces.JCollection;
import com.dsa.collections.interfaces.JList;
import com.dsa.collections.interfaces.JSet;
import com.dsa.collections.linear.JArrayList;
import com.dsa.collections.linear.JHashSet;

public class JTreeMap<K, V> implements JMap<K, V> {

    /**
     * Item class which represent a pair
     */
    public static class Item<K, V> extends Object {
        K key;
        V value;
        
        Item(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {return key; }
        public V getValue() {return value; }

        // toString for accessing data outside the package
        @Override 
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    /**
     * Node class which represents a tree node
     * It holds a key, value pair.
     */
    private static class Node<K, V> {
        // Date
        Item<K, V> item;

        // Addresses/References
        Node<K, V> left;
        Node<K, V> right;

        Node(K key, V value) {
            item = new Item<K,V>(key, value);
            this.left = this.right = null;
        }
    }

    // reference to the BST (tree)
    private Node<K, V> root;
    private int size;

    /**
     * ----------------------------------------------------------------------
     * Function to Help the Backend
     * ----------------------------------------------------------------------
     */
    public List<Item<K, V>> getPreorder() {
        List<Item<K, V>> pre_order = new ArrayList<Item<K, V>>();
        preorder(root, pre_order);
        return pre_order;
    }

    public List<Item<K, V>> getPath(K key) {
        if(!this.containsKey(key)) {
            return new ArrayList<>();
        }

        List<Item<K, V>> path = new ArrayList<>();
        r_path(root, key, path);
        return path;
    }
    
    /**
     * ----------------------------------------------------------------------
     * Recursive Helper Functions
     * ----------------------------------------------------------------------
     */
    private  static <K, V> Node<K, V> insert(Node<K, V> curr, K key, V value) {
        if(curr == null) {
            return new Node<>(key, value);
        }

        int result = ((Comparable<K>) key).compareTo(curr.item.key);

        if(result < 0) {
            curr.left = insert(curr.left, key, value);
        } else if(result > 0) {
            curr.right = insert(curr.right, key, value);
        } else {
            return curr;
        }

        return curr;
    }

    private  static <K, V> Node<K, V> delete(Node<K, V> curr, K key) {
        if(curr == null) {
            return null;
        }

        int result = ((Comparable<K>) key).compareTo(curr.item.key);

        if(result < 0) {
            curr.left = delete(curr.left, key);
        } else if(result > 0) {
            curr.right = delete(curr.right, key);
        } else {

            if(curr.left == null) {
                Node<K, V> temp = curr.right;
                return temp;
            }

            if(curr.right == null) {
                Node<K, V> temp = curr.left;
                return temp;
            }

            Node<K, V> maxNode = max(curr.left);
            curr.item = maxNode.item;
            curr.left = delete(curr.left, maxNode.item.key);
        }

        return curr;
    }

    private  static <K, V> Node<K, V> max(Node<K, V> curr) {
        if(curr.right != null) {
            return max(curr.right);
        }

        return curr;
    }

    private  static <K, V> Node<K, V> min(Node<K, V> curr) {
        if(curr.left != null) {
            return min(curr.left);
        }

        return curr;
    }

    private  static <K, V> Node<K, V> search(Node<K, V> curr, K key) {
        if(curr != null) {
            int result = ((Comparable<K>) key).compareTo(curr.item.key);

            if(result == 0) return curr;

            else if(result < 0) return search(curr.left, key);
            
            else return search(curr.right, key);
        }
        return null;
    }

    private  static <K, V> void inorder(Node<K, V> curr, JCollection<Item<K, V>> set) {
        if(curr != null) {
            inorder(curr.left, set);

            // add the current's item's key into the set.
            set.add(new Item<>(curr.item.key , curr.item.value));

            
            inorder(curr.right, set);
        }
    }

    private static <K, V> void preorder(Node<K, V> curr, List<Item<K, V>> set) {
        if(curr != null) {
            // add the current's item's key into the set.
            set.add(new Item<>(curr.item.key , curr.item.value));

            preorder(curr.left, set);            
            preorder(curr.right, set);
        }
    }

    private static <K, V> void r_path(Node<K, V> curr, K key, List<Item<K, V>> path) {
        if(curr == null) {
            return;
        }

        path.add(new Item<>(curr.item.key , curr.item.value));

        int result = ((Comparable<K>) key).compareTo(curr.item.key);

        if(result < 0) {
            r_path(curr.left, key, path);
        } else if(result > 0) {
            r_path(curr.right, key, path);
        } else {
            return;
        }
    }

    /**
     * ----------------------------------------------------------------------
     * Interface Functions (UI)
     * ----------------------------------------------------------------------
     */
    public JTreeMap() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size==0 && root == null);
    }

    @Override
    public boolean containsKey(Object key) {
        return search(root, (K) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        JCollection<V> valuesCollection = values();
        return valuesCollection.contains(value);
    }

    @Override
    public V get(K key) {
        Node<K, V> target = search(root, key);

        if(target==null)
            throw new IllegalArgumentException();

        return target.item.value;
    }

    @Override
    public V put(K key, V value) {
        if(key == null || value == null)
            throw new IllegalArgumentException();
        
        if(containsKey(key)) {
            setValue(key, value);
            return value;
        }

        root = insert(root, key, value);
        size++;
        return value;
    }

    @Override
    public V remove(K key) {
        if(key == null)
            throw new IllegalArgumentException();
        
        if(!containsKey(key))
            throw new IllegalArgumentException();

        V value = get(key);
        root = delete(root, key);
        size--;
        return value;
    }

    @Override
    public V setValue(K key, V value) {
        Node<K, V> target = search(root, key);

        if(target==null)
            throw new IllegalArgumentException();

        target.item.value = value;
        return value;
    }

    @Override
    public JSet<K> keySet() {
        JList<Item<K, V>> items = new JArrayList<>();

        inorder(root, items);

        JSet<K> keys = new JHashSet<>();
        for(int i=0; i<items.size(); i++)
            keys.add((K) items.get(i).key);
        
        return keys;
    }

    @Override
    public JCollection<V> values() {
        JList<Item<K, V>> items = new JArrayList<>();

        inorder(root, items);

        JCollection<V> valueCollection = new JArrayList<>();
        for(int i=0; i<items.size(); i++)
            valueCollection.add((V) items.get(i).value);

        return valueCollection;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

}

package com.dsa.collections.associative;

import java.util.HashSet;
import java.util.LinkedHashMap;
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
    private static class Item<K, V> {
        K key;
        V value;
        
        Item(K key, V value) {
            this.key = key;
            this.value = value;
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
     * Recursive Helper Functions
     * ----------------------------------------------------------------------
     */
    private Node<K, V> insert(Node<K, V> curr, K key, V value) {
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

    private Node<K, V> delete(Node<K, V> curr, K key) {
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

    private Node<K, V> max(Node<K, V> curr) {
        if(curr.right != null) {
            return max(curr.right);
        }

        return curr;
    }

    private Node<K, V> min(Node<K, V> curr) {
        if(curr.left != null) {
            return min(curr.left);
        }

        return curr;
    }

    private Node<K, V> search(Node<K, V> curr, K key) {
        if(curr != null) {
            int result = ((Comparable<K>) key).compareTo(curr.item.key);

            if(result == 0) return curr;

            else if(result < 0) return search(curr.left, key);
            
            else return search(curr.right, key);
        }
        return null;
    }

    private void inorder(Node<K, V> curr, JCollection<Item> set) {
        if(curr != null) {
            inorder(curr.left, set);

            // add the current's items's key into the set.
            set.add(curr.item);
            
            inorder(curr.right, set);
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
        JList<Item> items = new JArrayList<>();

        inorder(root, items);

        JSet<K> keys = new JHashSet<>();
        for(int i=0; i<items.size(); i++)
            keys.add((K) items.get(i).key);
        
        return keys;
    }

    @Override
    public JCollection<V> values() {
        JList<Item> items = new JArrayList<>();

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

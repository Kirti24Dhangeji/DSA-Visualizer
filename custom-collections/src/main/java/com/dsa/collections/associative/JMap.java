package com.dsa.collections.associative;

import com.dsa.collections.interfaces.JCollection;
import com.dsa.collections.interfaces.JSet;

public interface JMap<K, V> {
    int size();
    boolean isEmpty();
    boolean containsKey(Object key);
    boolean containsValue(Object value);
    V get(K key);
    V put(K key, V value);
    V remove(K key);
    V setValue(K key, V value);
    JSet<K> keySet();
    JCollection<V> values();
    void clear();
}

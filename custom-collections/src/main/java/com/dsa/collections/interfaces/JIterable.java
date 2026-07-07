package com.dsa.collections.interfaces;

import com.dsa.collections.iterator.JIterator;

public interface JIterable<T> {
    JIterator<T> iterator();
}
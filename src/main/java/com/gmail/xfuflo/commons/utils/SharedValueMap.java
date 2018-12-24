package com.gmail.xfuflo.commons.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class SharedValueMap<K, V> {

    private static class SharedValueMapEntry<V> {
        private final AtomicLong counter = new AtomicLong();
        private final V value;

        public SharedValueMapEntry(V value) { this.value = value; }
        public long increment() { return counter.addAndGet(1); }
        public long decrement() { return counter.addAndGet(-1); }
        public V getValue() { return value; }
    }

    private final ConcurrentHashMap<K, SharedValueMapEntry<V>> map = new ConcurrentHashMap<>();

    public V computeIfAbsent(final K key, final Supplier<V> supplier) {
        return map.compute(key, (k, value) -> {
            if (null == value) {
                value = new SharedValueMapEntry(supplier.get());
            }
            value.increment();
            return value;
        }).getValue();
    }

    public boolean release(final K key) {
        return map.compute(key, (k, value) -> {
            if (null == value) {
                throw new RuntimeException("SharedValueMap::release - value not found by key=" + String.valueOf(k));
            }

            return (0 == value.decrement()) ? null : value;
        }) == null;
    }
}

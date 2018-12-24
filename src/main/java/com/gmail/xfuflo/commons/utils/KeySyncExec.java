package com.gmail.xfuflo.commons.utils;


import java.util.function.Supplier;

public class KeySyncExec {
    private static final Supplier<Object> lockSupplier = () -> new Object();
    private final com.gmail.xfuflo.commons.utils.SharedValueMap<String, Object> mapLock = new com.gmail.xfuflo.commons.utils.SharedValueMap<>();

    public void exec(final String key, final Runnable r) {
        final Object lock = mapLock.computeIfAbsent(key, lockSupplier);
        try {
            synchronized (lock) {
                r.run();
            }
        } finally {
            mapLock.release(key);
        }
    }
}

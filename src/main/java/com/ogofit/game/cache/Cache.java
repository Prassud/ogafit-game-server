package com.ogofit.game.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private Map<String, Object> kvMap = new ConcurrentHashMap<>();

    public Object get(String key) {
        return kvMap.get(key);
    }


    public void put(String key, Object object) {
        Objects.requireNonNull(object);
        kvMap.put(key, object);
    }
}

package com.ogofit.game.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;

@Component
public class CacheManager {
    private Map<String, Cache> cache = new ConcurrentHashMap<>();

    public Cache getCache(String cacheName) {
        Cache cache = ofNullable(this.cache.get(cacheName))
                .orElse(new Cache());
        return this.cache.putIfAbsent(cacheName, cache);
    }
}

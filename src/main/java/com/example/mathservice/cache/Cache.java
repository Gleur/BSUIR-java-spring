package com.example.mathservice.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cache {
    private final Map<String, String> cacheMap = new HashMap<>();

    public void put(String key, String value) {
        cacheMap.put(key, value);
    }

    public String get(String key) {
        return cacheMap.get(key);
    }

    public Boolean contains(int value) {
        return this.cacheMap.containsKey(value);
    }
}

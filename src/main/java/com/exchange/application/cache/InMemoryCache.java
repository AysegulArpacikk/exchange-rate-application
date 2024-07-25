package com.exchange.application.cache;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.NullValue;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;

public class InMemoryCache extends CaffeineCache {

    public InMemoryCache(String name, Cache<Object, Object> cache) {
        super(name, cache);
    }

    @Override
    public Object lookup(Object key) {
        Object cachedValue = super.lookup(key);
        if (cachedValue instanceof NullValue) {
            return cachedValue;
        }
        return SerializationUtils.deserialize((byte[]) cachedValue);
    }

    @Override
    public void put(Object key, Object value) {
        byte[] serializedValue = SerializationUtils.serialize(value);
        super.put(key, serializedValue);
    }
}

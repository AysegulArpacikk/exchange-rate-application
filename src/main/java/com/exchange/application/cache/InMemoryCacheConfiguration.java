package com.exchange.application.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.TimeUnit;

@Configuration
public class InMemoryCacheConfiguration {

    @Autowired
    @Qualifier("threadPoolTaskScheduler")
    private ThreadPoolTaskScheduler taskScheduler;

    @Bean
    @Lazy
    @Qualifier(CacheNames.ONE_DAY_IN_MEMORY)
    public CaffeineCache oneDayInMemoryCache() {
        Cache<Object, Object> nativeCache = Caffeine.newBuilder()
                .maximumSize(100000)
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build();

        scheduleCleanUpThread(nativeCache);

        return new InMemoryCache(CacheNames.ONE_DAY_IN_MEMORY, nativeCache);
    }

    private void scheduleCleanUpThread(Cache inMemoryCache) {
        taskScheduler.scheduleWithFixedDelay(inMemoryCache::cleanUp, getCleanUpInterval());
    }

    private long getCleanUpInterval() {
        return TimeUnit.SECONDS.toMillis(60);
    }
}

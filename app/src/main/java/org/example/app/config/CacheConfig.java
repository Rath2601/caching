package org.example.app.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(
                buildCache("products", 100, Duration.ofMinutes(5)),
                buildCache("product", 100, Duration.ofMinutes(5))
        ));
        return cacheManager;
    }

    private CaffeineCache buildCache(String name, long maximumSize, Duration ttl) {
        return new CaffeineCache(name, 
                Caffeine.newBuilder()
                        .maximumSize(maximumSize) //  Eviction based on access recency. LRU-like policy called W-TinyLFU. Can't implement FIFO/Random here
                        .expireAfterWrite(ttl)
                        .initialCapacity(10)
//                        .maximumWeight(10) // allow 10 entries
//                        .weigher((k, v) -> 1) // each entry counts as 1 weight unit
//                        .refreshAfterWrite(Duration.ofMillis(10)) //refreshAfterWrite requires a LoadingCache
                        .expireAfterAccess(Duration.ofMillis(5000)) // Entry expires if it hasn’t been accessed in the last 5 seconds
                        .recordStats()
                        .build());
    }
}


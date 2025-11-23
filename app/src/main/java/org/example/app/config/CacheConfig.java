package org.example.app.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    @Configuration
    @ConditionalOnProperty(value = "app.cache.provider", havingValue = "local")
    static class LocalCacheConfiguration {

        @Bean
        public CacheManager cacheManager() {
            SimpleCacheManager cacheManager = new SimpleCacheManager();
            cacheManager.setCaches(List.of(
                    buildCache("products", 100, Duration.ofMinutes(10)),
                    buildCache("product", 500, Duration.ofMinutes(5))
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

    @Configuration
    @ConditionalOnProperty(value = "app.cache.provider", havingValue = "redis", matchIfMissing = true)
    static class CustomRedisCacheConfiguration {
        
        @Bean
        public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
            RedisCacheConfiguration cacheConfig = defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(10))
                    .disableCachingNullValues()
                    .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            new GenericJackson2JsonRedisSerializer()
                        )
                    );
            
            return RedisCacheManager.builder(connectionFactory)
                    .cacheDefaults(cacheConfig)
                    .build();
        }
    }
}


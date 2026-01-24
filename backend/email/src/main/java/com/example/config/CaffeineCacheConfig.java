package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import java.util.concurrent.TimeUnit;
@Configuration
public class CaffeineCacheConfig {
    @Bean 
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("inbox");
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(45, TimeUnit.SECONDS));
        return cacheManager;
    }       

}
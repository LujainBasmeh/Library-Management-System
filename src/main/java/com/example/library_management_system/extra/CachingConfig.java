package com.example.library_management_system.extra;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("books","patrons");
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeine() {
        return Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(500);
    }
}
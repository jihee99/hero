//package com.ex.hero.config.redis;
//
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//import io.github.bucket4j.grid.jcache.JCacheProxyManager;
//
//import org.redisson.Redisson;
//import org.redisson.config.Config;
//import org.redisson.jcache.configuration.RedissonConfiguration;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.redisson.api.RedissonClient;
//
//import javax.cache.Cache;
//import javax.cache.CacheManager;
//import javax.cache.Caching;
//
//@Configuration
//public class RedissonConfig {
//
//    @Value("${spring.data.redis.host}")
//    private String redisHost;
//    @Value("${spring.data.redis.port}")
//    private int redisPort;
//    private static final String REDISSON_HOST_PREFIX = "redis://";
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + redisHost + ":" + redisPort);
//        return Redisson.create(config);
//    }
//
//    /** for bucket4j */
//    @Bean
//    public CacheManager cacheManager(RedissonClient redissonClient) {
//        CacheManager manager = Caching.getCachingProvider().getCacheManager();
//        Cache<Object, Object> bucket4j = manager.getCache("bucket4j");
//        if (bucket4j == null) {
//            manager.createCache("bucket4j", RedissonConfiguration.fromInstance(redissonClient));
//        }
//        return manager;
//    }
//
//}

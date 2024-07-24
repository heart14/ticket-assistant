package com.heart.ticket.base.config;

import com.heart.ticket.base.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author wfli
 * @since 2024/7/24 16:13
 */
@Configuration
public class RedisConfig {

    /**
     * 修改redis缓存管理器中key和value的序列化方式，若不修改，则使用的是默认的jdk序列化，存储到redis里面查看是乱码
     * @param factory
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory factory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMillis(Constants.REDIS_CACHE_ENTRY_TTL))
                .computePrefixWith(name -> name + ":")//redis2.x版本中，保存缓存时默认会在cacheNames后面加上双冒号，使用该配置改为单冒号
                .disableCachingNullValues()//禁止缓存null值
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(configuration).build();
    }
}

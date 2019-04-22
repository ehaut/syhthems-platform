package top.sunriseydy.syhthems.db.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Spring Cache 自定义配置
 *
 * @author SunriseYDY
 * @date 2019-04-15 17:29
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    private final RedisConnectionFactory connectionFactory;

    /**
     * key serializer
     */
    private RedisSerializer<String> stringSerializer = RedisSerializer.string();

    /**
     * key serializer pair
     */
    private RedisSerializationContext.SerializationPair<String> stringPair =
            RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer);
    /**
     * value serializer
     * <p>注意不能使用Jackson2JsonRedisSerializer,该类不能根据类型来反序列化，会出现类型转换的异常。
     */
    private RedisSerializer<Object> jacksonSerializer;

    /**
     * value serializer pair
     */
    private RedisSerializationContext.SerializationPair<Object> jacksonPair;

    @Autowired
    public RedisCacheConfig(@Qualifier("cacheObjectMapper") ObjectMapper objectMapper, RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.jacksonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        this.jacksonPair = RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer);
    }

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuffer stringBuffer = new StringBuffer();
            // 获取目标的简单类名（去掉cglib动态代理的类名）
            stringBuffer.append(target.getClass().getSimpleName().replaceAll("\\$.*", ""));
            stringBuffer.append(":");
            stringBuffer.append(method.getName());
            stringBuffer.append(":");
            stringBuffer.append("(");
            String comma = "";
            for (Object obj : params) {
                stringBuffer.append(comma);
                if (obj == null) {
                    stringBuffer.append("NULL");
                } else {
                    stringBuffer.append(obj.toString());
                }
                comma = ",";
            }
            stringBuffer.append(")");
            return stringBuffer.toString();
        };
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(this.jacksonSerializer);
        template.setKeySerializer(this.stringSerializer);
        template.setHashKeySerializer(this.stringSerializer);
        template.setHashValueSerializer(this.jacksonSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Override
    @Bean
    @Primary
    public CacheManager cacheManager() {
        final RedisCacheWriter redisCacheWriter =
                RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        final RedisCacheConfiguration defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig()

                        // 不缓存 null 值
                        .disableCachingNullValues()
                        // 使用注解时的序列化、反序列化对
                        .serializeKeysWith(stringPair)
                        .serializeValuesWith(jacksonPair)

                        .prefixKeysWith("syhthems:cache:");
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    @Bean
    public CacheManager JDKCacheManager() {
        final RedisCacheWriter redisCacheWriter =
                RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        final RedisCacheConfiguration defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig()

                        // 不缓存 null 值
                        .disableCachingNullValues()
                        // 使用注解时的序列化、反序列化对
                        .serializeKeysWith(stringPair)
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()))
                        .prefixKeysWith("syhthems:cache:");
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }
}

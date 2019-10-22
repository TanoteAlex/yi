package com.yi.webserver.web.config.common;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis配置 配置序列化方式以及缓存管理器 @EnableCaching 开启缓存
 * 
 * @author xuyh
 *
 */
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	/**
	 * 自定义key规则
	 */
	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(":");
				sb.append(method.getName());
				sb.append(":");
				for (Object param : params) {
					sb.append(param.toString());
				}
				return sb.toString();
			}
		};
	}

	/**
	 * redisTemplate配置
	 * 
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		//
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

		// 解决查询缓存转换异常的问题
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		// redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

//	@Bean
//	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//		// 生成一个默认配置，通过config对象即可对缓存进行自定义配置
//		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//		// 设置缓存的默认过期时间，也是使用Duration设置
//		config = config.entryTtl(Duration.ofMinutes(1)).disableCachingNullValues(); // 不缓存空值
//
//		// 设置一个初始化的缓存空间set集合
//		Set<String> cacheNames = new HashSet<>();
//		cacheNames.add("redis-cache1");
//		cacheNames.add("redis-cache2");
//
//		// 对每个缓存空间应用不同的配置
//		Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//		configMap.put("redis-cache1", config);
//		configMap.put("redis-cache2", config.entryTtl(Duration.ofSeconds(120)));
//
//		// 使用自定义的缓存配置初始化一个cacheManager
//		// 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
//		RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory).initialCacheNames(cacheNames).withInitialCacheConfigurations(configMap).build();
//		return cacheManager;
//	}

}
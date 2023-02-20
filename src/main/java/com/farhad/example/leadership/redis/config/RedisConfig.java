package com.farhad.example.leadership.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Jedis;
import java.util.Random;

@Configuration
public class RedisConfig {

    // @Bean
    // public JedisConnectionFactory redisConnectionFactory() {
    //   return new JedisConnectionFactory();
    // }

    // @Bean
    // JedisConnectionFactory redisConnectionFactory() {
    //     JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
    //     jedisConFactory.setHostName("localhost");
    //     jedisConFactory.setPort(6379);
    //     return jedisConFactory;
    // }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
  
      RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
      return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
    
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig() ;
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestWhileIdle(true); 
        return new JedisPool(poolConfig,"localhost", 6379, 500);
    }

    @Bean
    public Jedis jedis(JedisPool jedisPool) {

        return jedisPool.getResource() ;

    }

    @Bean
	public String myApplicationId() {
		return "" + new Random().nextInt(Integer.MAX_VALUE);
	}
    
}

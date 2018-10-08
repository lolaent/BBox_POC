package com.lolatech.springkafka.adapter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
@Component
public class RedisAdapter {

	private RedissonClient redissonClient;
	
	@Value("${redis.address}")
	private String redisAddress;
	
	@Value("${redis.timeout}")
	private int redisTimeout;
	
	@Value("${redis.password}")
	private String redisPassword;
	
	@PostConstruct
	public void initialize() {
		Config config = new Config();
		
		if(redisPassword.equals("")) {
			config.useSingleServer().setAddress(redisAddress).setTimeout(redisTimeout);
		} else {
			config.useSingleServer().
			    setAddress(redisAddress).
			    setPassword(redisPassword).
			    setTimeout(redisTimeout);
		}
		
		
		redissonClient = Redisson.create(config);
	}
	
	@PreDestroy
	public void destroy() {
		redissonClient.shutdown();
	}
	
	public <V> RQueue<V> getQueue(String name) {
		return redissonClient.getQueue(name);
	}
}

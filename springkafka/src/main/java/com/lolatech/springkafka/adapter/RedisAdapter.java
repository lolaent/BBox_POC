package com.lolatech.springkafka.adapter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.redisson.Redisson;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
@Component
public class RedisAdapter {

	private RedissonClient redissonClient;
	
	@PostConstruct
	public void initialize() {
		Config config = new Config();
		
		config.useSingleServer().setAddress("http://localhost:6379").setTimeout(3000);
		
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

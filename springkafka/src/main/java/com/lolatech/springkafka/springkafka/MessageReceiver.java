package com.lolatech.springkafka.springkafka;

import com.lolatech.springkafka.adapter.RedisAdapter;
import org.redisson.api.RQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.lolatech.springkafka.service.RedisQueueService;

import javax.annotation.PostConstruct;

@Service
@ComponentScan(basePackages = "com.lolatech.springkafka")
public class MessageReceiver {
	
	@Autowired
	private RedisAdapter redisAdapter;
	
	@Autowired
	RedisQueueService redisQueueService;
	
	@PostConstruct
	public void initialize() {
		redisQueueService.executeAsynchronously();
	}
	
	MessageReceiver() {
	}
	
	@KafkaListener(topics = "#{'${kafka.topic.boot}'.split('\\\\ ')}", groupId = "boot")
	public void receive(String content) {
		RQueue<String> queue = redisAdapter.getQueue("SYSLOG");
		queue.add(content);
	}
}

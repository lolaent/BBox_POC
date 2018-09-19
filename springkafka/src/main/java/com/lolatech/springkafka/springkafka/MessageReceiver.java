package com.lolatech.springkafka.springkafka;

import com.lolatech.springkafka.adapter.RedisAdapter;
import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.util.GenericDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.redisson.api.RQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.lolatech.springkafka.service.ElasticsearchService;
import com.lolatech.springkafka.service.RedisQueueService;
import com.lolatech.springkafka.util.ObjectTypesEnum;
import com.lolatech.springkafka.util.QueueProcessor;

import java.sql.Time;

import javax.annotation.PostConstruct;

@Service
@ComponentScan(basePackages = "com.lolatech.springkafka")
public class MessageReceiver {

	@Autowired
	private ElasticsearchService service;
	
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
	
	/*public void receive(TimelineDto timelineDto) {
	  service.save(timelineDto);
	}*/
	
	private String getTopicName(String topic){
//		String[] topics = topic.split("-");
//
		return topic.replace("-", "_").toUpperCase();
	}
}

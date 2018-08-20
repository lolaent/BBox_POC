package com.lolatech.springkafka.springkafka;

import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.util.GenericDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.lolatech.springkafka.service.ElasticsearchService;
import com.lolatech.springkafka.util.ObjectTypesEnum;

import java.sql.Time;

@Service
@ComponentScan(basePackages = "com.lolatech.springkafka")
public class MessageReceiver {

	@Autowired
	private ElasticsearchService service;
	
	MessageReceiver() {
		System.out.println("messagereceiver_000_created.");
	}
	
	@KafkaListener(topics = "#{'${kafka.topic.boot}'.split('\\\\ ')}", groupId = "boot")
	public void receive(TimelineDto timelineDto) {
	  service.save(timelineDto);
	}
	
	private String getTopicName(String topic){
//		String[] topics = topic.split("-");
//
		return topic.replace("-", "_").toUpperCase();
	}
}

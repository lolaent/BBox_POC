package com.lolatech.springkafka.springkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.lolatech.springkafka.service.ElasticsearchService;
import com.lolatech.springkafka.util.ObjectTypesEnum;

@Service
@ComponentScan(basePackages = "com.lolatech.springkafka")
public class MessageReceiver {

	@Autowired
	private ElasticsearchService service;
	
	MessageReceiver() {
		System.out.println("messagereceiver_000_created.");
	}
	
	@KafkaListener(topics = "#{'${kafka.topic.boot}'.split('\\\\ ')}")
	public void receive(ConsumerRecord<?, ?> consumerRecord) {
	  System.out.println("received payload = " + consumerRecord.toString());
	  ObjectTypesEnum type = ObjectTypesEnum.valueOf(getTopicName(consumerRecord.topic()));
	  service.save(type, consumerRecord.value());
	}
	
	private String getTopicName(String topic){
		String[] topics = topic.split("-");
		
		return topics[0].toUpperCase();
	}
}

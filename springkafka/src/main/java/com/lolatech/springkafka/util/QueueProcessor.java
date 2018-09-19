package com.lolatech.springkafka.util;

import org.redisson.api.RQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lolatech.springkafka.adapter.RedisAdapter;
import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.service.ElasticsearchService;

@Component
@Scope("prototype")
public class QueueProcessor implements Runnable {

	@Autowired 
	private RedisAdapter redisAdapter;
	
	@Autowired
	private ElasticsearchService elasticsearchService;	
	
	private SyslogParser parser;
	
	@Override
	public void run() {
		parser = new SyslogParser();
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		while(true) {
			RQueue<String> messageQueue = redisAdapter.getQueue("SYSLOG");
			while(!messageQueue.isEmpty()) {
				String message = messageQueue.poll();
				TimelineDto timeline = parser.getTimeline(message);
				if(timeline == null) {
					continue;
				}
				elasticsearchService.save(timeline);
			    System.out.println(timeline.getPayload());
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("interrupted exception");
			}
		}
	}

}

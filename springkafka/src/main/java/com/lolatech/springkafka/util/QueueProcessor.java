package com.lolatech.springkafka.util;

import org.redisson.api.RQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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
	
	@Autowired
	private Environment env;
	
	private SyslogParser parser;
	
	@Override
	public void run() {
		parser = new SyslogParser(env.getProperty("environment"));
		
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
			} catch(Exception e) {
				System.out.println("some unexpected exception happened");
			}
			
		}
	}

}

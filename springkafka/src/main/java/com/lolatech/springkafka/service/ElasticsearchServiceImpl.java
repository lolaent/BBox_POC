package com.lolatech.springkafka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.repository.TimelineRepository;
import com.lolatech.springkafka.util.ObjectTypesEnum;

@Service
@EnableElasticsearchRepositories(basePackages = "com.lolatech.springkafka.repository")
public class ElasticsearchServiceImpl implements ElasticsearchService {

	private TimelineRepository timelineRepository;
	

	@Autowired
	public void setTimelineRepository(TimelineRepository timelineRepository) {
		this.timelineRepository = timelineRepository;
	}

	@Override
	public void save(TimelineDto data) {
	    timelineRepository.save(data);
	}
	
	public List<TimelineDto> findTransactions(String transactionId) {
		return timelineRepository.findTransactions(transactionId);
	}

}

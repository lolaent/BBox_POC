package com.lolatech.springkafka.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolatech.springkafka.dto.DemoDto;
import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.repository.DemoRepository;
import com.lolatech.springkafka.repository.TimelineRepository;
import com.lolatech.springkafka.util.ObjectTypesEnum;

@Service
@EnableElasticsearchRepositories(basePackages = "com.lolatech.springkafka.repository")
public class ElasticsearchServiceImpl implements ElasticsearchService {

	private TimelineRepository timelineRepository;
	
	private DemoRepository demoRepository;
	
	@Autowired
	public void setTimelineRepository(TimelineRepository timelineRepository) {
		this.timelineRepository = timelineRepository;
	}
	
	@Autowired
	public void setDemoRepository(DemoRepository demoRepository) {
		this.demoRepository = demoRepository;
	}
	@Override
	public <T> void save(ObjectTypesEnum type, T data) {

		switch(type) {
			case TEST:
			{
				ObjectMapper objectMapper = new ObjectMapper();
				DemoDto dto = null;
				try {
					dto = objectMapper.readValue((String)data,  DemoDto.class);
				} catch (JsonParseException e) {
					System.out.println("could not parse json");
				} catch (JsonMappingException e) {
					System.out.println("could not map json");
				} catch (IOException e) {
					System.out.println("could not retrieve json");
				}
				demoRepository.save(dto);
				break;
			}
			case HELLO:
			{
				TimelineDto dto = new TimelineDto();
				dto.setContent((String) data);
				timelineRepository.save(dto);
				break;
			}
			default:
				break;
		}

	}

}

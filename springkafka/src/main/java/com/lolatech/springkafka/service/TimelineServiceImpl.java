package com.lolatech.springkafka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;

import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.repository.TimelineRepository;

@Service
@EnableElasticsearchRepositories(basePackages = "com.lolatech.springkafka.repository")
public class TimelineServiceImpl implements TimelineService {

	private TimelineRepository timelineRepository;

	@Autowired
	public void setTimelineRepository(TimelineRepository timelineRepository) {
		this.timelineRepository = timelineRepository;
	}
	
	@Override
	public TimelineDto save(TimelineDto timelineDto) {
		// TODO Auto-generated method stub
		return timelineRepository.save(timelineDto);
	}

	/*@Override
	public void delete(TimelineDto timeline) {
		// TODO Auto-generated method stub
		timelineRepository.delete(timeline);
	}

	@Override
	public Iterable<TimelineDto> findAll() {
		// TODO Auto-generated method stub
		return timelineRepository.findAll();
	}

	@Override
	public List<TimelineDto> findByContent(String content) {
		// TODO Auto-generated method stub
		return timelineRepository.findByContent(content);
	}
*/
}

package com.lolatech.springkafka.service;

import java.util.List;

import com.lolatech.springkafka.dto.TimelineDto;

public interface TimelineService {

	public TimelineDto save(TimelineDto timelineDto);
	
	/*public void delete(TimelineDto timeline);
	
	Iterable<TimelineDto> findAll();
	
	List<TimelineDto> findByContent(String content);*/
}

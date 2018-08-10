package com.lolatech.springkafka.repository;

import com.lolatech.springkafka.dto.TimelineDto;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineRepository extends ElasticsearchRepository<TimelineDto,String> {

	List<TimelineDto> findByContent(String content);
}

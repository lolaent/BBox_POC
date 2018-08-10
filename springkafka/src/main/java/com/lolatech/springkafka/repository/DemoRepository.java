package com.lolatech.springkafka.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.lolatech.springkafka.dto.DemoDto;

public interface DemoRepository extends ElasticsearchRepository<DemoDto, String> {

}

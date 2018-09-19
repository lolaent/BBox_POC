package com.lolatech.springkafka.repository;

import com.lolatech.springkafka.dto.TimelineDto;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineRepository extends ElasticsearchRepository<TimelineDto,String> {

	List<TimelineDto> findByTransactionId(String transactionId);
	
	@Query("{\"bool\": {\"must\": {\"match\": {\"transaction_id\": \"?0\"}}}}")
	List<TimelineDto> findTransactions(String transactionId);
	
}

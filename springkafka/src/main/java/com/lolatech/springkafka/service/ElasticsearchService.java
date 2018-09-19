package com.lolatech.springkafka.service;

import java.util.List;

import com.lolatech.springkafka.dto.TimelineDto;

public interface ElasticsearchService {
    void save(TimelineDto data);
    
    List<TimelineDto> findTransactions(String transactionId);
}

package com.lolatech.springkafka.service;

import com.lolatech.springkafka.dto.TimelineDto;

public interface ElasticsearchService {
    void save(TimelineDto data);
}

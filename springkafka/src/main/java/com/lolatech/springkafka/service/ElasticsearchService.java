package com.lolatech.springkafka.service;

import com.lolatech.springkafka.util.ObjectTypesEnum;

public interface ElasticsearchService {
    <T> void save(ObjectTypesEnum type, T data);
}

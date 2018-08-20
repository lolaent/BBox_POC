package com.lolatech.springkafka.util;

import com.lolatech.springkafka.dto.TimelineDto;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GenericDeserializer implements Deserializer<TimelineDto> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public TimelineDto deserialize(String topic, byte[] bytes) {
        if (ObjectTypesEnum.SYSLOG_JSON.getType().equals(topic)) {
            return null;
        }
        return null;
    }

    @Override public void close() {

    }
}

package com.lolatech.springkafka.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolatech.springkafka.dto.TimelineDto;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GenericDeserializer implements Deserializer<TimelineDto> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public TimelineDto deserialize(String topic, byte[] bytes) {
        TimelineDto timelineDto = new TimelineDto();
        String data = readData(bytes);
        System.out.println(data);
        if (ObjectTypesEnum.SYSLOG_JSON.getType().equals(topic)) {
            System.out.println("Json!");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                timelineDto = objectMapper.readValue(data,  TimelineDto.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
                System.out.println("could not parse json");
            } catch (JsonMappingException e) {
                e.printStackTrace();
                System.out.println("could not map json");
            } catch (IOException e) {
                System.out.println("could not retrieve json");
            }
            return timelineDto;
        }
        else if (ObjectTypesEnum.SYSLOG_CSV.getType().equals(topic)) {
            try {
                String[] dataSplit = data.split(",");
                System.out.println(data);
                timelineDto = new TimelineDto(dataSplit[1], dataSplit[2]);
            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
        }

        return timelineDto;
    }

    @Override
    public void close() {

    }

    private String readData(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

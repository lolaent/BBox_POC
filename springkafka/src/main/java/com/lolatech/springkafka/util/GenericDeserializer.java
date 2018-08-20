package com.lolatech.springkafka.util;

import com.fasterxml.jackson.core.JsonParseException;
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
        TimelineDto timelineDto = null;
        String data = readData(bytes);

        if (ObjectTypesEnum.SYSLOG_JSON.getType().equals(topic)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                timelineDto = objectMapper.readValue(data,  TimelineDto.class);
            } catch (JsonParseException e) {
                System.out.println("could not parse json");
            } catch (JsonMappingException e) {
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
                timelineDto = new TimelineDto(null, dataSplit[0], dataSplit[1], dataSplit[2],
                        dataSplit[3], dataSplit[4], dataSplit[5], dataSplit[6], dataSplit[7], dataSplit[8], dataSplit[9], dataSplit[10],
                        dataSplit[11], dataSplit[12], dataSplit[13]);
            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
        }

        return timelineDto;
    }

    @Override public void close() {

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

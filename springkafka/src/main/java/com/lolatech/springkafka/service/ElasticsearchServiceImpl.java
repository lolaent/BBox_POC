package com.lolatech.springkafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;

import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.repository.TimelineRepository;
import com.lolatech.springkafka.util.ObjectTypesEnum;

@Service
@EnableElasticsearchRepositories(basePackages = "com.lolatech.springkafka.repository")
public class ElasticsearchServiceImpl implements ElasticsearchService {

	private TimelineRepository timelineRepository;
	

	@Autowired
	public void setTimelineRepository(TimelineRepository timelineRepository) {
		this.timelineRepository = timelineRepository;
	}

	@Override
	public <T> void save(ObjectTypesEnum type, T data) {

		switch(type) {
        case SYSLOG_CSV_STRING:
            {
                TimelineDto dto = null;
                System.out.println((String) data);
                try {
                    String[] dataSplit = ((String) data).split(",");
                    dto = new TimelineDto(null, dataSplit[0], dataSplit[1], dataSplit[2],
                            dataSplit[3], dataSplit[4], dataSplit[5], dataSplit[6], dataSplit[7], dataSplit[8], dataSplit[9], dataSplit[10],
                            dataSplit[11], dataSplit[12], dataSplit[13]);
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }
                timelineRepository.save(dto);
                break;
            }
			default:
				break;
		}

	}

}

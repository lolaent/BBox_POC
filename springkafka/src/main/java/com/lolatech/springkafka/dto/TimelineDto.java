package com.lolatech.springkafka.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="timelinedto", type="timeline")
public class TimelineDto {

	@Id
	private String id;
	
	private String content;
	
	public TimelineDto() {		
	}
	
	public TimelineDto(String id, String content) {
		this.id = id;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}

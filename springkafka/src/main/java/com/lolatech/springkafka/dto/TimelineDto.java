package com.lolatech.springkafka.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName="timelinedto", type="timeline")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimelineDto {

	public TimelineDto() {		
	}

	public TimelineDto(String messageId, String transactionId, Map<String,String> tags, 
			long datetime, String payload) {
		this.messageId = messageId;
		this.transactionId = transactionId;
		this.tags = tags;
		this.datetime = datetime;
		this.payload = payload;
	}
	
	@Id
    private String id;
	
	@JsonProperty("msg_id")
	private String messageId;
	
	@JsonProperty("transaction_id")
	private String transactionId;
	
	private Map<String,String> tags;
	
	private long datetime;
	
	private String payload;
	
	
    public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
}

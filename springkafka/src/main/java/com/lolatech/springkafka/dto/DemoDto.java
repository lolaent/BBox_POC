package com.lolatech.springkafka.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="users", type="userinfo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemoDto {

	@Id
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("account")
	private String account;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
	
}

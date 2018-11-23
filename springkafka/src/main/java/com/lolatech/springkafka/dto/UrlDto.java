package com.lolatech.springkafka.dto;

public class UrlDto {

	public UrlDto(){
		
	}
	
	public UrlDto(String ip, String port, String key) {
		this.ip = ip;
		this.port = port;
		this.key = key;
	}
	
	private String ip;
	
	private String port;
	
	private String key;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}

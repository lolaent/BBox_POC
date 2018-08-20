package com.lolatech.springkafka.util;

public enum ObjectTypesEnum {

	SYSLOG_JSON("syslog-json"),
	SYSLOG_CSV("syslog-csv");
	
	private String type;
	
	ObjectTypesEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

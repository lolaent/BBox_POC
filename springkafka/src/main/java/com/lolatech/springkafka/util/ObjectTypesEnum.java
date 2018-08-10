package com.lolatech.springkafka.util;

public enum ObjectTypesEnum {

	TEST("Test-Kafka"), HELLO("Hello-Kafka");
	
	private String type;
	
	ObjectTypesEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

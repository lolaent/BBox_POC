package com.lolatech.springkafka.dto;

public class VersionsDto {

	public VersionsDto() {
	}
	
	public VersionsDto(String drupal, String jsui) {
		this.drupal = drupal;
		this.jsui = jsui;
	}
	
	private String drupal;
	
	private String jsui;

	public String getDrupal() {
		return drupal;
	}

	public void setDrupal(String drupal) {
		this.drupal = drupal;
	}

	public String getJsui() {
		return jsui;
	}

	public void setJsui(String jsui) {
		this.jsui = jsui;
	}
	
	
}

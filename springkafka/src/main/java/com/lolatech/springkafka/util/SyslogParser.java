package com.lolatech.springkafka.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.dto.UrlDto;

public class SyslogParser {

	public TimelineDto getTimeline(String message) {
		TimelineDto timeline = new TimelineDto();
		
		String msgId = HashUtil.hash(message);
		int position = message.indexOf("{\"\"message");
		if(position == -1) {
			return null;
		}
		
		int endPosition = message.indexOf("}\",");
		if(endPosition == -1) {
			return null;
		}
		
		timeline.setMessageId(msgId);
		String payload = message.substring(position, endPosition);
		timeline.setPayload(payload);
		
		String preMessage = message.substring(0, position - 1);
		String[] fields = preMessage.split(",");
		if(fields.length < 3) {
			return null;
		}
		
		String[] tagFields = fields[2].split(" ");
		if(tagFields.length < 17) {
			return null;
		}
		
		Map<String,String> tags = new HashMap<>();
		tags.put("codeversion", tagFields[6]);
		tags.put("g4_silo", tagFields[7]);
		String[] channelInfo = tagFields[8].split("\\.");
		tags.put("channel", channelInfo[0]);
		tags.put("level_name", channelInfo[1]);
		tags.put("manifestid", tagFields[9]);
		tags.put("transactionid", tagFields[10]);
		tags.put("remoteip",tagFields[11]);
		tags.put("g4nache", tagFields[12]);
		tags.put("drupal_username", tagFields[13]);
		tags.put("drupal_roles", tagFields[14]);
		tags.put("type", tagFields[15]);
		tags.put("mask", tagFields[16]);
		
		timeline.setTags(tags);
		timeline.setTransactionId(tagFields[9]);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("\"dd/MM/yyyy HH:mm:ss");
		String currentDate = fields[1].split("\\.")[0];
		DateTime dt = formatter.parseDateTime(currentDate);
		long milisec = dt.getMillis();
		timeline.setDatetime(milisec);
		
		List<UrlDto> urls = getUrlPayload(message);
		return timeline;
	}
	
	public String getContent(String message) {
		int position = message.indexOf("context");
		if(position == -1) {
			return null;
		}
		
		int endPosition = message.indexOf("}\",");
		String content = message.substring(position + 10, endPosition);
		
		return content.replace("\"\"", "\"");
	}
	
	public List<UrlDto> getUrlPayload(String message) {
		List<String> urlPayload = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
		int startIndex = message.indexOf(":[");
		int endIndex = message.indexOf("],");
		String urlJson = message.substring(startIndex + 1, endIndex + 1);
		urlJson = urlJson.replace("\"\"", "\"");
		UrlDto[] urlData = null;
		
		try {
			urlData = objectMapper.readValue(urlJson, UrlDto[].class);
		} catch (JsonParseException e) {
			System.out.println("json parse exception");
		} catch (JsonMappingException e) {
			System.out.println("json mapping exception");
		} catch (IOException e) {
			System.out.println("json io exception");
		}
		
		for( UrlDto url : urlData ) {
			String ip = url.getIp();
			String key = url.getKey();
			String port = url.getPort();
			String urlContent = getContentFromUrl(ip + ":" + port + "\\" + key);
			urlPayload.add(urlContent);
		}
		
		return Arrays.asList(urlData);
	}
	
	private String getContentFromUrl(String url) {
		String content = null;
		
		return content;
	}
	
}

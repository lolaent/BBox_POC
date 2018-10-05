package com.lolatech.springkafka.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.dto.UrlDto;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;

public class SyslogParser {

	private String environment;
	
	public SyslogParser(String environment) {
		this.environment = environment;
	}
	
	public TimelineDto getTimeline(String message) {
		TimelineDto timeline = null;
		
		timeline = getSyslogMessage(message);
		if(timeline == null) {
			timeline = getSymfonyMessage(message);
		}
		
		return timeline;
	}
	
	/**
	 * Get the timeline object from a Syslog message
	 * @param message
	 * @return timeline object
	 */
	private TimelineDto getSyslogMessage(String message) {
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
		if(channelInfo.length >= 2) {
			tags.put("channel", channelInfo[0]);
			tags.put("level_name", channelInfo[1]);
		}
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
		
		List<String> urlPayload = getUrlPayload(message);
		timeline.setPayloadContent(urlPayload);
		
		return timeline;
	}
	
	/**
	 * Get timeline object from a Symfony message
	 * @param message
	 * @return timeline object
	 */
	private TimelineDto getSymfonyMessage(String message) {
		TimelineDto timeline = new TimelineDto();
		
		message = message.trim();
		String msgId = HashUtil.hash(message);
		int position = message.indexOf("{\"message");
		if(position == -1) {
			return null;
		}
		
		int endPosition = message.length() -1 ;
		
		timeline.setMessageId(msgId);
		String payload = message.substring(position, endPosition);
		timeline.setPayload(payload);
		
		String[] tagFields = message.split(" ");
		if(tagFields.length < 12) {
			return null;
		}
		
		Map<String,String> tags = new HashMap<>();
		tags.put("codeversion", tagFields[1]);
		tags.put("g4_silo", tagFields[2]);
		String[] channelInfo = tagFields[3].split("\\.");		
		if(channelInfo.length >= 2) {
			tags.put("channel", channelInfo[0]);
			tags.put("level_name", channelInfo[1]);
		}
		tags.put("manifestid", tagFields[4]);
		tags.put("transactionid", tagFields[5]);
		tags.put("remoteip",tagFields[6]);
		tags.put("g4nache", tagFields[7]);
		tags.put("drupal_username", tagFields[8]);
		tags.put("drupal_roles", tagFields[9]);
		tags.put("type", tagFields[10]);
		tags.put("mask", tagFields[11]);
		
		timeline.setTags(tags);
		timeline.setTransactionId(tagFields[4]);
		
		List<String> urlPayload = getUrlPayload(tagFields[tagFields.length - 1]);
		timeline.setPayloadContent(urlPayload);
		
		long milisec = DateTime.now().getMillis();
		timeline.setDatetime(milisec);
		
		return timeline;
	}
	
	/**
	 * get the payload content
	 * @param message
	 * @return list containing payload content
	 */
	public List<String> getUrlPayload(String message) {
		List<String> urlPayload = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
		int startIndex = message.indexOf(":[");
		int endIndex = message.indexOf("],");
		if(endIndex == -1) {
			endIndex = message.indexOf("]}");
		}
		if(startIndex == -1 || endIndex == -1) {
			return null;
		}
		String urlJson = message.substring(startIndex + 1, endIndex + 1);
		if( urlJson.contains("\"\"")) {
			urlJson = urlJson.replace("\"\"", "\"");
		}
		UrlDto[] urlData = null;
		
		try {
			urlData = objectMapper.readValue(urlJson, UrlDto[].class);
		} catch (Exception e) {
			System.out.println("Unable to parse json!!!");
			return null;
		}
		

		for( UrlDto url : urlData ) {
			String ip = url.getIp();
			String key = url.getKey();
			String port = url.getPort();
			MemcachedClient connection = null;
			String payloadContent = null;
			
			if( environment.equals("local")) {
				payloadContent = getContentFromUrl(ip, key);
			} else {
				String address = ip + ":" + port;
				try {
					//client = new MemcachedClient(new InetSocketAddress(ip, Integer.parseInt(port)));
					//client = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
					connection = new MemcachedClient( 
					        new ConnectionFactoryBuilder().setDaemon(true).build(), 
					        AddrUtil.getAddresses(address)); 
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				payloadContent = (String) connection.get(key);
				connection.shutdown();
			}
			
			urlPayload.add(payloadContent);
		}
		
		return urlPayload;
	}
	
	/**
	 * 
	 * @param address
	 * @param key
	 * @return
	 */
	private String getContentFromUrl(String address, String key) {
		String content = null;
		String fullUrl = "http://" + address + ":443/cache_wrapper.php?hash=" + key;
		
		try (Scanner scanner = new Scanner(new URL(fullUrl).openStream(), StandardCharsets.UTF_8.toString()))
	    {
	        scanner.useDelimiter("\\A");
	        content = scanner.hasNext() ? scanner.next() : "";
	    } catch (MalformedURLException e) {
			System.out.println("Invalid url!!!!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("an exception occured when reading url content");
			e.printStackTrace();
		}
		
		return content;
	}
}

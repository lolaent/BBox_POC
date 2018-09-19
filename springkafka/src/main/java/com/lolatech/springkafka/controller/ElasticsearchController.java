package com.lolatech.springkafka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lolatech.springkafka.dto.TimelineDto;
import com.lolatech.springkafka.service.ElasticsearchService;

@RestController
@RequestMapping("/manifests")
public class ElasticsearchController {

	@Autowired
	ElasticsearchService service;
	
	@RequestMapping(value="/transactions", method = RequestMethod.GET)
	public List<TimelineDto> getTransactions(@RequestParam("transactionId") String transactionId) {
		return service.findTransactions(transactionId);
	}
}

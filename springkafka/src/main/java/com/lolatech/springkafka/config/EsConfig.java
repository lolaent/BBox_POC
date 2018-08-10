package com.lolatech.springkafka.config;

import java.net.InetAddress;

import javax.annotation.PreDestroy;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.lolatech.springkafka.repository")
public class EsConfig {

	@Value("${elasticsearch.host}")
	private String esHost;
	
	@Value("${elasticsearch.port}")
	private int esPort;
	
	@Value("${elasticsearch.clustername}")
	private String esClusterName;
	
	private TransportClient elasticSearchClient;
	
	@Bean(destroyMethod="close")
	public Client client() throws Exception {
		Settings esSettings = Settings.builder().put("cluster.name", esClusterName).build();
		
		System.out.println("client_configuration created cluster_name %s"  + esClusterName);
		
		elasticSearchClient = new PreBuiltTransportClient(esSettings);
		return elasticSearchClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esHost), esPort));
	}
	
	@Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
	
	@PreDestroy
	public void close() {
		if(elasticSearchClient != null) {
			elasticSearchClient.close();
		}
	}
	
}

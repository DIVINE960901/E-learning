package com.example.springbootreg.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${es.hostname}")
    private String hostname;
//    private String hostname = "localhost";

    @Value("${es.port}")
    private int port;
//    private int port = 9200;



    @Bean
    public ElasticsearchClient elasticsearchClient(){
        RestClient client = RestClient.builder(new HttpHost(hostname,port,"http")).build();
        ElasticsearchTransport transport = new RestClientTransport(client, new JacksonJsonpMapper());
        //System.out.println("成功");
        return new ElasticsearchClient(transport);
    }


}

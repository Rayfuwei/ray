///*
//package com.springbootelasticsearch.config;
//
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Value("${spring.elasticsearch.rest.uris}")
//    private String host;
//
//    @Bean
//    public RestHighLevelClient restHighLevelClient() {
//        return new RestHighLevelClient(RestClient.builder(getHttpHostList(host)));
//    }
//
//    private HttpHost[] getHttpHostList(String hostList) {
//        String[] hosts = hostList.split(",");
//        HttpHost[] httpHostArr = new HttpHost[hosts.length];
//        for (int i = 0; i < hosts.length; i++) {
//            String[] items = hosts[i].split(":");
//            httpHostArr[i] = new HttpHost(items[0], Integer.parseInt(items[1]), "http");
//        }
//        return httpHostArr;
//    }
//
//    // rest low level client
//    @Bean
//    public RestClient restClient() {
//        return RestClient.builder(getHttpHostList(host)).build();
//    }
//}*/

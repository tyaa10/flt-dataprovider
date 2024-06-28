package org.tyaa.training.current.dataprovider.network;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class ApiClientConfig {

    @Bean
    HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }
}

package org.tyaa.training.current.dataprovider.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ObjectMapperBean {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

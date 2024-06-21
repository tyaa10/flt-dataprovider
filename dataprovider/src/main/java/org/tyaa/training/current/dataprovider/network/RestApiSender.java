package org.tyaa.training.current.dataprovider.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.tyaa.training.current.dataprovider.models.interfaces.IExportModel;
import org.tyaa.training.current.dataprovider.network.interfaces.ISender;

import java.util.List;

@Service
public class RestApiSender implements ISender {
    private final ObjectMapper objectMapper;

    public RestApiSender(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void send(List<IExportModel> data, String uri) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(data));
    }
}

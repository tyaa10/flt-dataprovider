package org.tyaa.training.current.dataprovider.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.tyaa.training.current.dataprovider.models.interfaces.IExportModel;
import org.tyaa.training.current.dataprovider.network.interfaces.ISender;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class RestApiSender implements ISender {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public RestApiSender(ObjectMapper objectMapper, HttpClient httpClient) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
    }

    public void send(List<IExportModel> data, String uri) throws IOException, InterruptedException {
        System.out.println(objectMapper.writeValueAsString(data));
        HttpRequest request = HttpRequest.newBuilder()
                // TODO заменить динамически формируемым uri
                .uri(URI.create("http://localhost:8090/lang-trainer/api/import/lessons/word-study"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(data)))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("HTTP response status code: %s", response.statusCode());
        System.out.printf("HTTP response body: %s", response.body());
    }
}

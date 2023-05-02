package br.com.fiap.MiaDBD.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FigmaService {

    @Value("${figma.api.token}")
    private String apiToken;

    @Value("${figma.api.base-url}")
    private String baseUrl;

    private RestTemplate restTemplate;

    public FigmaService() {
        this.restTemplate = new RestTemplate();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Figma-Token", apiToken);
        return headers;
    }

    public ResponseEntity<String> getFile(String fileId) {
        String url = baseUrl + "/v1/files/" + fileId;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getFileNodes(String fileId, String[] nodeIds) {
        String url = baseUrl + "/v1/files/" + fileId + "/nodes?ids=" + String.join(",", nodeIds);
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getImage(String fileId, String[] nodeIds, String format) {
        String url = baseUrl + "/v1/images/" + fileId + "?ids=" + String.join(",", nodeIds) + "&format=" + format;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
}


package br.ifsp.taskservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProjectServiceClient {
    private final RestTemplate restTemplate;
    private final String projectServiceUrl;

    public ProjectServiceClient(
        RestTemplate restTemplate,
        @Value("${monolith.project.service.url}") String projectServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.projectServiceUrl = projectServiceUrl;
    }

    public void validateProject(Long projectId, Long userId, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            
            restTemplate.exchange(
                projectServiceUrl + "/" + projectId + "?userId=" + userId,
                HttpMethod.GET,
                entity,
                Void.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Project validation failed: " + e.getMessage());
        }
    }
}


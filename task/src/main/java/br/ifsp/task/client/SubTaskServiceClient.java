package br.ifsp.task.client;

import br.ifsp.task.dto.SubTaskRequestDTO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SubTaskServiceClient {

    private final RestTemplate restTemplate;
    private final String subTaskServiceUrl;

    public SubTaskServiceClient(
        RestTemplate restTemplate,
        @Value(
            "${task.service.url:http://localhost:8081/api/tasks}"
        ) String taskServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.subTaskServiceUrl = taskServiceUrl.replace("/tasks", "/subtasks");
    }

    public List<Map<String, Object>> listSubTasksByTask(
        Long taskId,
        Long userId,
        String token
    ) {
        HttpHeaders headers = createHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response =
            restTemplate.exchange(
                subTaskServiceUrl + "/task/" + taskId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

        return response.getBody();
    }

    public Map<String, Object> createSubTask(
        Long taskId,
        SubTaskRequestDTO dto,
        Long userId,
        String token
    ) {
        HttpHeaders headers = createHeaders(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubTaskRequestDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            subTaskServiceUrl + "/task/" + taskId,
            HttpMethod.POST,
            entity,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        return response.getBody();
    }

    public Map<String, Object> updateSubTask(
        Long id,
        SubTaskRequestDTO dto,
        Long userId,
        String token
    ) {
        HttpHeaders headers = createHeaders(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SubTaskRequestDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            subTaskServiceUrl + "/" + id,
            HttpMethod.PUT,
            entity,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        return response.getBody();
    }

    public void deleteSubTask(Long id, Long userId, String token) {
        HttpHeaders headers = createHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
            subTaskServiceUrl + "/" + id,
            HttpMethod.DELETE,
            entity,
            Void.class
        );
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        return headers;
    }
}

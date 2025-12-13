package br.ifsp.task.client;

import br.ifsp.task.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class TaskServiceClient {
    private final RestTemplate restTemplate;
    private final String taskServiceUrl;

    public TaskServiceClient(
        RestTemplate restTemplate,
        @Value("${task.service.url:http://localhost:8081/api/tasks}") String taskServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.taskServiceUrl = taskServiceUrl;
    }

    public List<Map<String, Object>> listTasks(Long userId, String token) {
        HttpHeaders headers = createHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
            taskServiceUrl,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );
        
        return response.getBody();
    }

    public Map<String, Object> getTask(Long id, Long userId, String token) {
        HttpHeaders headers = createHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            taskServiceUrl + "/" + id,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        
        return response.getBody();
    }

    public Map<String, Object> createTask(TaskDTO dto, Long userId, String token) {
        HttpHeaders headers = createHeaders(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<TaskDTO> entity = new HttpEntity<>(dto, headers);
        
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            taskServiceUrl,
            HttpMethod.POST,
            entity,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        
        return response.getBody();
    }

    public Map<String, Object> updateTask(Long id, TaskDTO dto, Long userId, String token) {
        HttpHeaders headers = createHeaders(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<TaskDTO> entity = new HttpEntity<>(dto, headers);
        
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            taskServiceUrl + "/" + id,
            HttpMethod.PUT,
            entity,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        
        return response.getBody();
    }

    public void deleteTask(Long id, Long userId, String token) {
        HttpHeaders headers = createHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        
        restTemplate.exchange(
            taskServiceUrl + "/" + id,
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


package br.ifsp.taskservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {
    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public UserServiceClient(
        RestTemplate restTemplate,
        @Value("${monolith.user.service.url}") String userServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public void validateUser(Long userId, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            
            restTemplate.exchange(
                userServiceUrl + "/" + userId,
                HttpMethod.GET,
                entity,
                Void.class
            );
        } catch (Exception e) {
            // User validation - pode ser opcional dependendo da implementação
            // Por enquanto apenas logamos o erro
        }
    }
}


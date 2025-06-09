package md.teroschin.kafkaproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.teroschin.core.dto.UserEvent;
import md.teroschin.kafkaproducer.dto.RecommendResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final RestTemplate restTemplate;
    private final EventProducer eventProducer;

    @Value("${ml.service.base-url}")
    private String mlBaseUrl;

    public RecommendResponseDto getRecommendations(String userId, String productId) {
        String uri = UriComponentsBuilder.fromUriString(mlBaseUrl)
                .queryParam("user_id", userId)
                .queryParam("product_id", productId)
                .toUriString();

        try {
            ResponseEntity<RecommendResponseDto> response =
                    restTemplate.getForEntity(uri, RecommendResponseDto.class);

            UserEvent event = new UserEvent(userId, productId, "view", response.getBody().recommendations());
            eventProducer.sendEvent(event);
            return response.getBody();

        } catch (HttpClientErrorException e) {
            log.warn("ML error: {}", e.getResponseBodyAsString());
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "ML service failed");
        }
    }
}

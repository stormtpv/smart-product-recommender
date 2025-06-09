package md.teroschin.kafkaproducer.service;

import md.teroschin.kafkaproducer.dto.RecommendResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EventProducer eventProducer;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(recommendationService, "mlBaseUrl", "http://ml-service:8000/recommend");
    }

    @Test
    @DisplayName("getRecommendations успешно возвращает результат и отправляет событие")
    void shouldCallMLServiceAndSendKafkaEvent() {
        String userId = "1";
        String productId = "123";
        List<String> recommendations = List.of("prod-1", "prod-2");
        RecommendResponseDto dto = new RecommendResponseDto(recommendations);

        String expectedUrl = "http://ml-service:8000/recommend?user_id=1&product_id=123";
        when(restTemplate.getForEntity(expectedUrl, RecommendResponseDto.class))
                .thenReturn(new ResponseEntity<>(dto, HttpStatus.OK));

        RecommendResponseDto result = recommendationService.getRecommendations(userId, productId);

        assertEquals(recommendations, result.recommendations());
        verify(eventProducer).sendEvent(argThat(event ->
                event.userId().equals(userId) &&
                        event.productId().equals(productId) &&
                        event.recommendedProducts().equals(recommendations)
        ));
    }

    @Test
    @DisplayName("getRecommendations выбрасывает исключение, если ML-сервис вернул ошибку")
    void shouldThrowWhenMLFails() {
        String userId = "2";
        String productId = "456";

        when(restTemplate.getForEntity(anyString(), eq(RecommendResponseDto.class)))
                .thenThrow(HttpClientErrorException.create(HttpStatus.UNPROCESSABLE_ENTITY, "422", null, null, null));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                recommendationService.getRecommendations(userId, productId));

        assertEquals(HttpStatus.BAD_GATEWAY, exception.getStatusCode());
        verify(eventProducer, never()).sendEvent(any());
    }
}
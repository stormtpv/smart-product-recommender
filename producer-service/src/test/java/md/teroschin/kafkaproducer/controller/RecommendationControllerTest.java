package md.teroschin.kafkaproducer.controller;

import md.teroschin.kafkaproducer.dto.RecommendResponseDto;
import md.teroschin.kafkaproducer.service.RecommendationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    @Test
    @DisplayName("GET /recommendation возвращает рекомендации")
    void recommend_returnsRecommendations() throws Exception {
        List<String> recommendations = List.of("prod-101", "prod-202", "prod-303");
        RecommendResponseDto response = new RecommendResponseDto(recommendations);

        Mockito.when(recommendationService.getRecommendations("1", "100"))
                .thenReturn(response);

        mockMvc.perform(get("/recommendation")
                        .param("userId", "1")
                        .param("productId", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendations", hasSize(3)))
                .andExpect(jsonPath("$.recommendations[0]", startsWith("prod-")));
    }
}

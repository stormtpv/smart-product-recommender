package md.teroschin.kafkaproducer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.teroschin.kafkaproducer.dto.RecommendResponseDto;
import md.teroschin.kafkaproducer.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public RecommendResponseDto recommend(@RequestParam("userId") String userId,
                                          @RequestParam("productId") String productId) {
        log.info("Received userId=" + userId + ", productId=" + productId);
        RecommendResponseDto recommendations = recommendationService.getRecommendations(userId, productId);
        log.info("Returned: " + recommendations);
        return recommendations;
    }
}

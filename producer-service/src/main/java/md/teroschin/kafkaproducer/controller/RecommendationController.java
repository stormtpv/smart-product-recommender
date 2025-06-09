package md.teroschin.kafkaproducer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.teroschin.kafkaproducer.dto.RecommendResponseDto;
import md.teroschin.kafkaproducer.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public RecommendResponseDto recommend(@RequestParam("userId") String userId,
                                          @RequestParam("productId") String productId) {
        return recommendationService.getRecommendations(userId, productId);
    }
}

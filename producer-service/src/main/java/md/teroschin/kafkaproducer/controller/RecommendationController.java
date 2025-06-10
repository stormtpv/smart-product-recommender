package md.teroschin.kafkaproducer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.teroschin.kafkaproducer.dto.RecommendResponseDto;
import md.teroschin.kafkaproducer.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public RecommendResponseDto recommend(@RequestParam("userId") String userId,
                                          @RequestParam("productId") String productId) {
        System.out.println("Received userId=" + userId + ", productId=" + productId);
//        RecommendResponseDto recommendations = recommendationService.getRecommendations(userId, productId);
//        System.out.println("Returned: " + recommendations);
        return new RecommendResponseDto(List.of("user1", "user2", "user3"));
    }
}

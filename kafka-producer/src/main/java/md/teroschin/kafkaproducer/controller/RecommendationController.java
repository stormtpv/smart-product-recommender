package md.teroschin.kafkaproducer.controller;

import lombok.RequiredArgsConstructor;
import md.teroschin.kafkaproducer.dto.UserEvent;
import md.teroschin.kafkaproducer.service.EventProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final EventProducer eventProducer;

    @PostMapping
    public ResponseEntity<String> recommend(@RequestParam("userId") String userId,
                                            @RequestParam("productId") String productId) {
        UserEvent event = new UserEvent(userId, productId, "view");
        eventProducer.sendEvent(event);
        return ResponseEntity.ok("Event sent to Kafka");
    }
}

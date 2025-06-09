package md.teroschin.kafkaproducer.service;

import lombok.RequiredArgsConstructor;
import md.teroschin.core.constants.Topics;
import md.teroschin.core.dto.UserEvent;
import md.teroschin.core.util.JsonUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(UserEvent userEvent) {
        String message = JsonUtils.toJson(userEvent);
        kafkaTemplate.send(Topics.USER_EVENTS, message);
    }
}

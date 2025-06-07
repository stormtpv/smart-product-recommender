package md.teroschin.kafkaproducer.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import md.teroschin.kafkaproducer.dto.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(UserEvent userEvent) {
        String message = new Gson().toJson(userEvent);
        kafkaTemplate.send("user-events", message);
    }
}

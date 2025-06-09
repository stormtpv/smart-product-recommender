package md.teroschin.kafkaconsumer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.teroschin.core.constants.Topics;
import md.teroschin.core.dto.UserEvent;
import md.teroschin.core.util.JsonUtils;
import md.teroschin.kafkaconsumer.domain.EventLog;
import md.teroschin.kafkaconsumer.repository.EventLogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {

    private final EventLogRepository eventLogRepository;

    @KafkaListener(topics = Topics.USER_EVENTS, groupId = "event-consumer-group")
    public void consume(String message) {
        try {
            UserEvent event = JsonUtils.fromJson(message, UserEvent.class);
            EventLog eventLog = EventLog.builder()
                    .userId(event.userId())
                    .productId(event.productId())
                    .action(event.action())
                    .recommendations(event.recommendedProducts())
                    .timestamp(LocalDateTime.now())
                    .build();
            eventLogRepository.save(eventLog);
            log.info("Saved event to DB: {}", message);
        } catch (Exception e) {
            log.error("Failed to process message: {}", message, e);
        }
    }
}

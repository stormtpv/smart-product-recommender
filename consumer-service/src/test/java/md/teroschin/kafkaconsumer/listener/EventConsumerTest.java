package md.teroschin.kafkaconsumer.listener;

import md.teroschin.kafkaconsumer.domain.EventLog;
import md.teroschin.kafkaconsumer.repository.EventLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class EventConsumerTest {

    @Test
    void consume_shouldParseJsonAndSaveEventLog() {
        EventLogRepository mockRepo = Mockito.mock(EventLogRepository.class);
        EventConsumer consumer = new EventConsumer(mockRepo);

        String json = """
                {
                    "userId": "42",
                    "productId": "1001",
                    "action": "click",
                    "recommendedProducts": ["201", "202"]
                }
                """;

        consumer.consume(json);

        ArgumentCaptor<EventLog> captor = ArgumentCaptor.forClass(EventLog.class);
        Mockito.verify(mockRepo).save(captor.capture());

        EventLog saved = captor.getValue();
        assertThat(saved.getUserId()).isEqualTo("42");
        assertThat(saved.getProductId()).isEqualTo("1001");
        assertThat(saved.getAction()).isEqualTo("click");
        assertThat(saved.getRecommendations()).containsExactly("201", "202");
    }
}
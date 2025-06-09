package md.teroschin.kafkaproducer.service;

import md.teroschin.core.constants.Topics;
import md.teroschin.core.dto.UserEvent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EventProducerTest {

    @Test
    void sendEvent_shouldSendJsonToKafka() {
        KafkaTemplate<String, String> mockKafka = mock(KafkaTemplate.class);
        EventProducer producer = new EventProducer(mockKafka);

        UserEvent event = new UserEvent("u1", "p1", "click", List.of("r1", "r2"));

        producer.sendEvent(event);

        ArgumentCaptor<String> msgCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockKafka).send(eq(Topics.USER_EVENTS), msgCaptor.capture());

        String sentJson = msgCaptor.getValue();

        // Пример простой проверки содержимого JSON
        assertThat(sentJson).contains("\"userId\":\"u1\"");
        assertThat(sentJson).contains("\"recommendedProducts\":[\"r1\",\"r2\"]");
    }
}
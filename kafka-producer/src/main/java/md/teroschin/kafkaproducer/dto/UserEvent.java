package md.teroschin.kafkaproducer.dto;

public record UserEvent(String userId, String productId, String action) {
}

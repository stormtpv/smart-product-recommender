package md.teroschin.core.dto;

import java.util.List;

public record UserEvent(String userId, String productId, String action, List<String> recommendedProducts) {
}

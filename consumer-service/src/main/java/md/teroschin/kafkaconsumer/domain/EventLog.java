package md.teroschin.kafkaconsumer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String productId;
    private String action;

    @ElementCollection
    private List<String> recommendations;
    private LocalDateTime timestamp = LocalDateTime.now();
}

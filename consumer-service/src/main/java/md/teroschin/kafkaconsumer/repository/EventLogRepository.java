package md.teroschin.kafkaconsumer.repository;

import md.teroschin.kafkaconsumer.domain.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
}

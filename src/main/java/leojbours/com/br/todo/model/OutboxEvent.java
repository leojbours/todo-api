package leojbours.com.br.todo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "transactional_outbox")
@Getter @Setter
public class OutboxEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, length = 36)
  private String id;

  @Column(name = "topic", nullable = false)
  private String topic;

  @Column(name = "entity_id", nullable = false, length = 32)
  private String entityId;

  @Column(name = "event_name", nullable = false, length = 128)
  private String eventName;

  @Column(name = "event_data", nullable = false, columnDefinition = "JSON")
  @JdbcTypeCode(SqlTypes.JSON)
  private String eventData;

  @Column(name = "registered_at", nullable = false)
  private Instant registeredAt;

  @Column(name = "processed_at")
  private Instant processedAt;
}

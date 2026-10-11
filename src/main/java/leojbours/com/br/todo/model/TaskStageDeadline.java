package leojbours.com.br.todo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_stage_deadlines")
@Getter @Setter
public class TaskStageDeadline {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "task_id", nullable = false)
  private Task task;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "stage_id", nullable = false)
  private Stage stage;

  @Column(name = "due_at")
  private Instant dueAt;

  @Column(name = "entered_stage_at", nullable = false)
  private Instant enteredStageAt;

  @Column(name = "left_stage_at")
  private Instant leftStageAt;
}

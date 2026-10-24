package leojbours.com.br.todo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import leojbours.com.br.todo.model.valueobject.Email;
import leojbours.com.br.todo.model.valueobject.converter.EmailConverter;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(name = "UK_users__username", columnNames = "username"),
    @UniqueConstraint(name = "UK_users__email", columnNames = "email")
})
@Getter @Setter
public class User implements SoftDeletable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email", nullable = false)
  @Convert(converter = EmailConverter.class)
  private Email email;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "deadline_alert_hours", nullable = false)
  private Integer deadlineAlertHours = 24;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "deleted_at")
  private Instant deletedAt;

  @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
  private List<FellowGroup> managedGroups = new ArrayList<>();

  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
  private List<Project> projects = new ArrayList<>();

  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
  private List<Board> boards = new ArrayList<>();

  @OneToMany(mappedBy = "responsible", fetch = FetchType.LAZY)
  private List<Task> tasks = new ArrayList<>();
}

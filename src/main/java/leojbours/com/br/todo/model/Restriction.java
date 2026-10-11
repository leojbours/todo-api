package leojbours.com.br.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restrictions")
@Getter @Setter
public class Restriction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToMany
  @JoinTable(
      name = "restrictions_users",
      joinColumns = @JoinColumn(name = "restriction_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> users = new ArrayList<>();
}

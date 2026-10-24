package leojbours.com.br.todo.model;

import java.time.Instant;

public interface SoftDeletable {
  void setDeletedAt(Instant deletedAt);
}

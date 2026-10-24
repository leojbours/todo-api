package leojbours.com.br.todo.repository;

import java.time.Instant;
import leojbours.com.br.todo.model.SoftDeletable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SoftDeleteRepository<T extends SoftDeletable, ID> extends JpaRepository<T, ID> {

  @Override
  @Transactional
  default void delete(T entity) {
    entity.setDeletedAt(Instant.now());
    save(entity);
  }
}

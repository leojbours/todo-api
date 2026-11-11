package leojbours.com.br.todo.repository;

import java.util.Optional;
import leojbours.com.br.todo.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends SoftDeleteRepository<User, Integer> {

  Optional<User> findByUsername(String username);
}

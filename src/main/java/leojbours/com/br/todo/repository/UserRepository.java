package leojbours.com.br.todo.repository;

import leojbours.com.br.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}

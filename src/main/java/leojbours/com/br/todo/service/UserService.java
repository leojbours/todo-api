package leojbours.com.br.todo.service;

import java.time.Instant;
import leojbours.com.br.todo.dto.request.UserRegisterDto;
import leojbours.com.br.todo.mapper.UserMapper;
import leojbours.com.br.todo.model.User;
import leojbours.com.br.todo.repository.UserRepository;
import leojbours.com.br.todo.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordUtil passwordUtil;

  public User newUser(UserRegisterDto userRegisterDto) {
    User user = userMapper.toEntity(userRegisterDto);
    user.setPassword(passwordUtil.hashPassword(userRegisterDto.password()));
    user.setCreatedAt(Instant.now());
    return userRepository.save(user);
  }
}

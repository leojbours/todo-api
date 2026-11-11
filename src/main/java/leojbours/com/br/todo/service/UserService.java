package leojbours.com.br.todo.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import leojbours.com.br.todo.dto.request.UserRegisterDto;
import leojbours.com.br.todo.dto.response.UserResponseDto;
import leojbours.com.br.todo.exception.UserNotFoundException;
import leojbours.com.br.todo.mapper.UserMapper;
import leojbours.com.br.todo.model.User;
import leojbours.com.br.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public User register(UserRegisterDto userRegisterDto) {
    User user = userMapper.toEntity(userRegisterDto);
    user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
    user.setCreatedAt(Instant.now());
    user.setRole("user");
    return userRepository.save(user);
  }

  @Override
  @NonNull
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    Optional<User> fetchedUser = userRepository.findByUsername(username);

    return fetchedUser.map(user -> {
      List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(user.getRole()));
      return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public UserResponseDto getUserById(int id) {
    return userRepository.findById(id).map(userMapper::toResponseDto)
        .orElseThrow(() -> new UserNotFoundException("User not found for id: " + id));
  }
}

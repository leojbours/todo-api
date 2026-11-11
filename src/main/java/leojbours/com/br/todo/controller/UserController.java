package leojbours.com.br.todo.controller;

import leojbours.com.br.todo.dto.request.UserRegisterDto;
import leojbours.com.br.todo.dto.response.UserResponseDto;
import leojbours.com.br.todo.mapper.UserMapper;
import leojbours.com.br.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterDto userRegisterDto) {
    return ResponseEntity.ok(userMapper.toResponseDto(userService.register(userRegisterDto)));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id) {
    return ResponseEntity.ok(userService.getUserById(id));
  }
}

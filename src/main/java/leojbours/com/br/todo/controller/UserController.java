package leojbours.com.br.todo.controller;

import leojbours.com.br.todo.dto.request.UserRegisterDto;
import leojbours.com.br.todo.dto.response.UserResponseDto;
import leojbours.com.br.todo.mapper.UserMapper;
import leojbours.com.br.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/users")
  public ResponseEntity<UserResponseDto> newUser(@RequestBody UserRegisterDto userRegisterDto) {
    return ResponseEntity.ok(userMapper.toResponseDto(userService.newUser(userRegisterDto)));
  }
}

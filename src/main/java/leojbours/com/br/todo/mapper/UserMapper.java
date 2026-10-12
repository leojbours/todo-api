package leojbours.com.br.todo.mapper;

import leojbours.com.br.todo.config.DefaultMapperConfig;
import leojbours.com.br.todo.dto.request.UserRegisterDto;
import leojbours.com.br.todo.dto.response.UserResponseDto;
import leojbours.com.br.todo.mapper.annotation.IgnoreAuditFields;
import leojbours.com.br.todo.model.User;
import leojbours.com.br.todo.model.valueobject.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefaultMapperConfig.class)
public interface UserMapper {

  @IgnoreAuditFields
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "managedGroups", ignore = true)
  @Mapping(target = "projects", ignore = true)
  @Mapping(target = "boards", ignore = true)
  @Mapping(target = "tasks", ignore = true)
  User toEntity(UserRegisterDto dto);

  UserResponseDto toResponseDto(User user);

  default Email toEmail(String email) {
    return new Email(email);
  }

  default String fromEmail(Email email) {
    return email.toString();
  }
}

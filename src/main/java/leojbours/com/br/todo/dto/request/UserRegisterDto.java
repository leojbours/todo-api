package leojbours.com.br.todo.dto.request;

public record UserRegisterDto(
    String username,
    String password,
    String email,
    String fullName,
    Integer deadlineAlertHours
) {}

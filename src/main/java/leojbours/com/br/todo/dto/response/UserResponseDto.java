package leojbours.com.br.todo.dto.response;

public record UserResponseDto(
    String username,
    String email,
    String fullName,
    String deadlineAlertHours
) {

}

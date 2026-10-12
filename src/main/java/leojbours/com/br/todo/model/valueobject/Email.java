package leojbours.com.br.todo.model.valueobject;

import java.util.Locale;
import java.util.regex.Pattern;
import leojbours.com.br.todo.exception.InvalidEmailException;
import org.jspecify.annotations.NonNull;

public record Email(String email) {
  private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

  public Email {
    if (email == null || email.isBlank()) {
      throw new InvalidEmailException("Email cannot be null or blank");
    }

    email = email.trim().toLowerCase(Locale.ROOT);

    if (!PATTERN.matcher(email).matches()) {
      throw new InvalidEmailException("Invalid email format: " + email);
    }
  }

  @Override
  public @NonNull String toString() {
    return email;
  }
}

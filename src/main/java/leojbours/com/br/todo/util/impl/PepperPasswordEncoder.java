package leojbours.com.br.todo.util.impl;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PepperPasswordEncoder implements PasswordEncoder {

  private final PasswordEncoder delegateEncoder;
  private final String passwordPepper;

  public PepperPasswordEncoder(PasswordEncoder delegateEncoder, String passwordPepper) {
    this.delegateEncoder = delegateEncoder;
    this.passwordPepper = passwordPepper;
  }

  @Override
  public @Nullable String encode(@Nullable CharSequence rawPassword) {
    if ( rawPassword != null ) {
      return delegateEncoder.encode(rawPassword + passwordPepper);
    }

    return null;
  }

  @Override
  public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
    if (rawPassword == null || encodedPassword == null || encodedPassword.isEmpty()) {
      return false;
    }

    return delegateEncoder.matches(rawPassword + passwordPepper, encodedPassword);
  }
}

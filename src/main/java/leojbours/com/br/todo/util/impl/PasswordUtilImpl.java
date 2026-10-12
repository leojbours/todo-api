package leojbours.com.br.todo.util.impl;

import leojbours.com.br.todo.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtilImpl implements PasswordUtil {
  @Value( "${app.password.pepper}")
  private String passwordPepper;

  @Override
  public String hashPassword(String password) {
    Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 2, 19456, 4);
    return encoder.encode(password + passwordPepper);
  }

  @Override
  public boolean verifyPassword(String password, String hashedPassword) {
    return hashedPassword.equals(hashPassword(password));
  }
}

package leojbours.com.br.todo.config;

import leojbours.com.br.todo.util.impl.PepperPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) {
    // Spring Security temporarily disabled
    return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
               .csrf(AbstractHttpConfigurer::disable)
               .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder(@Value("${app.password.pepper}") String passwordPepper) {
    Argon2PasswordEncoder delegateEncoder = new Argon2PasswordEncoder(16, 32, 2, 19456, 4);
    return new PepperPasswordEncoder(delegateEncoder, passwordPepper);
  }
}

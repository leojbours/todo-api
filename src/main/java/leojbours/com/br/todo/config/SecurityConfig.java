package leojbours.com.br.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
}

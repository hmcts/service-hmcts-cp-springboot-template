package uk.gov.hmcts.cp.casedoc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  @ConditionalOnProperty(prefix = "security.jwt", name = "enabled", havingValue = "false", matchIfMissing = true)
  SecurityFilterChain devChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.authorizeHttpRequests(auth -> auth
      .requestMatchers("/actuator/health/**", "/actuator/prometheus", "/swagger-ui.html",
        "/swagger-ui/**", "/v3/api-docs/**").permitAll()
      .requestMatchers(HttpMethod.GET, "/api/v1/hello").permitAll()
      .anyRequest().authenticated());
    http.httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  @ConditionalOnProperty(prefix = "security.jwt", name = "enabled", havingValue = "true")
  SecurityFilterChain jwtChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable());
    http.authorizeHttpRequests(auth -> auth
      .requestMatchers("/actuator/health/**", "/actuator/prometheus", "/swagger-ui.html",
        "/swagger-ui/**", "/v3/api-docs/**").permitAll()
      .anyRequest().authenticated());
    http.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
    return http.build();
  }
}

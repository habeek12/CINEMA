package com.cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable()) // JSP + forms simples
        .authorizeHttpRequests(auth -> auth
            // .requestMatchers(
            // "/login",
            // "/css/**",
            // "/js/**",
            // "/img/**")
            // .permitAll()
            // .anyRequest().authenticated())
            // .formLogin(form -> form
            // .loginPage("/login")
            // .defaultSuccessUrl("/movies", true)
            // .failureUrl("/login?error=true")
            // .permitAll())
            // .logout(logout -> logout
            // .logoutSuccessUrl("/login?logout"));
            // Esta línea permite el acceso sin autenticación a todas las rutas, comentar
            // para habilitar seguridad
            .anyRequest().permitAll());
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

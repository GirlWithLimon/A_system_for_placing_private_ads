package com.example.config;

import com.example.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/register").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/").permitAll()

                        // Просмотр объявлений - без авторизации
                        .requestMatchers(HttpMethod.GET, "/api/advertisements").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/advertisements/**").permitAll()
                        // Удаление объявления - только ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/advertisements/**").hasRole("ADMIN")

                        // Получение своих объявлений
                        .requestMatchers(HttpMethod.GET, "/api/my/advertisements").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/my/advertisements/**").hasAnyRole("USER", "ADMIN")
                        // Создание объявления
                        .requestMatchers(HttpMethod.POST, "/api/my/advertisements").hasRole("USER")
                        // Изменение объявления
                        .requestMatchers(HttpMethod.PATCH, "/api/my/advertisements/**").hasRole("USER")
                        // Удаление объявления                        .requestMatchers(HttpMethod.DELETE, "/api/my/advertisements/**").hasAnyRole("USER", "ADMIN")
                        // Проплата топа (byestatus) - только ADMIN
                        .requestMatchers(HttpMethod.PATCH, "/api/my/advertisements/*/byestatus").hasRole("ADMIN")
                        // Изменение профиля
                        .requestMatchers(HttpMethod.GET, "/api/my/profile").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/my/profile").hasRole("USER")

                        // Получение чатов и сообщений
                        .requestMatchers(HttpMethod.GET, "/api/chats").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/chats/**").hasAnyRole("USER", "ADMIN")
                        // Создание чата и сообщений
                        .requestMatchers(HttpMethod.POST, "/api/chats").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/chats/**").hasRole("USER")
                        // Изменение сообщения
                        .requestMatchers(HttpMethod.PUT, "/api/chats/**").hasRole("USER")
                        // Удаление чата и сообщений
                        .requestMatchers(HttpMethod.DELETE, "/api/chats/**").hasRole("USER")

                        // Просмотр пользователей и оценок - без авторизации
                        .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                        // Создание оценки - только авторизованные
                        .requestMatchers(HttpMethod.POST, "/api/users/*/score").hasAnyRole("USER", "ADMIN")
                        // Изменение оценки - только владелец через проверку в контроллере
                        .requestMatchers(HttpMethod.PUT, "/api/users/*/score/*").hasAnyRole("USER", "ADMIN")
                        // Удаление своей оценки
                        .requestMatchers(HttpMethod.DELETE, "/api/users/*/score/*").hasAnyRole("USER", "ADMIN")
                        // Удаление любой оценки (ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/users/*/score/*/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\"}");
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Access denied\"}");
        };
    }
}
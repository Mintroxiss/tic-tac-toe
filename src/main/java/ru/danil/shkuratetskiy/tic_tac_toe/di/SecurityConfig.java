package ru.danil.shkuratetskiy.tic_tac_toe.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.model.AuthFilter;
import ru.danil.shkuratetskiy.tic_tac_toe.domain.service.AuthService;

@Configuration
public class SecurityConfig {
    private final AuthService authService;

    @Autowired
    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new AuthFilter(authService),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}



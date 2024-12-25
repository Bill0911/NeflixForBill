package com.example.netflix.config;

import com.example.netflix.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private JwtAuthenticationFilter jwtFilter;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/login" , "/api/languages", "/api/genres/**", "/api/users/register", "/api/movies/**", "/api/series/**", "/api/users/lang", "/api/users/add-profile", "/api/profiles/watch-movie", "/api/profiles/watch-series" ,"/api/genre-for-series/**", "/api/preferences/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public JwtAuthenticationFilter jwtFilter(@Lazy JwtUtil jwtUtil, @Lazy CustomUserDetailsService userDetailsService) {
//        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
//    }
}

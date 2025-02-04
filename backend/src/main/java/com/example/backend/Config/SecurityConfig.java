package com.example.backend.Config;

import com.example.backend.Service.AdminDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Ensures password is hashed properly
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AdminDetailsService adminDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // Ensure password comparison is done properly
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/register","/api/admin/login",
                                "/api/skills","/api/skills/get","/api/skills/get/{id}","/api/skills/put/{id}",
                                "/api/skills/del/{id}","/api/educations/get","/api/educations/get/{id}",
                                "/api/educations/create","/api/educations/recent","/api/educations/update/{id}","/api/educations/delete/{id}",
                                "/api/projects","/api/projects/get","/api/projects/get/{id}","/api/projects/{id}",
                                "/api/projects/del/{id}", "/api/users","/api/users/get","/api/users/{id}","/api/users/get/{id}",
                                "/api/users/put/{id}","/api/users/del/{id}","/api/users/recent").permitAll()

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Enable Basic Authentication
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Apply CORS configuration

        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Allow requests from React app on localhost:3000
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        config.setAllowCredentials(true); // Allow credentials (cookies, etc.)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply to all endpoints
        return source;
    }
}

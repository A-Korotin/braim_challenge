package org.simbir_soft.braim_challenge.config;

import org.apache.catalina.User;
import org.simbir_soft.braim_challenge.config.handler.CustomAccessDeniedHandler;
import org.simbir_soft.braim_challenge.domain.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers("/registration").anonymous()
                .requestMatchers(HttpMethod.DELETE, "/accounts/*").authenticated()
                .requestMatchers(HttpMethod.GET, "/accounts/search").hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/areas").authenticated()
                .requestMatchers("/areas/*").hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE,"/animals/*/types/*").hasAnyRole(UserRole.ADMIN.name(), UserRole.CHIPPER.name())
                .requestMatchers(HttpMethod.POST, "/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.CHIPPER.name())
                .requestMatchers(HttpMethod.PUT, "/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.CHIPPER.name())
                .requestMatchers(HttpMethod.DELETE, "/**").hasRole(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(customAccessDeniedHandler())
                .and()
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

}

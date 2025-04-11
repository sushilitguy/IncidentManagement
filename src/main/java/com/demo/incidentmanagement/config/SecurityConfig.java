package com.demo.incidentmanagement.config;

import com.demo.incidentmanagement.config.filter.AppJWTFilter;
import com.demo.incidentmanagement.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class to handle security in application using JWT
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AppUserDetailsService userDetailsService;
    @Autowired
    private AppJWTFilter jwtFilter;

    /**
     * Creates bean for SecurityFilterChain
     * @param security Security object
     * @return SecurityFilterChain object
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(customizer -> customizer.disable());
        security.authorizeHttpRequests(request -> request
                .requestMatchers("/","/login","/register","/forgot-password").permitAll()
                .anyRequest().authenticated());
        security.httpBasic(Customizer.withDefaults()); // To be used for enabling authentication for REST API
        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Makes sessions stateless
        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }

    /**
     * Creates bean for AuthenticationProvider to handle User Authentication
     * @return AuthenticationProvider object
     */
    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    /**
     * Creates bean for AuthenticationManager
     * @param config AuthenticationConfiguration Object
     * @return AuthenticationManager object
     * @throws Exception
     */
    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
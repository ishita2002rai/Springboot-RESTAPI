package com.example.green;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
   
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}adminpassword") 
                .roles("ADMIN");
    }
    
    
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/api/auth/signup").permitAll() // Allow unauthenticated access to the signup endpoint
            .requestMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable();
    }
    


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}

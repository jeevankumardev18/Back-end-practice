package com.wrogn.task.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig
{
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws  Exception
    {
        httpSecurity
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/swagger-ui/**",
                                                      "/v3/api-docs/**",
                                                         "/api/auth/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
                                .anyRequest().authenticated())

                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class);




        return httpSecurity.build();

    }


}

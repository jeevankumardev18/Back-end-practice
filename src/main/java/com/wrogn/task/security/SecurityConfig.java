package com.wrogn.task.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig
{

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
                                .permitAll().anyRequest().authenticated())

                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);




        return httpSecurity.build();

    }


}

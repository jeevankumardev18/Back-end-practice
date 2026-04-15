package com.wrogn.task.controller;

import com.wrogn.task.dto.request.LoginRequestDto;
import com.wrogn.task.dto.request.RefreshTokenRequestDto;
import com.wrogn.task.dto.response.LoginResponseDto;
import com.wrogn.task.dto.response.RefreshTokenResponseDto;
import com.wrogn.task.entity.UserEntity;
import com.wrogn.task.exceptions.ResourceNotFoundException;
import com.wrogn.task.repository.UserRepository;
import com.wrogn.task.security.JwtUtil;
import com.wrogn.task.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(UserRepository userRepository,JwtUtil jwtUtil,AuthService authService)
    {
        this.userRepository=userRepository;
        this.jwtUtil=jwtUtil;
        this.authService=authService;
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto)
    {

     return ResponseEntity.ok(authService.loginToken(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto dto) {

        return ResponseEntity.ok(authService.refreshToken(dto));
    }

}

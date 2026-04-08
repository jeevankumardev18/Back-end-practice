package com.wrogn.task.controller;

import com.wrogn.task.dto.LoginRequestDto;
import com.wrogn.task.entity.UserEntity;
import com.wrogn.task.exceptions.ResourceNotFoundException;
import com.wrogn.task.repository.UserRepository;
import com.wrogn.task.security.JwtUtil;
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

    public AuthController(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto)
    {
     UserEntity user= userRepository.findByEmailCustom(dto.getEmail())
              .orElseThrow(()-> new ResourceNotFoundException("Invalid email"));

     if(!user.getPassword() .equals(dto.getPassword()))
     {
         throw new RuntimeException("Invalid password");
     }

     String token=JwtUtil.generateToken(user.getEmail(),user.getRole().name());
     return ResponseEntity.ok(token);
    }

}

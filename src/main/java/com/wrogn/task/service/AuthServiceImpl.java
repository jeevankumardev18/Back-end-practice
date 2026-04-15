package com.wrogn.task.service;

import com.wrogn.task.dto.request.LoginRequestDto;
import com.wrogn.task.dto.request.RefreshTokenRequestDto;
import com.wrogn.task.dto.response.LoginResponseDto;
import com.wrogn.task.dto.response.RefreshTokenResponseDto;
import com.wrogn.task.entity.RefreshTokenEntity;
import com.wrogn.task.entity.UserEntity;
import com.wrogn.task.repository.RefreshTokenRepository;
import com.wrogn.task.repository.UserRepository;
import com.wrogn.task.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Service
public class AuthServiceImpl implements AuthService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public LoginResponseDto loginToken(LoginRequestDto loginRequestDto)
    {

        UserEntity user=userRepository.findByEmailCustom(loginRequestDto.getEmail())
                .orElseThrow(()->new RuntimeException("Invalid Email"));

        if(!passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword()))
        {
            throw new RuntimeException("Invalid password");
        }
        String accessToken=jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        String refreshToken= jwtUtil.generateRefreshToken(user.getEmail());
        refreshTokenRepository.deleteByEmail(user.getEmail());
        RefreshTokenEntity entity=new RefreshTokenEntity();
        entity.setToken(refreshToken);
        entity.setEmail(user.getEmail());
        entity.setExpiryDate(new Date(System.currentTimeMillis()+7 * 24 * 60 * 60 * 1000));
        refreshTokenRepository.save(entity);
        return new LoginResponseDto(accessToken,
                refreshToken,
                user.getEmail(),
                user.getRole().name(),
                1800000);
    }

    @Override
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto)
    {
        String refreshToken=refreshTokenRequestDto.getRefreshToken();
        if(!jwtUtil.validateToken(refreshToken))
        {
            throw new RuntimeException("Invalid refresh token");
        }
        String email = jwtUtil.extractEmail(refreshToken);


        RefreshTokenEntity savedToken = refreshTokenRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new RuntimeException("Token mismatch");
        }

        if (savedToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Refresh token expired");
        }

        // Get user role
        UserEntity user = userRepository
                .findByEmailCustom(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new RefreshTokenResponseDto(newAccessToken);

    }
}

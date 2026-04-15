package com.wrogn.task.service;

import com.wrogn.task.dto.request.LoginRequestDto;
import com.wrogn.task.dto.request.RefreshTokenRequestDto;
import com.wrogn.task.dto.response.LoginResponseDto;
import com.wrogn.task.dto.response.RefreshTokenResponseDto;

public interface AuthService
{
    LoginResponseDto  loginToken(LoginRequestDto loginRequestDto);
    RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);


}


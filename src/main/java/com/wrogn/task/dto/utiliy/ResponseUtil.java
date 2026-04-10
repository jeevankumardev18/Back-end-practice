package com.wrogn.task.dto.utiliy;

import com.wrogn.task.dto.response.ApiResponse;

import java.time.LocalDateTime;

public class ResponseUtil
{
    public static <T> ApiResponse<T> success(String message, T data)
    {
        return new ApiResponse<>(
                "Success",message,data, LocalDateTime.now()
        );
    }


    public static <T> ApiResponse<T> error(String message)
    {
        return new ApiResponse<>(
                "Error",message,null,LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> error(String message,T data)
    {
        return new ApiResponse<>(
                "Error",message,data,LocalDateTime.now()
        );
    }
}

package com.wrogn.task.dto.response;

import java.time.LocalDateTime;

public class ApiResponse<T>
{
    private String status;

    private String message;

    private T data;

    private LocalDateTime timeStamp;

    public ApiResponse(String status, String message, T data, LocalDateTime timeStamp ) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timeStamp=timeStamp;
    }

    public ApiResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}

package com.wrogn.task.exceptions;

import com.wrogn.task.dto.ApiResponse;

import com.wrogn.task.dto.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler 
{

    

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<String> > handleNotFound(ResourceNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ResponseUtil.error(ex.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<List<String>>> handleValidation(MethodArgumentNotValidException ex)
	{
		List<String> errors=ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError->fieldError.getField()+":"+fieldError.getDefaultMessage()).toList();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ResponseUtil.error("Validation failed",errors));
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<String>> handleGeneric(Exception ex)
	{
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResponseUtil.error("Internal server error"));
	}
}

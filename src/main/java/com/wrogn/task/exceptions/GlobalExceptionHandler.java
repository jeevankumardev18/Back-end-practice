package com.wrogn.task.exceptions;

import com.wrogn.task.dto.ApiResponse;

import com.wrogn.task.dto.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.nio.file.AccessDeniedException;
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


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<String>> handleGeneric(RuntimeException ex,
															 HttpServletRequest request)
	{

		if(request.getRequestURI().contains("api-docs"))
		{
			throw new RuntimeException(ex);
		}

		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ResponseUtil.error("Internal server error"));

	}


	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse<String >> handleAccessDenied(AccessDeniedException ex)
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtil.error("Access Denied"));
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ApiResponse<String >> handleAuthorizeDenied(AuthorizationDeniedException ex)
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseUtil.error("Access Denied"));
	}

}

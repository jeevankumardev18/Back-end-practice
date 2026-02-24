package com.wrogn.task.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.wrogn.task.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler 
{

    

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex)
	{
		ErrorResponseDto error=new ErrorResponseDto(ex.getMessage(),HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException ex)
	{
		String message=ex.getBindingResult().getFieldError().getDefaultMessage();
		
		ErrorResponseDto error=new ErrorResponseDto(message, HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex)
	{
		ErrorResponseDto error=new 
				         ErrorResponseDto("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

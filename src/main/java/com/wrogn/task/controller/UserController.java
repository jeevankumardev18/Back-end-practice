package com.wrogn.task.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wrogn.task.dto.UserDto;
import com.wrogn.task.dto.UserRequestDto;
import com.wrogn.task.dto.UserResponseDto;
import com.wrogn.task.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController 
{
	
	
	@PostMapping
	public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto request)
	{
		//UserResponseDto response = new UserResponseDto();
		//response.setId(1L);
		//response.setEmail(request.getEmail());
		//return response;
		//return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
		return ResponseEntity.ok("Login Sucessful");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id)
	{
		if(id != 1 && id!=2 && id!=3 )
		{
			throw new ResourceNotFoundException("User not found with id " + id);
		}
		 UserResponseDto dto = new UserResponseDto();
		    dto.setId(1L);
		    dto.setEmail("test@gmail.com");

		    return ResponseEntity.ok(dto);
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> getAll()
	{
		return ResponseEntity.ok(new ArrayList<>());
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody UserDto dto)
	{
		return ResponseEntity.ok("User Updated Sucessfully");
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) 
	{
        return ResponseEntity.ok("User deleted successfully");
    }
}

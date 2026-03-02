package com.wrogn.task.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrogn.task.dto.UserRequestDto;
import com.wrogn.task.dto.UserResponseDto;
import com.wrogn.task.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController 
{
	
	private final UserService service;
	
	public UserController(UserService service)
	{
		this.service=service;
	}
	
	@PostMapping
	public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDto request)
	{
				
		 service.createUser(request);
		return ResponseEntity.ok("User Created Sucessfully");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id)
	{

		    return ResponseEntity.ok(service.getUserById(id));
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getAll()
	{
		return ResponseEntity.ok(service.getAllUsers());
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody UserRequestDto dto)
	{
		 service.updateUser(id, dto);
		return ResponseEntity.ok("User Updated Sucessfully");
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) 
	{
		service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
	
	@GetMapping("/pagination")
	public ResponseEntity<Page<UserResponseDto>> getAllUsers(@RequestParam(defaultValue = "0")int page,
			                 @RequestParam(defaultValue = "5") int size,
			                 @RequestParam(defaultValue = "id") String sortBy
			)
	{
		return ResponseEntity.ok(service.getAllUsersWithPagination(page, size, sortBy));
	}
}

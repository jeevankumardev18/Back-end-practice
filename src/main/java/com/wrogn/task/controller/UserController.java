package com.wrogn.task.controller;

import java.util.List;

import com.wrogn.task.dto.ApiResponse;
import com.wrogn.task.dto.ResponseUtil;
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
	public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto request)
	{

		UserResponseDto user=service.createUser(request);
		return ResponseEntity.status(201).body(ResponseUtil.success("User Created",user));

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id)
	{

		UserResponseDto user=service.getUserById(id);
		return ResponseEntity.ok(ResponseUtil.success("User Fetched",user));

	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers()
	{
		List<UserResponseDto> users=service.getAllUsers();
		return  ResponseEntity.ok(ResponseUtil.success("All Users Fetched",users));


	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequestDto dto)
	{
		UserResponseDto user= service.updateUser(id, dto);
		return ResponseEntity.ok(ResponseUtil.success("User Updated",user));
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id)
	{
		service.deleteUser(id);
		return ResponseEntity.ok(ResponseUtil.success("User Deleted",null));
    }
	
	@GetMapping("/pagination")
	public ResponseEntity<ApiResponse<Page<UserResponseDto>> > getAllUsersWithPagination
			(
			                 @RequestParam(defaultValue = "0")int page,
			                 @RequestParam(defaultValue = "5") int size,
			                 @RequestParam(defaultValue = "id") String sortBy
			)
	{

		Page<UserResponseDto> users=service.getAllUsersWithPagination(page, size, sortBy);
		return ResponseEntity.ok(ResponseUtil.success("Users Fetched",users));



	}


	@GetMapping("/search")
	public ResponseEntity<ApiResponse<Page<UserResponseDto>>> searchApi
			(
			                                                    @RequestParam String keyword,
																@RequestParam int page,
																@RequestParam int size,
																@RequestParam String sortBy
			)
	{

		Page<UserResponseDto> users= service.searchUsers(keyword, page, size, sortBy);
		return ResponseEntity.ok(ResponseUtil.success("Search Users fetched",users));
	}


	@GetMapping("/search/native")
	public ResponseEntity<ApiResponse<List<UserResponseDto>>> searchNative(@RequestParam String keyword)
	{
		List<UserResponseDto> user=service.searchNative(keyword);
		return ResponseEntity.ok(ResponseUtil.success("Search Native users fetched",user));
	}

	@GetMapping("/email")
	public ResponseEntity<ApiResponse<UserResponseDto>> getByEmail(@RequestParam String email)
	{
		UserResponseDto user=service.findByEmail(email);
		return ResponseEntity.ok(ResponseUtil.success("User fetched by email",user));
	}




}

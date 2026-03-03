package com.wrogn.task.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wrogn.task.dto.UserRequestDto;
import com.wrogn.task.dto.UserResponseDto;


public interface UserService 
{
		UserResponseDto createUser(UserRequestDto dto);
		UserResponseDto getUserById(Long id);
	    List<UserResponseDto> getAllUsers();
	    UserResponseDto updateUser(Long id, UserRequestDto dto);
	    void deleteUser(Long id);
	    Page<UserResponseDto> getAllUsersWithPagination(int page,int size,String sortBY);
		Page<UserResponseDto> searchUsers(String keyword,int page,int size,String sortBy);

		
}

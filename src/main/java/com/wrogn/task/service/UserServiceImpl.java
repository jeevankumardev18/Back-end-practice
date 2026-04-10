package com.wrogn.task.service;

import java.util.List;

import com.wrogn.task.dto.request.UserRequestDto;
import com.wrogn.task.dto.response.UserResponseDto;
import com.wrogn.task.entity.Role;
import com.wrogn.task.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wrogn.task.entity.UserEntity;
import com.wrogn.task.exceptions.ResourceNotFoundException;
import com.wrogn.task.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserService
{
	
	private final UserRepository repo;
	 public UserServiceImpl(UserRepository repo) 
	 {
	        this.repo = repo;
	 }

	 private static final Logger logger=
			 LoggerFactory.getLogger(UserServiceImpl.class);




	@Override
	public UserResponseDto createUser(UserRequestDto request)
	{
		logger.info("Creating user with email: {}",request.getEmail());
		UserEntity userEntity=UserMapper.toEntity(request);
		userEntity.setRole(Role.valueOf("USER"));
		UserEntity savedUser=repo.save(userEntity);
		logger.info("User created with id: {}",savedUser.getId());
		return UserMapper.toDto(savedUser);
	}


	@Override
	public UserResponseDto getUserById(Long id) 
	{
		UserEntity entity = repo.findById(id)
                .orElseThrow(() ->
				{
					logger.error("User not found with id: {}",id);
					return new ResourceNotFoundException("User not found with id " + id);
				});

		logger.info("Fetching user with id: {}",id);

		return UserMapper.toDto(entity);
		
	}


	@Override
	public List<UserResponseDto> getAllUsers() 
	{
		List<UserEntity> entity=repo.findAll();
		return entity.stream().map(UserMapper::toDto).toList();
	}



	@Override
	public UserResponseDto updateUser(Long id, UserRequestDto dto)
	{
		logger.info("Updating user {}",id);
		UserEntity entity = repo.findById(id)
				.orElseThrow(() ->
				{
					logger.error("User not found {}",id);
					return new ResourceNotFoundException("User not found with id " + id);
				});
		UserMapper.updateEntity(entity,dto);

		UserEntity updatedUser = repo.save(entity);
		logger.info("User updated {}",id);
		return UserMapper.toDto(updatedUser);
	}



	@Override
	public void deleteUser(Long id) 
	{
		UserEntity entity = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id));
		logger.info("Deleting user {}",id);
		repo.delete(entity);
	}


	@Override
	public Page<UserResponseDto> getAllUsersWithPagination(int page, int size, String sortBY) 
	{
		Pageable request=PageRequest.of(page, size,Sort.by(sortBY).ascending());
		Page<UserEntity> userPage=repo.findAll(request);
		return userPage.map(UserMapper::toDto);
	}

	@Override
	public Page<UserResponseDto> searchUsers(String keyword, int page, int size, String sortBy)
	{
		Pageable pageable=PageRequest.of(page,size,Sort.by(sortBy).ascending());
		//Page<UserEntity> user=repo.findByEmailContaining(keyword,pageable); //Day 7
		Page<UserEntity> user= repo.searchByEmail(keyword,pageable);//Day 8
		return user.map(UserMapper::toDto);

	}

	@Override
	public List<UserResponseDto> searchNative(String keyword)
	{

		List<UserEntity> users = repo.searchNative(keyword);
		return users.stream().map(UserMapper::toDto).toList();
	}

	@Override
	public UserResponseDto findByEmail(String email)
	{
		UserEntity entity=repo.findByEmailCustom(email).orElseThrow(
				()->new ResourceNotFoundException("User not found with this email"+email));
		return UserMapper.toDto(entity);
	}


}




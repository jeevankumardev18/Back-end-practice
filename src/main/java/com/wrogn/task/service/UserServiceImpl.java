package com.wrogn.task.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.wrogn.task.dto.UserRequestDto;
import com.wrogn.task.dto.UserResponseDto;
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



	@Override
	public UserResponseDto createUser(UserRequestDto dto) 
	{
		UserEntity userEntity=new UserEntity();
		userEntity.setEmail(dto.getEmail());
		userEntity.setPassword(dto.getPassword());
		
		UserEntity savedUser=repo.save(userEntity);
		
		UserResponseDto response=new UserResponseDto();
		response.setEmail(savedUser.getEmail());
		response.setId(savedUser.getId());
		
		return response;
	}


	@Override
	public UserResponseDto getUserById(Long id) 
	{
		UserEntity entity = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id));
		UserResponseDto response =new UserResponseDto();
		response.setEmail(entity.getEmail());
		response.setId(entity.getId());
		
		return response;
		
	}


	@Override
	public List<UserResponseDto> getAllUsers() 
	{
		List<UserEntity> entity=repo.findAll();
		
		if(entity.isEmpty())
		{
			return new ArrayList<>();
		}
		return entity.stream().map(en->{
			UserResponseDto dto=new UserResponseDto();
			dto.setId(en.getId());
			dto.setEmail(en.getEmail());
			return dto;
		}).toList();
	}



	@Override
	public UserResponseDto updateUser(Long id, UserRequestDto dto) 
	{
		UserEntity entity=repo.findById(id)
        .orElseThrow(() ->
                new ResourceNotFoundException("User not found with id " + id));
		
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		
		UserEntity updatedUser=repo.save(entity);
		
		 UserResponseDto response = new UserResponseDto();
		 response.setId(updatedUser.getId());
		 response.setEmail(updatedUser.getEmail());
		return response;
	}



	@Override
	public void deleteUser(Long id) 
	{
		UserEntity entity = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id));
		
		repo.delete(entity);
	}









	@Override
	public Page<UserResponseDto> getAllUsersWithPagination(int page, int size, String sortBY) 
	{
		Pageable request=PageRequest.of(page, size,Sort.by(sortBY).ascending());
		Page<UserEntity> userPage=repo.findAll(request);
		
		return userPage.map(entity->{ 
			                                 	//Map to  Entity -> UserResponseDto...
			UserResponseDto dto=new UserResponseDto();
			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());
			
			return dto;
		});  
	}

	@Override
	public Page<UserResponseDto> searchUsers(String keyword, int page, int size, String sortBy)
	{
		Pageable pageable=PageRequest.of(page,size,Sort.by(sortBy).ascending());
		//Page<UserEntity> user=repo.findByEmailContaining(keyword,pageable); //Day 7
		Page<UserEntity> user= repo.searchByEmail(keyword,pageable);//Day 8

		return user.map(entity->{
			UserResponseDto dto=new UserResponseDto();
			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());
			return dto;
		});

	}

	@Override
	public List<UserResponseDto> searchNative(String keyword)
	{

		List<UserEntity> users =
				repo.searchNative(keyword);

		return users.stream().map(entity -> {

			UserResponseDto dto =
					new UserResponseDto();

			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());

			return dto;

		}).toList();
	}

	@Override
	public UserResponseDto findByEmail(String email) {
		UserEntity entity=repo.findByEmailCustom(email).orElseThrow(
				()->new ResourceNotFoundException("User not found with this email"+email));
		UserResponseDto dto =
				new UserResponseDto();

		dto.setId(entity.getId());
		dto.setEmail(entity.getEmail());

		return dto;
	}


}




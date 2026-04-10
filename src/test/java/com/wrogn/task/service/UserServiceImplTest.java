package com.wrogn.task.service;

import com.wrogn.task.dto.request.UserRequestDto;
import com.wrogn.task.dto.response.UserResponseDto;
import com.wrogn.task.entity.UserEntity;
import com.wrogn.task.exceptions.ResourceNotFoundException;
import com.wrogn.task.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest
{
    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void shouldCreateUser()
    {
        UserRequestDto requestDto=new UserRequestDto();
        requestDto.setEmail("test@gmail.com");
        requestDto.setPassword("password@test");

        UserEntity entity=new UserEntity();
        entity.setId(1L);
        entity.setEmail("test@gmail.com");

       when(repo.save(any(UserEntity.class))).thenReturn(entity);

       UserResponseDto responseDto=service.createUser(requestDto);
       assertNotNull(responseDto);
       assertEquals("test@gmail.com",responseDto.getEmail());
       verify(repo).save(any(UserEntity.class));

    }

    @Test
    void shouldReturnUser()
    {
        UserEntity entity=new UserEntity();
        entity.setId(1L);
        entity.setEmail("test@gmail.com");

        when(repo.findById(1L)).thenReturn(Optional.of(entity));

        UserResponseDto responseDto=service.getUserById(1L);
        assertEquals("test@gmail.com",responseDto.getEmail());
    }


    @Test
    void shouldThrowExceptionWhenUserNotFound()
    {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->service.getUserById(1L));
    }

    @Test
    void shouldDeleteUser()
    {
        UserEntity entity=new UserEntity();
        entity.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        service.deleteUser(1L);
        verify(repo).delete(entity);
    }


//    when() → mock behavior
//    thenReturn() → fake response
//    verify() → check method called
//    any() → ignore argument

}

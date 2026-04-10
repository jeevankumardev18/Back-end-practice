package com.wrogn.task.mapper;

import com.wrogn.task.dto.request.UserRequestDto;
import com.wrogn.task.dto.response.UserResponseDto;
import com.wrogn.task.entity.UserEntity;

public class UserMapper
{

    public static UserEntity toEntity(UserRequestDto requestDto)
    {
        UserEntity user=new UserEntity();
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());

        return user;
    }


    public static UserResponseDto toDto(UserEntity entity)
    {
        UserResponseDto dto=new UserResponseDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    public static void updateEntity(UserEntity entity, UserRequestDto dto)
    {

        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

    }

// process ---> userRequestDto -> UserEntity & UserEntity -> UserResponseDto


    // what is Mapper
    //conversion of Entity -> Dto and Dto -> Entity
    // UserEntity → UserResponseDto & UserRequestDto → UserEntity
    //Single responsibility principle.
}

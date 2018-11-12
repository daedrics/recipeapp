package com.recipeapp.converter;

import com.recipeapp.converter.CustomConverter;
import com.recipeapp.dto.user.UserCreateDto;
import com.recipeapp.dto.user.UserDto;
import com.recipeapp.entity.UserEntity;

public class UserConverter implements CustomConverter<UserEntity, UserCreateDto, UserDto> {

    public UserEntity convert(UserCreateDto userPostDto) {
        UserEntity user = new UserEntity();
        user.setName(userPostDto.getName());
        user.setSurname(userPostDto.getSurname());
        user.setUsername(userPostDto.getUsername());
        user.setGender(userPostDto.getGender());
        user.setPassword(userPostDto.getPassword());
        user.setEmail(userPostDto.getEmail());
        return user;
    }

    public UserDto toGetDto(UserEntity user) {
        UserDto userGetDto = new UserDto();
        userGetDto.setId(user.getId());
        userGetDto.setName(user.getName());
        userGetDto.setSurname(user.getSurname());
        userGetDto.setEmail(user.getEmail());
        userGetDto.setGender(user.getGender());
        userGetDto.setUsername(user.getUsername());
        userGetDto.setPassword(user.getPassword());
        return userGetDto;
    }
}

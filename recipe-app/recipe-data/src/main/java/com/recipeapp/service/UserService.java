package com.recipeapp.service;

import com.recipeapp.dto.user.ChangePasswordDto;
import com.recipeapp.dto.user.UserCreateDto;
import com.recipeapp.dto.user.UserDto;
import com.recipeapp.dto.user.UserUpdateDto;
import com.recipeapp.entity.UserEntity;

public interface UserService {

	UserDto addUser(UserCreateDto userPostDto);

	UserDto findUser(Integer id);

	UserDto updateUser(Integer id, UserUpdateDto userUpdateDto);

	UserEntity findByUsername(String username);

	void changePassword(Integer userId, ChangePasswordDto changePasswordDto);
}

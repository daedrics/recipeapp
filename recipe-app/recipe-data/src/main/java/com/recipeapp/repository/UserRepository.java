package com.recipeapp.repository;

import com.recipeapp.entity.UserEntity;

public interface UserRepository {

	UserEntity save(UserEntity user);
	
	UserEntity find(int id);
	
	UserEntity update(UserEntity user);
	
	UserEntity findByUsername(String username);
	
	Boolean ifExist(String username);
}

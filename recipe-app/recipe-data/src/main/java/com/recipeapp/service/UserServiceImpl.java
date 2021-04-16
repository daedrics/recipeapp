package com.recipeapp.service;

import com.recipeapp.converter.UserConverter;
import com.recipeapp.dto.user.ChangePasswordDto;
import com.recipeapp.dto.user.UserCreateDto;
import com.recipeapp.dto.user.UserDto;
import com.recipeapp.dto.user.UserUpdateDto;
import com.recipeapp.entity.UserEntity;
import com.recipeapp.exception.RecipeAppBadRequestException;
import com.recipeapp.repository.UserRepository;
import com.recipeapp.util.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

	private static final UserConverter USER_CONVERTER = new UserConverter();

	private static final LoggerUtil LOGGER_UTIL = new LoggerUtil();

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	public UserDto addUser(UserCreateDto userCreateDto) {

		LOGGER.debug(LOGGER_UTIL.started("adding user", userCreateDto.toString()));
		if (userRepository.ifExist(userCreateDto.getUsername())) {
			throw new RecipeAppBadRequestException("This username already exists!");
		}
		userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
		UserEntity user = USER_CONVERTER.convert(userCreateDto);

		LOGGER.debug(LOGGER_UTIL.finished("adding user"));
		return USER_CONVERTER.toGetDto(userRepository.save(user));
	}

	public UserDto findUser(Integer id) {

		LOGGER.debug(LOGGER_UTIL.started("finding user with id=", id.toString()));

		UserEntity user = userRepository.find(id);

		LOGGER.debug(LOGGER_UTIL.finished("finding user"));
		return USER_CONVERTER.toGetDto(user);
	}

	public UserDto updateUser(Integer id, UserUpdateDto userUpdateDto) {

		LOGGER.debug(LOGGER_UTIL.started("updating user with id=", id.toString()));

		UserEntity user = userRepository.find(id);
		user.setEmail(userUpdateDto.getEmail());
		user.setName(userUpdateDto.getName());
		user.setSurname(userUpdateDto.getSurname());

		LOGGER.debug(LOGGER_UTIL.finished("updating user"));
		return USER_CONVERTER.toGetDto(userRepository.update(user));
	}

	@Override
	public UserEntity findByUsername(String username) {

		LOGGER.debug(LOGGER_UTIL.started("finding user with username=", username));

		return userRepository.findByUsername(username);
	}

	@Override
	public void changePassword(Integer userId, ChangePasswordDto changePasswordDto) {

		LOGGER.debug(LOGGER_UTIL.started("updating users password with id=", userId.toString()));
		UserEntity userEntity = userRepository.find(userId);
		if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), userEntity.getPassword())) {
			throw new RecipeAppBadRequestException("old password does not match");
		}

		if (passwordEncoder.matches(changePasswordDto.getNewPassword(), userEntity.getPassword())) {
			throw new RecipeAppBadRequestException("new password can't be same as old password");
		}

		userEntity.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
		userRepository.update(userEntity);

		LOGGER.debug(LOGGER_UTIL.finished("updating users password"));
	}

}

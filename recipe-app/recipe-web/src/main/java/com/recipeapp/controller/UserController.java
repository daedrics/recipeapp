package com.recipeapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipeapp.dto.LazyDataModel;
import com.recipeapp.dto.user.ChangePasswordDto;
import com.recipeapp.dto.user.UserCreateDto;
import com.recipeapp.dto.user.UserDto;
import com.recipeapp.dto.user.UserUpdateDto;
import com.recipeapp.security.service.UserPrincipal;
import com.recipeapp.service.RecipeService;
import com.recipeapp.service.UserService;
import com.recipeapp.util.AuthenticationFacade;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private AuthenticationFacade authenticationFacade;

	@PostMapping(value = "/signup")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserCreateDto userPostDto) {
		return ResponseEntity.ok(userService.addUser(userPostDto));
	}

	@GetMapping()
	public ResponseEntity<UserDto> getUser() {
		UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		return ResponseEntity.ok(userService.findUser(userPrincipal.getId()));
	}

	@PutMapping()
	public ResponseEntity<UserDto> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
		UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		return ResponseEntity.ok(userService.updateUser(userPrincipal.getId(), userUpdateDto));
	}

	@PostMapping(value = "/changePassword")
	public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
		UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		userService.changePassword(userPrincipal.getId(), changePasswordDto);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/recipes/{pageNumber}/{pageSize}")
	public ResponseEntity<LazyDataModel> userRecipes(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@RequestParam(name = "name", required = false) String name) {
		UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		return ResponseEntity.ok(recipeService.userRecipes(userPrincipal.getId(), pageNumber, pageSize, name));
	}
}

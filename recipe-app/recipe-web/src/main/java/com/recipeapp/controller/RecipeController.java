package com.recipeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipeapp.dto.recipe.RecipeCreateDto;
import com.recipeapp.dto.recipe.RecipeDetailDto;
import com.recipeapp.dto.recipe.RecipeDto;
import com.recipeapp.security.service.UserPrincipal;
import com.recipeapp.service.RecipeService;
import com.recipeapp.util.AuthenticationFacade;

@RestController
@RequestMapping(value = "/api/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;
	@Autowired
	private AuthenticationFacade authenticationFacade;

	@PostMapping
	public ResponseEntity<RecipeDto> addRecipe(@RequestBody RecipeCreateDto recipePostDto) {
		UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		return ResponseEntity.ok(recipeService.addRecipe(userPrincipal.getId(), recipePostDto));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<RecipeDto> updateRecipe(@PathVariable int id, @RequestBody RecipeCreateDto recipePostDto) {
		UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		return ResponseEntity.ok(recipeService.updateRecipe(userPrincipal.getId(), id, recipePostDto));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<RecipeDetailDto> getRecipe(@PathVariable int id) {
		return ResponseEntity.ok(recipeService.findRecipe(id));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
		UserPrincipal userPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		recipeService.deleteRecipe(userPrincipal.getId(), id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<RecipeDto>> latestPublished(@RequestParam(name = "name", required = false) String name) {
		return ResponseEntity.ok(recipeService.latestPublished(name));
	}

}

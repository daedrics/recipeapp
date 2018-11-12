package com.recipeapp.service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipeapp.converter.RecipeConverter;
import com.recipeapp.dto.LazyDataModel;
import com.recipeapp.dto.recipe.RecipeCreateDto;
import com.recipeapp.dto.recipe.RecipeDetailDto;
import com.recipeapp.dto.recipe.RecipeDto;
import com.recipeapp.entity.RecipeEntity;
import com.recipeapp.entity.UserEntity;
import com.recipeapp.exception.RecipeAppAccessDeniedException;
import com.recipeapp.repository.RecipeRepository;
import com.recipeapp.repository.UserRepository;
import com.recipeapp.service.RecipeService;
import com.recipeapp.util.LoggerUtil;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

	private static final Logger LOGGER = Logger.getLogger(RecipeServiceImpl.class);
	private static final RecipeConverter RECIPE_CONVERTER = new RecipeConverter();
	private static final LoggerUtil LOGGER_UTIL = new LoggerUtil();

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private UserRepository userRepository;

	public RecipeDto addRecipe(Integer loggedUserId, RecipeCreateDto recipePostDto) {

		LOGGER.debug(LOGGER_UTIL.started("adding a recipe", recipePostDto.toString()));

		UserEntity user = userRepository.find(loggedUserId);
		RecipeEntity recipe = RECIPE_CONVERTER.convert(recipePostDto);
		if (recipe.isPublished()) {
			recipe.setPublishedOn(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC));
		}
		recipe.setUser(user);

		LOGGER.debug(LOGGER_UTIL.finished("adding a recipe"));

		return RECIPE_CONVERTER.toGetDto(recipeRepository.save(recipe));

	}

	public RecipeDetailDto findRecipe(Integer id) {

		LOGGER.debug(LOGGER_UTIL.started("finding recipe with id=", id.toString()));

		RecipeEntity recipe = recipeRepository.find(id);

		LOGGER.debug(LOGGER_UTIL.finished("finding the recipe"));
		return RECIPE_CONVERTER.toGetDetailDto(recipe);
	}

	public RecipeDto updateRecipe(Integer loggedUserId, Integer id, RecipeCreateDto recipePostDto) {

		LOGGER.debug(LOGGER_UTIL.started("updating recipe: ", recipePostDto.toString()));

		RecipeEntity recipe = recipeRepository.find(id);
		UserEntity loggedUser = userRepository.find(loggedUserId);
		if (!recipe.getUser().equals(loggedUser)) {
			throw new RecipeAppAccessDeniedException();
		}
		if (!recipe.isPublished() && recipePostDto.isPublished()) {
			recipe.setPublishedOn(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC));
		}
		if (!recipePostDto.isPublished()) {
			recipe.setPublishedOn(null);
		}
		recipe.setUser(loggedUser);
		recipe.setCalories(recipePostDto.getCalories());
		recipe.setName(recipePostDto.getName());
		recipe.setServings(recipePostDto.getServings());
		recipe.setTime(recipePostDto.getTime());
		recipe.setDescription(recipePostDto.getDescription());
		recipe.setImageUrl(recipePostDto.getImageUrl());
		recipe.setPublished(recipePostDto.isPublished());

		LOGGER.debug(LOGGER_UTIL.finished("updating recipe"));
		return RECIPE_CONVERTER.toGetDto(recipeRepository.save(recipe));
	}

	public void deleteRecipe(Integer loggedUserId, Integer id) {

		LOGGER.debug(LOGGER_UTIL.started("deleting recipe with id=", id.toString()));
		RecipeEntity recipe = recipeRepository.find(id);
		if (recipe.getUser().getId() != loggedUserId) {
			throw new RecipeAppAccessDeniedException();
		}

		LOGGER.debug(LOGGER_UTIL.finished("deleting recipe"));

		recipeRepository.delete(recipe);
	}

	@Override
	public List<RecipeDto> latestPublished(String name) {
		List<RecipeEntity> recipes = new ArrayList<>();
		if (name != null && !name.isEmpty()) {
			LOGGER.debug(LOGGER_UTIL.started("searching recipes by name=", name));
			recipes.addAll(recipeRepository.searchRecipesByName(name));
		} else {
			LOGGER.debug("getting latest published recipes ");
			recipes.addAll(recipeRepository.latestPublished());
		}
		return converList(recipes);
	}

	@Override
	public LazyDataModel userRecipes(Integer userId, Integer pageNumber, Integer pageSize, String name) {

		LazyDataModel lazyDataModel = new LazyDataModel();
		List<Object> recipes = new ArrayList<>();
		recipes.addAll(converList(recipeRepository.userRecipes(userId, pageNumber, pageSize, name)));
		lazyDataModel.setList(recipes);
		int total = recipeRepository.userTotalRecipes(userId, name);
		pageNumber = (pageNumber - 1) * pageSize;
		if (pageSize != 0) {
			if (total % pageSize == 0) {
				lazyDataModel.setTotalPageNumber(total / pageSize);
			} else {
				lazyDataModel.setTotalPageNumber((total / pageSize) + 1);
			}
			lazyDataModel.setCurrentPage((pageNumber / pageSize) + 1);
		}

		return lazyDataModel;
	}

	private List<RecipeDto> converList(List<RecipeEntity> recipes) {
		List<RecipeDto> recipeGetDtos = new ArrayList<>();
		recipes.forEach(recipe -> recipeGetDtos.add(RECIPE_CONVERTER.toGetDto(recipe)));
		return recipeGetDtos;
	}

}

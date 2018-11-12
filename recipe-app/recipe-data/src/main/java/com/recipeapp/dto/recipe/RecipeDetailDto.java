package com.recipeapp.dto.recipe;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.recipeapp.dto.user.UserDto;
import com.recipeapp.util.CustomUserDtoSerializer;
import com.recipeapp.util.CustomZonedDateTimeSerializer;

public class RecipeDetailDto {

	private Integer id;
	private String name;
	private Integer servings;
	private Long time;
	private Integer calories;
	private boolean published;
	private String imageUrl;
	@JsonSerialize(using = CustomZonedDateTimeSerializer.class)
	private ZonedDateTime createdOn;
	@JsonSerialize(using = CustomZonedDateTimeSerializer.class)
	private ZonedDateTime publishedOn;
	private String description;
	@JsonSerialize(using = CustomUserDtoSerializer.class)
	private UserDto user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getServings() {
		return servings;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public ZonedDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(ZonedDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public ZonedDateTime getPublishedOn() {
		return publishedOn;
	}

	public void setPublishedOn(ZonedDateTime publishedOn) {
		this.publishedOn = publishedOn;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto userGetDto) {
		this.user = userGetDto;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}

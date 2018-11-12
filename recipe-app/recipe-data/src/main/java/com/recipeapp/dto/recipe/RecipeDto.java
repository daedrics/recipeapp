package com.recipeapp.dto.recipe;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.recipeapp.dto.user.UserDto;
import com.recipeapp.util.CustomUserDtoSerializer;
import com.recipeapp.util.CustomZonedDateTimeSerializer;

public class RecipeDto {

	private Integer id;
	private String name;
	private Integer calories;
	private boolean published;
	private String imageUrl;
	@JsonSerialize(using = CustomZonedDateTimeSerializer.class)
	private ZonedDateTime publishedOn;
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

	public Integer getCalories() {
		return calories;
	}

	public void setCalories(Integer calories) {
		this.calories = calories;
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

	public void setUser(UserDto user) {
		this.user = user;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}

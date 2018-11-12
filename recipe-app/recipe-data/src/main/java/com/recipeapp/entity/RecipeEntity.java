package com.recipeapp.entity;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.recipeapp.entity.UserEntity;

@Entity
@Table(name = "recipes")
public class RecipeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "servings", nullable = false)
	private Integer servings;

	@Column(name = "time", nullable = false)
	private Long time; // in seconds

	@Column(name = "calories", nullable = false)
	private Integer calories;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "published", nullable = false)
	private boolean published;

	@Column(name = "imageUrl", nullable = false)
	private String imageUrl;

	@Column(name = "created_on")
	private ZonedDateTime createdOn = ZonedDateTime.now();

	@Column(name = "published_on", nullable = true)
	private ZonedDateTime publishedOn;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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

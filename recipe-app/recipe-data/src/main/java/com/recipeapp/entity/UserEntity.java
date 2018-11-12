package com.recipeapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipeapp.entity.RecipeEntity;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 22)
	private String email;

	@Column(nullable = false, length = 22)
	private String username;

	@Column(nullable = false, columnDefinition = "longtext")
	private String password;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "surname", nullable = false)
	private String surname;

	@Column(name = "gender", nullable = false)
	private String gender;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<RecipeEntity> recipes = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<RecipeEntity> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<RecipeEntity> recipes) {
		this.recipes = recipes;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

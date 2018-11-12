package com.recipeapp.dto.user;

import javax.validation.constraints.Pattern;

public class UserCreateDto {

	@Pattern(regexp = "^(?=.*[a-z])(?=.[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&+])[A-Za-z0-9$@$!%*?&+]{6,20}")
	private String password;
	@Pattern(regexp = "^[a-zA-Z0-9]{3,15}", message = "Wrong username format")
	private String username;
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}", message = "Wrong email format")
	private String email;
	@Pattern(regexp = "^[a-zA-Z]{3,15}", message = "Wrong name format")
	private String name;
	@Pattern(regexp = "^[a-zA-Z]{3,15}", message = "Wrong surname format")
	private String surname;
	private String gender;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "UserCreateDto [password=" + password + ", username=" + username + ", email=" + email + ", name=" + name
				+ ", surname=" + surname + ", gender=" + gender + "]";
	}

}

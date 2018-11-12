package com.recipeapp.dto.user;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class ChangePasswordDto {

	@NotBlank
	String oldPassword;

	@Pattern(regexp = "^(?=.*[a-z])(?=.[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&+])[A-Za-z0-9$@$!%*?&+]{6,20}")
	String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassowrd) {
		this.newPassword = newPassowrd;
	}

}

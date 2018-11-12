package com.recipeapp.util;

public class LoggerUtil {

	public String started(String message, String param) {
		return String.format("Started: %s %s", message, param);
	}

	public String finished(String message) {
		return String.format("Finished: %s", message);
	}

}

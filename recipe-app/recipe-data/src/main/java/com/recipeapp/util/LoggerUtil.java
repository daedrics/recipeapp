package com.recipeapp.util;

import java.util.Arrays;

public class LoggerUtil {

	public String started(String message, String... param) {
		return String.format("Started: %s %s", message, Arrays.toString(param));
	}

	public String finished(String message) {
		return String.format("Finished: %s", message);
	}

}

package com.recipeapp.converter;

public interface CustomConverter<T,S,V> {
	
	T convert(S source);
	
	V toGetDto(T source);
}

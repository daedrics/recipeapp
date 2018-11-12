package com.recipeapp.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.recipeapp.dto.user.UserDto;

public class CustomUserDtoSerializer extends JsonSerializer<UserDto> {

	@Override
	public void serialize(UserDto value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		jgen.writeNumberField("id", value.getId());
		jgen.writeStringField("name", value.getName());
		jgen.writeStringField("surname", value.getSurname());
		jgen.writeEndObject();
	}

	@Override
	public Class<UserDto> handledType() {
		return UserDto.class;
	}
}

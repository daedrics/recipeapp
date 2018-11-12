package com.recipeapp.util;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

	@Override
	public void serialize(ZonedDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(DTF.format(value));
	}

	@Override
	public Class<ZonedDateTime> handledType() {
		return ZonedDateTime.class;
	}

}

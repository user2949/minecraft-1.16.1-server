package com.google.gson;

import java.lang.reflect.Type;

public interface JsonSerializer<T> {
	JsonElement serialize(T object, Type type, JsonSerializationContext jsonSerializationContext);
}

package io.netty.handler.codec.serialization;

public interface ClassResolver {
	Class<?> resolve(String string) throws ClassNotFoundException;
}

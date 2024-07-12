package com.mojang.serialization;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Keyable {
	<T> Stream<T> keys(DynamicOps<T> dynamicOps);

	static Keyable forStrings(Supplier<Stream<String>> keys) {
		return new Keyable() {
			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return ((Stream)keys.get()).map(ops::createString);
			}
		};
	}
}

package com.mojang.serialization;

import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface MapLike<T> {
	@Nullable
	T get(T object);

	@Nullable
	T get(String string);

	Stream<Pair<T, T>> entries();

	static <T> MapLike<T> forMap(Map<T, T> map, DynamicOps<T> ops) {
		return new MapLike<T>() {
			@Nullable
			@Override
			public T get(T key) {
				return (T)map.get(key);
			}

			@Nullable
			@Override
			public T get(String key) {
				return (T)this.get(ops.createString(key));
			}

			@Override
			public Stream<Pair<T, T>> entries() {
				return map.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue()));
			}

			public String toString() {
				return "MapLike[" + map + "]";
			}
		};
	}
}

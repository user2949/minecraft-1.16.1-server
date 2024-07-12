package com.mojang.serialization;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class Dynamic<T> extends DynamicLike<T> {
	private final T value;

	public Dynamic(DynamicOps<T> ops) {
		this(ops, ops.empty());
	}

	public Dynamic(DynamicOps<T> ops, @Nullable T value) {
		super(ops);
		this.value = value == null ? ops.empty() : value;
	}

	public T getValue() {
		return this.value;
	}

	public Dynamic<T> map(Function<? super T, ? extends T> function) {
		return new Dynamic<>(this.ops, (T)function.apply(this.value));
	}

	public <U> Dynamic<U> castTyped(DynamicOps<U> ops) {
		if (!Objects.equals(this.ops, ops)) {
			throw new IllegalStateException("Dynamic type doesn't match");
		} else {
			return (Dynamic<U>)this;
		}
	}

	public <U> U cast(DynamicOps<U> ops) {
		return this.castTyped(ops).getValue();
	}

	public OptionalDynamic<T> merge(Dynamic<?> value) {
		DataResult<T> merged = this.ops.mergeToList(this.value, value.cast(this.ops));
		return new OptionalDynamic<>(this.ops, merged.map(m -> new Dynamic<>(this.ops, (T)m)));
	}

	public OptionalDynamic<T> merge(Dynamic<?> key, Dynamic<?> value) {
		DataResult<T> merged = this.ops.mergeToMap(this.value, key.cast(this.ops), value.cast(this.ops));
		return new OptionalDynamic<>(this.ops, merged.map(m -> new Dynamic<>(this.ops, (T)m)));
	}

	public DataResult<Map<Dynamic<T>, Dynamic<T>>> getMapValues() {
		return this.ops.getMapValues(this.value).map(map -> {
			Builder<Dynamic<T>, Dynamic<T>> builder = ImmutableMap.builder();
			map.forEach(entry -> builder.put(new Dynamic<>(this.ops, (T)entry.getFirst()), new Dynamic<>(this.ops, (T)entry.getSecond())));
			return builder.build();
		});
	}

	public Dynamic<T> updateMapValues(Function<Pair<Dynamic<?>, Dynamic<?>>, Pair<Dynamic<?>, Dynamic<?>>> updater) {
		return DataFixUtils.orElse(this.getMapValues().map(map -> (Map)map.entrySet().stream().map(e -> {
				Pair<Dynamic<?>, Dynamic<?>> pair = (Pair<Dynamic<?>, Dynamic<?>>)updater.apply(Pair.of(e.getKey(), e.getValue()));
				return Pair.of(pair.getFirst().castTyped(this.ops), pair.getSecond().castTyped(this.ops));
			}).collect(Pair.toMap())).map(this::createMap).result(), this);
	}

	@Override
	public DataResult<Number> asNumber() {
		return this.ops.getNumberValue(this.value);
	}

	@Override
	public DataResult<String> asString() {
		return this.ops.getStringValue(this.value);
	}

	@Override
	public DataResult<Stream<Dynamic<T>>> asStreamOpt() {
		return this.ops.getStream(this.value).map(s -> s.map(e -> new Dynamic<>(this.ops, (T)e)));
	}

	@Override
	public DataResult<Stream<Pair<Dynamic<T>, Dynamic<T>>>> asMapOpt() {
		return this.ops.getMapValues(this.value).map(s -> s.map(p -> Pair.of(new Dynamic<>(this.ops, (T)p.getFirst()), new Dynamic<>(this.ops, (T)p.getSecond()))));
	}

	@Override
	public DataResult<ByteBuffer> asByteBufferOpt() {
		return this.ops.getByteBuffer(this.value);
	}

	@Override
	public DataResult<IntStream> asIntStreamOpt() {
		return this.ops.getIntStream(this.value);
	}

	@Override
	public DataResult<LongStream> asLongStreamOpt() {
		return this.ops.getLongStream(this.value);
	}

	@Override
	public OptionalDynamic<T> get(String key) {
		return new OptionalDynamic<>(this.ops, this.ops.getMap(this.value).flatMap(m -> {
			T value = (T)m.get(key);
			return value == null ? DataResult.error("key missing: " + key + " in " + this.value) : DataResult.success(new Dynamic<>(this.ops, value));
		}));
	}

	@Override
	public DataResult<T> getGeneric(T key) {
		return this.ops.getGeneric(this.value, key);
	}

	public Dynamic<T> remove(String key) {
		return this.map(v -> this.ops.remove((T)v, key));
	}

	public Dynamic<T> set(String key, Dynamic<?> value) {
		return this.map(v -> this.ops.set((T)v, key, value.cast(this.ops)));
	}

	public Dynamic<T> update(String key, Function<Dynamic<?>, Dynamic<?>> function) {
		return this.map(v -> this.ops.update((T)v, key, value -> ((Dynamic)function.apply(new Dynamic<>(this.ops, (T)value))).cast(this.ops)));
	}

	public Dynamic<T> updateGeneric(T key, Function<T, T> function) {
		return this.map(v -> this.ops.updateGeneric((T)v, key, function));
	}

	@Override
	public DataResult<T> getElement(String key) {
		return this.getElementGeneric(this.ops.createString(key));
	}

	@Override
	public DataResult<T> getElementGeneric(T key) {
		return this.ops.getGeneric(this.value, key);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			Dynamic<?> dynamic = (Dynamic<?>)o;
			return Objects.equals(this.ops, dynamic.ops) && Objects.equals(this.value, dynamic.value);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.ops, this.value});
	}

	public String toString() {
		return String.format("%s[%s]", this.ops, this.value);
	}

	public <R> Dynamic<R> convert(DynamicOps<R> outOps) {
		return new Dynamic<>(outOps, convert(this.ops, outOps, this.value));
	}

	public <V> V into(Function<? super Dynamic<T>, ? extends V> action) {
		return (V)action.apply(this);
	}

	@Override
	public <A> DataResult<Pair<A, T>> decode(Decoder<? extends A> decoder) {
		return decoder.decode(this.ops, this.value).map(p -> p.mapFirst(Function.identity()));
	}

	public static <S, T> T convert(DynamicOps<S> inOps, DynamicOps<T> outOps, S input) {
		return (T)(Objects.equals(inOps, outOps) ? input : inOps.convertTo(outOps, input));
	}
}

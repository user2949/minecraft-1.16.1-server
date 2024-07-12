package com.mojang.serialization;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.function.UnaryOperator;

public interface RecordBuilder<T> {
	DynamicOps<T> ops();

	RecordBuilder<T> add(T object1, T object2);

	RecordBuilder<T> add(T object, DataResult<T> dataResult);

	RecordBuilder<T> add(DataResult<T> dataResult1, DataResult<T> dataResult2);

	RecordBuilder<T> withErrorsFrom(DataResult<?> dataResult);

	RecordBuilder<T> setLifecycle(Lifecycle lifecycle);

	RecordBuilder<T> mapError(UnaryOperator<String> unaryOperator);

	DataResult<T> build(T object);

	default DataResult<T> build(DataResult<T> prefix) {
		return prefix.flatMap(this::build);
	}

	default RecordBuilder<T> add(String key, T value) {
		return this.add(this.ops().createString(key), value);
	}

	default RecordBuilder<T> add(String key, DataResult<T> value) {
		return this.add(this.ops().createString(key), value);
	}

	default <E> RecordBuilder<T> add(String key, E value, Encoder<E> encoder) {
		return this.add(key, encoder.encodeStart(this.ops(), value));
	}

	public abstract static class AbstractBuilder<T, R> implements RecordBuilder<T> {
		private final DynamicOps<T> ops;
		protected DataResult<R> builder = DataResult.success(this.initBuilder(), Lifecycle.stable());

		protected AbstractBuilder(DynamicOps<T> ops) {
			this.ops = ops;
		}

		@Override
		public DynamicOps<T> ops() {
			return this.ops;
		}

		protected abstract R initBuilder();

		protected abstract DataResult<T> build(R object1, T object2);

		@Override
		public DataResult<T> build(T prefix) {
			DataResult<T> result = this.builder.flatMap(b -> this.build((R)b, prefix));
			this.builder = DataResult.success(this.initBuilder(), Lifecycle.stable());
			return result;
		}

		@Override
		public RecordBuilder<T> withErrorsFrom(DataResult<?> result) {
			this.builder = this.builder.flatMap(v -> result.map(r -> v));
			return this;
		}

		@Override
		public RecordBuilder<T> setLifecycle(Lifecycle lifecycle) {
			this.builder = this.builder.setLifecycle(lifecycle);
			return this;
		}

		@Override
		public RecordBuilder<T> mapError(UnaryOperator<String> onError) {
			this.builder = this.builder.mapError(onError);
			return this;
		}
	}

	public abstract static class AbstractStringBuilder<T, R> extends RecordBuilder.AbstractBuilder<T, R> {
		protected AbstractStringBuilder(DynamicOps<T> ops) {
			super(ops);
		}

		protected abstract R append(String string, T object2, R object3);

		@Override
		public RecordBuilder<T> add(String key, T value) {
			this.builder = this.builder.map(b -> this.append(key, value, (R)b));
			return this;
		}

		@Override
		public RecordBuilder<T> add(String key, DataResult<T> value) {
			this.builder = this.builder.apply2stable((b, v) -> this.append(key, (T)v, (R)b), value);
			return this;
		}

		@Override
		public RecordBuilder<T> add(T key, T value) {
			this.builder = this.ops().getStringValue(key).flatMap(k -> {
				this.add(k, value);
				return this.builder;
			});
			return this;
		}

		@Override
		public RecordBuilder<T> add(T key, DataResult<T> value) {
			this.builder = this.ops().getStringValue(key).flatMap(k -> {
				this.add(k, value);
				return this.builder;
			});
			return this;
		}

		@Override
		public RecordBuilder<T> add(DataResult<T> key, DataResult<T> value) {
			this.builder = key.flatMap(this.ops()::getStringValue).flatMap(k -> {
				this.add(k, value);
				return this.builder;
			});
			return this;
		}
	}

	public abstract static class AbstractUniversalBuilder<T, R> extends RecordBuilder.AbstractBuilder<T, R> {
		protected AbstractUniversalBuilder(DynamicOps<T> ops) {
			super(ops);
		}

		protected abstract R append(T object1, T object2, R object3);

		@Override
		public RecordBuilder<T> add(T key, T value) {
			this.builder = this.builder.map(b -> this.append(key, value, (R)b));
			return this;
		}

		@Override
		public RecordBuilder<T> add(T key, DataResult<T> value) {
			this.builder = this.builder.apply2stable((b, v) -> this.append(key, (T)v, (R)b), value);
			return this;
		}

		@Override
		public RecordBuilder<T> add(DataResult<T> key, DataResult<T> value) {
			this.builder = this.builder.ap(key.apply2stable((k, v) -> b -> this.append((T)k, (T)v, (R)b), value));
			return this;
		}
	}

	public static final class MapBuilder<T> extends RecordBuilder.AbstractUniversalBuilder<T, Builder<T, T>> {
		public MapBuilder(DynamicOps<T> ops) {
			super(ops);
		}

		protected Builder<T, T> initBuilder() {
			return ImmutableMap.builder();
		}

		protected Builder<T, T> append(T key, T value, Builder<T, T> builder) {
			return builder.put(key, value);
		}

		protected DataResult<T> build(Builder<T, T> builder, T prefix) {
			return this.ops().mergeToMap(prefix, builder.build());
		}
	}
}

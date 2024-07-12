package com.mojang.serialization;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.codecs.CompoundListCodec;
import com.mojang.serialization.codecs.EitherCodec;
import com.mojang.serialization.codecs.EitherMapCodec;
import com.mojang.serialization.codecs.KeyDispatchCodec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.OptionalFieldCodec;
import com.mojang.serialization.codecs.PairCodec;
import com.mojang.serialization.codecs.PairMapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.SimpleMapCodec;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface Codec<A> extends Encoder<A>, Decoder<A> {
	PrimitiveCodec<Boolean> BOOL = new PrimitiveCodec<Boolean>() {
		@Override
		public <T> DataResult<Boolean> read(DynamicOps<T> ops, T input) {
			return ops.getBooleanValue(input);
		}

		public <T> T write(DynamicOps<T> ops, Boolean value) {
			return ops.createBoolean(value);
		}

		public String toString() {
			return "Bool";
		}
	};
	PrimitiveCodec<Byte> BYTE = new PrimitiveCodec<Byte>() {
		@Override
		public <T> DataResult<Byte> read(DynamicOps<T> ops, T input) {
			return ops.getNumberValue(input).map(Number::byteValue);
		}

		public <T> T write(DynamicOps<T> ops, Byte value) {
			return ops.createByte(value);
		}

		public String toString() {
			return "Byte";
		}
	};
	PrimitiveCodec<Short> SHORT = new PrimitiveCodec<Short>() {
		@Override
		public <T> DataResult<Short> read(DynamicOps<T> ops, T input) {
			return ops.getNumberValue(input).map(Number::shortValue);
		}

		public <T> T write(DynamicOps<T> ops, Short value) {
			return ops.createShort(value);
		}

		public String toString() {
			return "Short";
		}
	};
	PrimitiveCodec<Integer> INT = new PrimitiveCodec<Integer>() {
		@Override
		public <T> DataResult<Integer> read(DynamicOps<T> ops, T input) {
			return ops.getNumberValue(input).map(Number::intValue);
		}

		public <T> T write(DynamicOps<T> ops, Integer value) {
			return ops.createInt(value);
		}

		public String toString() {
			return "Int";
		}
	};
	PrimitiveCodec<Long> LONG = new PrimitiveCodec<Long>() {
		@Override
		public <T> DataResult<Long> read(DynamicOps<T> ops, T input) {
			return ops.getNumberValue(input).map(Number::longValue);
		}

		public <T> T write(DynamicOps<T> ops, Long value) {
			return ops.createLong(value);
		}

		public String toString() {
			return "Long";
		}
	};
	PrimitiveCodec<Float> FLOAT = new PrimitiveCodec<Float>() {
		@Override
		public <T> DataResult<Float> read(DynamicOps<T> ops, T input) {
			return ops.getNumberValue(input).map(Number::floatValue);
		}

		public <T> T write(DynamicOps<T> ops, Float value) {
			return ops.createFloat(value);
		}

		public String toString() {
			return "Float";
		}
	};
	PrimitiveCodec<Double> DOUBLE = new PrimitiveCodec<Double>() {
		@Override
		public <T> DataResult<Double> read(DynamicOps<T> ops, T input) {
			return ops.getNumberValue(input).map(Number::doubleValue);
		}

		public <T> T write(DynamicOps<T> ops, Double value) {
			return ops.createDouble(value);
		}

		public String toString() {
			return "Double";
		}
	};
	PrimitiveCodec<String> STRING = new PrimitiveCodec<String>() {
		@Override
		public <T> DataResult<String> read(DynamicOps<T> ops, T input) {
			return ops.getStringValue(input);
		}

		public <T> T write(DynamicOps<T> ops, String value) {
			return ops.createString(value);
		}

		public String toString() {
			return "String";
		}
	};
	PrimitiveCodec<ByteBuffer> BYTE_BUFFER = new PrimitiveCodec<ByteBuffer>() {
		@Override
		public <T> DataResult<ByteBuffer> read(DynamicOps<T> ops, T input) {
			return ops.getByteBuffer(input);
		}

		public <T> T write(DynamicOps<T> ops, ByteBuffer value) {
			return ops.createByteList(value);
		}

		public String toString() {
			return "ByteBuffer";
		}
	};
	PrimitiveCodec<IntStream> INT_STREAM = new PrimitiveCodec<IntStream>() {
		@Override
		public <T> DataResult<IntStream> read(DynamicOps<T> ops, T input) {
			return ops.getIntStream(input);
		}

		public <T> T write(DynamicOps<T> ops, IntStream value) {
			return ops.createIntList(value);
		}

		public String toString() {
			return "IntStream";
		}
	};
	PrimitiveCodec<LongStream> LONG_STREAM = new PrimitiveCodec<LongStream>() {
		@Override
		public <T> DataResult<LongStream> read(DynamicOps<T> ops, T input) {
			return ops.getLongStream(input);
		}

		public <T> T write(DynamicOps<T> ops, LongStream value) {
			return ops.createLongList(value);
		}

		public String toString() {
			return "LongStream";
		}
	};
	Codec<Dynamic<?>> PASSTHROUGH = new Codec<Dynamic<?>>() {
		@Override
		public <T> DataResult<Pair<Dynamic<?>, T>> decode(DynamicOps<T> ops, T input) {
			return DataResult.success(Pair.of(new Dynamic<>(ops, input), ops.empty()));
		}

		public <T> DataResult<T> encode(Dynamic<?> input, DynamicOps<T> ops, T prefix) {
			if (input.getValue() == input.getOps().empty()) {
				return DataResult.success(prefix, Lifecycle.experimental());
			} else {
				T casted = input.convert(ops).getValue();
				if (prefix == ops.empty()) {
					return DataResult.success(casted, Lifecycle.experimental());
				} else {
					DataResult<T> toMap = ops.getMap(casted).flatMap(map -> ops.mergeToMap(prefix, map));
					return (DataResult<T>)toMap.result()
						.map(DataResult::success)
						.orElseGet(
							() -> {
								DataResult<T> toList = ops.getStream(casted).flatMap(stream -> ops.mergeToList(prefix, (List<T>)stream.collect(Collectors.toList())));
								return (DataResult)toList.result()
									.map(DataResult::success)
									.orElseGet(() -> DataResult.error("Don't know how to merge " + prefix + " and " + casted, prefix, Lifecycle.experimental()));
							}
						);
				}
			}
		}

		public String toString() {
			return "passthrough";
		}
	};
	MapCodec<Unit> EMPTY = MapCodec.of(Encoder.empty(), Decoder.unit(Unit.INSTANCE));

	default Codec<A> withLifecycle(Lifecycle lifecycle) {
		return new Codec<A>() {
			@Override
			public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
				return Codec.this.encode(input, ops, prefix).setLifecycle(lifecycle);
			}

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				return Codec.this.decode(ops, input).setLifecycle(lifecycle);
			}

			public String toString() {
				return Codec.this.toString();
			}
		};
	}

	default Codec<A> stable() {
		return this.withLifecycle(Lifecycle.stable());
	}

	default Codec<A> deprecated(int since) {
		return this.withLifecycle(Lifecycle.deprecated(since));
	}

	static <A> Codec<A> of(Encoder<A> encoder, Decoder<A> decoder) {
		return of(encoder, decoder, "Codec[" + encoder + " " + decoder + "]");
	}

	static <A> Codec<A> of(Encoder<A> encoder, Decoder<A> decoder, String name) {
		return new Codec<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				return decoder.decode(ops, input);
			}

			@Override
			public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
				return encoder.encode(input, ops, prefix);
			}

			public String toString() {
				return name;
			}
		};
	}

	static <A> MapCodec<A> of(MapEncoder<A> encoder, MapDecoder<A> decoder) {
		return of(encoder, decoder, "MapCodec[" + encoder + " " + decoder + "]");
	}

	static <A> MapCodec<A> of(MapEncoder<A> encoder, MapDecoder<A> decoder, String name) {
		return new MapCodec<A>() {
			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return Stream.concat(encoder.keys(ops), decoder.keys(ops));
			}

			@Override
			public <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input) {
				return decoder.decode(ops, input);
			}

			@Override
			public <T> RecordBuilder<T> encode(A input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
				return encoder.encode(input, ops, prefix);
			}

			public String toString() {
				return name;
			}
		};
	}

	static <F, S> Codec<Pair<F, S>> pair(Codec<F> first, Codec<S> second) {
		return new PairCodec<>(first, second);
	}

	static <F, S> Codec<Either<F, S>> either(Codec<F> first, Codec<S> second) {
		return new EitherCodec<>(first, second);
	}

	static <F, S> MapCodec<Pair<F, S>> mapPair(MapCodec<F> first, MapCodec<S> second) {
		return new PairMapCodec<>(first, second);
	}

	static <F, S> MapCodec<Either<F, S>> mapEither(MapCodec<F> first, MapCodec<S> second) {
		return new EitherMapCodec<>(first, second);
	}

	static <E> Codec<List<E>> list(Codec<E> elementCodec) {
		return new ListCodec<>(elementCodec);
	}

	static <K, V> Codec<List<Pair<K, V>>> compoundList(Codec<K> keyCodec, Codec<V> elementCodec) {
		return new CompoundListCodec<>(keyCodec, elementCodec);
	}

	static <K, V> SimpleMapCodec<K, V> simpleMap(Codec<K> keyCodec, Codec<V> elementCodec, Keyable keys) {
		return new SimpleMapCodec<>(keyCodec, elementCodec, keys);
	}

	static <K, V> UnboundedMapCodec<K, V> unboundedMap(Codec<K> keyCodec, Codec<V> elementCodec) {
		return new UnboundedMapCodec<>(keyCodec, elementCodec);
	}

	static <F> MapCodec<Optional<F>> optionalField(String name, Codec<F> elementCodec) {
		return new OptionalFieldCodec<>(name, elementCodec);
	}

	default Codec<List<A>> listOf() {
		return list(this);
	}

	default <S> Codec<S> xmap(Function<? super A, ? extends S> to, Function<? super S, ? extends A> from) {
		return of(this.comap(from), this.map(to), this.toString() + "[xmapped]");
	}

	default <S> Codec<S> comapFlatMap(Function<? super A, ? extends DataResult<? extends S>> to, Function<? super S, ? extends A> from) {
		return of(this.comap(from), this.flatMap(to), this.toString() + "[comapFlatMapped]");
	}

	default <S> Codec<S> flatComapMap(Function<? super A, ? extends S> to, Function<? super S, ? extends DataResult<? extends A>> from) {
		return of(this.flatComap(from), this.map(to), this.toString() + "[flatComapMapped]");
	}

	default <S> Codec<S> flatXmap(Function<? super A, ? extends DataResult<? extends S>> to, Function<? super S, ? extends DataResult<? extends A>> from) {
		return of(this.flatComap(from), this.flatMap(to), this.toString() + "[flatXmapped]");
	}

	default MapCodec<A> fieldOf(String name) {
		return MapCodec.of(Encoder.super.fieldOf(name), Decoder.super.fieldOf(name), "Field[" + name + ": " + this.toString() + "]");
	}

	default MapCodec<Optional<A>> optionalFieldOf(String name) {
		return optionalField(name, this);
	}

	default MapCodec<A> optionalFieldOf(String name, A defaultValue) {
		return optionalField(name, this).xmap(o -> o.orElse(defaultValue), a -> Objects.equals(a, defaultValue) ? Optional.empty() : Optional.of(a));
	}

	default MapCodec<A> optionalFieldOf(String name, A defaultValue, Lifecycle lifecycleOfDefault) {
		return this.optionalFieldOf(name, Lifecycle.experimental(), defaultValue, lifecycleOfDefault);
	}

	default MapCodec<A> optionalFieldOf(String name, Lifecycle fieldLifecycle, A defaultValue, Lifecycle lifecycleOfDefault) {
		return optionalField(name, this)
			.stable()
			.flatXmap(
				o -> (DataResult)o.map(v -> DataResult.success(v, fieldLifecycle)).orElse(DataResult.success(defaultValue, lifecycleOfDefault)),
				a -> Objects.equals(a, defaultValue) ? DataResult.success(Optional.empty(), lifecycleOfDefault) : DataResult.success(Optional.of(a), fieldLifecycle)
			);
	}

	default Codec<A> mapResult(Codec.ResultFunction<A> function) {
		return new Codec<A>() {
			@Override
			public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
				return function.coApply(ops, input, Codec.this.encode(input, ops, prefix));
			}

			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				return function.apply(ops, input, Codec.this.decode(ops, input));
			}

			public String toString() {
				return Codec.this + "[mapResult " + function + "]";
			}
		};
	}

	default Codec<A> withDefault(Consumer<String> onError, A value) {
		return this.withDefault(DataFixUtils.consumerToFunction(onError), value);
	}

	default Codec<A> withDefault(UnaryOperator<String> onError, A value) {
		return this.mapResult(new Codec.ResultFunction<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> ops, T input, DataResult<Pair<A, T>> a) {
				return DataResult.success((Pair<A, T>)a.mapError(onError).result().orElseGet(() -> Pair.of(value, input)));
			}

			@Override
			public <T> DataResult<T> coApply(DynamicOps<T> ops, A input, DataResult<T> t) {
				return t.mapError(onError);
			}

			public String toString() {
				return "WithDefault[" + onError + " " + value + "]";
			}
		});
	}

	default Codec<A> withDefault(Consumer<String> onError, Supplier<? extends A> value) {
		return this.withDefault(DataFixUtils.consumerToFunction(onError), value);
	}

	default Codec<A> withDefault(UnaryOperator<String> onError, Supplier<? extends A> value) {
		return this.mapResult(new Codec.ResultFunction<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> ops, T input, DataResult<Pair<A, T>> a) {
				return DataResult.success((Pair<A, T>)a.mapError(onError).result().orElseGet(() -> Pair.of(value.get(), input)));
			}

			@Override
			public <T> DataResult<T> coApply(DynamicOps<T> ops, A input, DataResult<T> t) {
				return t.mapError(onError);
			}

			public String toString() {
				return "WithDefault[" + onError + " " + value.get() + "]";
			}
		});
	}

	default Codec<A> withDefault(A value) {
		return this.mapResult(new Codec.ResultFunction<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> ops, T input, DataResult<Pair<A, T>> a) {
				return DataResult.success((Pair<A, T>)a.result().orElseGet(() -> Pair.of(value, input)));
			}

			@Override
			public <T> DataResult<T> coApply(DynamicOps<T> ops, A input, DataResult<T> t) {
				return t;
			}

			public String toString() {
				return "WithDefault[" + value + "]";
			}
		});
	}

	default Codec<A> withDefault(Supplier<? extends A> value) {
		return this.mapResult(new Codec.ResultFunction<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> ops, T input, DataResult<Pair<A, T>> a) {
				return DataResult.success((Pair<A, T>)a.result().orElseGet(() -> Pair.of(value.get(), input)));
			}

			@Override
			public <T> DataResult<T> coApply(DynamicOps<T> ops, A input, DataResult<T> t) {
				return t;
			}

			public String toString() {
				return "WithDefault[" + value.get() + "]";
			}
		});
	}

	default Codec<A> promotePartial(Consumer<String> onError) {
		return of(this, Decoder.super.promotePartial(onError));
	}

	static <A> Codec<A> unit(A defaultValue) {
		return unit((Supplier<A>)(() -> defaultValue));
	}

	static <A> Codec<A> unit(Supplier<A> defaultValue) {
		return MapCodec.<A>unit(defaultValue).codec();
	}

	default <E> Codec<E> dispatch(Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.dispatch("type", type, codec);
	}

	default <E> Codec<E> dispatch(String typeKey, Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.partialDispatch(typeKey, type.andThen(DataResult::success), codec.andThen(DataResult::success));
	}

	default <E> Codec<E> dispatchStable(Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.dispatchStable("type", type, codec);
	}

	default <E> Codec<E> dispatchStable(String typeKey, Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.partialDispatch(typeKey, e -> DataResult.success(type.apply(e), Lifecycle.stable()), a -> DataResult.success(codec.apply(a), Lifecycle.stable()));
	}

	default <E> Codec<E> dispatchDeprecated(int since, Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.dispatchDeprecated(since, "type", type, codec);
	}

	default <E> Codec<E> dispatchDeprecated(
		int since, String typeKey, Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec
	) {
		Lifecycle deprecated = Lifecycle.deprecated(since);
		return this.partialDispatch(typeKey, e -> DataResult.success(type.apply(e), deprecated), a -> DataResult.success(codec.apply(a), deprecated));
	}

	default <E> Codec<E> partialDispatch(
		String typeKey, Function<? super E, ? extends DataResult<? extends A>> type, Function<? super A, ? extends DataResult<? extends Codec<? extends E>>> codec
	) {
		return new KeyDispatchCodec(typeKey, this, type, codec).codec();
	}

	default <E> MapCodec<E> dispatchMap(Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.dispatchMap("type", type, codec);
	}

	default <E> MapCodec<E> dispatchMap(String typeKey, Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.partialDispatchMap(typeKey, type.andThen(DataResult::success), codec.andThen(DataResult::success));
	}

	default <E> MapCodec<E> dispatchStableMap(Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.dispatchStableMap("type", type, codec);
	}

	default <E> MapCodec<E> dispatchStableMap(String typeKey, Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.partialDispatchMap(
			typeKey, e -> DataResult.success(type.apply(e), Lifecycle.stable()), a -> DataResult.success(codec.apply(a), Lifecycle.stable())
		);
	}

	default <E> MapCodec<E> dispatchDeprecatedMap(int since, Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec) {
		return this.dispatchDeprecatedMap(since, "type", type, codec);
	}

	default <E> MapCodec<E> dispatchDeprecatedMap(
		int since, String typeKey, Function<? super E, ? extends A> type, Function<? super A, ? extends Codec<? extends E>> codec
	) {
		Lifecycle deprecated = Lifecycle.deprecated(since);
		return this.partialDispatchMap(typeKey, e -> DataResult.success(type.apply(e), deprecated), a -> DataResult.success(codec.apply(a), deprecated));
	}

	default <E> MapCodec<E> partialDispatchMap(
		String typeKey, Function<? super E, ? extends DataResult<? extends A>> type, Function<? super A, ? extends DataResult<? extends Codec<? extends E>>> codec
	) {
		return new KeyDispatchCodec<>(typeKey, this, type, codec);
	}

	public interface ResultFunction<A> {
		<T> DataResult<Pair<A, T>> apply(DynamicOps<T> dynamicOps, T object, DataResult<Pair<A, T>> dataResult);

		<T> DataResult<T> coApply(DynamicOps<T> dynamicOps, A object, DataResult<T> dataResult);
	}
}

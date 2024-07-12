package com.mojang.serialization;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public abstract class MapCodec<A> extends CompressorHolder implements MapDecoder<A>, MapEncoder<A> {
	public final <O> RecordCodecBuilder<O, A> forGetter(Function<O, A> getter) {
		return RecordCodecBuilder.of(getter, this);
	}

	public static <A> MapCodec<A> of(MapEncoder<A> encoder, MapDecoder<A> decoder) {
		return of(encoder, decoder, "MapCodec[" + encoder + " " + decoder + "]");
	}

	public static <A> MapCodec<A> of(MapEncoder<A> encoder, MapDecoder<A> decoder, String name) {
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

	public MapCodec<A> fieldOf(String name) {
		return this.codec().fieldOf(name);
	}

	public MapCodec<A> withLifecycle(Lifecycle lifecycle) {
		return new MapCodec<A>() {
			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return MapCodec.this.keys(ops);
			}

			@Override
			public <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input) {
				return MapCodec.this.decode(ops, input).setLifecycle(lifecycle);
			}

			@Override
			public <T> RecordBuilder<T> encode(A input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
				return MapCodec.this.encode(input, ops, prefix).setLifecycle(lifecycle);
			}

			public String toString() {
				return MapCodec.this.toString();
			}
		};
	}

	public Codec<A> codec() {
		return new MapCodec.MapCodecCodec<>(this);
	}

	public MapCodec<A> stable() {
		return this.withLifecycle(Lifecycle.stable());
	}

	public MapCodec<A> deprecated(int since) {
		return this.withLifecycle(Lifecycle.deprecated(since));
	}

	public <S> MapCodec<S> xmap(Function<? super A, ? extends S> to, Function<? super S, ? extends A> from) {
		return of(this.comap(from), this.map(to), this.toString() + "[xmapped]");
	}

	public <S> MapCodec<S> flatXmap(Function<? super A, ? extends DataResult<? extends S>> to, Function<? super S, ? extends DataResult<? extends A>> from) {
		return Codec.of(this.flatComap(from), this.flatMap(to), this.toString() + "[flatXmapped]");
	}

	public <E> MapCodec<A> dependent(MapCodec<E> initialInstance, Function<A, Pair<E, MapCodec<E>>> splitter, BiFunction<A, E, A> combiner) {
		return new MapCodec.Dependent<>(this, initialInstance, splitter, combiner);
	}

	@Override
	public abstract <T> Stream<T> keys(DynamicOps<T> dynamicOps);

	private MapCodec<A> mapResult(MapCodec.ResultFunction<A> function) {
		return new MapCodec<A>() {
			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return MapCodec.this.keys(ops);
			}

			@Override
			public <T> RecordBuilder<T> encode(A input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
				return function.coApply(ops, input, MapCodec.this.encode(input, ops, prefix));
			}

			@Override
			public <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input) {
				return function.apply(ops, input, MapCodec.this.decode(ops, input));
			}

			public String toString() {
				return MapCodec.this + "[mapResult " + function + "]";
			}
		};
	}

	public MapCodec<A> withDefault(Consumer<String> onError, A value) {
		return this.withDefault(DataFixUtils.consumerToFunction(onError), value);
	}

	public MapCodec<A> withDefault(UnaryOperator<String> onError, A value) {
		return this.mapResult(new MapCodec.ResultFunction<A>() {
			@Override
			public <T> DataResult<A> apply(DynamicOps<T> ops, MapLike<T> input, DataResult<A> a) {
				return DataResult.success((A)a.mapError(onError).result().orElse(value));
			}

			@Override
			public <T> RecordBuilder<T> coApply(DynamicOps<T> ops, A input, RecordBuilder<T> t) {
				return t.mapError(onError);
			}

			public String toString() {
				return "WithDefault[" + onError + " " + value + "]";
			}
		});
	}

	public MapCodec<A> withDefault(Consumer<String> onError, Supplier<? extends A> value) {
		return this.withDefault(DataFixUtils.consumerToFunction(onError), value);
	}

	public MapCodec<A> withDefault(UnaryOperator<String> onError, Supplier<? extends A> value) {
		return this.mapResult(new MapCodec.ResultFunction<A>() {
			@Override
			public <T> DataResult<A> apply(DynamicOps<T> ops, MapLike<T> input, DataResult<A> a) {
				return DataResult.success((A)a.mapError(onError).result().orElseGet(value));
			}

			@Override
			public <T> RecordBuilder<T> coApply(DynamicOps<T> ops, A input, RecordBuilder<T> t) {
				return t.mapError(onError);
			}

			public String toString() {
				return "WithDefault[" + onError + " " + value.get() + "]";
			}
		});
	}

	public MapCodec<A> withDefault(A value) {
		return this.mapResult(new MapCodec.ResultFunction<A>() {
			@Override
			public <T> DataResult<A> apply(DynamicOps<T> ops, MapLike<T> input, DataResult<A> a) {
				return DataResult.success((A)a.result().orElse(value));
			}

			@Override
			public <T> RecordBuilder<T> coApply(DynamicOps<T> ops, A input, RecordBuilder<T> t) {
				return t;
			}

			public String toString() {
				return "WithDefault[" + value + "]";
			}
		});
	}

	public MapCodec<A> withDefault(Supplier<? extends A> value) {
		return this.mapResult(new MapCodec.ResultFunction<A>() {
			@Override
			public <T> DataResult<A> apply(DynamicOps<T> ops, MapLike<T> input, DataResult<A> a) {
				return DataResult.success((A)a.result().orElseGet(value));
			}

			@Override
			public <T> RecordBuilder<T> coApply(DynamicOps<T> ops, A input, RecordBuilder<T> t) {
				return t;
			}

			public String toString() {
				return "WithDefault[" + value.get() + "]";
			}
		});
	}

	static <A> MapCodec<A> unit(A defaultValue) {
		return unit((Supplier<A>)(() -> defaultValue));
	}

	static <A> MapCodec<A> unit(Supplier<A> defaultValue) {
		return of(Encoder.empty(), Decoder.unit(defaultValue));
	}

	private static class Dependent<O, E> extends MapCodec<O> {
		private final MapCodec<E> initialInstance;
		private final Function<O, Pair<E, MapCodec<E>>> splitter;
		private final MapCodec<O> codec;
		private final BiFunction<O, E, O> combiner;

		public Dependent(MapCodec<O> codec, MapCodec<E> initialInstance, Function<O, Pair<E, MapCodec<E>>> splitter, BiFunction<O, E, O> combiner) {
			this.initialInstance = initialInstance;
			this.splitter = splitter;
			this.codec = codec;
			this.combiner = combiner;
		}

		@Override
		public <T> Stream<T> keys(DynamicOps<T> ops) {
			return Stream.concat(this.codec.keys(ops), this.initialInstance.keys(ops));
		}

		@Override
		public <T> DataResult<O> decode(DynamicOps<T> ops, MapLike<T> input) {
			return this.codec
				.decode(ops, input)
				.flatMap(
					base -> ((MapCodec)((Pair)this.splitter.apply(base)).getSecond())
							.decode(ops, input)
							.map(e -> this.combiner.apply(base, e))
							.setLifecycle(Lifecycle.experimental())
				);
		}

		@Override
		public <T> RecordBuilder<T> encode(O input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
			this.codec.encode(input, ops, prefix);
			Pair<E, MapCodec<E>> e = (Pair<E, MapCodec<E>>)this.splitter.apply(input);
			e.getSecond().encode(e.getFirst(), ops, prefix);
			return prefix.setLifecycle(Lifecycle.experimental());
		}
	}

	public static final class MapCodecCodec<A> implements Codec<A> {
		private final MapCodec<A> codec;

		public MapCodecCodec(MapCodec<A> codec) {
			this.codec = codec;
		}

		public MapCodec<A> codec() {
			return this.codec;
		}

		@Override
		public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
			return this.codec.compressedDecode(ops, input).map(r -> Pair.of(r, input));
		}

		@Override
		public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
			return this.codec.encode(input, ops, this.codec.compressedBuilder(ops)).build(prefix);
		}

		public String toString() {
			return this.codec.toString();
		}
	}

	interface ResultFunction<A> {
		<T> DataResult<A> apply(DynamicOps<T> dynamicOps, MapLike<T> mapLike, DataResult<A> dataResult);

		<T> RecordBuilder<T> coApply(DynamicOps<T> dynamicOps, A object, RecordBuilder<T> recordBuilder);
	}
}

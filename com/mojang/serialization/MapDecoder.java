package com.mojang.serialization;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface MapDecoder<A> extends Keyable {
	<T> DataResult<A> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike);

	default <T> DataResult<A> compressedDecode(DynamicOps<T> ops, T input) {
		if (ops.compressMaps()) {
			Optional<Consumer<Consumer<T>>> inputList = ops.getList(input).result();
			if (!inputList.isPresent()) {
				return DataResult.error("Input is not a list");
			} else {
				final KeyCompressor<T> compressor = this.compressor(ops);
				final List<T> entries = new ArrayList();
				((Consumer)inputList.get()).accept(entries::add);
				MapLike<T> map = new MapLike<T>() {
					@Nullable
					@Override
					public T get(T key) {
						return (T)entries.get(compressor.compress(key));
					}

					@Nullable
					@Override
					public T get(String key) {
						return (T)entries.get(compressor.compress(key));
					}

					@Override
					public Stream<Pair<T, T>> entries() {
						return IntStream.range(0, entries.size()).mapToObj(i -> Pair.of(compressor.decompress(i), entries.get(i))).filter(p -> p.getSecond() != null);
					}
				};
				return this.decode(ops, map);
			}
		} else {
			return ops.getMap(input).setLifecycle(Lifecycle.stable()).flatMap(mapx -> this.decode(ops, mapx));
		}
	}

	<T> KeyCompressor<T> compressor(DynamicOps<T> dynamicOps);

	default Decoder<A> decoder() {
		return new Decoder<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				return MapDecoder.this.compressedDecode(ops, input).map(r -> Pair.of(r, input));
			}

			public String toString() {
				return MapDecoder.this.toString();
			}
		};
	}

	default <B> MapDecoder<B> flatMap(Function<? super A, ? extends DataResult<? extends B>> function) {
		return new MapDecoder.Implementation<B>() {
			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return MapDecoder.this.keys(ops);
			}

			@Override
			public <T> DataResult<B> decode(DynamicOps<T> ops, MapLike<T> input) {
				return MapDecoder.this.decode(ops, input).flatMap(b -> ((DataResult)function.apply(b)).map(Function.identity()));
			}

			public String toString() {
				return MapDecoder.this.toString() + "[flatMapped]";
			}
		};
	}

	default <B> MapDecoder<B> map(Function<? super A, ? extends B> function) {
		return new MapDecoder.Implementation<B>() {
			@Override
			public <T> DataResult<B> decode(DynamicOps<T> ops, MapLike<T> input) {
				return MapDecoder.this.decode(ops, input).map(function);
			}

			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return MapDecoder.this.keys(ops);
			}

			public String toString() {
				return MapDecoder.this.toString() + "[mapped]";
			}
		};
	}

	default <E> MapDecoder<E> ap(MapDecoder<Function<? super A, ? extends E>> decoder) {
		return new MapDecoder.Implementation<E>() {
			@Override
			public <T> DataResult<E> decode(DynamicOps<T> ops, MapLike<T> input) {
				return MapDecoder.this.decode(ops, input).flatMap(f -> decoder.decode(ops, input).map(e -> e.apply(f)));
			}

			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return Stream.concat(MapDecoder.this.keys(ops), decoder.keys(ops));
			}

			public String toString() {
				return decoder.toString() + " * " + MapDecoder.this.toString();
			}
		};
	}

	default MapDecoder<A> withLifecycle(Lifecycle lifecycle) {
		return new MapDecoder.Implementation<A>() {
			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return MapDecoder.this.keys(ops);
			}

			@Override
			public <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input) {
				return MapDecoder.this.decode(ops, input).setLifecycle(lifecycle);
			}

			public String toString() {
				return MapDecoder.this.toString();
			}
		};
	}

	public abstract static class Implementation<A> extends CompressorHolder implements MapDecoder<A> {
	}
}

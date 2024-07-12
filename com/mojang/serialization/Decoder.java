package com.mojang.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapDecoder.Implementation;
import com.mojang.serialization.codecs.FieldDecoder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Decoder<A> {
	<T> DataResult<Pair<A, T>> decode(DynamicOps<T> dynamicOps, T object);

	default <T> DataResult<A> parse(DynamicOps<T> ops, T input) {
		return this.decode(ops, input).map(Pair::getFirst);
	}

	default <T> DataResult<Pair<A, T>> decode(Dynamic<T> input) {
		return this.decode(input.getOps(), input.getValue());
	}

	default <T> DataResult<A> parse(Dynamic<T> input) {
		return this.decode(input).map(Pair::getFirst);
	}

	default Decoder.Terminal<A> terminal() {
		return this::parse;
	}

	default Decoder.Boxed<A> boxed() {
		return this::decode;
	}

	default Decoder.Simple<A> simple() {
		return this::parse;
	}

	default MapDecoder<A> fieldOf(String name) {
		return new FieldDecoder<>(name, this);
	}

	default <B> Decoder<B> flatMap(Function<? super A, ? extends DataResult<? extends B>> function) {
		return new Decoder<B>() {
			@Override
			public <T> DataResult<Pair<B, T>> decode(DynamicOps<T> ops, T input) {
				return Decoder.this.decode(ops, input).flatMap(p -> ((DataResult)function.apply(p.getFirst())).map(r -> Pair.of(r, p.getSecond())));
			}

			public String toString() {
				return Decoder.this.toString() + "[flatMapped]";
			}
		};
	}

	default <B> Decoder<B> map(Function<? super A, ? extends B> function) {
		return new Decoder<B>() {
			@Override
			public <T> DataResult<Pair<B, T>> decode(DynamicOps<T> ops, T input) {
				return Decoder.this.decode(ops, input).map(p -> p.mapFirst(function));
			}

			public String toString() {
				return Decoder.this.toString() + "[mapped]";
			}
		};
	}

	default Decoder<A> promotePartial(Consumer<String> onError) {
		return new Decoder<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				return Decoder.this.decode(ops, input).promotePartial(onError);
			}

			public String toString() {
				return Decoder.this.toString() + "[promotePartial]";
			}
		};
	}

	default Decoder<A> withLifecycle(Lifecycle lifecycle) {
		return new Decoder<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				return Decoder.this.decode(ops, input).setLifecycle(lifecycle);
			}

			public String toString() {
				return Decoder.this.toString();
			}
		};
	}

	static <A> Decoder<A> ofTerminal(Decoder.Terminal<? extends A> terminal) {
		return terminal.decoder().map(Function.identity());
	}

	static <A> Decoder<A> ofBoxed(Decoder.Boxed<? extends A> boxed) {
		return boxed.decoder().map(Function.identity());
	}

	static <A> Decoder<A> ofSimple(Decoder.Simple<? extends A> simple) {
		return simple.decoder().map(Function.identity());
	}

	static <A> MapDecoder<A> unit(A instance) {
		return unit((Supplier<A>)(() -> instance));
	}

	static <A> MapDecoder<A> unit(Supplier<A> instance) {
		return new Implementation<A>() {
			@Override
			public <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input) {
				return DataResult.success((A)instance.get());
			}

			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return Stream.empty();
			}

			public String toString() {
				return "UnitDecoder[" + instance.get() + "]";
			}
		};
	}

	static <A> Decoder<A> error(String error) {
		return new Decoder<A>() {
			@Override
			public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
				return DataResult.error(error);
			}

			public String toString() {
				return "ErrorDecoder[" + error + ']';
			}
		};
	}

	public interface Boxed<A> {
		<T> DataResult<Pair<A, T>> decode(Dynamic<T> dynamic);

		default Decoder<A> decoder() {
			return new Decoder<A>() {
				@Override
				public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
					return Boxed.this.decode(new Dynamic<>(ops, input));
				}

				public String toString() {
					return "BoxedDecoder[" + Boxed.this + "]";
				}
			};
		}
	}

	public interface Simple<A> {
		<T> DataResult<A> decode(Dynamic<T> dynamic);

		default Decoder<A> decoder() {
			return new Decoder<A>() {
				@Override
				public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
					return Simple.this.decode(new Dynamic<>(ops, input)).map(a -> Pair.of(a, ops.empty()));
				}

				public String toString() {
					return "SimpleDecoder[" + Simple.this + "]";
				}
			};
		}
	}

	public interface Terminal<A> {
		<T> DataResult<A> decode(DynamicOps<T> dynamicOps, T object);

		default Decoder<A> decoder() {
			return new Decoder<A>() {
				@Override
				public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> ops, T input) {
					return Terminal.this.decode(ops, input).map(a -> Pair.of(a, ops.empty()));
				}

				public String toString() {
					return "TerminalDecoder[" + Terminal.this + "]";
				}
			};
		}
	}
}

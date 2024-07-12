package com.mojang.serialization;

import com.mojang.serialization.MapEncoder.Implementation;
import com.mojang.serialization.codecs.FieldEncoder;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Encoder<A> {
	<T> DataResult<T> encode(A object1, DynamicOps<T> dynamicOps, T object3);

	default <T> DataResult<T> encodeStart(DynamicOps<T> ops, A input) {
		return this.encode(input, ops, ops.empty());
	}

	default MapEncoder<A> fieldOf(String name) {
		return new FieldEncoder<>(name, this);
	}

	default <B> Encoder<B> comap(Function<? super B, ? extends A> function) {
		return new Encoder<B>() {
			@Override
			public <T> DataResult<T> encode(B input, DynamicOps<T> ops, T prefix) {
				return Encoder.this.encode(function.apply(input), ops, prefix);
			}

			public String toString() {
				return Encoder.this.toString() + "[comapped]";
			}
		};
	}

	default <B> Encoder<B> flatComap(Function<? super B, ? extends DataResult<? extends A>> function) {
		return new Encoder<B>() {
			@Override
			public <T> DataResult<T> encode(B input, DynamicOps<T> ops, T prefix) {
				return ((DataResult)function.apply(input)).flatMap(a -> Encoder.this.encode(a, ops, prefix));
			}

			public String toString() {
				return Encoder.this.toString() + "[flatComapped]";
			}
		};
	}

	default Encoder<A> withLifecycle(Lifecycle lifecycle) {
		return new Encoder<A>() {
			@Override
			public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
				return Encoder.this.encode(input, ops, prefix).setLifecycle(lifecycle);
			}

			public String toString() {
				return Encoder.this.toString();
			}
		};
	}

	static <A> MapEncoder<A> empty() {
		return new Implementation<A>() {
			@Override
			public <T> RecordBuilder<T> encode(A input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
				return prefix;
			}

			@Override
			public <T> Stream<T> keys(DynamicOps<T> ops) {
				return Stream.empty();
			}

			public String toString() {
				return "EmptyEncoder";
			}
		};
	}

	static <A> Encoder<A> error(String error) {
		return new Encoder<A>() {
			@Override
			public <T> DataResult<T> encode(A input, DynamicOps<T> ops, T prefix) {
				return DataResult.error(error + " " + input);
			}

			public String toString() {
				return "EmptyEncoder[" + error + "]";
			}
		};
	}
}

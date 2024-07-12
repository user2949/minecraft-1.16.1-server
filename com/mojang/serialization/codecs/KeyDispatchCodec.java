package com.mojang.serialization.codecs;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.MapCodec.MapCodecCodec;
import java.util.function.Function;
import java.util.stream.Stream;

public class KeyDispatchCodec<K, V> extends MapCodec<V> {
	private final String typeKey;
	private final Codec<K> keyCodec;
	private final String valueKey = "value";
	private final Function<? super V, ? extends DataResult<? extends K>> type;
	private final Function<? super K, ? extends DataResult<? extends Decoder<? extends V>>> decoder;
	private final Function<? super V, ? extends DataResult<? extends Encoder<V>>> encoder;

	public KeyDispatchCodec(
		String typeKey,
		Codec<K> keyCodec,
		Function<? super V, ? extends DataResult<? extends K>> type,
		Function<? super K, ? extends DataResult<? extends Decoder<? extends V>>> decoder,
		Function<? super V, ? extends DataResult<? extends Encoder<V>>> encoder
	) {
		this.typeKey = typeKey;
		this.keyCodec = keyCodec;
		this.type = type;
		this.decoder = decoder;
		this.encoder = encoder;
	}

	public KeyDispatchCodec(
		String typeKey,
		Codec<K> keyCodec,
		Function<? super V, ? extends DataResult<? extends K>> type,
		Function<? super K, ? extends DataResult<? extends Codec<? extends V>>> codec
	) {
		this(typeKey, keyCodec, type, codec, v -> getCodec(type, codec, v));
	}

	@Override
	public <T> DataResult<V> decode(DynamicOps<T> ops, MapLike<T> input) {
		T elementName = input.get(this.typeKey);
		return elementName == null
			? DataResult.error("Input does not contain a key [" + this.typeKey + "]: " + input)
			: this.keyCodec
				.decode(ops, elementName)
				.flatMap(
					type -> {
						DataResult<? extends Decoder<? extends V>> elementDecoder = (DataResult<? extends Decoder<? extends V>>)this.decoder.apply(type.getFirst());
						return elementDecoder.flatMap(
							c -> {
								if (ops.compressMaps()) {
									T value = input.get(ops.createString("value"));
									return value == null ? DataResult.error("Input does not have a \"value\" entry: " + input) : c.parse(ops, value).map(Function.identity());
								} else {
									return c instanceof MapCodecCodec
										? ((MapCodecCodec)c).codec().decode(ops, input).map(Function.identity())
										: c.decode(ops, ops.createMap(input.entries())).map(Pair::getFirst);
								}
							}
						);
					}
				);
	}

	@Override
	public <T> RecordBuilder<T> encode(V input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
		DataResult<? extends Encoder<V>> elementEncoder = (DataResult<? extends Encoder<V>>)this.encoder.apply(input);
		RecordBuilder<T> builder = prefix.withErrorsFrom(elementEncoder);
		if (!elementEncoder.result().isPresent()) {
			return builder;
		} else {
			Encoder<V> c = (Encoder<V>)elementEncoder.result().get();
			if (ops.compressMaps()) {
				return prefix.add(this.typeKey, ((DataResult)this.type.apply(input)).flatMap(t -> this.keyCodec.encodeStart(ops, (K)t)))
					.add("value", c.encodeStart(ops, input));
			} else if (c instanceof MapCodecCodec) {
				return ((MapCodecCodec)c)
					.codec()
					.encode(input, ops, prefix)
					.add(this.typeKey, ((DataResult)this.type.apply(input)).flatMap(t -> this.keyCodec.encodeStart(ops, (K)t)));
			} else {
				T typeString = ops.createString(this.typeKey);
				DataResult<MapLike<T>> element = c.encodeStart(ops, input).flatMap(ops::getMap);
				return (RecordBuilder<T>)element.map(map -> {
					map.entries().forEach(pair -> {
						if (pair.getFirst().equals(typeString)) {
							prefix.add(typeString, ((DataResult)this.type.apply(input)).flatMap(t -> this.keyCodec.encodeStart(ops, (K)t)));
						} else {
							prefix.add((T)pair.getFirst(), (T)pair.getSecond());
						}
					});
					return prefix;
				}).result().orElseGet(() -> prefix.withErrorsFrom(element));
			}
		}
	}

	@Override
	public <T> Stream<T> keys(DynamicOps<T> ops) {
		return Stream.of(this.typeKey, "value").map(ops::createString);
	}

	private static <K, V> DataResult<? extends Encoder<V>> getCodec(
		Function<? super V, ? extends DataResult<? extends K>> type, Function<? super K, ? extends DataResult<? extends Encoder<? extends V>>> encoder, V input
	) {
		return ((DataResult)type.apply(input)).flatMap(k -> ((DataResult)encoder.apply(k)).map(Function.identity())).map(c -> c);
	}

	public String toString() {
		return "KeyDispatchCodec[" + this.keyCodec.toString() + " " + this.type + " " + this.decoder + "]";
	}
}

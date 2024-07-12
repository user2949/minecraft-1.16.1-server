package com.mojang.serialization;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.kinds.ListBox;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.mutable.MutableObject;

public abstract class DynamicLike<T> {
	protected final DynamicOps<T> ops;

	public DynamicLike(DynamicOps<T> ops) {
		this.ops = ops;
	}

	public DynamicOps<T> getOps() {
		return this.ops;
	}

	public abstract DataResult<Number> asNumber();

	public abstract DataResult<String> asString();

	public abstract DataResult<Stream<Dynamic<T>>> asStreamOpt();

	public abstract DataResult<Stream<Pair<Dynamic<T>, Dynamic<T>>>> asMapOpt();

	public abstract DataResult<ByteBuffer> asByteBufferOpt();

	public abstract DataResult<IntStream> asIntStreamOpt();

	public abstract DataResult<LongStream> asLongStreamOpt();

	public abstract OptionalDynamic<T> get(String string);

	public abstract DataResult<T> getGeneric(T object);

	public abstract DataResult<T> getElement(String string);

	public abstract DataResult<T> getElementGeneric(T object);

	public abstract <A> DataResult<Pair<A, T>> decode(Decoder<? extends A> decoder);

	public <U> DataResult<List<U>> asListOpt(Function<Dynamic<T>, U> deserializer) {
		return this.asStreamOpt().map(stream -> (List)stream.map(deserializer).collect(Collectors.toList()));
	}

	public <K, V> DataResult<Map<K, V>> asMapOpt(Function<Dynamic<T>, K> keyDeserializer, Function<Dynamic<T>, V> valueDeserializer) {
		return this.asMapOpt().map(map -> {
			Builder<K, V> builder = ImmutableMap.builder();
			map.forEach(entry -> builder.put((K)keyDeserializer.apply(entry.getFirst()), (V)valueDeserializer.apply(entry.getSecond())));
			return builder.build();
		});
	}

	public <A> DataResult<A> read(Decoder<? extends A> decoder) {
		return this.decode(decoder).map(Pair::getFirst);
	}

	public <E> DataResult<List<E>> readList(Decoder<E> decoder) {
		return this.asStreamOpt()
			.map(s -> (List)s.map(d -> d.read(decoder)).collect(Collectors.toList()))
			.flatMap(l -> DataResult.unbox(ListBox.flip(DataResult.instance(), l)));
	}

	public <E> DataResult<List<E>> readList(Function<? super Dynamic<?>, ? extends DataResult<? extends E>> decoder) {
		return this.asStreamOpt()
			.map(s -> (List)s.map(decoder).map(r -> r.map(e -> e)).collect(Collectors.toList()))
			.flatMap(l -> DataResult.unbox(ListBox.flip(DataResult.instance(), l)));
	}

	public <K, V> DataResult<List<Pair<K, V>>> readMap(Decoder<K> keyDecoder, Decoder<V> valueDecoder) {
		return this.asMapOpt()
			.map(
				stream -> (List)stream.map(p -> ((Dynamic)p.getFirst()).read(keyDecoder).flatMap(f -> ((Dynamic)p.getSecond()).read(valueDecoder).map(s -> Pair.of(f, s))))
						.collect(Collectors.toList())
			)
			.flatMap(l -> DataResult.unbox(ListBox.flip(DataResult.instance(), l)));
	}

	public <K, V> DataResult<List<Pair<K, V>>> readMap(Decoder<K> keyDecoder, Function<K, Decoder<V>> valueDecoder) {
		return this.asMapOpt()
			.map(
				stream -> (List)stream.map(
							p -> ((Dynamic)p.getFirst()).read(keyDecoder).flatMap(f -> ((Dynamic)p.getSecond()).read((Decoder)valueDecoder.apply(f)).map(s -> Pair.of(f, s)))
						)
						.collect(Collectors.toList())
			)
			.flatMap(l -> DataResult.unbox(ListBox.flip(DataResult.instance(), l)));
	}

	public <R> DataResult<R> readMap(DataResult<R> empty, Function3<R, Dynamic<T>, Dynamic<T>, DataResult<R>> combiner) {
		return this.asMapOpt().flatMap(stream -> {
			MutableObject<DataResult<R>> result = new MutableObject<>(empty);
			stream.forEach(p -> result.setValue(result.getValue().flatMap(r -> combiner.apply((R)r, (Dynamic<T>)p.getFirst(), (Dynamic<T>)p.getSecond()))));
			return result.getValue();
		});
	}

	public Number asNumber(Number defaultValue) {
		return (Number)this.asNumber().result().orElse(defaultValue);
	}

	public int asInt(int defaultValue) {
		return this.asNumber(defaultValue).intValue();
	}

	public long asLong(long defaultValue) {
		return this.asNumber(defaultValue).longValue();
	}

	public float asFloat(float defaultValue) {
		return this.asNumber(defaultValue).floatValue();
	}

	public double asDouble(double defaultValue) {
		return this.asNumber(defaultValue).doubleValue();
	}

	public byte asByte(byte defaultValue) {
		return this.asNumber(defaultValue).byteValue();
	}

	public short asShort(short defaultValue) {
		return this.asNumber(defaultValue).shortValue();
	}

	public boolean asBoolean(boolean defaultValue) {
		return this.asNumber(defaultValue ? 1 : 0).intValue() != 0;
	}

	public String asString(String defaultValue) {
		return (String)this.asString().result().orElse(defaultValue);
	}

	public Stream<Dynamic<T>> asStream() {
		return (Stream<Dynamic<T>>)this.asStreamOpt().result().orElseGet(Stream::empty);
	}

	public ByteBuffer asByteBuffer() {
		return (ByteBuffer)this.asByteBufferOpt().result().orElseGet(() -> ByteBuffer.wrap(new byte[0]));
	}

	public IntStream asIntStream() {
		return (IntStream)this.asIntStreamOpt().result().orElseGet(IntStream::empty);
	}

	public LongStream asLongStream() {
		return (LongStream)this.asLongStreamOpt().result().orElseGet(LongStream::empty);
	}

	public <U> List<U> asList(Function<Dynamic<T>, U> deserializer) {
		return (List<U>)this.asListOpt(deserializer).result().orElseGet(ImmutableList::of);
	}

	public <K, V> Map<K, V> asMap(Function<Dynamic<T>, K> keyDeserializer, Function<Dynamic<T>, V> valueDeserializer) {
		return (Map<K, V>)this.asMapOpt(keyDeserializer, valueDeserializer).result().orElseGet(ImmutableMap::of);
	}

	public T getElement(String key, T defaultValue) {
		return (T)this.getElement(key).result().orElse(defaultValue);
	}

	public T getElementGeneric(T key, T defaultValue) {
		return (T)this.getElementGeneric(key).result().orElse(defaultValue);
	}

	public Dynamic<T> emptyList() {
		return new Dynamic<>(this.ops, this.ops.emptyList());
	}

	public Dynamic<T> emptyMap() {
		return new Dynamic<>(this.ops, this.ops.emptyMap());
	}

	public Dynamic<T> createNumeric(Number i) {
		return new Dynamic<>(this.ops, this.ops.createNumeric(i));
	}

	public Dynamic<T> createByte(byte value) {
		return new Dynamic<>(this.ops, this.ops.createByte(value));
	}

	public Dynamic<T> createShort(short value) {
		return new Dynamic<>(this.ops, this.ops.createShort(value));
	}

	public Dynamic<T> createInt(int value) {
		return new Dynamic<>(this.ops, this.ops.createInt(value));
	}

	public Dynamic<T> createLong(long value) {
		return new Dynamic<>(this.ops, this.ops.createLong(value));
	}

	public Dynamic<T> createFloat(float value) {
		return new Dynamic<>(this.ops, this.ops.createFloat(value));
	}

	public Dynamic<T> createDouble(double value) {
		return new Dynamic<>(this.ops, this.ops.createDouble(value));
	}

	public Dynamic<T> createBoolean(boolean value) {
		return new Dynamic<>(this.ops, this.ops.createBoolean(value));
	}

	public Dynamic<T> createString(String value) {
		return new Dynamic<>(this.ops, this.ops.createString(value));
	}

	public Dynamic<T> createList(Stream<? extends Dynamic<?>> input) {
		return new Dynamic<>(this.ops, this.ops.createList(input.map(element -> element.cast(this.ops))));
	}

	public Dynamic<T> createMap(Map<? extends Dynamic<?>, ? extends Dynamic<?>> map) {
		Builder<T, T> builder = ImmutableMap.builder();

		for (Entry<? extends Dynamic<?>, ? extends Dynamic<?>> entry : map.entrySet()) {
			builder.put(((Dynamic)entry.getKey()).cast(this.ops), ((Dynamic)entry.getValue()).cast(this.ops));
		}

		return new Dynamic<>(this.ops, this.ops.createMap(builder.build()));
	}

	public Dynamic<?> createByteList(ByteBuffer input) {
		return new Dynamic<>(this.ops, this.ops.createByteList(input));
	}

	public Dynamic<?> createIntList(IntStream input) {
		return new Dynamic<>(this.ops, this.ops.createIntList(input));
	}

	public Dynamic<?> createLongList(LongStream input) {
		return new Dynamic<>(this.ops, this.ops.createLongList(input));
	}
}

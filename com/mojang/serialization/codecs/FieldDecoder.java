package com.mojang.serialization.codecs;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.MapDecoder.Implementation;
import java.util.Objects;
import java.util.stream.Stream;

public final class FieldDecoder<A> extends Implementation<A> {
	protected final String name;
	private final Decoder<A> elementCodec;

	public FieldDecoder(String name, Decoder<A> elementCodec) {
		this.name = name;
		this.elementCodec = elementCodec;
	}

	@Override
	public <T> DataResult<A> decode(DynamicOps<T> ops, MapLike<T> input) {
		T value = input.get(this.name);
		return value == null ? DataResult.error("No key " + this.name + " in " + input) : this.elementCodec.parse(ops, value);
	}

	@Override
	public <T> Stream<T> keys(DynamicOps<T> ops) {
		return Stream.of(ops.createString(this.name));
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			FieldDecoder<?> that = (FieldDecoder<?>)o;
			return Objects.equals(this.name, that.name) && Objects.equals(this.elementCodec, that.elementCodec);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.name, this.elementCodec});
	}

	public String toString() {
		return "FieldDecoder[" + this.name + ": " + this.elementCodec + ']';
	}
}

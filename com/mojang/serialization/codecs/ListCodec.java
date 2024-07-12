package com.mojang.serialization.codecs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.ListBuilder;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.apache.commons.lang3.mutable.MutableObject;

public final class ListCodec<A> implements Codec<List<A>> {
	private final Codec<A> elementCodec;

	public ListCodec(Codec<A> elementCodec) {
		this.elementCodec = elementCodec;
	}

	public <T> DataResult<T> encode(List<A> input, DynamicOps<T> ops, T prefix) {
		ListBuilder<T> builder = ops.listBuilder();

		for (A a : input) {
			builder.add(this.elementCodec.encodeStart(ops, a));
		}

		return builder.build(prefix);
	}

	@Override
	public <T> DataResult<Pair<List<A>, T>> decode(DynamicOps<T> ops, T input) {
		return ops.getList(input).setLifecycle(Lifecycle.stable()).flatMap(stream -> {
			Builder<A> read = ImmutableList.builder();
			java.util.stream.Stream.Builder<T> failed = Stream.builder();
			MutableObject<DataResult<Unit>> result = new MutableObject<>(DataResult.success(Unit.INSTANCE, Lifecycle.stable()));
			stream.accept((Consumer)t -> {
				DataResult<Pair<A, T>> element = this.elementCodec.decode(ops, (T)t);
				element.error().ifPresent(e -> failed.add(t));
				result.setValue(result.getValue().apply2stable((r, v) -> {
					read.add((A)v.getFirst());
					return r;
				}, element));
			});
			ImmutableList<A> elements = read.build();
			T errors = ops.createList(failed.build());
			Pair<List<A>, T> pair = Pair.of(elements, errors);
			return result.getValue().<Pair<List<A>, T>>map(unit -> pair).setPartial(pair);
		});
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o != null && this.getClass() == o.getClass()) {
			ListCodec<?> listCodec = (ListCodec<?>)o;
			return Objects.equals(this.elementCodec, listCodec.elementCodec);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.elementCodec});
	}

	public String toString() {
		return "ListCodec[" + this.elementCodec + ']';
	}
}

package com.mojang.serialization.codecs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.Map;
import java.util.Map.Entry;

public interface BaseMapCodec<K, V> {
	Codec<K> keyCodec();

	Codec<V> elementCodec();

	default <T> DataResult<Map<K, V>> decode(DynamicOps<T> ops, MapLike<T> input) {
		Builder<K, V> read = ImmutableMap.builder();
		com.google.common.collect.ImmutableList.Builder<Pair<T, T>> failed = ImmutableList.builder();
		DataResult<Unit> result = (DataResult<Unit>)input.entries().reduce(DataResult.success(Unit.INSTANCE, Lifecycle.stable()), (r, pair) -> {
			DataResult<K> k = this.keyCodec().parse(ops, (T)pair.getFirst());
			DataResult<V> v = this.elementCodec().parse(ops, (T)pair.getSecond());
			DataResult<Pair<K, V>> entry = k.apply2stable(Pair::of, v);
			entry.error().ifPresent(e -> failed.add(pair));
			return r.apply2stable((u, p) -> {
				read.put((K)p.getFirst(), (V)p.getSecond());
				return u;
			}, entry);
		}, (r1, r2) -> r1.apply2stable((u1, u2) -> u1, r2));
		Map<K, V> elements = read.build();
		T errors = ops.createMap(failed.build().stream());
		return result.<Map<K, V>>map(unit -> elements).setPartial(elements).mapError(e -> e + " missed input: " + errors);
	}

	default <T> RecordBuilder<T> encode(Map<K, V> input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
		for (Entry<K, V> entry : input.entrySet()) {
			prefix.add(this.keyCodec().encodeStart(ops, (K)entry.getKey()), this.elementCodec().encodeStart(ops, (V)entry.getValue()));
		}

		return prefix;
	}
}

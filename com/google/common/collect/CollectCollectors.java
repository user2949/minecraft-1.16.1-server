package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap.Builder;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

@GwtCompatible
final class CollectCollectors {
	static <T, K, V> Collector<T, ?, ImmutableBiMap<K, V>> toImmutableBiMap(
		Function<? super T, ? extends K> keyFunction, Function<? super T, ? extends V> valueFunction
	) {
		Preconditions.checkNotNull(keyFunction);
		Preconditions.checkNotNull(valueFunction);
		return Collector.of(Builder::new, (builder, input) -> builder.put(keyFunction.apply(input), valueFunction.apply(input)), Builder::combine, Builder::build);
	}

	static <E> Collector<E, ?, ImmutableList<E>> toImmutableList() {
		return Collector.of(
			ImmutableList::builder,
			com.google.common.collect.ImmutableList.Builder::add,
			com.google.common.collect.ImmutableList.Builder::combine,
			com.google.common.collect.ImmutableList.Builder::build
		);
	}

	static <T, K, V> Collector<T, ?, ImmutableMap<K, V>> toImmutableMap(
		Function<? super T, ? extends K> keyFunction, Function<? super T, ? extends V> valueFunction
	) {
		Preconditions.checkNotNull(keyFunction);
		Preconditions.checkNotNull(valueFunction);
		return Collector.of(
			com.google.common.collect.ImmutableMap.Builder::new,
			(builder, input) -> builder.put(keyFunction.apply(input), valueFunction.apply(input)),
			com.google.common.collect.ImmutableMap.Builder::combine,
			com.google.common.collect.ImmutableMap.Builder::build
		);
	}

	static <E> Collector<E, ?, ImmutableSet<E>> toImmutableSet() {
		return Collector.of(
			ImmutableSet::builder,
			com.google.common.collect.ImmutableSet.Builder::add,
			com.google.common.collect.ImmutableSet.Builder::combine,
			com.google.common.collect.ImmutableSet.Builder::build
		);
	}

	static <T, K, V> Collector<T, ?, ImmutableSortedMap<K, V>> toImmutableSortedMap(
		Comparator<? super K> comparator, Function<? super T, ? extends K> keyFunction, Function<? super T, ? extends V> valueFunction
	) {
		Preconditions.checkNotNull(comparator);
		Preconditions.checkNotNull(keyFunction);
		Preconditions.checkNotNull(valueFunction);
		return Collector.of(
			() -> new com.google.common.collect.ImmutableSortedMap.Builder(comparator),
			(builder, input) -> builder.put(keyFunction.apply(input), valueFunction.apply(input)),
			com.google.common.collect.ImmutableSortedMap.Builder::combine,
			com.google.common.collect.ImmutableSortedMap.Builder::build,
			Characteristics.UNORDERED
		);
	}

	static <E> Collector<E, ?, ImmutableSortedSet<E>> toImmutableSortedSet(Comparator<? super E> comparator) {
		Preconditions.checkNotNull(comparator);
		return Collector.of(
			() -> new com.google.common.collect.ImmutableSortedSet.Builder(comparator),
			com.google.common.collect.ImmutableSortedSet.Builder::add,
			com.google.common.collect.ImmutableSortedSet.Builder::combine,
			com.google.common.collect.ImmutableSortedSet.Builder::build
		);
	}
}

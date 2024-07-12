package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public interface SetMultimap<K, V> extends Multimap<K, V> {
	Set<V> get(@Nullable K object);

	@CanIgnoreReturnValue
	Set<V> removeAll(@Nullable Object object);

	@CanIgnoreReturnValue
	Set<V> replaceValues(K object, Iterable<? extends V> iterable);

	Set<Entry<K, V>> entries();

	@Override
	Map<K, Collection<V>> asMap();

	@Override
	boolean equals(@Nullable Object object);
}

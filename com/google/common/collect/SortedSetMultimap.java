package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public interface SortedSetMultimap<K, V> extends SetMultimap<K, V> {
	SortedSet<V> get(@Nullable K object);

	@CanIgnoreReturnValue
	SortedSet<V> removeAll(@Nullable Object object);

	@CanIgnoreReturnValue
	SortedSet<V> replaceValues(K object, Iterable<? extends V> iterable);

	@Override
	Map<K, Collection<V>> asMap();

	Comparator<? super V> valueComparator();
}

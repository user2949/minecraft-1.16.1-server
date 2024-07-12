package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public interface ListMultimap<K, V> extends Multimap<K, V> {
	List<V> get(@Nullable K object);

	@CanIgnoreReturnValue
	List<V> removeAll(@Nullable Object object);

	@CanIgnoreReturnValue
	List<V> replaceValues(K object, Iterable<? extends V> iterable);

	@Override
	Map<K, Collection<V>> asMap();

	@Override
	boolean equals(@Nullable Object object);
}

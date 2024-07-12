package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible
public interface Multimap<K, V> {
	int size();

	boolean isEmpty();

	boolean containsKey(@Nullable @CompatibleWith("K") Object object);

	boolean containsValue(@Nullable @CompatibleWith("V") Object object);

	boolean containsEntry(@Nullable @CompatibleWith("K") Object object1, @Nullable @CompatibleWith("V") Object object2);

	@CanIgnoreReturnValue
	boolean put(@Nullable K object1, @Nullable V object2);

	@CanIgnoreReturnValue
	boolean remove(@Nullable @CompatibleWith("K") Object object1, @Nullable @CompatibleWith("V") Object object2);

	@CanIgnoreReturnValue
	boolean putAll(@Nullable K object, Iterable<? extends V> iterable);

	@CanIgnoreReturnValue
	boolean putAll(Multimap<? extends K, ? extends V> multimap);

	@CanIgnoreReturnValue
	Collection<V> replaceValues(@Nullable K object, Iterable<? extends V> iterable);

	@CanIgnoreReturnValue
	Collection<V> removeAll(@Nullable @CompatibleWith("K") Object object);

	void clear();

	Collection<V> get(@Nullable K object);

	Set<K> keySet();

	Multiset<K> keys();

	Collection<V> values();

	Collection<Entry<K, V>> entries();

	default void forEach(BiConsumer<? super K, ? super V> action) {
		Preconditions.checkNotNull(action);
		this.entries().forEach(entry -> action.accept(entry.getKey(), entry.getValue()));
	}

	Map<K, Collection<V>> asMap();

	boolean equals(@Nullable Object object);

	int hashCode();
}

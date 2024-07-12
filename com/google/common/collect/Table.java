package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public interface Table<R, C, V> {
	boolean contains(@Nullable @CompatibleWith("R") Object object1, @Nullable @CompatibleWith("C") Object object2);

	boolean containsRow(@Nullable @CompatibleWith("R") Object object);

	boolean containsColumn(@Nullable @CompatibleWith("C") Object object);

	boolean containsValue(@Nullable @CompatibleWith("V") Object object);

	V get(@Nullable @CompatibleWith("R") Object object1, @Nullable @CompatibleWith("C") Object object2);

	boolean isEmpty();

	int size();

	boolean equals(@Nullable Object object);

	int hashCode();

	void clear();

	@Nullable
	@CanIgnoreReturnValue
	V put(R object1, C object2, V object3);

	void putAll(Table<? extends R, ? extends C, ? extends V> table);

	@Nullable
	@CanIgnoreReturnValue
	V remove(@Nullable @CompatibleWith("R") Object object1, @Nullable @CompatibleWith("C") Object object2);

	Map<C, V> row(R object);

	Map<R, V> column(C object);

	Set<Table.Cell<R, C, V>> cellSet();

	Set<R> rowKeySet();

	Set<C> columnKeySet();

	Collection<V> values();

	Map<R, Map<C, V>> rowMap();

	Map<C, Map<R, V>> columnMap();

	public interface Cell<R, C, V> {
		@Nullable
		R getRowKey();

		@Nullable
		C getColumnKey();

		@Nullable
		V getValue();

		boolean equals(@Nullable Object object);

		int hashCode();
	}
}

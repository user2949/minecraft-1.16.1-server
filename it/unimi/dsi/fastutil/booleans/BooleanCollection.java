package it.unimi.dsi.fastutil.booleans;

import java.util.Collection;

public interface BooleanCollection extends Collection<Boolean>, BooleanIterable {
	@Override
	BooleanIterator iterator();

	boolean add(boolean boolean1);

	boolean contains(boolean boolean1);

	boolean rem(boolean boolean1);

	@Deprecated
	default boolean add(Boolean key) {
		return this.add(key.booleanValue());
	}

	@Deprecated
	default boolean contains(Object key) {
		return key == null ? false : this.contains(((Boolean)key).booleanValue());
	}

	@Deprecated
	default boolean remove(Object key) {
		return key == null ? false : this.rem((Boolean)key);
	}

	boolean[] toBooleanArray();

	@Deprecated
	boolean[] toBooleanArray(boolean[] arr);

	boolean[] toArray(boolean[] arr);

	boolean addAll(BooleanCollection booleanCollection);

	boolean containsAll(BooleanCollection booleanCollection);

	boolean removeAll(BooleanCollection booleanCollection);

	boolean retainAll(BooleanCollection booleanCollection);
}

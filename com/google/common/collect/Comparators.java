package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Iterator;

@Beta
@GwtCompatible
public final class Comparators {
	private Comparators() {
	}

	public static <T, S extends T> Comparator<Iterable<S>> lexicographical(Comparator<T> comparator) {
		return new LexicographicalOrdering<>(Preconditions.checkNotNull(comparator));
	}

	public static <T> boolean isInOrder(Iterable<? extends T> iterable, Comparator<T> comparator) {
		Preconditions.checkNotNull(comparator);
		Iterator<? extends T> it = iterable.iterator();
		if (it.hasNext()) {
			T prev = (T)it.next();

			while (it.hasNext()) {
				T next = (T)it.next();
				if (comparator.compare(prev, next) > 0) {
					return false;
				}

				prev = next;
			}
		}

		return true;
	}

	public static <T> boolean isInStrictOrder(Iterable<? extends T> iterable, Comparator<T> comparator) {
		Preconditions.checkNotNull(comparator);
		Iterator<? extends T> it = iterable.iterator();
		if (it.hasNext()) {
			T prev = (T)it.next();

			while (it.hasNext()) {
				T next = (T)it.next();
				if (comparator.compare(prev, next) >= 0) {
					return false;
				}

				prev = next;
			}
		}

		return true;
	}
}

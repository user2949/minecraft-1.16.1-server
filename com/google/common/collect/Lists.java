package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import javax.annotation.Nullable;

@GwtCompatible(
	emulated = true
)
public final class Lists {
	private Lists() {
	}

	@GwtCompatible(
		serializable = true
	)
	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList();
	}

	@SafeVarargs
	@CanIgnoreReturnValue
	@GwtCompatible(
		serializable = true
	)
	public static <E> ArrayList<E> newArrayList(E... elements) {
		Preconditions.checkNotNull(elements);
		int capacity = computeArrayListCapacity(elements.length);
		ArrayList<E> list = new ArrayList(capacity);
		Collections.addAll(list, elements);
		return list;
	}

	@VisibleForTesting
	static int computeArrayListCapacity(int arraySize) {
		CollectPreconditions.checkNonnegative(arraySize, "arraySize");
		return Ints.saturatedCast(5L + (long)arraySize + (long)(arraySize / 10));
	}

	@CanIgnoreReturnValue
	@GwtCompatible(
		serializable = true
	)
	public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
		Preconditions.checkNotNull(elements);
		return elements instanceof Collection ? new ArrayList(Collections2.cast(elements)) : newArrayList(elements.iterator());
	}

	@CanIgnoreReturnValue
	@GwtCompatible(
		serializable = true
	)
	public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
		ArrayList<E> list = newArrayList();
		Iterators.addAll(list, elements);
		return list;
	}

	@GwtCompatible(
		serializable = true
	)
	public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
		CollectPreconditions.checkNonnegative(initialArraySize, "initialArraySize");
		return new ArrayList(initialArraySize);
	}

	@GwtCompatible(
		serializable = true
	)
	public static <E> ArrayList<E> newArrayListWithExpectedSize(int estimatedSize) {
		return new ArrayList(computeArrayListCapacity(estimatedSize));
	}

	@GwtCompatible(
		serializable = true
	)
	public static <E> LinkedList<E> newLinkedList() {
		return new LinkedList();
	}

	@GwtCompatible(
		serializable = true
	)
	public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
		LinkedList<E> list = newLinkedList();
		Iterables.addAll(list, elements);
		return list;
	}

	@GwtIncompatible
	public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
		return new CopyOnWriteArrayList();
	}

	@GwtIncompatible
	public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> elements) {
		Collection<? extends E> elementsCollection = (Collection<? extends E>)(elements instanceof Collection ? Collections2.cast(elements) : newArrayList(elements));
		return new CopyOnWriteArrayList(elementsCollection);
	}

	public static <E> List<E> asList(@Nullable E first, E[] rest) {
		return new Lists.OnePlusArrayList<E>(first, rest);
	}

	public static <E> List<E> asList(@Nullable E first, @Nullable E second, E[] rest) {
		return new Lists.TwoPlusArrayList<E>(first, second, rest);
	}

	public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> lists) {
		return CartesianList.create(lists);
	}

	@SafeVarargs
	public static <B> List<List<B>> cartesianProduct(List<? extends B>... lists) {
		return cartesianProduct(Arrays.asList(lists));
	}

	public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function) {
		return (List<T>)(fromList instanceof RandomAccess
			? new Lists.TransformingRandomAccessList<F, T>(fromList, function)
			: new Lists.TransformingSequentialList<F, T>(fromList, function));
	}

	public static <T> List<List<T>> partition(List<T> list, int size) {
		Preconditions.checkNotNull(list);
		Preconditions.checkArgument(size > 0);
		return (List<List<T>>)(list instanceof RandomAccess ? new Lists.RandomAccessPartition<List<T>>(list, size) : new Lists.Partition<List<T>>(list, size));
	}

	public static ImmutableList<Character> charactersOf(String string) {
		return new Lists.StringAsImmutableList(Preconditions.checkNotNull(string));
	}

	@Beta
	public static List<Character> charactersOf(CharSequence sequence) {
		return new Lists.CharSequenceAsList(Preconditions.checkNotNull(sequence));
	}

	public static <T> List<T> reverse(List<T> list) {
		if (list instanceof ImmutableList) {
			return ((ImmutableList)list).reverse();
		} else if (list instanceof Lists.ReverseList) {
			return ((Lists.ReverseList)list).getForwardList();
		} else {
			return (List<T>)(list instanceof RandomAccess ? new Lists.RandomAccessReverseList<T>(list) : new Lists.ReverseList<T>(list));
		}
	}

	static int hashCodeImpl(List<?> list) {
		int hashCode = 1;

		for (Object o : list) {
			hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());
			hashCode = ~(~hashCode);
		}

		return hashCode;
	}

	static boolean equalsImpl(List<?> thisList, @Nullable Object other) {
		if (other == Preconditions.<List<?>>checkNotNull(thisList)) {
			return true;
		} else if (!(other instanceof List)) {
			return false;
		} else {
			List<?> otherList = (List<?>)other;
			int size = thisList.size();
			if (size != otherList.size()) {
				return false;
			} else if (thisList instanceof RandomAccess && otherList instanceof RandomAccess) {
				for (int i = 0; i < size; i++) {
					if (!Objects.equal(thisList.get(i), otherList.get(i))) {
						return false;
					}
				}

				return true;
			} else {
				return Iterators.elementsEqual(thisList.iterator(), otherList.iterator());
			}
		}
	}

	static <E> boolean addAllImpl(List<E> list, int index, Iterable<? extends E> elements) {
		boolean changed = false;
		ListIterator<E> listIterator = list.listIterator(index);

		for (E e : elements) {
			listIterator.add(e);
			changed = true;
		}

		return changed;
	}

	static int indexOfImpl(List<?> list, @Nullable Object element) {
		if (list instanceof RandomAccess) {
			return indexOfRandomAccess(list, element);
		} else {
			ListIterator<?> listIterator = list.listIterator();

			while (listIterator.hasNext()) {
				if (Objects.equal(element, listIterator.next())) {
					return listIterator.previousIndex();
				}
			}

			return -1;
		}
	}

	private static int indexOfRandomAccess(List<?> list, @Nullable Object element) {
		int size = list.size();
		if (element == null) {
			for (int i = 0; i < size; i++) {
				if (list.get(i) == null) {
					return i;
				}
			}
		} else {
			for (int ix = 0; ix < size; ix++) {
				if (element.equals(list.get(ix))) {
					return ix;
				}
			}
		}

		return -1;
	}

	static int lastIndexOfImpl(List<?> list, @Nullable Object element) {
		if (list instanceof RandomAccess) {
			return lastIndexOfRandomAccess(list, element);
		} else {
			ListIterator<?> listIterator = list.listIterator(list.size());

			while (listIterator.hasPrevious()) {
				if (Objects.equal(element, listIterator.previous())) {
					return listIterator.nextIndex();
				}
			}

			return -1;
		}
	}

	private static int lastIndexOfRandomAccess(List<?> list, @Nullable Object element) {
		if (element == null) {
			for (int i = list.size() - 1; i >= 0; i--) {
				if (list.get(i) == null) {
					return i;
				}
			}
		} else {
			for (int ix = list.size() - 1; ix >= 0; ix--) {
				if (element.equals(list.get(ix))) {
					return ix;
				}
			}
		}

		return -1;
	}

	static <E> ListIterator<E> listIteratorImpl(List<E> list, int index) {
		return new Lists.AbstractListWrapper(list).listIterator(index);
	}

	static <E> List<E> subListImpl(List<E> list, int fromIndex, int toIndex) {
		List<E> wrapper;
		if (list instanceof RandomAccess) {
			wrapper = new Lists.RandomAccessListWrapper<E>(list) {
				private static final long serialVersionUID = 0L;

				public ListIterator<E> listIterator(int index) {
					return this.backingList.listIterator(index);
				}
			};
		} else {
			wrapper = new Lists.AbstractListWrapper<E>(list) {
				private static final long serialVersionUID = 0L;

				public ListIterator<E> listIterator(int index) {
					return this.backingList.listIterator(index);
				}
			};
		}

		return wrapper.subList(fromIndex, toIndex);
	}

	static <T> List<T> cast(Iterable<T> iterable) {
		return (List<T>)iterable;
	}

	private static class AbstractListWrapper<E> extends AbstractList<E> {
		final List<E> backingList;

		AbstractListWrapper(List<E> backingList) {
			this.backingList = Preconditions.checkNotNull(backingList);
		}

		public void add(int index, E element) {
			this.backingList.add(index, element);
		}

		public boolean addAll(int index, Collection<? extends E> c) {
			return this.backingList.addAll(index, c);
		}

		public E get(int index) {
			return (E)this.backingList.get(index);
		}

		public E remove(int index) {
			return (E)this.backingList.remove(index);
		}

		public E set(int index, E element) {
			return (E)this.backingList.set(index, element);
		}

		public boolean contains(Object o) {
			return this.backingList.contains(o);
		}

		public int size() {
			return this.backingList.size();
		}
	}

	private static final class CharSequenceAsList extends AbstractList<Character> {
		private final CharSequence sequence;

		CharSequenceAsList(CharSequence sequence) {
			this.sequence = sequence;
		}

		public Character get(int index) {
			Preconditions.checkElementIndex(index, this.size());
			return this.sequence.charAt(index);
		}

		public int size() {
			return this.sequence.length();
		}
	}

	private static class OnePlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
		final E first;
		final E[] rest;
		private static final long serialVersionUID = 0L;

		OnePlusArrayList(@Nullable E first, E[] rest) {
			this.first = first;
			this.rest = (E[])((Object[])Preconditions.checkNotNull(rest));
		}

		public int size() {
			return IntMath.saturatedAdd(this.rest.length, 1);
		}

		public E get(int index) {
			Preconditions.checkElementIndex(index, this.size());
			return index == 0 ? this.first : this.rest[index - 1];
		}
	}

	private static class Partition<T> extends AbstractList<List<T>> {
		final List<T> list;
		final int size;

		Partition(List<T> list, int size) {
			this.list = list;
			this.size = size;
		}

		public List<T> get(int index) {
			Preconditions.checkElementIndex(index, this.size());
			int start = index * this.size;
			int end = Math.min(start + this.size, this.list.size());
			return this.list.subList(start, end);
		}

		public int size() {
			return IntMath.divide(this.list.size(), this.size, RoundingMode.CEILING);
		}

		public boolean isEmpty() {
			return this.list.isEmpty();
		}
	}

	private static class RandomAccessListWrapper<E> extends Lists.AbstractListWrapper<E> implements RandomAccess {
		RandomAccessListWrapper(List<E> backingList) {
			super(backingList);
		}
	}

	private static class RandomAccessPartition<T> extends Lists.Partition<T> implements RandomAccess {
		RandomAccessPartition(List<T> list, int size) {
			super(list, size);
		}
	}

	private static class RandomAccessReverseList<T> extends Lists.ReverseList<T> implements RandomAccess {
		RandomAccessReverseList(List<T> forwardList) {
			super(forwardList);
		}
	}

	private static class ReverseList<T> extends AbstractList<T> {
		private final List<T> forwardList;

		ReverseList(List<T> forwardList) {
			this.forwardList = Preconditions.checkNotNull(forwardList);
		}

		List<T> getForwardList() {
			return this.forwardList;
		}

		private int reverseIndex(int index) {
			int size = this.size();
			Preconditions.checkElementIndex(index, size);
			return size - 1 - index;
		}

		private int reversePosition(int index) {
			int size = this.size();
			Preconditions.checkPositionIndex(index, size);
			return size - index;
		}

		public void add(int index, @Nullable T element) {
			this.forwardList.add(this.reversePosition(index), element);
		}

		public void clear() {
			this.forwardList.clear();
		}

		public T remove(int index) {
			return (T)this.forwardList.remove(this.reverseIndex(index));
		}

		protected void removeRange(int fromIndex, int toIndex) {
			this.subList(fromIndex, toIndex).clear();
		}

		public T set(int index, @Nullable T element) {
			return (T)this.forwardList.set(this.reverseIndex(index), element);
		}

		public T get(int index) {
			return (T)this.forwardList.get(this.reverseIndex(index));
		}

		public int size() {
			return this.forwardList.size();
		}

		public List<T> subList(int fromIndex, int toIndex) {
			Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
			return Lists.reverse(this.forwardList.subList(this.reversePosition(toIndex), this.reversePosition(fromIndex)));
		}

		public Iterator<T> iterator() {
			return this.listIterator();
		}

		public ListIterator<T> listIterator(int index) {
			int start = this.reversePosition(index);
			final ListIterator<T> forwardIterator = this.forwardList.listIterator(start);
			return new ListIterator<T>() {
				boolean canRemoveOrSet;

				public void add(T e) {
					forwardIterator.add(e);
					forwardIterator.previous();
					this.canRemoveOrSet = false;
				}

				public boolean hasNext() {
					return forwardIterator.hasPrevious();
				}

				public boolean hasPrevious() {
					return forwardIterator.hasNext();
				}

				public T next() {
					if (!this.hasNext()) {
						throw new NoSuchElementException();
					} else {
						this.canRemoveOrSet = true;
						return (T)forwardIterator.previous();
					}
				}

				public int nextIndex() {
					return ReverseList.this.reversePosition(forwardIterator.nextIndex());
				}

				public T previous() {
					if (!this.hasPrevious()) {
						throw new NoSuchElementException();
					} else {
						this.canRemoveOrSet = true;
						return (T)forwardIterator.next();
					}
				}

				public int previousIndex() {
					return this.nextIndex() - 1;
				}

				public void remove() {
					CollectPreconditions.checkRemove(this.canRemoveOrSet);
					forwardIterator.remove();
					this.canRemoveOrSet = false;
				}

				public void set(T e) {
					Preconditions.checkState(this.canRemoveOrSet);
					forwardIterator.set(e);
				}
			};
		}
	}

	private static final class StringAsImmutableList extends ImmutableList<Character> {
		private final String string;

		StringAsImmutableList(String string) {
			this.string = string;
		}

		@Override
		public int indexOf(@Nullable Object object) {
			return object instanceof Character ? this.string.indexOf((Character)object) : -1;
		}

		@Override
		public int lastIndexOf(@Nullable Object object) {
			return object instanceof Character ? this.string.lastIndexOf((Character)object) : -1;
		}

		@Override
		public ImmutableList<Character> subList(int fromIndex, int toIndex) {
			Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
			return Lists.charactersOf(this.string.substring(fromIndex, toIndex));
		}

		@Override
		boolean isPartialView() {
			return false;
		}

		public Character get(int index) {
			Preconditions.checkElementIndex(index, this.size());
			return this.string.charAt(index);
		}

		public int size() {
			return this.string.length();
		}
	}

	private static class TransformingRandomAccessList<F, T> extends AbstractList<T> implements RandomAccess, Serializable {
		final List<F> fromList;
		final Function<? super F, ? extends T> function;
		private static final long serialVersionUID = 0L;

		TransformingRandomAccessList(List<F> fromList, Function<? super F, ? extends T> function) {
			this.fromList = Preconditions.checkNotNull(fromList);
			this.function = Preconditions.checkNotNull(function);
		}

		public void clear() {
			this.fromList.clear();
		}

		public T get(int index) {
			return (T)this.function.apply((F)this.fromList.get(index));
		}

		public Iterator<T> iterator() {
			return this.listIterator();
		}

		public ListIterator<T> listIterator(int index) {
			return new TransformedListIterator<F, T>(this.fromList.listIterator(index)) {
				@Override
				T transform(F from) {
					return (T)TransformingRandomAccessList.this.function.apply(from);
				}
			};
		}

		public boolean isEmpty() {
			return this.fromList.isEmpty();
		}

		public boolean removeIf(Predicate<? super T> filter) {
			Preconditions.checkNotNull(filter);
			return this.fromList.removeIf(element -> filter.test(this.function.apply((F)element)));
		}

		public T remove(int index) {
			return (T)this.function.apply((F)this.fromList.remove(index));
		}

		public int size() {
			return this.fromList.size();
		}
	}

	private static class TransformingSequentialList<F, T> extends AbstractSequentialList<T> implements Serializable {
		final List<F> fromList;
		final Function<? super F, ? extends T> function;
		private static final long serialVersionUID = 0L;

		TransformingSequentialList(List<F> fromList, Function<? super F, ? extends T> function) {
			this.fromList = Preconditions.checkNotNull(fromList);
			this.function = Preconditions.checkNotNull(function);
		}

		public void clear() {
			this.fromList.clear();
		}

		public int size() {
			return this.fromList.size();
		}

		public ListIterator<T> listIterator(int index) {
			return new TransformedListIterator<F, T>(this.fromList.listIterator(index)) {
				@Override
				T transform(F from) {
					return (T)TransformingSequentialList.this.function.apply(from);
				}
			};
		}

		public boolean removeIf(Predicate<? super T> filter) {
			Preconditions.checkNotNull(filter);
			return this.fromList.removeIf(element -> filter.test(this.function.apply((F)element)));
		}
	}

	private static class TwoPlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
		final E first;
		final E second;
		final E[] rest;
		private static final long serialVersionUID = 0L;

		TwoPlusArrayList(@Nullable E first, @Nullable E second, E[] rest) {
			this.first = first;
			this.second = second;
			this.rest = (E[])((Object[])Preconditions.checkNotNull(rest));
		}

		public int size() {
			return IntMath.saturatedAdd(this.rest.length, 2);
		}

		public E get(int index) {
			switch (index) {
				case 0:
					return this.first;
				case 1:
					return this.second;
				default:
					Preconditions.checkElementIndex(index, this.size());
					return this.rest[index - 2];
			}
		}
	}
}

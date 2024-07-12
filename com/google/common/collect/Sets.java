package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2.FilteredCollection;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.math.IntMath;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.Collector.Characteristics;
import javax.annotation.Nullable;

@GwtCompatible(
	emulated = true
)
public final class Sets {
	private Sets() {
	}

	@GwtCompatible(
		serializable = true
	)
	public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E anElement, E... otherElements) {
		return ImmutableEnumSet.asImmutable(EnumSet.of(anElement, otherElements));
	}

	@GwtCompatible(
		serializable = true
	)
	public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> elements) {
		if (elements instanceof ImmutableEnumSet) {
			return (ImmutableEnumSet)elements;
		} else if (elements instanceof Collection) {
			Collection<E> collection = (Collection<E>)elements;
			return collection.isEmpty() ? ImmutableSet.of() : ImmutableEnumSet.asImmutable(EnumSet.copyOf(collection));
		} else {
			Iterator<E> itr = elements.iterator();
			if (itr.hasNext()) {
				EnumSet<E> enumSet = EnumSet.of((Enum)itr.next());
				Iterators.addAll(enumSet, itr);
				return ImmutableEnumSet.asImmutable(enumSet);
			} else {
				return ImmutableSet.of();
			}
		}
	}

	@Beta
	public static <E extends Enum<E>> Collector<E, ?, ImmutableSet<E>> toImmutableEnumSet() {
		return (Collector<E, ?, ImmutableSet<E>>)Sets.Accumulator.TO_IMMUTABLE_ENUM_SET;
	}

	public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> elementType) {
		EnumSet<E> set = EnumSet.noneOf(elementType);
		Iterables.addAll(set, iterable);
		return set;
	}

	public static <E> HashSet<E> newHashSet() {
		return new HashSet();
	}

	public static <E> HashSet<E> newHashSet(E... elements) {
		HashSet<E> set = newHashSetWithExpectedSize(elements.length);
		Collections.addAll(set, elements);
		return set;
	}

	public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
		return new HashSet(Maps.capacity(expectedSize));
	}

	public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
		return elements instanceof Collection ? new HashSet(Collections2.cast(elements)) : newHashSet(elements.iterator());
	}

	public static <E> HashSet<E> newHashSet(Iterator<? extends E> elements) {
		HashSet<E> set = newHashSet();
		Iterators.addAll(set, elements);
		return set;
	}

	public static <E> Set<E> newConcurrentHashSet() {
		return Collections.newSetFromMap(new ConcurrentHashMap());
	}

	public static <E> Set<E> newConcurrentHashSet(Iterable<? extends E> elements) {
		Set<E> set = newConcurrentHashSet();
		Iterables.addAll(set, elements);
		return set;
	}

	public static <E> LinkedHashSet<E> newLinkedHashSet() {
		return new LinkedHashSet();
	}

	public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int expectedSize) {
		return new LinkedHashSet(Maps.capacity(expectedSize));
	}

	public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> elements) {
		if (elements instanceof Collection) {
			return new LinkedHashSet(Collections2.cast(elements));
		} else {
			LinkedHashSet<E> set = newLinkedHashSet();
			Iterables.addAll(set, elements);
			return set;
		}
	}

	public static <E extends Comparable> TreeSet<E> newTreeSet() {
		return new TreeSet();
	}

	public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> elements) {
		TreeSet<E> set = newTreeSet();
		Iterables.addAll(set, elements);
		return set;
	}

	public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
		return new TreeSet(Preconditions.checkNotNull(comparator));
	}

	public static <E> Set<E> newIdentityHashSet() {
		return Collections.newSetFromMap(Maps.newIdentityHashMap());
	}

	@GwtIncompatible
	public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
		return new CopyOnWriteArraySet();
	}

	@GwtIncompatible
	public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(Iterable<? extends E> elements) {
		Collection<? extends E> elementsCollection = (Collection<? extends E>)(elements instanceof Collection
			? Collections2.cast(elements)
			: Lists.newArrayList(elements));
		return new CopyOnWriteArraySet(elementsCollection);
	}

	public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
		if (collection instanceof EnumSet) {
			return EnumSet.complementOf((EnumSet)collection);
		} else {
			Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
			Class<E> type = ((Enum)collection.iterator().next()).getDeclaringClass();
			return makeComplementByHand(collection, type);
		}
	}

	public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> type) {
		Preconditions.checkNotNull(collection);
		return collection instanceof EnumSet ? EnumSet.complementOf((EnumSet)collection) : makeComplementByHand(collection, type);
	}

	private static <E extends Enum<E>> EnumSet<E> makeComplementByHand(Collection<E> collection, Class<E> type) {
		EnumSet<E> result = EnumSet.allOf(type);
		result.removeAll(collection);
		return result;
	}

	@Deprecated
	public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
		return Collections.newSetFromMap(map);
	}

	public static <E> Sets.SetView<E> union(Set<? extends E> set1, Set<? extends E> set2) {
		Preconditions.checkNotNull(set1, "set1");
		Preconditions.checkNotNull(set2, "set2");
		final Set<? extends E> set2minus1 = difference(set2, set1);
		return new Sets.SetView<E>() {
			public int size() {
				return IntMath.saturatedAdd(set1.size(), set2minus1.size());
			}

			public boolean isEmpty() {
				return set1.isEmpty() && set2.isEmpty();
			}

			@Override
			public UnmodifiableIterator<E> iterator() {
				return Iterators.unmodifiableIterator(Iterators.concat(set1.iterator(), set2minus1.iterator()));
			}

			public Stream<E> stream() {
				return Stream.concat(set1.stream(), set2minus1.stream());
			}

			public Stream<E> parallelStream() {
				return Stream.concat(set1.parallelStream(), set2minus1.parallelStream());
			}

			public boolean contains(Object object) {
				return set1.contains(object) || set2.contains(object);
			}

			@Override
			public <S extends Set<E>> S copyInto(S set) {
				set.addAll(set1);
				set.addAll(set2);
				return set;
			}

			@Override
			public ImmutableSet<E> immutableCopy() {
				return new com.google.common.collect.ImmutableSet.Builder<E>().addAll(set1).addAll(set2).build();
			}
		};
	}

	public static <E> Sets.SetView<E> intersection(Set<E> set1, Set<?> set2) {
		Preconditions.checkNotNull(set1, "set1");
		Preconditions.checkNotNull(set2, "set2");
		final Predicate<Object> inSet2 = Predicates.in((Collection<? extends Object>)set2);
		return new Sets.SetView<E>() {
			@Override
			public UnmodifiableIterator<E> iterator() {
				return Iterators.filter(set1.iterator(), inSet2);
			}

			public Stream<E> stream() {
				return set1.stream().filter(inSet2);
			}

			public Stream<E> parallelStream() {
				return set1.parallelStream().filter(inSet2);
			}

			public int size() {
				return Iterators.size(this.iterator());
			}

			public boolean isEmpty() {
				return !this.iterator().hasNext();
			}

			public boolean contains(Object object) {
				return set1.contains(object) && set2.contains(object);
			}

			public boolean containsAll(Collection<?> collection) {
				return set1.containsAll(collection) && set2.containsAll(collection);
			}
		};
	}

	public static <E> Sets.SetView<E> difference(Set<E> set1, Set<?> set2) {
		Preconditions.checkNotNull(set1, "set1");
		Preconditions.checkNotNull(set2, "set2");
		final Predicate<Object> notInSet2 = Predicates.not(Predicates.in((Collection<? extends Object>)set2));
		return new Sets.SetView<E>() {
			@Override
			public UnmodifiableIterator<E> iterator() {
				return Iterators.filter(set1.iterator(), notInSet2);
			}

			public Stream<E> stream() {
				return set1.stream().filter(notInSet2);
			}

			public Stream<E> parallelStream() {
				return set1.parallelStream().filter(notInSet2);
			}

			public int size() {
				return Iterators.size(this.iterator());
			}

			public boolean isEmpty() {
				return set2.containsAll(set1);
			}

			public boolean contains(Object element) {
				return set1.contains(element) && !set2.contains(element);
			}
		};
	}

	public static <E> Sets.SetView<E> symmetricDifference(Set<? extends E> set1, Set<? extends E> set2) {
		Preconditions.checkNotNull(set1, "set1");
		Preconditions.checkNotNull(set2, "set2");
		return new Sets.SetView<E>() {
			@Override
			public UnmodifiableIterator<E> iterator() {
				final Iterator<? extends E> itr1 = set1.iterator();
				final Iterator<? extends E> itr2 = set2.iterator();
				return new AbstractIterator<E>() {
					@Override
					public E computeNext() {
						while (itr1.hasNext()) {
							E elem1 = (E)itr1.next();
							if (!set2.contains(elem1)) {
								return elem1;
							}
						}

						while (itr2.hasNext()) {
							E elem2 = (E)itr2.next();
							if (!set1.contains(elem2)) {
								return elem2;
							}
						}

						return (E)this.endOfData();
					}
				};
			}

			public int size() {
				return Iterators.size(this.iterator());
			}

			public boolean isEmpty() {
				return set1.equals(set2);
			}

			public boolean contains(Object element) {
				return set1.contains(element) ^ set2.contains(element);
			}
		};
	}

	public static <E> Set<E> filter(Set<E> unfiltered, Predicate<? super E> predicate) {
		if (unfiltered instanceof SortedSet) {
			return filter((SortedSet<E>)unfiltered, predicate);
		} else if (unfiltered instanceof Sets.FilteredSet) {
			Sets.FilteredSet<E> filtered = (Sets.FilteredSet<E>)unfiltered;
			Predicate<E> combinedPredicate = Predicates.and(filtered.predicate, predicate);
			return new Sets.FilteredSet<>((Set<E>)filtered.unfiltered, combinedPredicate);
		} else {
			return new Sets.FilteredSet<>(Preconditions.checkNotNull(unfiltered), Preconditions.checkNotNull(predicate));
		}
	}

	public static <E> SortedSet<E> filter(SortedSet<E> unfiltered, Predicate<? super E> predicate) {
		if (unfiltered instanceof Sets.FilteredSet) {
			Sets.FilteredSet<E> filtered = (Sets.FilteredSet<E>)unfiltered;
			Predicate<E> combinedPredicate = Predicates.and(filtered.predicate, predicate);
			return new Sets.FilteredSortedSet<>((SortedSet<E>)filtered.unfiltered, combinedPredicate);
		} else {
			return new Sets.FilteredSortedSet<>(Preconditions.checkNotNull(unfiltered), Preconditions.checkNotNull(predicate));
		}
	}

	@GwtIncompatible
	public static <E> NavigableSet<E> filter(NavigableSet<E> unfiltered, Predicate<? super E> predicate) {
		if (unfiltered instanceof Sets.FilteredSet) {
			Sets.FilteredSet<E> filtered = (Sets.FilteredSet<E>)unfiltered;
			Predicate<E> combinedPredicate = Predicates.and(filtered.predicate, predicate);
			return new Sets.FilteredNavigableSet<>((NavigableSet<E>)filtered.unfiltered, combinedPredicate);
		} else {
			return new Sets.FilteredNavigableSet<>(Preconditions.checkNotNull(unfiltered), Preconditions.checkNotNull(predicate));
		}
	}

	public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> sets) {
		return Sets.CartesianSet.create(sets);
	}

	public static <B> Set<List<B>> cartesianProduct(Set<? extends B>... sets) {
		return cartesianProduct(Arrays.asList(sets));
	}

	@GwtCompatible(
		serializable = false
	)
	public static <E> Set<Set<E>> powerSet(Set<E> set) {
		return new Sets.PowerSet<Set<E>>(set);
	}

	static int hashCodeImpl(Set<?> s) {
		int hashCode = 0;

		for (Object o : s) {
			hashCode += o != null ? o.hashCode() : 0;
			hashCode = ~(~hashCode);
		}

		return hashCode;
	}

	static boolean equalsImpl(Set<?> s, @Nullable Object object) {
		if (s == object) {
			return true;
		} else if (object instanceof Set) {
			Set<?> o = (Set<?>)object;

			try {
				return s.size() == o.size() && s.containsAll(o);
			} catch (NullPointerException var4) {
				return false;
			} catch (ClassCastException var5) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static <E> NavigableSet<E> unmodifiableNavigableSet(NavigableSet<E> set) {
		return (NavigableSet<E>)(!(set instanceof ImmutableSortedSet) && !(set instanceof Sets.UnmodifiableNavigableSet)
			? new Sets.UnmodifiableNavigableSet<>(set)
			: set);
	}

	@GwtIncompatible
	public static <E> NavigableSet<E> synchronizedNavigableSet(NavigableSet<E> navigableSet) {
		return Synchronized.navigableSet(navigableSet);
	}

	static boolean removeAllImpl(Set<?> set, Iterator<?> iterator) {
		boolean changed = false;

		while (iterator.hasNext()) {
			changed |= set.remove(iterator.next());
		}

		return changed;
	}

	static boolean removeAllImpl(Set<?> set, Collection<?> collection) {
		Preconditions.checkNotNull(collection);
		if (collection instanceof Multiset) {
			collection = ((Multiset)collection).elementSet();
		}

		return collection instanceof Set && collection.size() > set.size()
			? Iterators.removeAll(set.iterator(), collection)
			: removeAllImpl(set, collection.iterator());
	}

	@Beta
	@GwtIncompatible
	public static <K extends Comparable<? super K>> NavigableSet<K> subSet(NavigableSet<K> set, Range<K> range) {
		if (set.comparator() != null && set.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
			Preconditions.checkArgument(
				set.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0,
				"set is using a custom comparator which is inconsistent with the natural ordering."
			);
		}

		if (range.hasLowerBound() && range.hasUpperBound()) {
			return set.subSet(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED, range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED);
		} else if (range.hasLowerBound()) {
			return set.tailSet(range.lowerEndpoint(), range.lowerBoundType() == BoundType.CLOSED);
		} else {
			return range.hasUpperBound() ? set.headSet(range.upperEndpoint(), range.upperBoundType() == BoundType.CLOSED) : Preconditions.checkNotNull(set);
		}
	}

	private static final class Accumulator<E extends Enum<E>> {
		static final Collector<Enum<?>, ?, ImmutableSet<? extends Enum<?>>> TO_IMMUTABLE_ENUM_SET = Collector.of(
			Sets.Accumulator::new, Sets.Accumulator::add, Sets.Accumulator::combine, Sets.Accumulator::toImmutableSet, Characteristics.UNORDERED
		);
		private EnumSet<E> set;

		void add(E e) {
			if (this.set == null) {
				this.set = EnumSet.of(e);
			} else {
				this.set.add(e);
			}
		}

		Sets.Accumulator<E> combine(Sets.Accumulator<E> other) {
			if (this.set == null) {
				return other;
			} else if (other.set == null) {
				return this;
			} else {
				this.set.addAll(other.set);
				return this;
			}
		}

		ImmutableSet<E> toImmutableSet() {
			return this.set == null ? ImmutableSet.of() : ImmutableEnumSet.asImmutable(this.set);
		}
	}

	private static final class CartesianSet<E> extends ForwardingCollection<List<E>> implements Set<List<E>> {
		private final transient ImmutableList<ImmutableSet<E>> axes;
		private final transient CartesianList<E> delegate;

		static <E> Set<List<E>> create(List<? extends Set<? extends E>> sets) {
			Builder<ImmutableSet<E>> axesBuilder = new Builder<>(sets.size());

			for (Set<? extends E> set : sets) {
				ImmutableSet<E> copy = ImmutableSet.copyOf(set);
				if (copy.isEmpty()) {
					return ImmutableSet.of();
				}

				axesBuilder.add(copy);
			}

			final ImmutableList<ImmutableSet<E>> axes = axesBuilder.build();
			ImmutableList<List<E>> listAxes = new ImmutableList<List<E>>() {
				public int size() {
					return axes.size();
				}

				public List<E> get(int index) {
					return ((ImmutableSet)axes.get(index)).asList();
				}

				@Override
				boolean isPartialView() {
					return true;
				}
			};
			return new Sets.CartesianSet<>(axes, new CartesianList<>(listAxes));
		}

		private CartesianSet(ImmutableList<ImmutableSet<E>> axes, CartesianList<E> delegate) {
			this.axes = axes;
			this.delegate = delegate;
		}

		@Override
		protected Collection<List<E>> delegate() {
			return this.delegate;
		}

		public boolean equals(@Nullable Object object) {
			if (object instanceof Sets.CartesianSet) {
				Sets.CartesianSet<?> that = (Sets.CartesianSet<?>)object;
				return this.axes.equals(that.axes);
			} else {
				return super.equals(object);
			}
		}

		public int hashCode() {
			int adjust = this.size() - 1;

			for (int i = 0; i < this.axes.size(); i++) {
				adjust *= 31;
				adjust = ~(~adjust);
			}

			int hash = 1;

			for (Set<E> axis : this.axes) {
				hash = 31 * hash + this.size() / axis.size() * axis.hashCode();
				hash = ~(~hash);
			}

			hash += adjust;
			return ~(~hash);
		}
	}

	@GwtIncompatible
	static class DescendingSet<E> extends ForwardingNavigableSet<E> {
		private final NavigableSet<E> forward;

		DescendingSet(NavigableSet<E> forward) {
			this.forward = forward;
		}

		@Override
		protected NavigableSet<E> delegate() {
			return this.forward;
		}

		@Override
		public E lower(E e) {
			return (E)this.forward.higher(e);
		}

		@Override
		public E floor(E e) {
			return (E)this.forward.ceiling(e);
		}

		@Override
		public E ceiling(E e) {
			return (E)this.forward.floor(e);
		}

		@Override
		public E higher(E e) {
			return (E)this.forward.lower(e);
		}

		@Override
		public E pollFirst() {
			return (E)this.forward.pollLast();
		}

		@Override
		public E pollLast() {
			return (E)this.forward.pollFirst();
		}

		@Override
		public NavigableSet<E> descendingSet() {
			return this.forward;
		}

		@Override
		public Iterator<E> descendingIterator() {
			return this.forward.iterator();
		}

		@Override
		public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
			return this.forward.subSet(toElement, toInclusive, fromElement, fromInclusive).descendingSet();
		}

		@Override
		public NavigableSet<E> headSet(E toElement, boolean inclusive) {
			return this.forward.tailSet(toElement, inclusive).descendingSet();
		}

		@Override
		public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
			return this.forward.headSet(fromElement, inclusive).descendingSet();
		}

		@Override
		public Comparator<? super E> comparator() {
			Comparator<? super E> forwardComparator = this.forward.comparator();
			return forwardComparator == null ? Ordering.natural().reverse() : reverse(forwardComparator);
		}

		private static <T> Ordering<T> reverse(Comparator<T> forward) {
			return Ordering.from(forward).reverse();
		}

		@Override
		public E first() {
			return (E)this.forward.last();
		}

		@Override
		public SortedSet<E> headSet(E toElement) {
			return this.standardHeadSet(toElement);
		}

		@Override
		public E last() {
			return (E)this.forward.first();
		}

		@Override
		public SortedSet<E> subSet(E fromElement, E toElement) {
			return this.standardSubSet(fromElement, toElement);
		}

		@Override
		public SortedSet<E> tailSet(E fromElement) {
			return this.standardTailSet(fromElement);
		}

		@Override
		public Iterator<E> iterator() {
			return this.forward.descendingIterator();
		}

		@Override
		public Object[] toArray() {
			return this.standardToArray();
		}

		@Override
		public <T> T[] toArray(T[] array) {
			return (T[])this.standardToArray(array);
		}

		@Override
		public String toString() {
			return this.standardToString();
		}
	}

	@GwtIncompatible
	private static class FilteredNavigableSet<E> extends Sets.FilteredSortedSet<E> implements NavigableSet<E> {
		FilteredNavigableSet(NavigableSet<E> unfiltered, Predicate<? super E> predicate) {
			super(unfiltered, predicate);
		}

		NavigableSet<E> unfiltered() {
			return (NavigableSet<E>)this.unfiltered;
		}

		@Nullable
		public E lower(E e) {
			return Iterators.getNext(this.headSet(e, false).descendingIterator(), null);
		}

		@Nullable
		public E floor(E e) {
			return Iterators.getNext(this.headSet(e, true).descendingIterator(), null);
		}

		public E ceiling(E e) {
			return Iterables.getFirst(this.tailSet(e, true), null);
		}

		public E higher(E e) {
			return Iterables.getFirst(this.tailSet(e, false), null);
		}

		public E pollFirst() {
			return Iterables.removeFirstMatching(this.unfiltered(), this.predicate);
		}

		public E pollLast() {
			return Iterables.removeFirstMatching(this.unfiltered().descendingSet(), this.predicate);
		}

		public NavigableSet<E> descendingSet() {
			return Sets.filter(this.unfiltered().descendingSet(), this.predicate);
		}

		public Iterator<E> descendingIterator() {
			return Iterators.filter(this.unfiltered().descendingIterator(), this.predicate);
		}

		@Override
		public E last() {
			return (E)this.descendingIterator().next();
		}

		public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
			return Sets.filter(this.unfiltered().subSet(fromElement, fromInclusive, toElement, toInclusive), this.predicate);
		}

		public NavigableSet<E> headSet(E toElement, boolean inclusive) {
			return Sets.filter(this.unfiltered().headSet(toElement, inclusive), this.predicate);
		}

		public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
			return Sets.filter(this.unfiltered().tailSet(fromElement, inclusive), this.predicate);
		}
	}

	private static class FilteredSet<E> extends FilteredCollection<E> implements Set<E> {
		FilteredSet(Set<E> unfiltered, Predicate<? super E> predicate) {
			super(unfiltered, predicate);
		}

		public boolean equals(@Nullable Object object) {
			return Sets.equalsImpl(this, object);
		}

		public int hashCode() {
			return Sets.hashCodeImpl(this);
		}
	}

	private static class FilteredSortedSet<E> extends Sets.FilteredSet<E> implements SortedSet<E> {
		FilteredSortedSet(SortedSet<E> unfiltered, Predicate<? super E> predicate) {
			super(unfiltered, predicate);
		}

		public Comparator<? super E> comparator() {
			return ((SortedSet)this.unfiltered).comparator();
		}

		public SortedSet<E> subSet(E fromElement, E toElement) {
			return new Sets.FilteredSortedSet<>(((SortedSet)this.unfiltered).subSet(fromElement, toElement), this.predicate);
		}

		public SortedSet<E> headSet(E toElement) {
			return new Sets.FilteredSortedSet<>(((SortedSet)this.unfiltered).headSet(toElement), this.predicate);
		}

		public SortedSet<E> tailSet(E fromElement) {
			return new Sets.FilteredSortedSet<>(((SortedSet)this.unfiltered).tailSet(fromElement), this.predicate);
		}

		public E first() {
			return (E)this.iterator().next();
		}

		public E last() {
			SortedSet<E> sortedUnfiltered = (SortedSet<E>)this.unfiltered;

			while (true) {
				E element = (E)sortedUnfiltered.last();
				if (this.predicate.apply(element)) {
					return element;
				}

				sortedUnfiltered = sortedUnfiltered.headSet(element);
			}
		}
	}

	abstract static class ImprovedAbstractSet<E> extends AbstractSet<E> {
		public boolean removeAll(Collection<?> c) {
			return Sets.removeAllImpl(this, c);
		}

		public boolean retainAll(Collection<?> c) {
			return super.retainAll(Preconditions.checkNotNull(c));
		}
	}

	private static final class PowerSet<E> extends AbstractSet<Set<E>> {
		final ImmutableMap<E, Integer> inputSet;

		PowerSet(Set<E> input) {
			this.inputSet = Maps.indexMap(input);
			Preconditions.checkArgument(this.inputSet.size() <= 30, "Too many elements to create power set: %s > 30", this.inputSet.size());
		}

		public int size() {
			return 1 << this.inputSet.size();
		}

		public boolean isEmpty() {
			return false;
		}

		public Iterator<Set<E>> iterator() {
			return new AbstractIndexedListIterator<Set<E>>(this.size()) {
				protected Set<E> get(int setBits) {
					return new Sets.SubSet<E>(PowerSet.this.inputSet, setBits);
				}
			};
		}

		public boolean contains(@Nullable Object obj) {
			if (obj instanceof Set) {
				Set<?> set = (Set<?>)obj;
				return this.inputSet.keySet().containsAll(set);
			} else {
				return false;
			}
		}

		public boolean equals(@Nullable Object obj) {
			if (obj instanceof Sets.PowerSet) {
				Sets.PowerSet<?> that = (Sets.PowerSet<?>)obj;
				return this.inputSet.equals(that.inputSet);
			} else {
				return super.equals(obj);
			}
		}

		public int hashCode() {
			return this.inputSet.keySet().hashCode() << this.inputSet.size() - 1;
		}

		public String toString() {
			return "powerSet(" + this.inputSet + ")";
		}
	}

	public abstract static class SetView<E> extends AbstractSet<E> {
		private SetView() {
		}

		public ImmutableSet<E> immutableCopy() {
			return ImmutableSet.copyOf(this);
		}

		@CanIgnoreReturnValue
		public <S extends Set<E>> S copyInto(S set) {
			set.addAll(this);
			return set;
		}

		@Deprecated
		@CanIgnoreReturnValue
		public final boolean add(E e) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@CanIgnoreReturnValue
		public final boolean remove(Object object) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@CanIgnoreReturnValue
		public final boolean addAll(Collection<? extends E> newElements) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@CanIgnoreReturnValue
		public final boolean removeAll(Collection<?> oldElements) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@CanIgnoreReturnValue
		public final boolean removeIf(java.util.function.Predicate<? super E> filter) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@CanIgnoreReturnValue
		public final boolean retainAll(Collection<?> elementsToKeep) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		public final void clear() {
			throw new UnsupportedOperationException();
		}

		public abstract UnmodifiableIterator<E> iterator();
	}

	private static final class SubSet<E> extends AbstractSet<E> {
		private final ImmutableMap<E, Integer> inputSet;
		private final int mask;

		SubSet(ImmutableMap<E, Integer> inputSet, int mask) {
			this.inputSet = inputSet;
			this.mask = mask;
		}

		public Iterator<E> iterator() {
			return new UnmodifiableIterator<E>() {
				final ImmutableList<E> elements = SubSet.this.inputSet.keySet().asList();
				int remainingSetBits = SubSet.this.mask;

				public boolean hasNext() {
					return this.remainingSetBits != 0;
				}

				public E next() {
					int index = Integer.numberOfTrailingZeros(this.remainingSetBits);
					if (index == 32) {
						throw new NoSuchElementException();
					} else {
						this.remainingSetBits &= ~(1 << index);
						return (E)this.elements.get(index);
					}
				}
			};
		}

		public int size() {
			return Integer.bitCount(this.mask);
		}

		public boolean contains(@Nullable Object o) {
			Integer index = this.inputSet.get(o);
			return index != null && (this.mask & 1 << index) != 0;
		}
	}

	static final class UnmodifiableNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E>, Serializable {
		private final NavigableSet<E> delegate;
		private transient Sets.UnmodifiableNavigableSet<E> descendingSet;
		private static final long serialVersionUID = 0L;

		UnmodifiableNavigableSet(NavigableSet<E> delegate) {
			this.delegate = Preconditions.checkNotNull(delegate);
		}

		@Override
		protected SortedSet<E> delegate() {
			return Collections.unmodifiableSortedSet(this.delegate);
		}

		public E lower(E e) {
			return (E)this.delegate.lower(e);
		}

		public E floor(E e) {
			return (E)this.delegate.floor(e);
		}

		public E ceiling(E e) {
			return (E)this.delegate.ceiling(e);
		}

		public E higher(E e) {
			return (E)this.delegate.higher(e);
		}

		public E pollFirst() {
			throw new UnsupportedOperationException();
		}

		public E pollLast() {
			throw new UnsupportedOperationException();
		}

		public NavigableSet<E> descendingSet() {
			Sets.UnmodifiableNavigableSet<E> result = this.descendingSet;
			if (result == null) {
				result = this.descendingSet = new Sets.UnmodifiableNavigableSet<>(this.delegate.descendingSet());
				result.descendingSet = this;
			}

			return result;
		}

		public Iterator<E> descendingIterator() {
			return Iterators.unmodifiableIterator(this.delegate.descendingIterator());
		}

		public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
			return Sets.unmodifiableNavigableSet(this.delegate.subSet(fromElement, fromInclusive, toElement, toInclusive));
		}

		public NavigableSet<E> headSet(E toElement, boolean inclusive) {
			return Sets.unmodifiableNavigableSet(this.delegate.headSet(toElement, inclusive));
		}

		public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
			return Sets.unmodifiableNavigableSet(this.delegate.tailSet(fromElement, inclusive));
		}
	}
}

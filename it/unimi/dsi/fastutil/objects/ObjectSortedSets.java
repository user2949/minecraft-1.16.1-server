package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectSets.SynchronizedSet;
import it.unimi.dsi.fastutil.objects.ObjectSets.UnmodifiableSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;

public final class ObjectSortedSets {
	public static final ObjectSortedSets.EmptySet EMPTY_SET = new ObjectSortedSets.EmptySet();

	private ObjectSortedSets() {
	}

	public static <K> ObjectSet<K> emptySet() {
		return EMPTY_SET;
	}

	public static <K> ObjectSortedSet<K> singleton(K element) {
		return new ObjectSortedSets.Singleton<>(element);
	}

	public static <K> ObjectSortedSet<K> singleton(K element, Comparator<? super K> comparator) {
		return new ObjectSortedSets.Singleton<>(element, comparator);
	}

	public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s) {
		return new ObjectSortedSets.SynchronizedSortedSet<>(s);
	}

	public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s, Object sync) {
		return new ObjectSortedSets.SynchronizedSortedSet<>(s, sync);
	}

	public static <K> ObjectSortedSet<K> unmodifiable(ObjectSortedSet<K> s) {
		return new ObjectSortedSets.UnmodifiableSortedSet<>(s);
	}

	public static class EmptySet<K> extends ObjectSets.EmptySet<K> implements ObjectSortedSet<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySet() {
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return ObjectIterators.EMPTY_ITERATOR;
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ObjectSortedSet<K> headSet(K from) {
			return ObjectSortedSets.EMPTY_SET;
		}

		@Override
		public ObjectSortedSet<K> tailSet(K to) {
			return ObjectSortedSets.EMPTY_SET;
		}

		public K first() {
			throw new NoSuchElementException();
		}

		public K last() {
			throw new NoSuchElementException();
		}

		public Comparator<? super K> comparator() {
			return null;
		}

		@Override
		public Object clone() {
			return ObjectSortedSets.EMPTY_SET;
		}

		private Object readResolve() {
			return ObjectSortedSets.EMPTY_SET;
		}
	}

	public static class Singleton<K> extends ObjectSets.Singleton<K> implements ObjectSortedSet<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		final Comparator<? super K> comparator;

		protected Singleton(K element, Comparator<? super K> comparator) {
			super(element);
			this.comparator = comparator;
		}

		private Singleton(K element) {
			this(element, null);
		}

		final int compare(K k1, K k2) {
			return this.comparator == null ? ((Comparable)k1).compareTo(k2) : this.comparator.compare(k1, k2);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			ObjectBidirectionalIterator<K> i = this.iterator();
			if (this.compare(this.element, from) <= 0) {
				i.next();
			}

			return i;
		}

		public Comparator<? super K> comparator() {
			return this.comparator;
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return (ObjectSortedSet<K>)(this.compare(from, this.element) <= 0 && this.compare(this.element, to) < 0 ? this : ObjectSortedSets.EMPTY_SET);
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return (ObjectSortedSet<K>)(this.compare(this.element, to) < 0 ? this : ObjectSortedSets.EMPTY_SET);
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return (ObjectSortedSet<K>)(this.compare(from, this.element) <= 0 ? this : ObjectSortedSets.EMPTY_SET);
		}

		public K first() {
			return this.element;
		}

		public K last() {
			return this.element;
		}
	}

	public static class SynchronizedSortedSet<K> extends SynchronizedSet<K> implements ObjectSortedSet<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectSortedSet<K> sortedSet;

		protected SynchronizedSortedSet(ObjectSortedSet<K> s, Object sync) {
			super(s, sync);
			this.sortedSet = s;
		}

		protected SynchronizedSortedSet(ObjectSortedSet<K> s) {
			super(s);
			this.sortedSet = s;
		}

		public Comparator<? super K> comparator() {
			synchronized (this.sync) {
				return this.sortedSet.comparator();
			}
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return new ObjectSortedSets.SynchronizedSortedSet<>(this.sortedSet.subSet(from, to), this.sync);
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return new ObjectSortedSets.SynchronizedSortedSet<>(this.sortedSet.headSet(to), this.sync);
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return new ObjectSortedSets.SynchronizedSortedSet<>(this.sortedSet.tailSet(from), this.sync);
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return this.sortedSet.iterator();
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return this.sortedSet.iterator(from);
		}

		public K first() {
			synchronized (this.sync) {
				return (K)this.sortedSet.first();
			}
		}

		public K last() {
			synchronized (this.sync) {
				return (K)this.sortedSet.last();
			}
		}
	}

	public static class UnmodifiableSortedSet<K> extends UnmodifiableSet<K> implements ObjectSortedSet<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final ObjectSortedSet<K> sortedSet;

		protected UnmodifiableSortedSet(ObjectSortedSet<K> s) {
			super(s);
			this.sortedSet = s;
		}

		public Comparator<? super K> comparator() {
			return this.sortedSet.comparator();
		}

		@Override
		public ObjectSortedSet<K> subSet(K from, K to) {
			return new ObjectSortedSets.UnmodifiableSortedSet<>(this.sortedSet.subSet(from, to));
		}

		@Override
		public ObjectSortedSet<K> headSet(K to) {
			return new ObjectSortedSets.UnmodifiableSortedSet<>(this.sortedSet.headSet(to));
		}

		@Override
		public ObjectSortedSet<K> tailSet(K from) {
			return new ObjectSortedSets.UnmodifiableSortedSet<>(this.sortedSet.tailSet(from));
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator() {
			return ObjectIterators.unmodifiable(this.sortedSet.iterator());
		}

		@Override
		public ObjectBidirectionalIterator<K> iterator(K from) {
			return ObjectIterators.unmodifiable(this.sortedSet.iterator(from));
		}

		public K first() {
			return (K)this.sortedSet.first();
		}

		public K last() {
			return (K)this.sortedSet.last();
		}
	}
}

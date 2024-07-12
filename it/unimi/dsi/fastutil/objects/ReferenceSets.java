package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ReferenceCollections.EmptyCollection;
import it.unimi.dsi.fastutil.objects.ReferenceCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.objects.ReferenceCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public final class ReferenceSets {
	public static final ReferenceSets.EmptySet EMPTY_SET = new ReferenceSets.EmptySet();

	private ReferenceSets() {
	}

	public static <K> ReferenceSet<K> emptySet() {
		return EMPTY_SET;
	}

	public static <K> ReferenceSet<K> singleton(K element) {
		return new ReferenceSets.Singleton<>(element);
	}

	public static <K> ReferenceSet<K> synchronize(ReferenceSet<K> s) {
		return new ReferenceSets.SynchronizedSet<>(s);
	}

	public static <K> ReferenceSet<K> synchronize(ReferenceSet<K> s, Object sync) {
		return new ReferenceSets.SynchronizedSet<>(s, sync);
	}

	public static <K> ReferenceSet<K> unmodifiable(ReferenceSet<K> s) {
		return new ReferenceSets.UnmodifiableSet<>(s);
	}

	public static class EmptySet<K> extends EmptyCollection<K> implements ReferenceSet<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySet() {
		}

		public boolean remove(Object ok) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return ReferenceSets.EMPTY_SET;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Set && ((Set)o).isEmpty();
		}

		private Object readResolve() {
			return ReferenceSets.EMPTY_SET;
		}
	}

	public static class Singleton<K> extends AbstractReferenceSet<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final K element;

		protected Singleton(K element) {
			this.element = element;
		}

		public boolean contains(Object k) {
			return k == this.element;
		}

		public boolean remove(Object k) {
			throw new UnsupportedOperationException();
		}

		public ObjectListIterator<K> iterator() {
			return ObjectIterators.singleton(this.element);
		}

		public int size() {
			return 1;
		}

		public boolean addAll(Collection<? extends K> c) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedSet<K> extends SynchronizedCollection<K> implements ReferenceSet<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected SynchronizedSet(ReferenceSet<K> s, Object sync) {
			super(s, sync);
		}

		protected SynchronizedSet(ReferenceSet<K> s) {
			super(s);
		}

		@Override
		public boolean remove(Object k) {
			synchronized (this.sync) {
				return this.collection.remove(k);
			}
		}
	}

	public static class UnmodifiableSet<K> extends UnmodifiableCollection<K> implements ReferenceSet<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected UnmodifiableSet(ReferenceSet<K> s) {
			super(s);
		}

		@Override
		public boolean remove(Object k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean equals(Object o) {
			return o == this ? true : this.collection.equals(o);
		}

		@Override
		public int hashCode() {
			return this.collection.hashCode();
		}
	}
}

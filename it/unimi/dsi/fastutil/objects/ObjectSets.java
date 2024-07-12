package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectCollections.EmptyCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection;
import it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public final class ObjectSets {
	public static final ObjectSets.EmptySet EMPTY_SET = new ObjectSets.EmptySet();

	private ObjectSets() {
	}

	public static <K> ObjectSet<K> emptySet() {
		return EMPTY_SET;
	}

	public static <K> ObjectSet<K> singleton(K element) {
		return new ObjectSets.Singleton<>(element);
	}

	public static <K> ObjectSet<K> synchronize(ObjectSet<K> s) {
		return new ObjectSets.SynchronizedSet<>(s);
	}

	public static <K> ObjectSet<K> synchronize(ObjectSet<K> s, Object sync) {
		return new ObjectSets.SynchronizedSet<>(s, sync);
	}

	public static <K> ObjectSet<K> unmodifiable(ObjectSet<K> s) {
		return new ObjectSets.UnmodifiableSet<>(s);
	}

	public static class EmptySet<K> extends EmptyCollection<K> implements ObjectSet<K>, Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptySet() {
		}

		public boolean remove(Object ok) {
			throw new UnsupportedOperationException();
		}

		public Object clone() {
			return ObjectSets.EMPTY_SET;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Set && ((Set)o).isEmpty();
		}

		private Object readResolve() {
			return ObjectSets.EMPTY_SET;
		}
	}

	public static class Singleton<K> extends AbstractObjectSet<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final K element;

		protected Singleton(K element) {
			this.element = element;
		}

		public boolean contains(Object k) {
			return Objects.equals(k, this.element);
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

	public static class SynchronizedSet<K> extends SynchronizedCollection<K> implements ObjectSet<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected SynchronizedSet(ObjectSet<K> s, Object sync) {
			super(s, sync);
		}

		protected SynchronizedSet(ObjectSet<K> s) {
			super(s);
		}

		@Override
		public boolean remove(Object k) {
			synchronized (this.sync) {
				return this.collection.remove(k);
			}
		}
	}

	public static class UnmodifiableSet<K> extends UnmodifiableCollection<K> implements ObjectSet<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected UnmodifiableSet(ObjectSet<K> s) {
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

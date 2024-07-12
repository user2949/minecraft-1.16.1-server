package it.unimi.dsi.fastutil.longs;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.LongFunction;

public final class Long2ObjectFunctions {
	public static final Long2ObjectFunctions.EmptyFunction EMPTY_FUNCTION = new Long2ObjectFunctions.EmptyFunction();

	private Long2ObjectFunctions() {
	}

	public static <V> Long2ObjectFunction<V> singleton(long key, V value) {
		return new Long2ObjectFunctions.Singleton<>(key, value);
	}

	public static <V> Long2ObjectFunction<V> singleton(Long key, V value) {
		return new Long2ObjectFunctions.Singleton<>(key, value);
	}

	public static <V> Long2ObjectFunction<V> synchronize(Long2ObjectFunction<V> f) {
		return new Long2ObjectFunctions.SynchronizedFunction<>(f);
	}

	public static <V> Long2ObjectFunction<V> synchronize(Long2ObjectFunction<V> f, Object sync) {
		return new Long2ObjectFunctions.SynchronizedFunction<>(f, sync);
	}

	public static <V> Long2ObjectFunction<V> unmodifiable(Long2ObjectFunction<V> f) {
		return new Long2ObjectFunctions.UnmodifiableFunction<>(f);
	}

	public static <V> Long2ObjectFunction<V> primitive(Function<? super Long, ? extends V> f) {
		Objects.requireNonNull(f);
		if (f instanceof Long2ObjectFunction) {
			return (Long2ObjectFunction<V>)f;
		} else {
			return (Long2ObjectFunction<V>)(f instanceof LongFunction ? ((LongFunction)f)::apply : new Long2ObjectFunctions.PrimitiveFunction<>(f));
		}
	}

	public static class EmptyFunction<V> extends AbstractLong2ObjectFunction<V> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public V get(long k) {
			return null;
		}

		@Override
		public boolean containsKey(long k) {
			return false;
		}

		@Override
		public V defaultReturnValue() {
			return null;
		}

		@Override
		public void defaultReturnValue(V defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public void clear() {
		}

		public Object clone() {
			return Long2ObjectFunctions.EMPTY_FUNCTION;
		}

		public int hashCode() {
			return 0;
		}

		public boolean equals(Object o) {
			return !(o instanceof it.unimi.dsi.fastutil.Function) ? false : ((it.unimi.dsi.fastutil.Function)o).size() == 0;
		}

		public String toString() {
			return "{}";
		}

		private Object readResolve() {
			return Long2ObjectFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction<V> implements Long2ObjectFunction<V> {
		protected final Function<? super Long, ? extends V> function;

		protected PrimitiveFunction(Function<? super Long, ? extends V> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(long key) {
			return this.function.apply(key) != null;
		}

		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			return key == null ? false : this.function.apply((Long)key) != null;
		}

		@Override
		public V get(long key) {
			V v = (V)this.function.apply(key);
			return v == null ? null : v;
		}

		@Deprecated
		@Override
		public V get(Object key) {
			return (V)(key == null ? null : this.function.apply((Long)key));
		}

		@Deprecated
		@Override
		public V put(Long key, V value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton<V> extends AbstractLong2ObjectFunction<V> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final long key;
		protected final V value;

		protected Singleton(long key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(long k) {
			return this.key == k;
		}

		@Override
		public V get(long k) {
			return this.key == k ? this.value : this.defRetValue;
		}

		@Override
		public int size() {
			return 1;
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedFunction<V> implements Long2ObjectFunction<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2ObjectFunction<V> function;
		protected final Object sync;

		protected SynchronizedFunction(Long2ObjectFunction<V> f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Long2ObjectFunction<V> f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Override
		public V apply(long operand) {
			synchronized (this.sync) {
				return this.function.apply(operand);
			}
		}

		@Deprecated
		public V apply(Long key) {
			synchronized (this.sync) {
				return this.function.apply(key);
			}
		}

		@Override
		public int size() {
			synchronized (this.sync) {
				return this.function.size();
			}
		}

		@Override
		public V defaultReturnValue() {
			synchronized (this.sync) {
				return this.function.defaultReturnValue();
			}
		}

		@Override
		public void defaultReturnValue(V defRetValue) {
			synchronized (this.sync) {
				this.function.defaultReturnValue(defRetValue);
			}
		}

		@Override
		public boolean containsKey(long k) {
			synchronized (this.sync) {
				return this.function.containsKey(k);
			}
		}

		@Deprecated
		@Override
		public boolean containsKey(Object k) {
			synchronized (this.sync) {
				return this.function.containsKey(k);
			}
		}

		@Override
		public V put(long k, V v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public V get(long k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public V remove(long k) {
			synchronized (this.sync) {
				return this.function.remove(k);
			}
		}

		@Override
		public void clear() {
			synchronized (this.sync) {
				this.function.clear();
			}
		}

		@Deprecated
		@Override
		public V put(Long k, V v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Deprecated
		@Override
		public V get(Object k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Deprecated
		@Override
		public V remove(Object k) {
			synchronized (this.sync) {
				return this.function.remove(k);
			}
		}

		public int hashCode() {
			synchronized (this.sync) {
				return this.function.hashCode();
			}
		}

		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else {
				synchronized (this.sync) {
					return this.function.equals(o);
				}
			}
		}

		public String toString() {
			synchronized (this.sync) {
				return this.function.toString();
			}
		}

		private void writeObject(ObjectOutputStream s) throws IOException {
			synchronized (this.sync) {
				s.defaultWriteObject();
			}
		}
	}

	public static class UnmodifiableFunction<V> extends AbstractLong2ObjectFunction<V> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2ObjectFunction<V> function;

		protected UnmodifiableFunction(Long2ObjectFunction<V> f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
			}
		}

		@Override
		public int size() {
			return this.function.size();
		}

		@Override
		public V defaultReturnValue() {
			return this.function.defaultReturnValue();
		}

		@Override
		public void defaultReturnValue(V defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsKey(long k) {
			return this.function.containsKey(k);
		}

		@Override
		public V put(long k, V v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V get(long k) {
			return this.function.get(k);
		}

		@Override
		public V remove(long k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V put(Long k, V v) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V get(Object k) {
			return this.function.get(k);
		}

		@Deprecated
		@Override
		public V remove(Object k) {
			throw new UnsupportedOperationException();
		}

		public int hashCode() {
			return this.function.hashCode();
		}

		public boolean equals(Object o) {
			return o == this || this.function.equals(o);
		}

		public String toString() {
			return this.function.toString();
		}
	}
}

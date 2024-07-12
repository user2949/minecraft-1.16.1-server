package it.unimi.dsi.fastutil.objects;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public final class Object2IntFunctions {
	public static final Object2IntFunctions.EmptyFunction EMPTY_FUNCTION = new Object2IntFunctions.EmptyFunction();

	private Object2IntFunctions() {
	}

	public static <K> Object2IntFunction<K> singleton(K key, int value) {
		return new Object2IntFunctions.Singleton<>(key, value);
	}

	public static <K> Object2IntFunction<K> singleton(K key, Integer value) {
		return new Object2IntFunctions.Singleton<>(key, value);
	}

	public static <K> Object2IntFunction<K> synchronize(Object2IntFunction<K> f) {
		return new Object2IntFunctions.SynchronizedFunction<>(f);
	}

	public static <K> Object2IntFunction<K> synchronize(Object2IntFunction<K> f, Object sync) {
		return new Object2IntFunctions.SynchronizedFunction<>(f, sync);
	}

	public static <K> Object2IntFunction<K> unmodifiable(Object2IntFunction<K> f) {
		return new Object2IntFunctions.UnmodifiableFunction<>(f);
	}

	public static <K> Object2IntFunction<K> primitive(Function<? super K, ? extends Integer> f) {
		Objects.requireNonNull(f);
		if (f instanceof Object2IntFunction) {
			return (Object2IntFunction<K>)f;
		} else {
			return (Object2IntFunction<K>)(f instanceof ToIntFunction ? key -> ((ToIntFunction)f).applyAsInt(key) : new Object2IntFunctions.PrimitiveFunction<>(f));
		}
	}

	public static class EmptyFunction<K> extends AbstractObject2IntFunction<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public int getInt(Object k) {
			return 0;
		}

		@Override
		public boolean containsKey(Object k) {
			return false;
		}

		@Override
		public int defaultReturnValue() {
			return 0;
		}

		@Override
		public void defaultReturnValue(int defRetValue) {
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
			return Object2IntFunctions.EMPTY_FUNCTION;
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
			return Object2IntFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction<K> implements Object2IntFunction<K> {
		protected final Function<? super K, ? extends Integer> function;

		protected PrimitiveFunction(Function<? super K, ? extends Integer> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(Object key) {
			return this.function.apply(key) != null;
		}

		@Override
		public int getInt(Object key) {
			Integer v = (Integer)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Integer get(Object key) {
			return (Integer)this.function.apply(key);
		}

		@Deprecated
		@Override
		public Integer put(K key, Integer value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton<K> extends AbstractObject2IntFunction<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final K key;
		protected final int value;

		protected Singleton(K key, int value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(Object k) {
			return Objects.equals(this.key, k);
		}

		@Override
		public int getInt(Object k) {
			return Objects.equals(this.key, k) ? this.value : this.defRetValue;
		}

		@Override
		public int size() {
			return 1;
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedFunction<K> implements Object2IntFunction<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2IntFunction<K> function;
		protected final Object sync;

		protected SynchronizedFunction(Object2IntFunction<K> f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Object2IntFunction<K> f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Override
		public int applyAsInt(K operand) {
			synchronized (this.sync) {
				return this.function.applyAsInt(operand);
			}
		}

		@Deprecated
		public Integer apply(K key) {
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
		public int defaultReturnValue() {
			synchronized (this.sync) {
				return this.function.defaultReturnValue();
			}
		}

		@Override
		public void defaultReturnValue(int defRetValue) {
			synchronized (this.sync) {
				this.function.defaultReturnValue(defRetValue);
			}
		}

		@Override
		public boolean containsKey(Object k) {
			synchronized (this.sync) {
				return this.function.containsKey(k);
			}
		}

		@Override
		public int put(K k, int v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public int getInt(Object k) {
			synchronized (this.sync) {
				return this.function.getInt(k);
			}
		}

		@Override
		public int removeInt(Object k) {
			synchronized (this.sync) {
				return this.function.removeInt(k);
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
		public Integer put(K k, Integer v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Deprecated
		@Override
		public Integer get(Object k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Deprecated
		@Override
		public Integer remove(Object k) {
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

	public static class UnmodifiableFunction<K> extends AbstractObject2IntFunction<K> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2IntFunction<K> function;

		protected UnmodifiableFunction(Object2IntFunction<K> f) {
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
		public int defaultReturnValue() {
			return this.function.defaultReturnValue();
		}

		@Override
		public void defaultReturnValue(int defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsKey(Object k) {
			return this.function.containsKey(k);
		}

		@Override
		public int put(K k, int v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getInt(Object k) {
			return this.function.getInt(k);
		}

		@Override
		public int removeInt(Object k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer put(K k, Integer v) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer get(Object k) {
			return this.function.get(k);
		}

		@Deprecated
		@Override
		public Integer remove(Object k) {
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

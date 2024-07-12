package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public final class Object2ShortFunctions {
	public static final Object2ShortFunctions.EmptyFunction EMPTY_FUNCTION = new Object2ShortFunctions.EmptyFunction();

	private Object2ShortFunctions() {
	}

	public static <K> Object2ShortFunction<K> singleton(K key, short value) {
		return new Object2ShortFunctions.Singleton<>(key, value);
	}

	public static <K> Object2ShortFunction<K> singleton(K key, Short value) {
		return new Object2ShortFunctions.Singleton<>(key, value);
	}

	public static <K> Object2ShortFunction<K> synchronize(Object2ShortFunction<K> f) {
		return new Object2ShortFunctions.SynchronizedFunction<>(f);
	}

	public static <K> Object2ShortFunction<K> synchronize(Object2ShortFunction<K> f, Object sync) {
		return new Object2ShortFunctions.SynchronizedFunction<>(f, sync);
	}

	public static <K> Object2ShortFunction<K> unmodifiable(Object2ShortFunction<K> f) {
		return new Object2ShortFunctions.UnmodifiableFunction<>(f);
	}

	public static <K> Object2ShortFunction<K> primitive(Function<? super K, ? extends Short> f) {
		Objects.requireNonNull(f);
		if (f instanceof Object2ShortFunction) {
			return (Object2ShortFunction<K>)f;
		} else {
			return (Object2ShortFunction<K>)(f instanceof ToIntFunction
				? key -> SafeMath.safeIntToShort(((ToIntFunction)f).applyAsInt(key))
				: new Object2ShortFunctions.PrimitiveFunction<>(f));
		}
	}

	public static class EmptyFunction<K> extends AbstractObject2ShortFunction<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public short getShort(Object k) {
			return 0;
		}

		@Override
		public boolean containsKey(Object k) {
			return false;
		}

		@Override
		public short defaultReturnValue() {
			return 0;
		}

		@Override
		public void defaultReturnValue(short defRetValue) {
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
			return Object2ShortFunctions.EMPTY_FUNCTION;
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
			return Object2ShortFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction<K> implements Object2ShortFunction<K> {
		protected final Function<? super K, ? extends Short> function;

		protected PrimitiveFunction(Function<? super K, ? extends Short> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(Object key) {
			return this.function.apply(key) != null;
		}

		@Override
		public short getShort(Object key) {
			Short v = (Short)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Short get(Object key) {
			return (Short)this.function.apply(key);
		}

		@Deprecated
		@Override
		public Short put(K key, Short value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton<K> extends AbstractObject2ShortFunction<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final K key;
		protected final short value;

		protected Singleton(K key, short value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(Object k) {
			return Objects.equals(this.key, k);
		}

		@Override
		public short getShort(Object k) {
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

	public static class SynchronizedFunction<K> implements Object2ShortFunction<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2ShortFunction<K> function;
		protected final Object sync;

		protected SynchronizedFunction(Object2ShortFunction<K> f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Object2ShortFunction<K> f) {
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
		public Short apply(K key) {
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
		public short defaultReturnValue() {
			synchronized (this.sync) {
				return this.function.defaultReturnValue();
			}
		}

		@Override
		public void defaultReturnValue(short defRetValue) {
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
		public short put(K k, short v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public short getShort(Object k) {
			synchronized (this.sync) {
				return this.function.getShort(k);
			}
		}

		@Override
		public short removeShort(Object k) {
			synchronized (this.sync) {
				return this.function.removeShort(k);
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
		public Short put(K k, Short v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Deprecated
		@Override
		public Short get(Object k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Deprecated
		@Override
		public Short remove(Object k) {
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

	public static class UnmodifiableFunction<K> extends AbstractObject2ShortFunction<K> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Object2ShortFunction<K> function;

		protected UnmodifiableFunction(Object2ShortFunction<K> f) {
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
		public short defaultReturnValue() {
			return this.function.defaultReturnValue();
		}

		@Override
		public void defaultReturnValue(short defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsKey(Object k) {
			return this.function.containsKey(k);
		}

		@Override
		public short put(K k, short v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public short getShort(Object k) {
			return this.function.getShort(k);
		}

		@Override
		public short removeShort(Object k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short put(K k, Short v) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Short get(Object k) {
			return this.function.get(k);
		}

		@Deprecated
		@Override
		public Short remove(Object k) {
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

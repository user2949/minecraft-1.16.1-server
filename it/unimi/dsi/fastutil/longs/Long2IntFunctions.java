package it.unimi.dsi.fastutil.longs;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.LongToIntFunction;

public final class Long2IntFunctions {
	public static final Long2IntFunctions.EmptyFunction EMPTY_FUNCTION = new Long2IntFunctions.EmptyFunction();

	private Long2IntFunctions() {
	}

	public static Long2IntFunction singleton(long key, int value) {
		return new Long2IntFunctions.Singleton(key, value);
	}

	public static Long2IntFunction singleton(Long key, Integer value) {
		return new Long2IntFunctions.Singleton(key, value);
	}

	public static Long2IntFunction synchronize(Long2IntFunction f) {
		return new Long2IntFunctions.SynchronizedFunction(f);
	}

	public static Long2IntFunction synchronize(Long2IntFunction f, Object sync) {
		return new Long2IntFunctions.SynchronizedFunction(f, sync);
	}

	public static Long2IntFunction unmodifiable(Long2IntFunction f) {
		return new Long2IntFunctions.UnmodifiableFunction(f);
	}

	public static Long2IntFunction primitive(Function<? super Long, ? extends Integer> f) {
		Objects.requireNonNull(f);
		if (f instanceof Long2IntFunction) {
			return (Long2IntFunction)f;
		} else {
			return (Long2IntFunction)(f instanceof LongToIntFunction ? ((LongToIntFunction)f)::applyAsInt : new Long2IntFunctions.PrimitiveFunction(f));
		}
	}

	public static class EmptyFunction extends AbstractLong2IntFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public int get(long k) {
			return 0;
		}

		@Override
		public boolean containsKey(long k) {
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
			return Long2IntFunctions.EMPTY_FUNCTION;
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
			return Long2IntFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction implements Long2IntFunction {
		protected final Function<? super Long, ? extends Integer> function;

		protected PrimitiveFunction(Function<? super Long, ? extends Integer> function) {
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
		public int get(long key) {
			Integer v = (Integer)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Integer get(Object key) {
			return key == null ? null : (Integer)this.function.apply((Long)key);
		}

		@Deprecated
		@Override
		public Integer put(Long key, Integer value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton extends AbstractLong2IntFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final long key;
		protected final int value;

		protected Singleton(long key, int value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(long k) {
			return this.key == k;
		}

		@Override
		public int get(long k) {
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

	public static class SynchronizedFunction implements Long2IntFunction, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2IntFunction function;
		protected final Object sync;

		protected SynchronizedFunction(Long2IntFunction f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Long2IntFunction f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Override
		public int applyAsInt(long operand) {
			synchronized (this.sync) {
				return this.function.applyAsInt(operand);
			}
		}

		@Deprecated
		public Integer apply(Long key) {
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
		public int put(long k, int v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public int get(long k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public int remove(long k) {
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
		public Integer put(Long k, Integer v) {
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

	public static class UnmodifiableFunction extends AbstractLong2IntFunction implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Long2IntFunction function;

		protected UnmodifiableFunction(Long2IntFunction f) {
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
		public boolean containsKey(long k) {
			return this.function.containsKey(k);
		}

		@Override
		public int put(long k, int v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int get(long k) {
			return this.function.get(k);
		}

		@Override
		public int remove(long k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer put(Long k, Integer v) {
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

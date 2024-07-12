package it.unimi.dsi.fastutil.chars;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntToLongFunction;

public final class Char2LongFunctions {
	public static final Char2LongFunctions.EmptyFunction EMPTY_FUNCTION = new Char2LongFunctions.EmptyFunction();

	private Char2LongFunctions() {
	}

	public static Char2LongFunction singleton(char key, long value) {
		return new Char2LongFunctions.Singleton(key, value);
	}

	public static Char2LongFunction singleton(Character key, Long value) {
		return new Char2LongFunctions.Singleton(key, value);
	}

	public static Char2LongFunction synchronize(Char2LongFunction f) {
		return new Char2LongFunctions.SynchronizedFunction(f);
	}

	public static Char2LongFunction synchronize(Char2LongFunction f, Object sync) {
		return new Char2LongFunctions.SynchronizedFunction(f, sync);
	}

	public static Char2LongFunction unmodifiable(Char2LongFunction f) {
		return new Char2LongFunctions.UnmodifiableFunction(f);
	}

	public static Char2LongFunction primitive(Function<? super Character, ? extends Long> f) {
		Objects.requireNonNull(f);
		if (f instanceof Char2LongFunction) {
			return (Char2LongFunction)f;
		} else {
			return (Char2LongFunction)(f instanceof IntToLongFunction ? ((IntToLongFunction)f)::applyAsLong : new Char2LongFunctions.PrimitiveFunction(f));
		}
	}

	public static class EmptyFunction extends AbstractChar2LongFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public long get(char k) {
			return 0L;
		}

		@Override
		public boolean containsKey(char k) {
			return false;
		}

		@Override
		public long defaultReturnValue() {
			return 0L;
		}

		@Override
		public void defaultReturnValue(long defRetValue) {
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
			return Char2LongFunctions.EMPTY_FUNCTION;
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
			return Char2LongFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction implements Char2LongFunction {
		protected final Function<? super Character, ? extends Long> function;

		protected PrimitiveFunction(Function<? super Character, ? extends Long> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(char key) {
			return this.function.apply(key) != null;
		}

		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			return key == null ? false : this.function.apply((Character)key) != null;
		}

		@Override
		public long get(char key) {
			Long v = (Long)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Long get(Object key) {
			return key == null ? null : (Long)this.function.apply((Character)key);
		}

		@Deprecated
		@Override
		public Long put(Character key, Long value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton extends AbstractChar2LongFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final char key;
		protected final long value;

		protected Singleton(char key, long value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(char k) {
			return this.key == k;
		}

		@Override
		public long get(char k) {
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

	public static class SynchronizedFunction implements Char2LongFunction, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2LongFunction function;
		protected final Object sync;

		protected SynchronizedFunction(Char2LongFunction f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Char2LongFunction f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Deprecated
		@Override
		public long applyAsLong(int operand) {
			synchronized (this.sync) {
				return this.function.applyAsLong(operand);
			}
		}

		@Deprecated
		public Long apply(Character key) {
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
		public long defaultReturnValue() {
			synchronized (this.sync) {
				return this.function.defaultReturnValue();
			}
		}

		@Override
		public void defaultReturnValue(long defRetValue) {
			synchronized (this.sync) {
				this.function.defaultReturnValue(defRetValue);
			}
		}

		@Override
		public boolean containsKey(char k) {
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
		public long put(char k, long v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public long get(char k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public long remove(char k) {
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
		public Long put(Character k, Long v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Deprecated
		@Override
		public Long get(Object k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Deprecated
		@Override
		public Long remove(Object k) {
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

	public static class UnmodifiableFunction extends AbstractChar2LongFunction implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2LongFunction function;

		protected UnmodifiableFunction(Char2LongFunction f) {
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
		public long defaultReturnValue() {
			return this.function.defaultReturnValue();
		}

		@Override
		public void defaultReturnValue(long defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsKey(char k) {
			return this.function.containsKey(k);
		}

		@Override
		public long put(char k, long v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public long get(char k) {
			return this.function.get(k);
		}

		@Override
		public long remove(char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long put(Character k, Long v) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Long get(Object k) {
			return this.function.get(k);
		}

		@Deprecated
		@Override
		public Long remove(Object k) {
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

package it.unimi.dsi.fastutil.bytes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntPredicate;

public final class Byte2BooleanFunctions {
	public static final Byte2BooleanFunctions.EmptyFunction EMPTY_FUNCTION = new Byte2BooleanFunctions.EmptyFunction();

	private Byte2BooleanFunctions() {
	}

	public static Byte2BooleanFunction singleton(byte key, boolean value) {
		return new Byte2BooleanFunctions.Singleton(key, value);
	}

	public static Byte2BooleanFunction singleton(Byte key, Boolean value) {
		return new Byte2BooleanFunctions.Singleton(key, value);
	}

	public static Byte2BooleanFunction synchronize(Byte2BooleanFunction f) {
		return new Byte2BooleanFunctions.SynchronizedFunction(f);
	}

	public static Byte2BooleanFunction synchronize(Byte2BooleanFunction f, Object sync) {
		return new Byte2BooleanFunctions.SynchronizedFunction(f, sync);
	}

	public static Byte2BooleanFunction unmodifiable(Byte2BooleanFunction f) {
		return new Byte2BooleanFunctions.UnmodifiableFunction(f);
	}

	public static Byte2BooleanFunction primitive(Function<? super Byte, ? extends Boolean> f) {
		Objects.requireNonNull(f);
		if (f instanceof Byte2BooleanFunction) {
			return (Byte2BooleanFunction)f;
		} else {
			return (Byte2BooleanFunction)(f instanceof IntPredicate ? ((IntPredicate)f)::test : new Byte2BooleanFunctions.PrimitiveFunction(f));
		}
	}

	public static class EmptyFunction extends AbstractByte2BooleanFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public boolean get(byte k) {
			return false;
		}

		@Override
		public boolean containsKey(byte k) {
			return false;
		}

		@Override
		public boolean defaultReturnValue() {
			return false;
		}

		@Override
		public void defaultReturnValue(boolean defRetValue) {
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
			return Byte2BooleanFunctions.EMPTY_FUNCTION;
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
			return Byte2BooleanFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction implements Byte2BooleanFunction {
		protected final Function<? super Byte, ? extends Boolean> function;

		protected PrimitiveFunction(Function<? super Byte, ? extends Boolean> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(byte key) {
			return this.function.apply(key) != null;
		}

		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			return key == null ? false : this.function.apply((Byte)key) != null;
		}

		@Override
		public boolean get(byte key) {
			Boolean v = (Boolean)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Boolean get(Object key) {
			return key == null ? null : (Boolean)this.function.apply((Byte)key);
		}

		@Deprecated
		@Override
		public Boolean put(Byte key, Boolean value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton extends AbstractByte2BooleanFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final byte key;
		protected final boolean value;

		protected Singleton(byte key, boolean value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(byte k) {
			return this.key == k;
		}

		@Override
		public boolean get(byte k) {
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

	public static class SynchronizedFunction implements Byte2BooleanFunction, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Byte2BooleanFunction function;
		protected final Object sync;

		protected SynchronizedFunction(Byte2BooleanFunction f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Byte2BooleanFunction f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Deprecated
		@Override
		public boolean test(int operand) {
			synchronized (this.sync) {
				return this.function.test(operand);
			}
		}

		@Deprecated
		public Boolean apply(Byte key) {
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
		public boolean defaultReturnValue() {
			synchronized (this.sync) {
				return this.function.defaultReturnValue();
			}
		}

		@Override
		public void defaultReturnValue(boolean defRetValue) {
			synchronized (this.sync) {
				this.function.defaultReturnValue(defRetValue);
			}
		}

		@Override
		public boolean containsKey(byte k) {
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
		public boolean put(byte k, boolean v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public boolean get(byte k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public boolean remove(byte k) {
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
		public Boolean put(Byte k, Boolean v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Deprecated
		@Override
		public Boolean get(Object k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Deprecated
		@Override
		public Boolean remove(Object k) {
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

	public static class UnmodifiableFunction extends AbstractByte2BooleanFunction implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Byte2BooleanFunction function;

		protected UnmodifiableFunction(Byte2BooleanFunction f) {
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
		public boolean defaultReturnValue() {
			return this.function.defaultReturnValue();
		}

		@Override
		public void defaultReturnValue(boolean defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsKey(byte k) {
			return this.function.containsKey(k);
		}

		@Override
		public boolean put(byte k, boolean v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean get(byte k) {
			return this.function.get(k);
		}

		@Override
		public boolean remove(byte k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean put(Byte k, Boolean v) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean get(Object k) {
			return this.function.get(k);
		}

		@Deprecated
		@Override
		public Boolean remove(Object k) {
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

package it.unimi.dsi.fastutil.floats;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public final class Float2IntFunctions {
	public static final Float2IntFunctions.EmptyFunction EMPTY_FUNCTION = new Float2IntFunctions.EmptyFunction();

	private Float2IntFunctions() {
	}

	public static Float2IntFunction singleton(float key, int value) {
		return new Float2IntFunctions.Singleton(key, value);
	}

	public static Float2IntFunction singleton(Float key, Integer value) {
		return new Float2IntFunctions.Singleton(key, value);
	}

	public static Float2IntFunction synchronize(Float2IntFunction f) {
		return new Float2IntFunctions.SynchronizedFunction(f);
	}

	public static Float2IntFunction synchronize(Float2IntFunction f, Object sync) {
		return new Float2IntFunctions.SynchronizedFunction(f, sync);
	}

	public static Float2IntFunction unmodifiable(Float2IntFunction f) {
		return new Float2IntFunctions.UnmodifiableFunction(f);
	}

	public static Float2IntFunction primitive(Function<? super Float, ? extends Integer> f) {
		Objects.requireNonNull(f);
		if (f instanceof Float2IntFunction) {
			return (Float2IntFunction)f;
		} else {
			return (Float2IntFunction)(f instanceof DoubleToIntFunction ? ((DoubleToIntFunction)f)::applyAsInt : new Float2IntFunctions.PrimitiveFunction(f));
		}
	}

	public static class EmptyFunction extends AbstractFloat2IntFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public int get(float k) {
			return 0;
		}

		@Override
		public boolean containsKey(float k) {
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
			return Float2IntFunctions.EMPTY_FUNCTION;
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
			return Float2IntFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction implements Float2IntFunction {
		protected final Function<? super Float, ? extends Integer> function;

		protected PrimitiveFunction(Function<? super Float, ? extends Integer> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(float key) {
			return this.function.apply(key) != null;
		}

		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			return key == null ? false : this.function.apply((Float)key) != null;
		}

		@Override
		public int get(float key) {
			Integer v = (Integer)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Integer get(Object key) {
			return key == null ? null : (Integer)this.function.apply((Float)key);
		}

		@Deprecated
		@Override
		public Integer put(Float key, Integer value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton extends AbstractFloat2IntFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final float key;
		protected final int value;

		protected Singleton(float key, int value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(float k) {
			return Float.floatToIntBits(this.key) == Float.floatToIntBits(k);
		}

		@Override
		public int get(float k) {
			return Float.floatToIntBits(this.key) == Float.floatToIntBits(k) ? this.value : this.defRetValue;
		}

		@Override
		public int size() {
			return 1;
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedFunction implements Float2IntFunction, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Float2IntFunction function;
		protected final Object sync;

		protected SynchronizedFunction(Float2IntFunction f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Float2IntFunction f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Deprecated
		@Override
		public int applyAsInt(double operand) {
			synchronized (this.sync) {
				return this.function.applyAsInt(operand);
			}
		}

		@Deprecated
		public Integer apply(Float key) {
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
		public boolean containsKey(float k) {
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
		public int put(float k, int v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public int get(float k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public int remove(float k) {
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
		public Integer put(Float k, Integer v) {
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

	public static class UnmodifiableFunction extends AbstractFloat2IntFunction implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Float2IntFunction function;

		protected UnmodifiableFunction(Float2IntFunction f) {
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
		public boolean containsKey(float k) {
			return this.function.containsKey(k);
		}

		@Override
		public int put(float k, int v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int get(float k) {
			return this.function.get(k);
		}

		@Override
		public int remove(float k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Integer put(Float k, Integer v) {
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

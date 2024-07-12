package it.unimi.dsi.fastutil.doubles;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public final class Double2DoubleFunctions {
	public static final Double2DoubleFunctions.EmptyFunction EMPTY_FUNCTION = new Double2DoubleFunctions.EmptyFunction();

	private Double2DoubleFunctions() {
	}

	public static Double2DoubleFunction singleton(double key, double value) {
		return new Double2DoubleFunctions.Singleton(key, value);
	}

	public static Double2DoubleFunction singleton(Double key, Double value) {
		return new Double2DoubleFunctions.Singleton(key, value);
	}

	public static Double2DoubleFunction synchronize(Double2DoubleFunction f) {
		return new Double2DoubleFunctions.SynchronizedFunction(f);
	}

	public static Double2DoubleFunction synchronize(Double2DoubleFunction f, Object sync) {
		return new Double2DoubleFunctions.SynchronizedFunction(f, sync);
	}

	public static Double2DoubleFunction unmodifiable(Double2DoubleFunction f) {
		return new Double2DoubleFunctions.UnmodifiableFunction(f);
	}

	public static Double2DoubleFunction primitive(Function<? super Double, ? extends Double> f) {
		Objects.requireNonNull(f);
		if (f instanceof Double2DoubleFunction) {
			return (Double2DoubleFunction)f;
		} else {
			return (Double2DoubleFunction)(f instanceof DoubleUnaryOperator ? ((DoubleUnaryOperator)f)::applyAsDouble : new Double2DoubleFunctions.PrimitiveFunction(f));
		}
	}

	public static class EmptyFunction extends AbstractDouble2DoubleFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public double get(double k) {
			return 0.0;
		}

		@Override
		public boolean containsKey(double k) {
			return false;
		}

		@Override
		public double defaultReturnValue() {
			return 0.0;
		}

		@Override
		public void defaultReturnValue(double defRetValue) {
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
			return Double2DoubleFunctions.EMPTY_FUNCTION;
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
			return Double2DoubleFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction implements Double2DoubleFunction {
		protected final Function<? super Double, ? extends Double> function;

		protected PrimitiveFunction(Function<? super Double, ? extends Double> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(double key) {
			return this.function.apply(key) != null;
		}

		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			return key == null ? false : this.function.apply((Double)key) != null;
		}

		@Override
		public double get(double key) {
			Double v = (Double)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Double get(Object key) {
			return key == null ? null : (Double)this.function.apply((Double)key);
		}

		@Deprecated
		@Override
		public Double put(Double key, Double value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton extends AbstractDouble2DoubleFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final double key;
		protected final double value;

		protected Singleton(double key, double value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(double k) {
			return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k);
		}

		@Override
		public double get(double k) {
			return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k) ? this.value : this.defRetValue;
		}

		@Override
		public int size() {
			return 1;
		}

		public Object clone() {
			return this;
		}
	}

	public static class SynchronizedFunction implements Double2DoubleFunction, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Double2DoubleFunction function;
		protected final Object sync;

		protected SynchronizedFunction(Double2DoubleFunction f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Double2DoubleFunction f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Override
		public double applyAsDouble(double operand) {
			synchronized (this.sync) {
				return this.function.applyAsDouble(operand);
			}
		}

		@Deprecated
		public Double apply(Double key) {
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
		public double defaultReturnValue() {
			synchronized (this.sync) {
				return this.function.defaultReturnValue();
			}
		}

		@Override
		public void defaultReturnValue(double defRetValue) {
			synchronized (this.sync) {
				this.function.defaultReturnValue(defRetValue);
			}
		}

		@Override
		public boolean containsKey(double k) {
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
		public double put(double k, double v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public double get(double k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public double remove(double k) {
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
		public Double put(Double k, Double v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Deprecated
		@Override
		public Double get(Object k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Deprecated
		@Override
		public Double remove(Object k) {
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

	public static class UnmodifiableFunction extends AbstractDouble2DoubleFunction implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Double2DoubleFunction function;

		protected UnmodifiableFunction(Double2DoubleFunction f) {
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
		public double defaultReturnValue() {
			return this.function.defaultReturnValue();
		}

		@Override
		public void defaultReturnValue(double defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsKey(double k) {
			return this.function.containsKey(k);
		}

		@Override
		public double put(double k, double v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public double get(double k) {
			return this.function.get(k);
		}

		@Override
		public double remove(double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double put(Double k, Double v) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Double get(Object k) {
			return this.function.get(k);
		}

		@Deprecated
		@Override
		public Double remove(Object k) {
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

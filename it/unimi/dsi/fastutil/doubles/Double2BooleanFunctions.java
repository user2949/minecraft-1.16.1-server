package it.unimi.dsi.fastutil.doubles;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.Function;

public final class Double2BooleanFunctions {
	public static final Double2BooleanFunctions.EmptyFunction EMPTY_FUNCTION = new Double2BooleanFunctions.EmptyFunction();

	private Double2BooleanFunctions() {
	}

	public static Double2BooleanFunction singleton(double key, boolean value) {
		return new Double2BooleanFunctions.Singleton(key, value);
	}

	public static Double2BooleanFunction singleton(Double key, Boolean value) {
		return new Double2BooleanFunctions.Singleton(key, value);
	}

	public static Double2BooleanFunction synchronize(Double2BooleanFunction f) {
		return new Double2BooleanFunctions.SynchronizedFunction(f);
	}

	public static Double2BooleanFunction synchronize(Double2BooleanFunction f, Object sync) {
		return new Double2BooleanFunctions.SynchronizedFunction(f, sync);
	}

	public static Double2BooleanFunction unmodifiable(Double2BooleanFunction f) {
		return new Double2BooleanFunctions.UnmodifiableFunction(f);
	}

	public static Double2BooleanFunction primitive(Function<? super Double, ? extends Boolean> f) {
		Objects.requireNonNull(f);
		if (f instanceof Double2BooleanFunction) {
			return (Double2BooleanFunction)f;
		} else {
			return (Double2BooleanFunction)(f instanceof DoublePredicate ? ((DoublePredicate)f)::test : new Double2BooleanFunctions.PrimitiveFunction(f));
		}
	}

	public static class EmptyFunction extends AbstractDouble2BooleanFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public boolean get(double k) {
			return false;
		}

		@Override
		public boolean containsKey(double k) {
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
			return Double2BooleanFunctions.EMPTY_FUNCTION;
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
			return Double2BooleanFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction implements Double2BooleanFunction {
		protected final Function<? super Double, ? extends Boolean> function;

		protected PrimitiveFunction(Function<? super Double, ? extends Boolean> function) {
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
		public boolean get(double key) {
			Boolean v = (Boolean)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Boolean get(Object key) {
			return key == null ? null : (Boolean)this.function.apply((Double)key);
		}

		@Deprecated
		@Override
		public Boolean put(Double key, Boolean value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton extends AbstractDouble2BooleanFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final double key;
		protected final boolean value;

		protected Singleton(double key, boolean value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(double k) {
			return Double.doubleToLongBits(this.key) == Double.doubleToLongBits(k);
		}

		@Override
		public boolean get(double k) {
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

	public static class SynchronizedFunction implements Double2BooleanFunction, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Double2BooleanFunction function;
		protected final Object sync;

		protected SynchronizedFunction(Double2BooleanFunction f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Double2BooleanFunction f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Override
		public boolean test(double operand) {
			synchronized (this.sync) {
				return this.function.test(operand);
			}
		}

		@Deprecated
		public Boolean apply(Double key) {
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
		public boolean put(double k, boolean v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public boolean get(double k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public boolean remove(double k) {
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
		public Boolean put(Double k, Boolean v) {
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

	public static class UnmodifiableFunction extends AbstractDouble2BooleanFunction implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Double2BooleanFunction function;

		protected UnmodifiableFunction(Double2BooleanFunction f) {
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
		public boolean containsKey(double k) {
			return this.function.containsKey(k);
		}

		@Override
		public boolean put(double k, boolean v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean get(double k) {
			return this.function.get(k);
		}

		@Override
		public boolean remove(double k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean put(Double k, Boolean v) {
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

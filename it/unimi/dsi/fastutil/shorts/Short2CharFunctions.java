package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.SafeMath;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

public final class Short2CharFunctions {
	public static final Short2CharFunctions.EmptyFunction EMPTY_FUNCTION = new Short2CharFunctions.EmptyFunction();

	private Short2CharFunctions() {
	}

	public static Short2CharFunction singleton(short key, char value) {
		return new Short2CharFunctions.Singleton(key, value);
	}

	public static Short2CharFunction singleton(Short key, Character value) {
		return new Short2CharFunctions.Singleton(key, value);
	}

	public static Short2CharFunction synchronize(Short2CharFunction f) {
		return new Short2CharFunctions.SynchronizedFunction(f);
	}

	public static Short2CharFunction synchronize(Short2CharFunction f, Object sync) {
		return new Short2CharFunctions.SynchronizedFunction(f, sync);
	}

	public static Short2CharFunction unmodifiable(Short2CharFunction f) {
		return new Short2CharFunctions.UnmodifiableFunction(f);
	}

	public static Short2CharFunction primitive(Function<? super Short, ? extends Character> f) {
		Objects.requireNonNull(f);
		if (f instanceof Short2CharFunction) {
			return (Short2CharFunction)f;
		} else {
			return (Short2CharFunction)(f instanceof IntUnaryOperator
				? key -> SafeMath.safeIntToChar(((IntUnaryOperator)f).applyAsInt(key))
				: new Short2CharFunctions.PrimitiveFunction(f));
		}
	}

	public static class EmptyFunction extends AbstractShort2CharFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public char get(short k) {
			return '\u0000';
		}

		@Override
		public boolean containsKey(short k) {
			return false;
		}

		@Override
		public char defaultReturnValue() {
			return '\u0000';
		}

		@Override
		public void defaultReturnValue(char defRetValue) {
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
			return Short2CharFunctions.EMPTY_FUNCTION;
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
			return Short2CharFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction implements Short2CharFunction {
		protected final Function<? super Short, ? extends Character> function;

		protected PrimitiveFunction(Function<? super Short, ? extends Character> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(short key) {
			return this.function.apply(key) != null;
		}

		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			return key == null ? false : this.function.apply((Short)key) != null;
		}

		@Override
		public char get(short key) {
			Character v = (Character)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Character get(Object key) {
			return key == null ? null : (Character)this.function.apply((Short)key);
		}

		@Deprecated
		@Override
		public Character put(Short key, Character value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton extends AbstractShort2CharFunction implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final short key;
		protected final char value;

		protected Singleton(short key, char value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(short k) {
			return this.key == k;
		}

		@Override
		public char get(short k) {
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

	public static class SynchronizedFunction implements Short2CharFunction, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Short2CharFunction function;
		protected final Object sync;

		protected SynchronizedFunction(Short2CharFunction f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Short2CharFunction f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Deprecated
		@Override
		public int applyAsInt(int operand) {
			synchronized (this.sync) {
				return this.function.applyAsInt(operand);
			}
		}

		@Deprecated
		public Character apply(Short key) {
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
		public char defaultReturnValue() {
			synchronized (this.sync) {
				return this.function.defaultReturnValue();
			}
		}

		@Override
		public void defaultReturnValue(char defRetValue) {
			synchronized (this.sync) {
				this.function.defaultReturnValue(defRetValue);
			}
		}

		@Override
		public boolean containsKey(short k) {
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
		public char put(short k, char v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public char get(short k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public char remove(short k) {
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
		public Character put(Short k, Character v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Deprecated
		@Override
		public Character get(Object k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Deprecated
		@Override
		public Character remove(Object k) {
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

	public static class UnmodifiableFunction extends AbstractShort2CharFunction implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Short2CharFunction function;

		protected UnmodifiableFunction(Short2CharFunction f) {
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
		public char defaultReturnValue() {
			return this.function.defaultReturnValue();
		}

		@Override
		public void defaultReturnValue(char defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsKey(short k) {
			return this.function.containsKey(k);
		}

		@Override
		public char put(short k, char v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public char get(short k) {
			return this.function.get(k);
		}

		@Override
		public char remove(short k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character put(Short k, Character v) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Character get(Object k) {
			return this.function.get(k);
		}

		@Deprecated
		@Override
		public Character remove(Object k) {
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

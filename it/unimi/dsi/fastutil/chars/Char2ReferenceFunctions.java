package it.unimi.dsi.fastutil.chars;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Char2ReferenceFunctions {
	public static final Char2ReferenceFunctions.EmptyFunction EMPTY_FUNCTION = new Char2ReferenceFunctions.EmptyFunction();

	private Char2ReferenceFunctions() {
	}

	public static <V> Char2ReferenceFunction<V> singleton(char key, V value) {
		return new Char2ReferenceFunctions.Singleton<>(key, value);
	}

	public static <V> Char2ReferenceFunction<V> singleton(Character key, V value) {
		return new Char2ReferenceFunctions.Singleton<>(key, value);
	}

	public static <V> Char2ReferenceFunction<V> synchronize(Char2ReferenceFunction<V> f) {
		return new Char2ReferenceFunctions.SynchronizedFunction<>(f);
	}

	public static <V> Char2ReferenceFunction<V> synchronize(Char2ReferenceFunction<V> f, Object sync) {
		return new Char2ReferenceFunctions.SynchronizedFunction<>(f, sync);
	}

	public static <V> Char2ReferenceFunction<V> unmodifiable(Char2ReferenceFunction<V> f) {
		return new Char2ReferenceFunctions.UnmodifiableFunction<>(f);
	}

	public static <V> Char2ReferenceFunction<V> primitive(Function<? super Character, ? extends V> f) {
		Objects.requireNonNull(f);
		if (f instanceof Char2ReferenceFunction) {
			return (Char2ReferenceFunction<V>)f;
		} else {
			return (Char2ReferenceFunction<V>)(f instanceof IntFunction ? ((IntFunction)f)::apply : new Char2ReferenceFunctions.PrimitiveFunction<>(f));
		}
	}

	public static class EmptyFunction<V> extends AbstractChar2ReferenceFunction<V> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public V get(char k) {
			return null;
		}

		@Override
		public boolean containsKey(char k) {
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
			return Char2ReferenceFunctions.EMPTY_FUNCTION;
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
			return Char2ReferenceFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction<V> implements Char2ReferenceFunction<V> {
		protected final Function<? super Character, ? extends V> function;

		protected PrimitiveFunction(Function<? super Character, ? extends V> function) {
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
		public V get(char key) {
			V v = (V)this.function.apply(key);
			return v == null ? null : v;
		}

		@Deprecated
		@Override
		public V get(Object key) {
			return (V)(key == null ? null : this.function.apply((Character)key));
		}

		@Deprecated
		@Override
		public V put(Character key, V value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton<V> extends AbstractChar2ReferenceFunction<V> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final char key;
		protected final V value;

		protected Singleton(char key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(char k) {
			return this.key == k;
		}

		@Override
		public V get(char k) {
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

	public static class SynchronizedFunction<V> implements Char2ReferenceFunction<V>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2ReferenceFunction<V> function;
		protected final Object sync;

		protected SynchronizedFunction(Char2ReferenceFunction<V> f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Char2ReferenceFunction<V> f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Deprecated
		@Override
		public V apply(int operand) {
			synchronized (this.sync) {
				return this.function.apply(operand);
			}
		}

		@Deprecated
		public V apply(Character key) {
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
		public V put(char k, V v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public V get(char k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Override
		public V remove(char k) {
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
		public V put(Character k, V v) {
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

	public static class UnmodifiableFunction<V> extends AbstractChar2ReferenceFunction<V> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Char2ReferenceFunction<V> function;

		protected UnmodifiableFunction(Char2ReferenceFunction<V> f) {
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
		public boolean containsKey(char k) {
			return this.function.containsKey(k);
		}

		@Override
		public V put(char k, V v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public V get(char k) {
			return this.function.get(k);
		}

		@Override
		public V remove(char k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public V put(Character k, V v) {
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

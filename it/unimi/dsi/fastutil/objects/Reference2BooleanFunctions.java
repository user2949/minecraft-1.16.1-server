package it.unimi.dsi.fastutil.objects;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Reference2BooleanFunctions {
	public static final Reference2BooleanFunctions.EmptyFunction EMPTY_FUNCTION = new Reference2BooleanFunctions.EmptyFunction();

	private Reference2BooleanFunctions() {
	}

	public static <K> Reference2BooleanFunction<K> singleton(K key, boolean value) {
		return new Reference2BooleanFunctions.Singleton<>(key, value);
	}

	public static <K> Reference2BooleanFunction<K> singleton(K key, Boolean value) {
		return new Reference2BooleanFunctions.Singleton<>(key, value);
	}

	public static <K> Reference2BooleanFunction<K> synchronize(Reference2BooleanFunction<K> f) {
		return new Reference2BooleanFunctions.SynchronizedFunction<>(f);
	}

	public static <K> Reference2BooleanFunction<K> synchronize(Reference2BooleanFunction<K> f, Object sync) {
		return new Reference2BooleanFunctions.SynchronizedFunction<>(f, sync);
	}

	public static <K> Reference2BooleanFunction<K> unmodifiable(Reference2BooleanFunction<K> f) {
		return new Reference2BooleanFunctions.UnmodifiableFunction<>(f);
	}

	public static <K> Reference2BooleanFunction<K> primitive(Function<? super K, ? extends Boolean> f) {
		Objects.requireNonNull(f);
		if (f instanceof Reference2BooleanFunction) {
			return (Reference2BooleanFunction<K>)f;
		} else {
			return (Reference2BooleanFunction<K>)(f instanceof Predicate ? key -> ((Predicate)f).test(key) : new Reference2BooleanFunctions.PrimitiveFunction<>(f));
		}
	}

	public static class EmptyFunction<K> extends AbstractReference2BooleanFunction<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public boolean getBoolean(Object k) {
			return false;
		}

		@Override
		public boolean containsKey(Object k) {
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
			return Reference2BooleanFunctions.EMPTY_FUNCTION;
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
			return Reference2BooleanFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction<K> implements Reference2BooleanFunction<K> {
		protected final Function<? super K, ? extends Boolean> function;

		protected PrimitiveFunction(Function<? super K, ? extends Boolean> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(Object key) {
			return this.function.apply(key) != null;
		}

		@Override
		public boolean getBoolean(Object key) {
			Boolean v = (Boolean)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Boolean get(Object key) {
			return (Boolean)this.function.apply(key);
		}

		@Deprecated
		@Override
		public Boolean put(K key, Boolean value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton<K> extends AbstractReference2BooleanFunction<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final K key;
		protected final boolean value;

		protected Singleton(K key, boolean value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(Object k) {
			return this.key == k;
		}

		@Override
		public boolean getBoolean(Object k) {
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

	public static class SynchronizedFunction<K> implements Reference2BooleanFunction<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2BooleanFunction<K> function;
		protected final Object sync;

		protected SynchronizedFunction(Reference2BooleanFunction<K> f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Reference2BooleanFunction<K> f) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = this;
			}
		}

		@Override
		public boolean test(K operand) {
			synchronized (this.sync) {
				return this.function.test(operand);
			}
		}

		@Deprecated
		public Boolean apply(K key) {
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
		public boolean containsKey(Object k) {
			synchronized (this.sync) {
				return this.function.containsKey(k);
			}
		}

		@Override
		public boolean put(K k, boolean v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public boolean getBoolean(Object k) {
			synchronized (this.sync) {
				return this.function.getBoolean(k);
			}
		}

		@Override
		public boolean removeBoolean(Object k) {
			synchronized (this.sync) {
				return this.function.removeBoolean(k);
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
		public Boolean put(K k, Boolean v) {
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

	public static class UnmodifiableFunction<K> extends AbstractReference2BooleanFunction<K> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2BooleanFunction<K> function;

		protected UnmodifiableFunction(Reference2BooleanFunction<K> f) {
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
		public boolean containsKey(Object k) {
			return this.function.containsKey(k);
		}

		@Override
		public boolean put(K k, boolean v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean getBoolean(Object k) {
			return this.function.getBoolean(k);
		}

		@Override
		public boolean removeBoolean(Object k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Boolean put(K k, Boolean v) {
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

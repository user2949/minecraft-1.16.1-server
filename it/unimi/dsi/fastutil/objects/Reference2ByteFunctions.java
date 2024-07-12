package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public final class Reference2ByteFunctions {
	public static final Reference2ByteFunctions.EmptyFunction EMPTY_FUNCTION = new Reference2ByteFunctions.EmptyFunction();

	private Reference2ByteFunctions() {
	}

	public static <K> Reference2ByteFunction<K> singleton(K key, byte value) {
		return new Reference2ByteFunctions.Singleton<>(key, value);
	}

	public static <K> Reference2ByteFunction<K> singleton(K key, Byte value) {
		return new Reference2ByteFunctions.Singleton<>(key, value);
	}

	public static <K> Reference2ByteFunction<K> synchronize(Reference2ByteFunction<K> f) {
		return new Reference2ByteFunctions.SynchronizedFunction<>(f);
	}

	public static <K> Reference2ByteFunction<K> synchronize(Reference2ByteFunction<K> f, Object sync) {
		return new Reference2ByteFunctions.SynchronizedFunction<>(f, sync);
	}

	public static <K> Reference2ByteFunction<K> unmodifiable(Reference2ByteFunction<K> f) {
		return new Reference2ByteFunctions.UnmodifiableFunction<>(f);
	}

	public static <K> Reference2ByteFunction<K> primitive(Function<? super K, ? extends Byte> f) {
		Objects.requireNonNull(f);
		if (f instanceof Reference2ByteFunction) {
			return (Reference2ByteFunction<K>)f;
		} else {
			return (Reference2ByteFunction<K>)(f instanceof ToIntFunction
				? key -> SafeMath.safeIntToByte(((ToIntFunction)f).applyAsInt(key))
				: new Reference2ByteFunctions.PrimitiveFunction<>(f));
		}
	}

	public static class EmptyFunction<K> extends AbstractReference2ByteFunction<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;

		protected EmptyFunction() {
		}

		@Override
		public byte getByte(Object k) {
			return 0;
		}

		@Override
		public boolean containsKey(Object k) {
			return false;
		}

		@Override
		public byte defaultReturnValue() {
			return 0;
		}

		@Override
		public void defaultReturnValue(byte defRetValue) {
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
			return Reference2ByteFunctions.EMPTY_FUNCTION;
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
			return Reference2ByteFunctions.EMPTY_FUNCTION;
		}
	}

	public static class PrimitiveFunction<K> implements Reference2ByteFunction<K> {
		protected final Function<? super K, ? extends Byte> function;

		protected PrimitiveFunction(Function<? super K, ? extends Byte> function) {
			this.function = function;
		}

		@Override
		public boolean containsKey(Object key) {
			return this.function.apply(key) != null;
		}

		@Override
		public byte getByte(Object key) {
			Byte v = (Byte)this.function.apply(key);
			return v == null ? this.defaultReturnValue() : v;
		}

		@Deprecated
		@Override
		public Byte get(Object key) {
			return (Byte)this.function.apply(key);
		}

		@Deprecated
		@Override
		public Byte put(K key, Byte value) {
			throw new UnsupportedOperationException();
		}
	}

	public static class Singleton<K> extends AbstractReference2ByteFunction<K> implements Serializable, Cloneable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final K key;
		protected final byte value;

		protected Singleton(K key, byte value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean containsKey(Object k) {
			return this.key == k;
		}

		@Override
		public byte getByte(Object k) {
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

	public static class SynchronizedFunction<K> implements Reference2ByteFunction<K>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2ByteFunction<K> function;
		protected final Object sync;

		protected SynchronizedFunction(Reference2ByteFunction<K> f, Object sync) {
			if (f == null) {
				throw new NullPointerException();
			} else {
				this.function = f;
				this.sync = sync;
			}
		}

		protected SynchronizedFunction(Reference2ByteFunction<K> f) {
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
		public Byte apply(K key) {
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
		public byte defaultReturnValue() {
			synchronized (this.sync) {
				return this.function.defaultReturnValue();
			}
		}

		@Override
		public void defaultReturnValue(byte defRetValue) {
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
		public byte put(K k, byte v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Override
		public byte getByte(Object k) {
			synchronized (this.sync) {
				return this.function.getByte(k);
			}
		}

		@Override
		public byte removeByte(Object k) {
			synchronized (this.sync) {
				return this.function.removeByte(k);
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
		public Byte put(K k, Byte v) {
			synchronized (this.sync) {
				return this.function.put(k, v);
			}
		}

		@Deprecated
		@Override
		public Byte get(Object k) {
			synchronized (this.sync) {
				return this.function.get(k);
			}
		}

		@Deprecated
		@Override
		public Byte remove(Object k) {
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

	public static class UnmodifiableFunction<K> extends AbstractReference2ByteFunction<K> implements Serializable {
		private static final long serialVersionUID = -7046029254386353129L;
		protected final Reference2ByteFunction<K> function;

		protected UnmodifiableFunction(Reference2ByteFunction<K> f) {
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
		public byte defaultReturnValue() {
			return this.function.defaultReturnValue();
		}

		@Override
		public void defaultReturnValue(byte defRetValue) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsKey(Object k) {
			return this.function.containsKey(k);
		}

		@Override
		public byte put(K k, byte v) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte getByte(Object k) {
			return this.function.getByte(k);
		}

		@Override
		public byte removeByte(Object k) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte put(K k, Byte v) {
			throw new UnsupportedOperationException();
		}

		@Deprecated
		@Override
		public Byte get(Object k) {
			return this.function.get(k);
		}

		@Deprecated
		@Override
		public Byte remove(Object k) {
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

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class aeg<T> extends AbstractSet<T> {
	private final Comparator<T> a;
	private T[] b;
	private int c;

	private aeg(int integer, Comparator<T> comparator) {
		this.a = comparator;
		if (integer < 0) {
			throw new IllegalArgumentException("Initial capacity (" + integer + ") is negative");
		} else {
			this.b = (T[])a(new Object[integer]);
		}
	}

	public static <T extends Comparable<T>> aeg<T> a(int integer) {
		return new aeg<>(integer, Comparator.naturalOrder());
	}

	private static <T> T[] a(Object[] arr) {
		return (T[])arr;
	}

	private int c(T object) {
		return Arrays.binarySearch(this.b, 0, this.c, object, this.a);
	}

	private static int b(int integer) {
		return -integer - 1;
	}

	public boolean add(T object) {
		int integer3 = this.c(object);
		if (integer3 >= 0) {
			return false;
		} else {
			int integer4 = b(integer3);
			this.a(object, integer4);
			return true;
		}
	}

	private void c(int integer) {
		if (integer > this.b.length) {
			if (this.b != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
				integer = (int)Math.max(Math.min((long)this.b.length + (long)(this.b.length >> 1), 2147483639L), (long)integer);
			} else if (integer < 10) {
				integer = 10;
			}

			Object[] arr3 = new Object[integer];
			System.arraycopy(this.b, 0, arr3, 0, this.c);
			this.b = (T[])a(arr3);
		}
	}

	private void a(T object, int integer) {
		this.c(this.c + 1);
		if (integer != this.c) {
			System.arraycopy(this.b, integer, this.b, integer + 1, this.c - integer);
		}

		this.b[integer] = object;
		this.c++;
	}

	private void d(int integer) {
		this.c--;
		if (integer != this.c) {
			System.arraycopy(this.b, integer + 1, this.b, integer, this.c - integer);
		}

		this.b[this.c] = null;
	}

	private T e(int integer) {
		return this.b[integer];
	}

	public T a(T object) {
		int integer3 = this.c(object);
		if (integer3 >= 0) {
			return this.e(integer3);
		} else {
			this.a(object, b(integer3));
			return object;
		}
	}

	public boolean remove(Object object) {
		int integer3 = this.c((T)object);
		if (integer3 >= 0) {
			this.d(integer3);
			return true;
		} else {
			return false;
		}
	}

	public T b() {
		return this.e(0);
	}

	public boolean contains(Object object) {
		int integer3 = this.c((T)object);
		return integer3 >= 0;
	}

	public Iterator<T> iterator() {
		return new aeg.a();
	}

	public int size() {
		return this.c;
	}

	public Object[] toArray() {
		return (Object[])this.b.clone();
	}

	public <U> U[] toArray(U[] arr) {
		if (arr.length < this.c) {
			return (U[])Arrays.copyOf(this.b, this.c, arr.getClass());
		} else {
			System.arraycopy(this.b, 0, arr, 0, this.c);
			if (arr.length > this.c) {
				arr[this.c] = null;
			}

			return arr;
		}
	}

	public void clear() {
		Arrays.fill(this.b, 0, this.c, null);
		this.c = 0;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else {
			if (object instanceof aeg) {
				aeg<?> aeg3 = (aeg<?>)object;
				if (this.a.equals(aeg3.a)) {
					return this.c == aeg3.c && Arrays.equals(this.b, aeg3.b);
				}
			}

			return super.equals(object);
		}
	}

	class a implements Iterator<T> {
		private int b;
		private int c = -1;

		private a() {
		}

		public boolean hasNext() {
			return this.b < aeg.this.c;
		}

		public T next() {
			if (this.b >= aeg.this.c) {
				throw new NoSuchElementException();
			} else {
				this.c = this.b++;
				return aeg.this.b[this.c];
			}
		}

		public void remove() {
			if (this.c == -1) {
				throw new IllegalStateException();
			} else {
				aeg.this.d(this.c);
				this.b--;
				this.c = -1;
			}
		}
	}
}

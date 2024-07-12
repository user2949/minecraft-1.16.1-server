import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;

public class adm<K> implements gd<K> {
	private static final Object a = null;
	private K[] b;
	private int[] c;
	private K[] d;
	private int e;
	private int f;

	public adm(int integer) {
		integer = (int)((float)integer / 0.8F);
		this.b = (K[])(new Object[integer]);
		this.c = new int[integer];
		this.d = (K[])(new Object[integer]);
	}

	public int a(@Nullable K object) {
		return this.c(this.b(object, this.d(object)));
	}

	@Nullable
	@Override
	public K a(int integer) {
		return integer >= 0 && integer < this.d.length ? this.d[integer] : null;
	}

	private int c(int integer) {
		return integer == -1 ? -1 : this.c[integer];
	}

	public boolean b(int integer) {
		return this.a(integer) != null;
	}

	public int c(K object) {
		int integer3 = this.c();
		this.a(object, integer3);
		return integer3;
	}

	private int c() {
		while (this.e < this.d.length && this.d[this.e] != null) {
			this.e++;
		}

		return this.e;
	}

	private void d(int integer) {
		K[] arr3 = this.b;
		int[] arr4 = this.c;
		this.b = (K[])(new Object[integer]);
		this.c = new int[integer];
		this.d = (K[])(new Object[integer]);
		this.e = 0;
		this.f = 0;

		for (int integer5 = 0; integer5 < arr3.length; integer5++) {
			if (arr3[integer5] != null) {
				this.a(arr3[integer5], arr4[integer5]);
			}
		}
	}

	public void a(K object, int integer) {
		int integer4 = Math.max(integer, this.f + 1);
		if ((float)integer4 >= (float)this.b.length * 0.8F) {
			int integer5 = this.b.length << 1;

			while (integer5 < integer) {
				integer5 <<= 1;
			}

			this.d(integer5);
		}

		int integer5 = this.e(this.d(object));
		this.b[integer5] = object;
		this.c[integer5] = integer;
		this.d[integer] = object;
		this.f++;
		if (integer == this.e) {
			this.e++;
		}
	}

	private int d(@Nullable K object) {
		return (aec.g(System.identityHashCode(object)) & 2147483647) % this.b.length;
	}

	private int b(@Nullable K object, int integer) {
		for (int integer4 = integer; integer4 < this.b.length; integer4++) {
			if (this.b[integer4] == object) {
				return integer4;
			}

			if (this.b[integer4] == a) {
				return -1;
			}
		}

		for (int integer4 = 0; integer4 < integer; integer4++) {
			if (this.b[integer4] == object) {
				return integer4;
			}

			if (this.b[integer4] == a) {
				return -1;
			}
		}

		return -1;
	}

	private int e(int integer) {
		for (int integer3 = integer; integer3 < this.b.length; integer3++) {
			if (this.b[integer3] == a) {
				return integer3;
			}
		}

		for (int integer3x = 0; integer3x < integer; integer3x++) {
			if (this.b[integer3x] == a) {
				return integer3x;
			}
		}

		throw new RuntimeException("Overflowed :(");
	}

	public Iterator<K> iterator() {
		return Iterators.filter(Iterators.forArray(this.d), Predicates.notNull());
	}

	public void a() {
		Arrays.fill(this.b, null);
		Arrays.fill(this.d, null);
		this.e = 0;
		this.f = 0;
	}

	public int b() {
		return this.f;
	}
}

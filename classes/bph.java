import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public class bph {
	public static final long a = a(1875016, 1875016);
	public final int b;
	public final int c;

	public bph(int integer1, int integer2) {
		this.b = integer1;
		this.c = integer2;
	}

	public bph(fu fu) {
		this.b = fu.u() >> 4;
		this.c = fu.w() >> 4;
	}

	public bph(long long1) {
		this.b = (int)long1;
		this.c = (int)(long1 >> 32);
	}

	public long a() {
		return a(this.b, this.c);
	}

	public static long a(int integer1, int integer2) {
		return (long)integer1 & 4294967295L | ((long)integer2 & 4294967295L) << 32;
	}

	public static int a(long long1) {
		return (int)(long1 & 4294967295L);
	}

	public static int b(long long1) {
		return (int)(long1 >>> 32 & 4294967295L);
	}

	public int hashCode() {
		int integer2 = 1664525 * this.b + 1013904223;
		int integer3 = 1664525 * (this.c ^ -559038737) + 1013904223;
		return integer2 ^ integer3;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof bph)) {
			return false;
		} else {
			bph bph3 = (bph)object;
			return this.b == bph3.b && this.c == bph3.c;
		}
	}

	public int d() {
		return this.b << 4;
	}

	public int e() {
		return this.c << 4;
	}

	public int f() {
		return (this.b << 4) + 15;
	}

	public int g() {
		return (this.c << 4) + 15;
	}

	public int h() {
		return this.b >> 5;
	}

	public int i() {
		return this.c >> 5;
	}

	public int j() {
		return this.b & 31;
	}

	public int k() {
		return this.c & 31;
	}

	public fu a(int integer1, int integer2, int integer3) {
		return new fu((this.b << 4) + integer1, integer2, (this.c << 4) + integer3);
	}

	public String toString() {
		return "[" + this.b + ", " + this.c + "]";
	}

	public fu l() {
		return new fu(this.d(), 0, this.e());
	}

	public int a(bph bph) {
		return Math.max(Math.abs(this.b - bph.b), Math.abs(this.c - bph.c));
	}

	public static Stream<bph> a(bph bph, int integer) {
		return a(new bph(bph.b - integer, bph.c - integer), new bph(bph.b + integer, bph.c + integer));
	}

	public static Stream<bph> a(bph bph1, bph bph2) {
		int integer3 = Math.abs(bph1.b - bph2.b) + 1;
		int integer4 = Math.abs(bph1.c - bph2.c) + 1;
		final int integer5 = bph1.b < bph2.b ? 1 : -1;
		final int integer6 = bph1.c < bph2.c ? 1 : -1;
		return StreamSupport.stream(new AbstractSpliterator<bph>((long)(integer3 * integer4), 64) {
			@Nullable
			private bph e;

			public boolean tryAdvance(Consumer<? super bph> consumer) {
				if (this.e == null) {
					this.e = bph1;
				} else {
					int integer3 = this.e.b;
					int integer4 = this.e.c;
					if (integer3 == bph2.b) {
						if (integer4 == bph2.c) {
							return false;
						}

						this.e = new bph(bph1.b, integer4 + integer6);
					} else {
						this.e = new bph(integer3 + integer5, integer4);
					}
				}

				consumer.accept(this.e);
				return true;
			}
		}, false);
	}
}

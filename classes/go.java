import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class go extends gr {
	private go(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	public static go a(int integer1, int integer2, int integer3) {
		return new go(integer1, integer2, integer3);
	}

	public static go a(fu fu) {
		return new go(a(fu.u()), a(fu.v()), a(fu.w()));
	}

	public static go a(bph bph, int integer) {
		return new go(bph.b, integer, bph.c);
	}

	public static go a(aom aom) {
		return new go(a(aec.c(aom.cC())), a(aec.c(aom.cD())), a(aec.c(aom.cG())));
	}

	public static go a(long long1) {
		return new go(b(long1), c(long1), d(long1));
	}

	public static long a(long long1, fz fz) {
		return a(long1, fz.i(), fz.j(), fz.k());
	}

	public static long a(long long1, int integer2, int integer3, int integer4) {
		return b(b(long1) + integer2, c(long1) + integer3, d(long1) + integer4);
	}

	public static int a(int integer) {
		return integer >> 4;
	}

	public static int b(int integer) {
		return integer & 15;
	}

	public static short b(fu fu) {
		int integer2 = b(fu.u());
		int integer3 = b(fu.v());
		int integer4 = b(fu.w());
		return (short)(integer2 << 8 | integer4 << 4 | integer3);
	}

	public static int c(int integer) {
		return integer << 4;
	}

	public static int b(long long1) {
		return (int)(long1 << 0 >> 42);
	}

	public static int c(long long1) {
		return (int)(long1 << 44 >> 44);
	}

	public static int d(long long1) {
		return (int)(long1 << 22 >> 42);
	}

	public int a() {
		return this.u();
	}

	public int b() {
		return this.v();
	}

	public int c() {
		return this.w();
	}

	public int d() {
		return this.a() << 4;
	}

	public int e() {
		return this.b() << 4;
	}

	public int f() {
		return this.c() << 4;
	}

	public int g() {
		return (this.a() << 4) + 15;
	}

	public int h() {
		return (this.b() << 4) + 15;
	}

	public int i() {
		return (this.c() << 4) + 15;
	}

	public static long e(long long1) {
		return b(a(fu.b(long1)), a(fu.c(long1)), a(fu.d(long1)));
	}

	public static long f(long long1) {
		return long1 & -1048576L;
	}

	public fu p() {
		return new fu(c(this.a()), c(this.b()), c(this.c()));
	}

	public fu q() {
		int integer2 = 8;
		return this.p().b(8, 8, 8);
	}

	public bph r() {
		return new bph(this.a(), this.c());
	}

	public static long b(int integer1, int integer2, int integer3) {
		long long4 = 0L;
		long4 |= ((long)integer1 & 4194303L) << 42;
		long4 |= ((long)integer2 & 1048575L) << 0;
		return long4 | ((long)integer3 & 4194303L) << 20;
	}

	public long s() {
		return b(this.a(), this.b(), this.c());
	}

	public Stream<fu> t() {
		return fu.a(this.d(), this.e(), this.f(), this.g(), this.h(), this.i());
	}

	public static Stream<go> a(go go, int integer) {
		int integer3 = go.a();
		int integer4 = go.b();
		int integer5 = go.c();
		return a(integer3 - integer, integer4 - integer, integer5 - integer, integer3 + integer, integer4 + integer, integer5 + integer);
	}

	public static Stream<go> b(bph bph, int integer) {
		int integer3 = bph.b;
		int integer4 = bph.c;
		return a(integer3 - integer, 0, integer4 - integer, integer3 + integer, 15, integer4 + integer);
	}

	public static Stream<go> a(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		return StreamSupport.stream(new AbstractSpliterator<go>((long)((integer4 - integer1 + 1) * (integer5 - integer2 + 1) * (integer6 - integer3 + 1)), 64) {
			final fx a = new fx(integer1, integer2, integer3, integer4, integer5, integer6);

			public boolean tryAdvance(Consumer<? super go> consumer) {
				if (this.a.a()) {
					consumer.accept(new go(this.a.b(), this.a.c(), this.a.d()));
					return true;
				} else {
					return false;
				}
			}
		}, false);
	}
}

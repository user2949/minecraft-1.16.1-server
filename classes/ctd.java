import com.google.common.base.MoreObjects;

public class ctd {
	public int a;
	public int b;
	public int c;
	public int d;
	public int e;
	public int f;

	public ctd() {
	}

	public ctd(int[] arr) {
		if (arr.length == 6) {
			this.a = arr[0];
			this.b = arr[1];
			this.c = arr[2];
			this.d = arr[3];
			this.e = arr[4];
			this.f = arr[5];
		}
	}

	public static ctd a() {
		return new ctd(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	public static ctd b() {
		return new ctd(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public static ctd a(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, int integer9, fz fz) {
		switch (fz) {
			case NORTH:
				return new ctd(
					integer1 + integer4,
					integer2 + integer5,
					integer3 - integer9 + 1 + integer6,
					integer1 + integer7 - 1 + integer4,
					integer2 + integer8 - 1 + integer5,
					integer3 + integer6
				);
			case SOUTH:
				return new ctd(
					integer1 + integer4,
					integer2 + integer5,
					integer3 + integer6,
					integer1 + integer7 - 1 + integer4,
					integer2 + integer8 - 1 + integer5,
					integer3 + integer9 - 1 + integer6
				);
			case WEST:
				return new ctd(
					integer1 - integer9 + 1 + integer6,
					integer2 + integer5,
					integer3 + integer4,
					integer1 + integer6,
					integer2 + integer8 - 1 + integer5,
					integer3 + integer7 - 1 + integer4
				);
			case EAST:
				return new ctd(
					integer1 + integer6,
					integer2 + integer5,
					integer3 + integer4,
					integer1 + integer9 - 1 + integer6,
					integer2 + integer8 - 1 + integer5,
					integer3 + integer7 - 1 + integer4
				);
			default:
				return new ctd(
					integer1 + integer4,
					integer2 + integer5,
					integer3 + integer6,
					integer1 + integer7 - 1 + integer4,
					integer2 + integer8 - 1 + integer5,
					integer3 + integer9 - 1 + integer6
				);
		}
	}

	public static ctd a(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		return new ctd(
			Math.min(integer1, integer4),
			Math.min(integer2, integer5),
			Math.min(integer3, integer6),
			Math.max(integer1, integer4),
			Math.max(integer2, integer5),
			Math.max(integer3, integer6)
		);
	}

	public ctd(ctd ctd) {
		this.a = ctd.a;
		this.b = ctd.b;
		this.c = ctd.c;
		this.d = ctd.d;
		this.e = ctd.e;
		this.f = ctd.f;
	}

	public ctd(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		this.a = integer1;
		this.b = integer2;
		this.c = integer3;
		this.d = integer4;
		this.e = integer5;
		this.f = integer6;
	}

	public ctd(gr gr1, gr gr2) {
		this.a = Math.min(gr1.u(), gr2.u());
		this.b = Math.min(gr1.v(), gr2.v());
		this.c = Math.min(gr1.w(), gr2.w());
		this.d = Math.max(gr1.u(), gr2.u());
		this.e = Math.max(gr1.v(), gr2.v());
		this.f = Math.max(gr1.w(), gr2.w());
	}

	public ctd(int integer1, int integer2, int integer3, int integer4) {
		this.a = integer1;
		this.c = integer2;
		this.d = integer3;
		this.f = integer4;
		this.b = 1;
		this.e = 512;
	}

	public boolean b(ctd ctd) {
		return this.d >= ctd.a && this.a <= ctd.d && this.f >= ctd.c && this.c <= ctd.f && this.e >= ctd.b && this.b <= ctd.e;
	}

	public boolean a(int integer1, int integer2, int integer3, int integer4) {
		return this.d >= integer1 && this.a <= integer3 && this.f >= integer2 && this.c <= integer4;
	}

	public void c(ctd ctd) {
		this.a = Math.min(this.a, ctd.a);
		this.b = Math.min(this.b, ctd.b);
		this.c = Math.min(this.c, ctd.c);
		this.d = Math.max(this.d, ctd.d);
		this.e = Math.max(this.e, ctd.e);
		this.f = Math.max(this.f, ctd.f);
	}

	public void a(int integer1, int integer2, int integer3) {
		this.a += integer1;
		this.b += integer2;
		this.c += integer3;
		this.d += integer1;
		this.e += integer2;
		this.f += integer3;
	}

	public ctd b(int integer1, int integer2, int integer3) {
		return new ctd(this.a + integer1, this.b + integer2, this.c + integer3, this.d + integer1, this.e + integer2, this.f + integer3);
	}

	public void a(gr gr) {
		this.a(gr.u(), gr.v(), gr.w());
	}

	public boolean b(gr gr) {
		return gr.u() >= this.a && gr.u() <= this.d && gr.w() >= this.c && gr.w() <= this.f && gr.v() >= this.b && gr.v() <= this.e;
	}

	public gr c() {
		return new gr(this.d - this.a, this.e - this.b, this.f - this.c);
	}

	public int d() {
		return this.d - this.a + 1;
	}

	public int e() {
		return this.e - this.b + 1;
	}

	public int f() {
		return this.f - this.c + 1;
	}

	public gr g() {
		return new fu(this.a + (this.d - this.a + 1) / 2, this.b + (this.e - this.b + 1) / 2, this.c + (this.f - this.c + 1) / 2);
	}

	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("x0", this.a)
			.add("y0", this.b)
			.add("z0", this.c)
			.add("x1", this.d)
			.add("y1", this.e)
			.add("z1", this.f)
			.toString();
	}

	public li h() {
		return new li(new int[]{this.a, this.b, this.c, this.d, this.e, this.f});
	}
}

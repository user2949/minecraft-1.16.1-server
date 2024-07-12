public final class dff extends dev {
	private final dev d;
	private final int e;
	private final int f;
	private final int g;
	private final int h;
	private final int i;
	private final int j;

	protected dff(dev dev, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7) {
		super(integer5 - integer2, integer6 - integer3, integer7 - integer4);
		this.d = dev;
		this.e = integer2;
		this.f = integer3;
		this.g = integer4;
		this.h = integer5;
		this.i = integer6;
		this.j = integer7;
	}

	@Override
	public boolean b(int integer1, int integer2, int integer3) {
		return this.d.b(this.e + integer1, this.f + integer2, this.g + integer3);
	}

	@Override
	public void a(int integer1, int integer2, int integer3, boolean boolean4, boolean boolean5) {
		this.d.a(this.e + integer1, this.f + integer2, this.g + integer3, boolean4, boolean5);
	}

	@Override
	public int a(fz.a a) {
		return Math.max(0, this.d.a(a) - a.a(this.e, this.f, this.g));
	}

	@Override
	public int b(fz.a a) {
		return Math.min(a.a(this.h, this.i, this.j), this.d.b(a) - a.a(this.e, this.f, this.g));
	}
}

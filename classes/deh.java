public class deh extends dej {
	private final fz b;
	private final fu c;
	private final boolean d;
	private final boolean e;

	public static deh a(dem dem, fz fz, fu fu) {
		return new deh(true, dem, fz, fu, false);
	}

	public deh(dem dem, fz fz, fu fu, boolean boolean4) {
		this(false, dem, fz, fu, boolean4);
	}

	private deh(boolean boolean1, dem dem, fz fz, fu fu, boolean boolean5) {
		super(dem);
		this.d = boolean1;
		this.b = fz;
		this.c = fu;
		this.e = boolean5;
	}

	public deh a(fz fz) {
		return new deh(this.d, this.a, fz, this.c, this.e);
	}

	public deh a(fu fu) {
		return new deh(this.d, this.a, this.b, fu, this.e);
	}

	public fu a() {
		return this.c;
	}

	public fz b() {
		return this.b;
	}

	@Override
	public dej.a c() {
		return this.d ? dej.a.MISS : dej.a.BLOCK;
	}

	public boolean d() {
		return this.e;
	}
}

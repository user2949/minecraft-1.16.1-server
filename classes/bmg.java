public abstract class bmg implements bmu<amz> {
	protected final bmx<?> a;
	protected final uh b;
	protected final String c;
	protected final bmr d;
	protected final bki e;
	protected final float f;
	protected final int g;

	public bmg(bmx<?> bmx, uh uh, String string, bmr bmr, bki bki, float float6, int integer) {
		this.a = bmx;
		this.b = uh;
		this.c = string;
		this.d = bmr;
		this.e = bki;
		this.f = float6;
		this.g = integer;
	}

	@Override
	public boolean a(amz amz, bqb bqb) {
		return this.d.a(amz.a(0));
	}

	@Override
	public bki a(amz amz) {
		return this.e.i();
	}

	@Override
	public gi<bmr> a() {
		gi<bmr> gi2 = gi.a();
		gi2.add(this.d);
		return gi2;
	}

	public float b() {
		return this.f;
	}

	@Override
	public bki c() {
		return this.e;
	}

	public int e() {
		return this.g;
	}

	@Override
	public uh f() {
		return this.b;
	}

	@Override
	public bmx<?> g() {
		return this.a;
	}
}

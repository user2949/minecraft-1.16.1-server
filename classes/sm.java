import java.io.IOException;

public class sm implements ni<qz> {
	private fu a;
	private cel.a b;
	private cgq c;
	private String d;
	private fu e;
	private fu f;
	private bzj g;
	private cap h;
	private String i;
	private boolean j;
	private boolean k;
	private boolean l;
	private float m;
	private long n;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
		this.b = mg.a(cel.a.class);
		this.c = mg.a(cgq.class);
		this.d = mg.e(32767);
		this.e = new fu(aec.a(mg.readByte(), -32, 32), aec.a(mg.readByte(), -32, 32), aec.a(mg.readByte(), -32, 32));
		this.f = new fu(aec.a(mg.readByte(), 0, 32), aec.a(mg.readByte(), 0, 32), aec.a(mg.readByte(), 0, 32));
		this.g = mg.a(bzj.class);
		this.h = mg.a(cap.class);
		this.i = mg.e(12);
		this.m = aec.a(mg.readFloat(), 0.0F, 1.0F);
		this.n = mg.j();
		int integer3 = mg.readByte();
		this.j = (integer3 & 1) != 0;
		this.k = (integer3 & 2) != 0;
		this.l = (integer3 & 4) != 0;
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b);
		mg.a(this.c);
		mg.a(this.d);
		mg.writeByte(this.e.u());
		mg.writeByte(this.e.v());
		mg.writeByte(this.e.w());
		mg.writeByte(this.f.u());
		mg.writeByte(this.f.v());
		mg.writeByte(this.f.w());
		mg.a(this.g);
		mg.a(this.h);
		mg.a(this.i);
		mg.writeFloat(this.m);
		mg.b(this.n);
		int integer3 = 0;
		if (this.j) {
			integer3 |= 1;
		}

		if (this.k) {
			integer3 |= 2;
		}

		if (this.l) {
			integer3 |= 4;
		}

		mg.writeByte(integer3);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public fu b() {
		return this.a;
	}

	public cel.a c() {
		return this.b;
	}

	public cgq d() {
		return this.c;
	}

	public String e() {
		return this.d;
	}

	public fu f() {
		return this.e;
	}

	public fu g() {
		return this.f;
	}

	public bzj h() {
		return this.g;
	}

	public cap i() {
		return this.h;
	}

	public String j() {
		return this.i;
	}

	public boolean k() {
		return this.j;
	}

	public boolean l() {
		return this.k;
	}

	public boolean m() {
		return this.l;
	}

	public float n() {
		return this.m;
	}

	public long o() {
		return this.n;
	}
}

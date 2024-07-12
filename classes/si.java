import java.io.IOException;

public class si implements ni<qz> {
	private fu a;
	private String b;
	private boolean c;
	private boolean d;
	private boolean e;
	private cdq.a f;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
		this.b = mg.e(32767);
		this.f = mg.a(cdq.a.class);
		int integer3 = mg.readByte();
		this.c = (integer3 & 1) != 0;
		this.d = (integer3 & 2) != 0;
		this.e = (integer3 & 4) != 0;
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b);
		mg.a(this.f);
		int integer3 = 0;
		if (this.c) {
			integer3 |= 1;
		}

		if (this.d) {
			integer3 |= 2;
		}

		if (this.e) {
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

	public String c() {
		return this.b;
	}

	public boolean d() {
		return this.c;
	}

	public boolean e() {
		return this.d;
	}

	public boolean f() {
		return this.e;
	}

	public cdq.a g() {
		return this.f;
	}
}

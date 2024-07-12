import javax.annotation.Nullable;

public class blv {
	protected final bec b;
	protected final anf c;
	protected final deh d;
	protected final bqb e;
	protected final bki f;

	public blv(bec bec, anf anf, deh deh) {
		this(bec.l, bec, anf, bec.b(anf), deh);
	}

	protected blv(bqb bqb, @Nullable bec bec, anf anf, bki bki, deh deh) {
		this.b = bec;
		this.c = anf;
		this.d = deh;
		this.f = bki;
		this.e = bqb;
	}

	public fu a() {
		return this.d.a();
	}

	public fz i() {
		return this.d.b();
	}

	public dem j() {
		return this.d.e();
	}

	public boolean k() {
		return this.d.d();
	}

	public bki l() {
		return this.f;
	}

	@Nullable
	public bec m() {
		return this.b;
	}

	public anf n() {
		return this.c;
	}

	public bqb o() {
		return this.e;
	}

	public fz f() {
		return this.b == null ? fz.NORTH : this.b.bY();
	}

	public boolean g() {
		return this.b != null && this.b.ep();
	}

	public float h() {
		return this.b == null ? 0.0F : this.b.p;
	}
}

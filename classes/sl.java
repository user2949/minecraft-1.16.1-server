import java.io.IOException;

public class sl implements ni<qz> {
	private fu a;
	private uh b;
	private uh c;
	private uh d;
	private String e;
	private ceb.a f;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
		this.b = mg.o();
		this.c = mg.o();
		this.d = mg.o();
		this.e = mg.e(32767);
		this.f = (ceb.a)ceb.a.a(mg.e(32767)).orElse(ceb.a.ALIGNED);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b);
		mg.a(this.c);
		mg.a(this.d);
		mg.a(this.e);
		mg.a(this.f.a());
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public fu b() {
		return this.a;
	}

	public uh c() {
		return this.b;
	}

	public uh d() {
		return this.c;
	}

	public uh e() {
		return this.d;
	}

	public String f() {
		return this.e;
	}

	public ceb.a g() {
		return this.f;
	}
}

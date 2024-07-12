import java.io.IOException;

public class rg implements ni<qz> {
	private int a;
	private String b;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.e(32500);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b, 32500);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public int b() {
		return this.a;
	}

	public String c() {
		return this.b;
	}
}

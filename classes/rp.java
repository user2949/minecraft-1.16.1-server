import java.io.IOException;

public class rp implements ni<qz> {
	private fu a;
	private int b;
	private boolean c;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
		this.b = mg.i();
		this.c = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.d(this.b);
		mg.writeBoolean(this.c);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public fu b() {
		return this.a;
	}

	public int c() {
		return this.b;
	}

	public boolean d() {
		return this.c;
	}
}

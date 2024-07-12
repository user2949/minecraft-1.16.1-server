import java.io.IOException;

public class rb implements ni<qz> {
	private int a;
	private fu b;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.e();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public int b() {
		return this.a;
	}

	public fu c() {
		return this.b;
	}
}

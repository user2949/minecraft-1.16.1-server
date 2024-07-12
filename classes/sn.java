import java.io.IOException;

public class sn implements ni<qz> {
	private fu a;
	private String[] b;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
		this.b = new String[4];

		for (int integer3 = 0; integer3 < 4; integer3++) {
			this.b[integer3] = mg.e(384);
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);

		for (int integer3 = 0; integer3 < 4; integer3++) {
			mg.a(this.b[integer3]);
		}
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public fu b() {
		return this.a;
	}

	public String[] c() {
		return this.b;
	}
}

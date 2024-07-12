import java.io.IOException;

public class rq implements ni<qz> {
	private long a;

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readLong();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeLong(this.a);
	}

	public long b() {
		return this.a;
	}
}

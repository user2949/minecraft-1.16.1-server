import java.io.IOException;

public class tn implements ni<tm> {
	private long a;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readLong();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeLong(this.a);
	}

	public void a(tm tm) {
		tm.a(this);
	}

	public long b() {
		return this.a;
	}
}

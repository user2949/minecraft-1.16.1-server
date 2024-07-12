import java.io.IOException;

public class tj implements ni<ti> {
	private long a;

	public tj() {
	}

	public tj(long long1) {
		this.a = long1;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readLong();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeLong(this.a);
	}

	public void a(ti ti) {
		ti.a(this);
	}
}

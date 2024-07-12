import java.io.IOException;

public class os implements ni<nl> {
	private long a;

	public os() {
	}

	public os(long long1) {
		this.a = long1;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readLong();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeLong(this.a);
	}
}

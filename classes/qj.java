import java.io.IOException;

public class qj implements ni<nl> {
	private long a;
	private long b;

	public qj() {
	}

	public qj(long long1, long long2, boolean boolean3) {
		this.a = long1;
		this.b = long2;
		if (!boolean3) {
			this.b = -this.b;
			if (this.b == 0L) {
				this.b = -1L;
			}
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readLong();
		this.b = mg.readLong();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeLong(this.a);
		mg.writeLong(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

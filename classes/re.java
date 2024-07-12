import java.io.IOException;

public class re implements ni<qz> {
	private re.a a;

	public re() {
	}

	public re(re.a a) {
		this.a = a;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(re.a.class);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public re.a b() {
		return this.a;
	}

	public static enum a {
		PERFORM_RESPAWN,
		REQUEST_STATS;
	}
}

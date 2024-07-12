import java.io.IOException;

public class se implements ni<qz> {
	private se.a a;
	private uh b;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(se.a.class);
		if (this.a == se.a.OPENED_TAB) {
			this.b = mg.o();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		if (this.a == se.a.OPENED_TAB) {
			mg.a(this.b);
		}
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public se.a c() {
		return this.a;
	}

	public uh d() {
		return this.b;
	}

	public static enum a {
		OPENED_TAB,
		CLOSED_SCREEN;
	}
}

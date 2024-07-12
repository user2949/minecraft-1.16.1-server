import java.io.IOException;

public class sd implements ni<qz> {
	private sd.a a;

	public sd() {
	}

	public sd(sd.a a) {
		this.a = a;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.a(sd.a.class);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public static enum a {
		SUCCESSFULLY_LOADED,
		DECLINED,
		FAILED_DOWNLOAD,
		ACCEPTED;
	}
}

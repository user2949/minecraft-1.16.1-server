import java.io.IOException;

public class rd implements ni<qz> {
	private String a;

	public rd() {
	}

	public rd(String string) {
		if (string.length() > 256) {
			string = string.substring(0, 256);
		}

		this.a = string;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e(256);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public String b() {
		return this.a;
	}
}

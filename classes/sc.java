import java.io.IOException;

public class sc implements ni<qz> {
	private String a;

	public sc() {
	}

	public sc(String string) {
		this.a = string;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e(32767);
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

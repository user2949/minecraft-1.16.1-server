import java.io.IOException;

public class po implements ni<nl> {
	private String a;
	private String b;

	public po() {
	}

	public po(String string1, String string2) {
		this.a = string1;
		this.b = string2;
		if (string2.length() > 40) {
			throw new IllegalArgumentException("Hash is too long (max 40, was " + string2.length() + ")");
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e(32767);
		this.b = mg.e(40);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

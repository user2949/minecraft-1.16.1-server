import java.io.IOException;

public class sk implements ni<qz> {
	private int a;
	private bki b;

	public sk() {
		this.b = bki.b;
	}

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readShort();
		this.b = mg.m();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeShort(this.a);
		mg.a(this.b);
	}

	public int b() {
		return this.a;
	}

	public bki c() {
		return this.b;
	}
}

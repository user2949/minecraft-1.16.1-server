import java.io.IOException;

public class ri implements ni<qz> {
	private int a;
	private int b;

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
		this.b = mg.readByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.writeByte(this.b);
	}

	public int b() {
		return this.a;
	}

	public int c() {
		return this.b;
	}
}

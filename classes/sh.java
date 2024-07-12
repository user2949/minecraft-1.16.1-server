import java.io.IOException;

public class sh implements ni<qz> {
	private int a;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readShort();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeShort(this.a);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public int b() {
		return this.a;
	}
}

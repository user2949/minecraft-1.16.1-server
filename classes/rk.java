import java.io.IOException;

public class rk implements ni<qz> {
	private int a;

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
	}
}

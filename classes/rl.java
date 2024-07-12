import java.io.IOException;

public class rl implements ni<qz> {
	public static final uh a = new uh("brand");
	private uh b;
	private mg c;

	@Override
	public void a(mg mg) throws IOException {
		this.b = mg.o();
		int integer3 = mg.readableBytes();
		if (integer3 >= 0 && integer3 <= 32767) {
			this.c = new mg(mg.readBytes(integer3));
		} else {
			throw new IOException("Payload may not be larger than 32767 bytes");
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.b);
		mg.writeBytes(this.c);
	}

	public void a(qz qz) {
		qz.a(this);
		if (this.c != null) {
			this.c.release();
		}
	}
}

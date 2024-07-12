import java.io.IOException;

public class sx implements ni<sw> {
	private int a;
	private uh b;
	private mg c;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.o();
		int integer3 = mg.readableBytes();
		if (integer3 >= 0 && integer3 <= 1048576) {
			this.c = new mg(mg.readBytes(integer3));
		} else {
			throw new IOException("Payload may not be larger than 1048576 bytes");
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.writeBytes(this.c.copy());
	}

	public void a(sw sw) {
		sw.a(this);
	}
}

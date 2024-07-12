import java.io.IOException;

public class td implements ni<tc> {
	private int a;
	private mg b;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		if (mg.readBoolean()) {
			int integer3 = mg.readableBytes();
			if (integer3 < 0 || integer3 > 1048576) {
				throw new IOException("Payload may not be larger than 1048576 bytes");
			}

			this.b = new mg(mg.readBytes(integer3));
		} else {
			this.b = null;
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		if (this.b != null) {
			mg.writeBoolean(true);
			mg.writeBytes(this.b.copy());
		} else {
			mg.writeBoolean(false);
		}
	}

	public void a(tc tc) {
		tc.a(this);
	}
}

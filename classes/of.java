import java.io.IOException;

public class of implements ni<nl> {
	private int a;

	public of() {
	}

	public of(int integer) {
		this.a = integer;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readUnsignedByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
	}
}

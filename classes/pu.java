import java.io.IOException;

public class pu implements ni<nl> {
	private int a;

	public pu() {
	}

	public pu(int integer) {
		this.a = integer;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

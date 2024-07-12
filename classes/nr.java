import java.io.IOException;

public class nr implements ni<nl> {
	private int a;
	private int b;

	public nr() {
	}

	public nr(aom aom, int integer) {
		this.a = aom.V();
		this.b = integer;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.readUnsignedByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeByte(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

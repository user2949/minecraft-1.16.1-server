import java.io.IOException;

public class nz implements ni<nl> {
	private and a;
	private boolean b;

	public nz() {
	}

	public nz(and and, boolean boolean2) {
		this.a = and;
		this.b = boolean2;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = and.a(mg.readUnsignedByte());
		this.b = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a.a());
		mg.writeBoolean(this.b);
	}
}

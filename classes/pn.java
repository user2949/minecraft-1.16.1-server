import java.io.IOException;

public class pn implements ni<nl> {
	private int a;
	private aoe b;

	public pn() {
	}

	public pn(int integer, aoe aoe) {
		this.a = integer;
		this.b = aoe;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = aoe.a(mg.readUnsignedByte());
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeByte(aoe.a(this.b));
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

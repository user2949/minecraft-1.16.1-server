import java.io.IOException;

public class oi implements ni<nl> {
	private int a;
	private int b;
	private bki c = bki.b;

	public oi() {
	}

	public oi(int integer1, int integer2, bki bki) {
		this.a = integer1;
		this.b = integer2;
		this.c = bki.i();
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
		this.b = mg.readShort();
		this.c = mg.m();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.writeShort(this.b);
		mg.a(this.c);
	}
}

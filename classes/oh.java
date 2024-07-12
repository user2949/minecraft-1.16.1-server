import java.io.IOException;

public class oh implements ni<nl> {
	private int a;
	private int b;
	private int c;

	public oh() {
	}

	public oh(int integer1, int integer2, int integer3) {
		this.a = integer1;
		this.b = integer2;
		this.c = integer3;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readUnsignedByte();
		this.b = mg.readShort();
		this.c = mg.readShort();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.writeShort(this.b);
		mg.writeShort(this.c);
	}
}

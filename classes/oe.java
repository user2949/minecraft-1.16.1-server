import java.io.IOException;

public class oe implements ni<nl> {
	private int a;
	private short b;
	private boolean c;

	public oe() {
	}

	public oe(int integer, short short2, boolean boolean3) {
		this.a = integer;
		this.b = short2;
		this.c = boolean3;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readUnsignedByte();
		this.b = mg.readShort();
		this.c = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.writeShort(this.b);
		mg.writeBoolean(this.c);
	}
}

import java.io.IOException;

public class on implements ni<nl> {
	private int a;
	private byte b;

	public on() {
	}

	public on(aom aom, byte byte2) {
		this.a = aom.V();
		this.b = byte2;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readInt();
		this.b = mg.readByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeInt(this.a);
		mg.writeByte(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

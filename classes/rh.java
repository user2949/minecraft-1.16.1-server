import java.io.IOException;

public class rh implements ni<qz> {
	private int a;
	private short b;
	private boolean c;

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
		this.b = mg.readShort();
		this.c = mg.readByte() != 0;
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.writeShort(this.b);
		mg.writeByte(this.c ? 1 : 0);
	}

	public int b() {
		return this.a;
	}

	public short c() {
		return this.b;
	}
}

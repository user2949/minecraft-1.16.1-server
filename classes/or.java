import java.io.IOException;

public class or implements ni<nl> {
	private int a;
	private int b;
	private int c;

	public or() {
	}

	public or(int integer1, int integer2, int integer3) {
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
		this.b = mg.i();
		this.c = mg.readInt();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.d(this.b);
		mg.writeInt(this.c);
	}
}

import java.io.IOException;

public class nv implements ni<nl> {
	private fu a;
	private int b;
	private le c;

	public nv() {
	}

	public nv(fu fu, int integer, le le) {
		this.a = fu;
		this.b = integer;
		this.c = le;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
		this.b = mg.readUnsignedByte();
		this.c = mg.l();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.writeByte((byte)this.b);
		mg.a(this.c);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

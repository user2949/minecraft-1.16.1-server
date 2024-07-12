import java.io.IOException;

public class nu implements ni<nl> {
	private int a;
	private fu b;
	private int c;

	public nu() {
	}

	public nu(int integer1, fu fu, int integer3) {
		this.a = integer1;
		this.b = fu;
		this.c = integer3;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.e();
		this.c = mg.readUnsignedByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.writeByte(this.c);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

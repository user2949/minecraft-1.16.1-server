import java.io.IOException;

public class st implements ni<su> {
	private int a;
	private String b;
	private int c;
	private mf d;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.e(255);
		this.c = mg.readUnsignedShort();
		this.d = mf.a(mg.i());
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.writeShort(this.c);
		mg.d(this.d.a());
	}

	public void a(su su) {
		su.a(this);
	}

	public mf b() {
		return this.d;
	}

	public int c() {
		return this.a;
	}
}

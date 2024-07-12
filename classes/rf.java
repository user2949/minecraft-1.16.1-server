import java.io.IOException;

public class rf implements ni<qz> {
	private String a;
	private int b;
	private bea c;
	private boolean d;
	private int e;
	private aou f;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e(16);
		this.b = mg.readByte();
		this.c = mg.a(bea.class);
		this.d = mg.readBoolean();
		this.e = mg.readUnsignedByte();
		this.f = mg.a(aou.class);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.writeByte(this.b);
		mg.a(this.c);
		mg.writeBoolean(this.d);
		mg.writeByte(this.e);
		mg.a(this.f);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public bea d() {
		return this.c;
	}

	public boolean e() {
		return this.d;
	}

	public int f() {
		return this.e;
	}

	public aou g() {
		return this.f;
	}
}

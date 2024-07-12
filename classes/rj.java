import java.io.IOException;

public class rj implements ni<qz> {
	private int a;
	private int b;
	private int c;
	private short d;
	private bki e = bki.b;
	private bgq f;

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
		this.b = mg.readShort();
		this.c = mg.readByte();
		this.d = mg.readShort();
		this.f = mg.a(bgq.class);
		this.e = mg.m();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.writeShort(this.b);
		mg.writeByte(this.c);
		mg.writeShort(this.d);
		mg.a(this.f);
		mg.a(this.e);
	}

	public int b() {
		return this.a;
	}

	public int c() {
		return this.b;
	}

	public int d() {
		return this.c;
	}

	public short e() {
		return this.d;
	}

	public bki f() {
		return this.e;
	}

	public bgq g() {
		return this.f;
	}
}

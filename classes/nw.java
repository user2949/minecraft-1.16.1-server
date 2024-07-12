import java.io.IOException;

public class nw implements ni<nl> {
	private fu a;
	private int b;
	private int c;
	private bvr d;

	public nw() {
	}

	public nw(fu fu, bvr bvr, int integer3, int integer4) {
		this.a = fu;
		this.d = bvr;
		this.b = integer3;
		this.c = integer4;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
		this.b = mg.readUnsignedByte();
		this.c = mg.readUnsignedByte();
		this.d = gl.aj.a(mg.i());
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.writeByte(this.b);
		mg.writeByte(this.c);
		mg.d(gl.aj.a(this.d));
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

import java.io.IOException;
import java.util.UUID;

public class np implements ni<nl> {
	private int a;
	private UUID b;
	private fu c;
	private fz d;
	private int e;

	public np() {
	}

	public np(bbd bbd) {
		this.a = bbd.V();
		this.b = bbd.bR();
		this.c = bbd.n();
		this.d = bbd.bY();
		this.e = gl.aD.a(bbd.e);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.k();
		this.e = mg.i();
		this.c = mg.e();
		this.d = fz.b(mg.readUnsignedByte());
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.d(this.e);
		mg.a(this.c);
		mg.writeByte(this.d.d());
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

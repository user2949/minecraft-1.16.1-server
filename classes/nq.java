import java.io.IOException;
import java.util.UUID;

public class nq implements ni<nl> {
	private int a;
	private UUID b;
	private double c;
	private double d;
	private double e;
	private byte f;
	private byte g;

	public nq() {
	}

	public nq(bec bec) {
		this.a = bec.V();
		this.b = bec.ez().getId();
		this.c = bec.cC();
		this.d = bec.cD();
		this.e = bec.cG();
		this.f = (byte)((int)(bec.p * 256.0F / 360.0F));
		this.g = (byte)((int)(bec.q * 256.0F / 360.0F));
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.k();
		this.c = mg.readDouble();
		this.d = mg.readDouble();
		this.e = mg.readDouble();
		this.f = mg.readByte();
		this.g = mg.readByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.writeDouble(this.c);
		mg.writeDouble(this.d);
		mg.writeDouble(this.e);
		mg.writeByte(this.f);
		mg.writeByte(this.g);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

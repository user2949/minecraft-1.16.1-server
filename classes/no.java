import java.io.IOException;
import java.util.UUID;

public class no implements ni<nl> {
	private int a;
	private UUID b;
	private int c;
	private double d;
	private double e;
	private double f;
	private int g;
	private int h;
	private int i;
	private byte j;
	private byte k;
	private byte l;

	public no() {
	}

	public no(aoy aoy) {
		this.a = aoy.V();
		this.b = aoy.bR();
		this.c = gl.al.a(aoy.U());
		this.d = aoy.cC();
		this.e = aoy.cD();
		this.f = aoy.cG();
		this.j = (byte)((int)(aoy.p * 256.0F / 360.0F));
		this.k = (byte)((int)(aoy.q * 256.0F / 360.0F));
		this.l = (byte)((int)(aoy.aJ * 256.0F / 360.0F));
		double double3 = 3.9;
		dem dem5 = aoy.cB();
		double double6 = aec.a(dem5.b, -3.9, 3.9);
		double double8 = aec.a(dem5.c, -3.9, 3.9);
		double double10 = aec.a(dem5.d, -3.9, 3.9);
		this.g = (int)(double6 * 8000.0);
		this.h = (int)(double8 * 8000.0);
		this.i = (int)(double10 * 8000.0);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.k();
		this.c = mg.i();
		this.d = mg.readDouble();
		this.e = mg.readDouble();
		this.f = mg.readDouble();
		this.j = mg.readByte();
		this.k = mg.readByte();
		this.l = mg.readByte();
		this.g = mg.readShort();
		this.h = mg.readShort();
		this.i = mg.readShort();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.d(this.c);
		mg.writeDouble(this.d);
		mg.writeDouble(this.e);
		mg.writeDouble(this.f);
		mg.writeByte(this.j);
		mg.writeByte(this.k);
		mg.writeByte(this.l);
		mg.writeShort(this.g);
		mg.writeShort(this.h);
		mg.writeShort(this.i);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

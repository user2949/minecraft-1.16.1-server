import java.io.IOException;

public class qr implements ni<nl> {
	private int a;
	private double b;
	private double c;
	private double d;
	private byte e;
	private byte f;
	private boolean g;

	public qr() {
	}

	public qr(aom aom) {
		this.a = aom.V();
		this.b = aom.cC();
		this.c = aom.cD();
		this.d = aom.cG();
		this.e = (byte)((int)(aom.p * 256.0F / 360.0F));
		this.f = (byte)((int)(aom.q * 256.0F / 360.0F));
		this.g = aom.aj();
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.readDouble();
		this.c = mg.readDouble();
		this.d = mg.readDouble();
		this.e = mg.readByte();
		this.f = mg.readByte();
		this.g = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeDouble(this.b);
		mg.writeDouble(this.c);
		mg.writeDouble(this.d);
		mg.writeByte(this.e);
		mg.writeByte(this.f);
		mg.writeBoolean(this.g);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

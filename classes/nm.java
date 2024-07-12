import java.io.IOException;
import java.util.UUID;

public class nm implements ni<nl> {
	private int a;
	private UUID b;
	private double c;
	private double d;
	private double e;
	private int f;
	private int g;
	private int h;
	private int i;
	private int j;
	private aoq<?> k;
	private int l;

	public nm() {
	}

	public nm(int integer1, UUID uUID, double double3, double double4, double double5, float float6, float float7, aoq<?> aoq, int integer9, dem dem) {
		this.a = integer1;
		this.b = uUID;
		this.c = double3;
		this.d = double4;
		this.e = double5;
		this.i = aec.d(float6 * 256.0F / 360.0F);
		this.j = aec.d(float7 * 256.0F / 360.0F);
		this.k = aoq;
		this.l = integer9;
		this.f = (int)(aec.a(dem.b, -3.9, 3.9) * 8000.0);
		this.g = (int)(aec.a(dem.c, -3.9, 3.9) * 8000.0);
		this.h = (int)(aec.a(dem.d, -3.9, 3.9) * 8000.0);
	}

	public nm(aom aom) {
		this(aom, 0);
	}

	public nm(aom aom, int integer) {
		this(aom.V(), aom.bR(), aom.cC(), aom.cD(), aom.cG(), aom.q, aom.p, aom.U(), integer, aom.cB());
	}

	public nm(aom aom, aoq<?> aoq, int integer, fu fu) {
		this(aom.V(), aom.bR(), (double)fu.u(), (double)fu.v(), (double)fu.w(), aom.q, aom.p, aoq, integer, aom.cB());
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.k();
		this.k = gl.al.a(mg.i());
		this.c = mg.readDouble();
		this.d = mg.readDouble();
		this.e = mg.readDouble();
		this.i = mg.readByte();
		this.j = mg.readByte();
		this.l = mg.readInt();
		this.f = mg.readShort();
		this.g = mg.readShort();
		this.h = mg.readShort();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.d(gl.al.a(this.k));
		mg.writeDouble(this.c);
		mg.writeDouble(this.d);
		mg.writeDouble(this.e);
		mg.writeByte(this.i);
		mg.writeByte(this.j);
		mg.writeInt(this.l);
		mg.writeShort(this.f);
		mg.writeShort(this.g);
		mg.writeShort(this.h);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

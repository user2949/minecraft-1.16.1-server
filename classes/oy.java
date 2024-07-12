import java.io.IOException;
import java.util.Collection;

public class oy implements ni<nl> {
	private int a;
	private byte b;
	private boolean c;
	private boolean d;
	private czs[] e;
	private int f;
	private int g;
	private int h;
	private int i;
	private byte[] j;

	public oy() {
	}

	public oy(
		int integer1, byte byte2, boolean boolean3, boolean boolean4, Collection<czs> collection, byte[] arr, int integer7, int integer8, int integer9, int integer10
	) {
		this.a = integer1;
		this.b = byte2;
		this.c = boolean3;
		this.d = boolean4;
		this.e = (czs[])collection.toArray(new czs[collection.size()]);
		this.f = integer7;
		this.g = integer8;
		this.h = integer9;
		this.i = integer10;
		this.j = new byte[integer9 * integer10];

		for (int integer12 = 0; integer12 < integer9; integer12++) {
			for (int integer13 = 0; integer13 < integer10; integer13++) {
				this.j[integer12 + integer13 * integer9] = arr[integer7 + integer12 + (integer8 + integer13) * 128];
			}
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.readByte();
		this.c = mg.readBoolean();
		this.d = mg.readBoolean();
		this.e = new czs[mg.i()];

		for (int integer3 = 0; integer3 < this.e.length; integer3++) {
			czs.a a4 = mg.a(czs.a.class);
			this.e[integer3] = new czs(a4, mg.readByte(), mg.readByte(), (byte)(mg.readByte() & 15), mg.readBoolean() ? mg.h() : null);
		}

		this.h = mg.readUnsignedByte();
		if (this.h > 0) {
			this.i = mg.readUnsignedByte();
			this.f = mg.readUnsignedByte();
			this.g = mg.readUnsignedByte();
			this.j = mg.a();
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeByte(this.b);
		mg.writeBoolean(this.c);
		mg.writeBoolean(this.d);
		mg.d(this.e.length);

		for (czs czs6 : this.e) {
			mg.a(czs6.b());
			mg.writeByte(czs6.c());
			mg.writeByte(czs6.d());
			mg.writeByte(czs6.e() & 15);
			if (czs6.g() != null) {
				mg.writeBoolean(true);
				mg.a(czs6.g());
			} else {
				mg.writeBoolean(false);
			}
		}

		mg.writeByte(this.h);
		if (this.h > 0) {
			mg.writeByte(this.i);
			mg.writeByte(this.f);
			mg.writeByte(this.g);
			mg.a(this.j);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

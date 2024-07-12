import java.io.IOException;

public class pg implements ni<nl> {
	private boolean a;
	private boolean b;
	private boolean c;
	private boolean d;
	private float e;
	private float f;

	public pg() {
	}

	public pg(bdz bdz) {
		this.a = bdz.a;
		this.b = bdz.b;
		this.c = bdz.c;
		this.d = bdz.d;
		this.e = bdz.a();
		this.f = bdz.b();
	}

	@Override
	public void a(mg mg) throws IOException {
		byte byte3 = mg.readByte();
		this.a = (byte3 & 1) != 0;
		this.b = (byte3 & 2) != 0;
		this.c = (byte3 & 4) != 0;
		this.d = (byte3 & 8) != 0;
		this.e = mg.readFloat();
		this.f = mg.readFloat();
	}

	@Override
	public void b(mg mg) throws IOException {
		byte byte3 = 0;
		if (this.a) {
			byte3 = (byte)(byte3 | 1);
		}

		if (this.b) {
			byte3 = (byte)(byte3 | 2);
		}

		if (this.c) {
			byte3 = (byte)(byte3 | 4);
		}

		if (this.d) {
			byte3 = (byte)(byte3 | 8);
		}

		mg.writeByte(byte3);
		mg.writeFloat(this.e);
		mg.writeFloat(this.f);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

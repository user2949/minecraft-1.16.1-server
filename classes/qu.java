import java.io.IOException;

public class qu implements ni<nl> {
	private int a;
	private byte b;
	private byte c;
	private int d;
	private byte e;

	public qu() {
	}

	public qu(int integer, aog aog) {
		this.a = integer;
		this.b = (byte)(aoe.a(aog.a()) & 0xFF);
		this.c = (byte)(aog.c() & 0xFF);
		if (aog.b() > 32767) {
			this.d = 32767;
		} else {
			this.d = aog.b();
		}

		this.e = 0;
		if (aog.d()) {
			this.e = (byte)(this.e | 1);
		}

		if (aog.e()) {
			this.e = (byte)(this.e | 2);
		}

		if (aog.f()) {
			this.e = (byte)(this.e | 4);
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.readByte();
		this.c = mg.readByte();
		this.d = mg.i();
		this.e = mg.readByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeByte(this.b);
		mg.writeByte(this.c);
		mg.d(this.d);
		mg.writeByte(this.e);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

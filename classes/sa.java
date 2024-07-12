import java.io.IOException;

public class sa implements ni<qz> {
	private float a;
	private float b;
	private boolean c;
	private boolean d;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readFloat();
		this.b = mg.readFloat();
		byte byte3 = mg.readByte();
		this.c = (byte3 & 1) > 0;
		this.d = (byte3 & 2) > 0;
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeFloat(this.a);
		mg.writeFloat(this.b);
		byte byte3 = 0;
		if (this.c) {
			byte3 = (byte)(byte3 | 1);
		}

		if (this.d) {
			byte3 = (byte)(byte3 | 2);
		}

		mg.writeByte(byte3);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public float b() {
		return this.a;
	}

	public float c() {
		return this.b;
	}

	public boolean d() {
		return this.c;
	}

	public boolean e() {
		return this.d;
	}
}

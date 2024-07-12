import java.io.IOException;

public class rx implements ni<qz> {
	private boolean a;

	public rx() {
	}

	public rx(bdz bdz) {
		this.a = bdz.b;
	}

	@Override
	public void a(mg mg) throws IOException {
		byte byte3 = mg.readByte();
		this.a = (byte3 & 2) != 0;
	}

	@Override
	public void b(mg mg) throws IOException {
		byte byte3 = 0;
		if (this.a) {
			byte3 = (byte)(byte3 | 2);
		}

		mg.writeByte(byte3);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public boolean b() {
		return this.a;
	}
}

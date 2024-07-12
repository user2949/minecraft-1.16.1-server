import java.io.IOException;

public class pq implements ni<nl> {
	private int a;
	private byte b;

	public pq() {
	}

	public pq(aom aom, byte byte2) {
		this.a = aom.V();
		this.b = byte2;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.readByte();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.writeByte(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

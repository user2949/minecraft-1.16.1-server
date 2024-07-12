import java.io.IOException;
import java.util.List;

public class og implements ni<nl> {
	private int a;
	private List<bki> b;

	public og() {
	}

	public og(int integer, gi<bki> gi) {
		this.a = integer;
		this.b = gi.<bki>a(gi.size(), bki.b);

		for (int integer4 = 0; integer4 < this.b.size(); integer4++) {
			this.b.set(integer4, gi.get(integer4).i());
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readUnsignedByte();
		int integer3 = mg.readShort();
		this.b = gi.<bki>a(integer3, bki.b);

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			this.b.set(integer4, mg.m());
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.writeShort(this.b.size());

		for (bki bki4 : this.b) {
			mg.a(bki4);
		}
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

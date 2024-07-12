import java.io.IOException;

public class rc implements ni<qz> {
	private and a;

	public rc() {
	}

	public rc(and and) {
		this.a = and;
	}

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = and.a(mg.readUnsignedByte());
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a.a());
	}

	public and b() {
		return this.a;
	}
}

import java.io.IOException;

public class rw implements ni<qz> {
	private int a;
	private uh b;
	private boolean c;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readByte();
		this.b = mg.o();
		this.c = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeByte(this.a);
		mg.a(this.b);
		mg.writeBoolean(this.c);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public int b() {
		return this.a;
	}

	public uh c() {
		return this.b;
	}

	public boolean d() {
		return this.c;
	}
}

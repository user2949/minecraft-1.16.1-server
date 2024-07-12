import java.io.IOException;

public class rm implements ni<qz> {
	private bki a;
	private boolean b;
	private anf c;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.m();
		this.b = mg.readBoolean();
		this.c = mg.a(anf.class);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.writeBoolean(this.b);
		mg.a(this.c);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public bki b() {
		return this.a;
	}

	public boolean c() {
		return this.b;
	}

	public anf d() {
		return this.c;
	}
}

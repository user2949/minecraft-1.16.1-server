import java.io.IOException;

public class ru implements ni<qz> {
	private boolean a;
	private boolean b;

	public ru() {
	}

	public ru(boolean boolean1, boolean boolean2) {
		this.a = boolean1;
		this.b = boolean2;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readBoolean();
		this.b = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeBoolean(this.a);
		mg.writeBoolean(this.b);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	public boolean b() {
		return this.a;
	}

	public boolean c() {
		return this.b;
	}
}

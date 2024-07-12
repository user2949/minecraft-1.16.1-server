import java.io.IOException;

public class rr implements ni<qz> {
	private boolean a;

	public void a(qz qz) {
		qz.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.writeBoolean(this.a);
	}

	public boolean b() {
		return this.a;
	}
}

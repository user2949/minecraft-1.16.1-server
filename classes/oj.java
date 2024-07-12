import java.io.IOException;

public class oj implements ni<nl> {
	private bke a;
	private int b;

	public oj() {
	}

	public oj(bke bke, int integer) {
		this.a = bke;
		this.b = integer;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = bke.b(mg.i());
		this.b = mg.i();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(bke.a(this.a));
		mg.d(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

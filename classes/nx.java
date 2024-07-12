import java.io.IOException;

public class nx implements ni<nl> {
	private fu a;
	private cfj b;

	public nx() {
	}

	public nx(bpg bpg, fu fu) {
		this.a = fu;
		this.b = bpg.d_(fu);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
		this.b = bvr.m.a(mg.i());
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.d(bvr.i(this.b));
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

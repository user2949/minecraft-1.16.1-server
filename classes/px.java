import java.io.IOException;

public class px implements ni<nl> {
	private fu a;

	public px() {
	}

	public px(fu fu) {
		this.a = fu;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

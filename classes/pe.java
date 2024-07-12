import java.io.IOException;

public class pe implements ni<nl> {
	private fu a;

	public pe() {
	}

	public pe(fu fu) {
		this.a = fu;
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.e();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
	}
}

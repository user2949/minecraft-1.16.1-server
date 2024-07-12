import java.io.IOException;

public class qo implements ni<nl> {
	private mr a;
	private mr b;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.h();
		this.b = mg.h();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.a);
		mg.a(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

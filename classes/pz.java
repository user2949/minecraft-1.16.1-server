import java.io.IOException;
import java.util.List;

public class pz implements ni<nl> {
	private int a;
	private List<tt.a<?>> b;

	public pz() {
	}

	public pz(int integer, tt tt, boolean boolean3) {
		this.a = integer;
		if (boolean3) {
			this.b = tt.c();
			tt.e();
		} else {
			this.b = tt.b();
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = tt.a(mg);
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		tt.a(this.b, mg);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

import java.io.IOException;

public class pd implements ni<nl> {
	private int a;
	private int b;
	private mr c;

	public pd() {
	}

	public pd(int integer, bhk<?> bhk, mr mr) {
		this.a = integer;
		this.b = gl.aM.a(bhk);
		this.c = mr;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.i();
		this.c = mg.h();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.d(this.b);
		mg.a(this.c);
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

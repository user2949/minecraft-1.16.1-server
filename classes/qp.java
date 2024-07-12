import java.io.IOException;
import javax.annotation.Nullable;

public class qp implements ni<nl> {
	private int a;
	@Nullable
	private le b;

	public qp() {
	}

	public qp(int integer, @Nullable le le) {
		this.a = integer;
		this.b = le;
	}

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.l();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
	}

	public void a(nl nl) {
		nl.a(this);
	}

	@Override
	public boolean a() {
		return true;
	}
}

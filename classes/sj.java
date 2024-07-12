import java.io.IOException;
import javax.annotation.Nullable;

public class sj implements ni<qz> {
	private int a;
	private String b;
	private boolean c;

	@Override
	public void a(mg mg) throws IOException {
		this.a = mg.i();
		this.b = mg.e(32767);
		this.c = mg.readBoolean();
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.d(this.a);
		mg.a(this.b);
		mg.writeBoolean(this.c);
	}

	public void a(qz qz) {
		qz.a(this);
	}

	@Nullable
	public bpc a(bqb bqb) {
		aom aom3 = bqb.a(this.a);
		return aom3 instanceof bfx ? ((bfx)aom3).u() : null;
	}

	public String b() {
		return this.b;
	}

	public boolean c() {
		return this.c;
	}
}

import javax.annotation.Nullable;

public class any extends anx {
	private final aom x;

	public any(String string, aom aom2, @Nullable aom aom3) {
		super(string, aom2);
		this.x = aom3;
	}

	@Nullable
	@Override
	public aom j() {
		return this.w;
	}

	@Nullable
	@Override
	public aom k() {
		return this.x;
	}

	@Override
	public mr a(aoy aoy) {
		mr mr3 = this.x == null ? this.w.d() : this.x.d();
		bki bki4 = this.x instanceof aoy ? ((aoy)this.x).dC() : bki.b;
		String string5 = "death.attack." + this.v;
		String string6 = string5 + ".item";
		return !bki4.a() && bki4.t() ? new ne(string6, aoy.d(), mr3, bki4.C()) : new ne(string5, aoy.d(), mr3);
	}
}

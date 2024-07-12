import javax.annotation.Nullable;

public class anx extends anw {
	@Nullable
	protected final aom w;
	private boolean x;

	public anx(String string, @Nullable aom aom) {
		super(string);
		this.w = aom;
	}

	public anx x() {
		this.x = true;
		return this;
	}

	public boolean y() {
		return this.x;
	}

	@Nullable
	@Override
	public aom k() {
		return this.w;
	}

	@Override
	public mr a(aoy aoy) {
		bki bki3 = this.w instanceof aoy ? ((aoy)this.w).dC() : bki.b;
		String string4 = "death.attack." + this.v;
		return !bki3.a() && bki3.t() ? new ne(string4 + ".item", aoy.d(), this.w.d(), bki3.C()) : new ne(string4, aoy.d(), this.w.d());
	}

	@Override
	public boolean s() {
		return this.w != null && this.w instanceof aoy && !(this.w instanceof bec);
	}

	@Nullable
	@Override
	public dem w() {
		return this.w != null ? this.w.cz() : null;
	}

	@Override
	public String toString() {
		return "EntityDamageSource (" + this.w + ")";
	}
}

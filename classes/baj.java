import javax.annotation.Nullable;

public class baj extends bae {
	private dem b;

	public baj(bac bac) {
		super(bac);
	}

	@Override
	public void c() {
		if (this.b == null) {
			this.b = this.a.cz();
		}
	}

	@Override
	public boolean a() {
		return true;
	}

	@Override
	public void d() {
		this.b = null;
	}

	@Override
	public float f() {
		return 1.0F;
	}

	@Nullable
	@Override
	public dem g() {
		return this.b;
	}

	@Override
	public bas<baj> i() {
		return bas.k;
	}
}

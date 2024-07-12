import javax.annotation.Nullable;

public abstract class awj extends aug {
	protected final aoz e;
	protected final boolean f;
	private final boolean a;
	private int b;
	private int c;
	private int d;
	protected aoy g;
	protected int h = 60;

	public awj(aoz aoz, boolean boolean2) {
		this(aoz, boolean2, false);
	}

	public awj(aoz aoz, boolean boolean2, boolean boolean3) {
		this.e = aoz;
		this.f = boolean2;
		this.a = boolean3;
	}

	@Override
	public boolean b() {
		aoy aoy2 = this.e.A();
		if (aoy2 == null) {
			aoy2 = this.g;
		}

		if (aoy2 == null) {
			return false;
		} else if (!aoy2.aU()) {
			return false;
		} else {
			dfo dfo3 = this.e.bC();
			dfo dfo4 = aoy2.bC();
			if (dfo3 != null && dfo4 == dfo3) {
				return false;
			} else {
				double double5 = this.k();
				if (this.e.h(aoy2) > double5 * double5) {
					return false;
				} else {
					if (this.f) {
						if (this.e.z().a(aoy2)) {
							this.d = 0;
						} else if (++this.d > this.h) {
							return false;
						}
					}

					if (aoy2 instanceof bec && ((bec)aoy2).bJ.a) {
						return false;
					} else {
						this.e.i(aoy2);
						return true;
					}
				}
			}
		}
	}

	protected double k() {
		return this.e.b(apx.b);
	}

	@Override
	public void c() {
		this.b = 0;
		this.c = 0;
		this.d = 0;
	}

	@Override
	public void d() {
		this.e.i(null);
		this.g = null;
	}

	protected boolean a(@Nullable aoy aoy, axs axs) {
		if (aoy == null) {
			return false;
		} else if (!axs.a(this.e, aoy)) {
			return false;
		} else if (!this.e.a(aoy.cA())) {
			return false;
		} else {
			if (this.a) {
				if (--this.c <= 0) {
					this.b = 0;
				}

				if (this.b == 0) {
					this.b = this.a(aoy) ? 1 : 2;
				}

				if (this.b == 2) {
					return false;
				}
			}

			return true;
		}
	}

	private boolean a(aoy aoy) {
		this.c = 10 + this.e.cX().nextInt(5);
		czf czf3 = this.e.x().a(aoy, 0);
		if (czf3 == null) {
			return false;
		} else {
			czd czd4 = czf3.c();
			if (czd4 == null) {
				return false;
			} else {
				int integer5 = czd4.a - aec.c(aoy.cC());
				int integer6 = czd4.c - aec.c(aoy.cG());
				return (double)(integer5 * integer5 + integer6 * integer6) <= 2.25;
			}
		}
	}

	public awj a(int integer) {
		this.h = integer;
		return this;
	}
}

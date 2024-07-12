public abstract class atx extends aug {
	protected aoz d;
	protected fu e = fu.b;
	protected boolean f;
	private boolean a;
	private float b;
	private float c;

	public atx(aoz aoz) {
		this.d = aoz;
		if (!this.h()) {
			throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
		}
	}

	protected boolean g() {
		if (!this.f) {
			return false;
		} else {
			cfj cfj2 = this.d.l.d_(this.e);
			if (!(cfj2.b() instanceof bxe)) {
				this.f = false;
				return false;
			} else {
				return (Boolean)cfj2.c(bxe.b);
			}
		}
	}

	protected void a(boolean boolean1) {
		if (this.f) {
			cfj cfj3 = this.d.l.d_(this.e);
			if (cfj3.b() instanceof bxe) {
				((bxe)cfj3.b()).a(this.d.l, this.e, boolean1);
			}
		}
	}

	@Override
	public boolean a() {
		if (!this.h()) {
			return false;
		} else if (!this.d.u) {
			return false;
		} else {
			awu awu2 = (awu)this.d.x();
			czf czf3 = awu2.k();
			if (czf3 != null && !czf3.b() && awu2.f()) {
				for (int integer4 = 0; integer4 < Math.min(czf3.f() + 2, czf3.e()); integer4++) {
					czd czd5 = czf3.a(integer4);
					this.e = new fu(czd5.a, czd5.b + 1, czd5.c);
					if (!(this.d.g((double)this.e.u(), this.d.cD(), (double)this.e.w()) > 2.25)) {
						this.f = bxe.a(this.d.l, this.e);
						if (this.f) {
							return true;
						}
					}
				}

				this.e = this.d.cA().b();
				this.f = bxe.a(this.d.l, this.e);
				return this.f;
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean b() {
		return !this.a;
	}

	@Override
	public void c() {
		this.a = false;
		this.b = (float)((double)this.e.u() + 0.5 - this.d.cC());
		this.c = (float)((double)this.e.w() + 0.5 - this.d.cG());
	}

	@Override
	public void e() {
		float float2 = (float)((double)this.e.u() + 0.5 - this.d.cC());
		float float3 = (float)((double)this.e.w() + 0.5 - this.d.cG());
		float float4 = this.b * float2 + this.c * float3;
		if (float4 < 0.0F) {
			this.a = true;
		}
	}

	private boolean h() {
		return this.d.x() instanceof awu;
	}
}

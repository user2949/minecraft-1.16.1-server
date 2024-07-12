public class ati extends atl {
	private final int h;

	public ati(aoz aoz, int integer) {
		super(aoz);
		this.h = integer;
	}

	@Override
	public void a() {
		if (this.d) {
			this.d = false;
			this.a.aJ = this.a(this.a.aJ, this.h() + 20.0F, this.b);
			this.a.q = this.a(this.a.q, this.g() + 10.0F, this.c);
		} else {
			if (this.a.x().m()) {
				this.a.q = this.a(this.a.q, 0.0F, 5.0F);
			}

			this.a.aJ = this.a(this.a.aJ, this.a.aH, this.b);
		}

		float float2 = aec.g(this.a.aJ - this.a.aH);
		if (float2 < (float)(-this.h)) {
			this.a.aH -= 4.0F;
		} else if (float2 > (float)this.h) {
			this.a.aH += 4.0F;
		}
	}
}

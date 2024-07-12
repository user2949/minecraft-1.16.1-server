import javax.annotation.Nullable;

public class bfb extends bey {
	public bfb(aoq<? extends bfb> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfb(bqb bqb, aoy aoy) {
		super(aoq.aG, aoy, bqb);
	}

	@Override
	protected bke h() {
		return bkk.nq;
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		dei.a().a(anw.b(this, this.v()), 0.0F);
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		aom aom3 = this.v();

		for (int integer4 = 0; integer4 < 32; integer4++) {
			this.l.a(hh.Q, this.cC(), this.cD() + this.J.nextDouble() * 2.0, this.cG(), this.J.nextGaussian(), 0.0, this.J.nextGaussian());
		}

		if (!this.l.v && !this.y) {
			if (aom3 instanceof ze) {
				ze ze4 = (ze)aom3;
				if (ze4.b.a().g() && ze4.l == this.l && !ze4.el()) {
					if (this.J.nextFloat() < 0.05F && this.l.S().b(bpx.d)) {
						bbs bbs5 = aoq.v.a(this.l);
						bbs5.t(true);
						bbs5.b(aom3.cC(), aom3.cD(), aom3.cG(), aom3.p, aom3.q);
						this.l.c(bbs5);
					}

					if (aom3.bn()) {
						aom3.l();
					}

					aom3.a(this.cC(), this.cD(), this.cG());
					aom3.C = 0.0F;
					aom3.a(anw.k, 5.0F);
				}
			} else if (aom3 != null) {
				aom3.a(this.cC(), this.cD(), this.cG());
				aom3.C = 0.0F;
			}

			this.aa();
		}
	}

	@Override
	public void j() {
		aom aom2 = this.v();
		if (aom2 instanceof bec && !aom2.aU()) {
			this.aa();
		} else {
			super.j();
		}
	}

	@Nullable
	@Override
	public aom a(zd zd) {
		aom aom3 = this.v();
		if (aom3 != null && aom3.l.W() != zd.W()) {
			this.b(null);
		}

		return super.a(zd);
	}
}

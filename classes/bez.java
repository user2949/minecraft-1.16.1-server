public abstract class bez extends bes {
	protected bez(aoq<? extends bez> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	protected bez(aoq<? extends bez> aoq, double double2, double double3, double double4, bqb bqb) {
		this(aoq, bqb);
		this.d(double2, double3, double4);
	}

	protected bez(aoq<? extends bez> aoq, aoy aoy, bqb bqb) {
		this(aoq, aoy.cC(), aoy.cF() - 0.1F, aoy.cG(), bqb);
		this.b(aoy);
	}

	@Override
	public void j() {
		super.j();
		dej dej2 = bet.a(this, this::a, bpj.a.OUTLINE);
		boolean boolean3 = false;
		if (dej2.c() == dej.a.BLOCK) {
			fu fu4 = ((deh)dej2).a();
			cfj cfj5 = this.l.d_(fu4);
			if (cfj5.a(bvs.cT)) {
				this.d(fu4);
				boolean3 = true;
			} else if (cfj5.a(bvs.iF)) {
				cdl cdl6 = this.l.c(fu4);
				if (cdl6 instanceof cem) {
					((cem)cdl6).a(this);
				}

				boolean3 = true;
			}
		}

		if (dej2.c() != dej.a.MISS && !boolean3) {
			this.a(dej2);
		}

		dem dem4 = this.cB();
		double double5 = this.cC() + dem4.b;
		double double7 = this.cD() + dem4.c;
		double double9 = this.cG() + dem4.d;
		this.x();
		float float11;
		if (this.aA()) {
			for (int integer12 = 0; integer12 < 4; integer12++) {
				float float13 = 0.25F;
				this.l.a(hh.e, double5 - dem4.b * 0.25, double7 - dem4.c * 0.25, double9 - dem4.d * 0.25, dem4.b, dem4.c, dem4.d);
			}

			float11 = 0.8F;
		} else {
			float11 = 0.99F;
		}

		this.e(dem4.a((double)float11));
		if (!this.aw()) {
			dem dem12 = this.cB();
			this.m(dem12.b, dem12.c - (double)this.k(), dem12.d);
		}

		this.d(double5, double7, double9);
	}

	protected float k() {
		return 0.03F;
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}

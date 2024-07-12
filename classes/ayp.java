public class ayp extends ayk {
	public ayp(aoq<? extends ayp> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void o() {
		this.br.a(0, new aua(this));
		this.br.a(1, new avb(this, 2.0));
		this.br.a(2, new att(this, 1.0));
		this.br.a(3, new avr(this, 1.25, bmr.a(bkk.kW), false));
		this.br.a(4, new auf(this, 1.25));
		this.br.a(5, new avw(this, 1.0));
		this.br.a(6, new auo(this, bec.class, 6.0F));
		this.br.a(7, new ave(this));
	}

	public static apw.a eL() {
		return aoz.p().a(apx.a, 10.0).a(apx.d, 0.2F);
	}

	@Override
	protected ack I() {
		return acl.ci;
	}

	@Override
	protected ack e(anw anw) {
		return acl.ck;
	}

	@Override
	protected ack dp() {
		return acl.cj;
	}

	@Override
	protected void a(fu fu, cfj cfj) {
		this.a(acl.cm, 0.15F, 1.0F);
	}

	@Override
	protected float dF() {
		return 0.4F;
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.lK && !this.x_()) {
			bec.a(acl.cl, 1.0F, 1.0F);
			bki bki5 = bkj.a(bki4, bec, bkk.lT.r());
			bec.a(anf, bki5);
			return ang.a(this.l.v);
		} else {
			return super.b(bec, anf);
		}
	}

	public ayp a(aok aok) {
		return aoq.l.a(this.l);
	}

	@Override
	protected float b(apj apj, aon aon) {
		return this.x_() ? aon.b * 0.95F : 1.3F;
	}
}

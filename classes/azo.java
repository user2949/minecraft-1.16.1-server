import java.util.UUID;
import javax.annotation.Nullable;

public class azo extends azm {
	private static final UUID bD = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
	private static final tq<Integer> bE = tt.a(azo.class, ts.b);

	public azo(aoq<? extends azo> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected void eL() {
		this.a(apx.a).a((double)this.fq());
		this.a(apx.d).a(this.fs());
		this.a(apx.m).a(this.fr());
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bE, 0);
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Variant", this.eV());
		if (!this.by.a(1).a()) {
			le.a("ArmorItem", this.by.a(1).b(new le()));
		}
	}

	public bki eM() {
		return this.b(aor.CHEST);
	}

	private void m(bki bki) {
		this.a(aor.CHEST, bki);
		this.a(aor.CHEST, 0.0F);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.w(le.h("Variant"));
		if (le.c("ArmorItem", 10)) {
			bki bki3 = bki.a(le.p("ArmorItem"));
			if (!bki3.a() && this.l(bki3)) {
				this.by.a(1, bki3);
			}
		}

		this.ff();
	}

	private void w(int integer) {
		this.S.b(bE, integer);
	}

	private int eV() {
		return this.S.a(bE);
	}

	private void a(azv azv, azq azq) {
		this.w(azv.a() & 0xFF | azq.a() << 8 & 0xFF00);
	}

	public azv eN() {
		return azv.a(this.eV() & 0xFF);
	}

	public azq eP() {
		return azq.a((this.eV() & 0xFF00) >> 8);
	}

	@Override
	protected void ff() {
		if (!this.l.v) {
			super.ff();
			this.n(this.by.a(1));
			this.a(aor.CHEST, 0.0F);
		}
	}

	private void n(bki bki) {
		this.m(bki);
		if (!this.l.v) {
			this.a(apx.i).b(bD);
			if (this.l(bki)) {
				int integer3 = ((bkd)bki.b()).g();
				if (integer3 != 0) {
					this.a(apx.i).b(new apv(bD, "Horse armor bonus", (double)integer3, apv.a.ADDITION));
				}
			}
		}
	}

	@Override
	public void a(amz amz) {
		bki bki3 = this.eM();
		super.a(amz);
		bki bki4 = this.eM();
		if (this.K > 20 && this.l(bki4) && bki3 != bki4) {
			this.a(acl.fS, 0.5F, 1.0F);
		}
	}

	@Override
	protected void a(cbh cbh) {
		super.a(cbh);
		if (this.J.nextInt(10) == 0) {
			this.a(acl.fT, cbh.a() * 0.6F, cbh.b());
		}
	}

	@Override
	protected ack I() {
		super.I();
		return acl.fQ;
	}

	@Override
	protected ack dp() {
		super.dp();
		return acl.fU;
	}

	@Nullable
	@Override
	protected ack fh() {
		return acl.fV;
	}

	@Override
	protected ack e(anw anw) {
		super.e(anw);
		return acl.fX;
	}

	@Override
	protected ack fi() {
		super.fi();
		return acl.fR;
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (!this.x_()) {
			if (this.eX() && bec.ep()) {
				this.f(bec);
				return ang.a(this.l.v);
			}

			if (this.bo()) {
				return super.b(bec, anf);
			}
		}

		if (!bki4.a()) {
			if (this.k(bki4)) {
				return this.b(bec, bki4);
			}

			ang ang5 = bki4.a(bec, this, anf);
			if (ang5.a()) {
				return ang5;
			}

			if (!this.eX()) {
				this.fn();
				return ang.a(this.l.v);
			}

			boolean boolean6 = !this.x_() && !this.N_() && bki4.b() == bkk.lO;
			if (this.l(bki4) || boolean6) {
				this.f(bec);
				return ang.a(this.l.v);
			}
		}

		if (this.x_()) {
			return super.b(bec, anf);
		} else {
			this.h(bec);
			return ang.a(this.l.v);
		}
	}

	@Override
	public boolean a(ayk ayk) {
		if (ayk == this) {
			return false;
		} else {
			return !(ayk instanceof azn) && !(ayk instanceof azo) ? false : this.fp() && ((azm)ayk).fp();
		}
	}

	@Override
	public aok a(aok aok) {
		azm azm3;
		if (aok instanceof azn) {
			azm3 = aoq.aa.a(this.l);
		} else {
			azo azo4 = (azo)aok;
			azm3 = aoq.H.a(this.l);
			int integer6 = this.J.nextInt(9);
			azv azv5;
			if (integer6 < 4) {
				azv5 = this.eN();
			} else if (integer6 < 8) {
				azv5 = azo4.eN();
			} else {
				azv5 = v.a(azv.values(), this.J);
			}

			int integer8 = this.J.nextInt(5);
			azq azq7;
			if (integer8 < 2) {
				azq7 = this.eP();
			} else if (integer8 < 4) {
				azq7 = azo4.eP();
			} else {
				azq7 = v.a(azq.values(), this.J);
			}

			((azo)azm3).a(azv5, azq7);
		}

		this.a(aok, azm3);
		return azm3;
	}

	@Override
	public boolean ft() {
		return true;
	}

	@Override
	public boolean l(bki bki) {
		return bki.b() instanceof bkd;
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		azv azv7;
		if (apo instanceof azo.a) {
			azv7 = ((azo.a)apo).a;
		} else {
			azv7 = v.a(azv.values(), this.J);
			apo = new azo.a(azv7);
		}

		this.a(azv7, v.a(azq.values(), this.J));
		return super.a(bqc, ane, apb, apo, le);
	}

	public static class a extends aok.a {
		public final azv a;

		public a(azv azv) {
			this.a = azv;
		}
	}
}

import javax.annotation.Nullable;

public class biu extends bke {
	private final cwz a;

	public biu(cwz cwz, bke.a a) {
		super(a);
		this.a = cwz;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		dej dej6 = a(bqb, bec, this.a == cxb.a ? bpj.b.SOURCE_ONLY : bpj.b.NONE);
		if (dej6.c() == dej.a.MISS) {
			return anh.c(bki5);
		} else if (dej6.c() != dej.a.BLOCK) {
			return anh.c(bki5);
		} else {
			deh deh7 = (deh)dej6;
			fu fu8 = deh7.a();
			fz fz9 = deh7.b();
			fu fu10 = fu8.a(fz9);
			if (!bqb.a(bec, fu8) || !bec.a(fu10, fz9, bki5)) {
				return anh.d(bki5);
			} else if (this.a == cxb.a) {
				cfj cfj11 = bqb.d_(fu8);
				if (cfj11.b() instanceof bvw) {
					cwz cwz12 = ((bvw)cfj11.b()).b(bqb, fu8, cfj11);
					if (cwz12 != cxb.a) {
						bec.b(acu.c.b(this));
						bec.a(cwz12.a(acz.b) ? acl.bo : acl.bm, 1.0F, 1.0F);
						bki bki13 = bkj.a(bki5, bec, new bki(cwz12.a()));
						if (!bqb.v) {
							aa.j.a((ze)bec, new bki(cwz12.a()));
						}

						return anh.a(bki13, bqb.s_());
					}
				}

				return anh.d(bki5);
			} else {
				cfj cfj11 = bqb.d_(fu8);
				fu fu12 = cfj11.b() instanceof bzf && this.a == cxb.c ? fu8 : fu10;
				if (this.a(bec, bqb, fu12, deh7)) {
					this.a(bqb, bki5, fu12);
					if (bec instanceof ze) {
						aa.y.a((ze)bec, fu12, bki5);
					}

					bec.b(acu.c.b(this));
					return anh.a(this.a(bki5, bec), bqb.s_());
				} else {
					return anh.d(bki5);
				}
			}
		}
	}

	protected bki a(bki bki, bec bec) {
		return !bec.bJ.d ? new bki(bkk.lK) : bki;
	}

	public void a(bqb bqb, bki bki, fu fu) {
	}

	public boolean a(@Nullable bec bec, bqb bqb, fu fu, @Nullable deh deh) {
		if (!(this.a instanceof cwy)) {
			return false;
		} else {
			cfj cfj6 = bqb.d_(fu);
			bvr bvr7 = cfj6.b();
			cxd cxd8 = cfj6.c();
			boolean boolean9 = cfj6.a(this.a);
			boolean boolean10 = cfj6.g() || boolean9 || bvr7 instanceof bzf && ((bzf)bvr7).a(bqb, fu, cfj6, this.a);
			if (!boolean10) {
				return deh != null && this.a(bec, bqb, deh.a().a(deh.b()), null);
			} else if (bqb.m().f() && this.a.a(acz.a)) {
				int integer11 = fu.u();
				int integer12 = fu.v();
				int integer13 = fu.w();
				bqb.a(bec, fu, acl.ej, acm.BLOCKS, 0.5F, 2.6F + (bqb.t.nextFloat() - bqb.t.nextFloat()) * 0.8F);

				for (int integer14 = 0; integer14 < 8; integer14++) {
					bqb.a(hh.L, (double)integer11 + Math.random(), (double)integer12 + Math.random(), (double)integer13 + Math.random(), 0.0, 0.0, 0.0);
				}

				return true;
			} else if (bvr7 instanceof bzf && this.a == cxb.c) {
				((bzf)bvr7).a(bqb, fu, cfj6, ((cwy)this.a).a(false));
				this.a(bec, bqb, fu);
				return true;
			} else {
				if (!bqb.v && boolean9 && !cxd8.a()) {
					bqb.b(fu, true);
				}

				if (!bqb.a(fu, this.a.h().g(), 11) && !cfj6.m().b()) {
					return false;
				} else {
					this.a(bec, bqb, fu);
					return true;
				}
			}
		}
	}

	protected void a(@Nullable bec bec, bqc bqc, fu fu) {
		ack ack5 = this.a.a(acz.b) ? acl.bl : acl.bj;
		bqc.a(bec, fu, ack5, acm.BLOCKS, 1.0F, 1.0F);
	}
}

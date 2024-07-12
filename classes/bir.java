import java.util.List;

public class bir extends bke {
	public bir(bke.a a) {
		super(a);
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		List<aol> list5 = bqb.a(aol.class, bec.cb().g(2.0), aol -> aol != null && aol.aU() && aol.t() instanceof bac);
		bki bki6 = bec.b(anf);
		if (!list5.isEmpty()) {
			aol aol7 = (aol)list5.get(0);
			aol7.a(aol7.g() - 0.5F);
			bqb.a(null, bec.cC(), bec.cD(), bec.cG(), acl.bc, acm.NEUTRAL, 1.0F, 1.0F);
			return anh.a(this.a(bki6, bec, new bki(bkk.qh)), bqb.s_());
		} else {
			dej dej7 = a(bqb, bec, bpj.b.SOURCE_ONLY);
			if (dej7.c() == dej.a.MISS) {
				return anh.c(bki6);
			} else {
				if (dej7.c() == dej.a.BLOCK) {
					fu fu8 = ((deh)dej7).a();
					if (!bqb.a(bec, fu8)) {
						return anh.c(bki6);
					}

					if (bqb.b(fu8).a(acz.a)) {
						bqb.a(bec, bec.cC(), bec.cD(), bec.cG(), acl.bb, acm.NEUTRAL, 1.0F, 1.0F);
						return anh.a(this.a(bki6, bec, bmd.a(new bki(bkk.nv), bme.b)), bqb.s_());
					}
				}

				return anh.c(bki6);
			}
		}
	}

	protected bki a(bki bki1, bec bec, bki bki3) {
		bki1.g(1);
		bec.b(acu.c.b(this));
		if (bki1.a()) {
			return bki3;
		} else {
			if (!bec.bt.e(bki3)) {
				bec.a(bki3, false);
			}

			return bki1;
		}
	}
}

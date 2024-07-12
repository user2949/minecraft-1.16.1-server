import java.util.List;
import java.util.Random;

public class bdl implements bpm {
	private int a;

	@Override
	public int a(zd zd, boolean boolean2, boolean boolean3) {
		if (boolean3 && zd.S().b(bpx.d)) {
			this.a--;
			if (this.a > 0) {
				return 0;
			} else {
				this.a = 1200;
				bec bec5 = zd.h();
				if (bec5 == null) {
					return 0;
				} else {
					Random random6 = zd.t;
					int integer7 = (8 + random6.nextInt(24)) * (random6.nextBoolean() ? -1 : 1);
					int integer8 = (8 + random6.nextInt(24)) * (random6.nextBoolean() ? -1 : 1);
					fu fu9 = bec5.cA().b(integer7, 0, integer8);
					if (!zd.a(fu9.u() - 10, fu9.v() - 10, fu9.w() - 10, fu9.u() + 10, fu9.v() + 10, fu9.w() + 10)) {
						return 0;
					} else {
						if (bqj.a(app.c.ON_GROUND, zd, fu9, aoq.h)) {
							if (zd.a(fu9, 2)) {
								return this.a(zd, fu9);
							}

							if (zd.a().a(fu9, true, cml.j).e()) {
								return this.a((bqb)zd, fu9);
							}
						}

						return 0;
					}
				}
			}
		} else {
			return 0;
		}
	}

	private int a(zd zd, fu fu) {
		int integer4 = 48;
		if (zd.x().a(ayc.r.c(), fu, 48, axz.b.IS_OCCUPIED) > 4L) {
			List<aym> list5 = zd.a(aym.class, new deg(fu).c(48.0, 8.0, 48.0));
			if (list5.size() < 5) {
				return this.a(fu, zd);
			}
		}

		return 0;
	}

	private int a(bqb bqb, fu fu) {
		int integer4 = 16;
		List<aym> list5 = bqb.a(aym.class, new deg(fu).c(16.0, 8.0, 16.0));
		return list5.size() < 1 ? this.a(fu, bqb) : 0;
	}

	private int a(fu fu, bqb bqb) {
		aym aym4 = aoq.h.a(bqb);
		if (aym4 == null) {
			return 0;
		} else {
			aym4.a(bqb, bqb.d(fu), apb.NATURAL, null, null);
			aym4.a(fu, 0.0F, 0.0F);
			bqb.c(aym4);
			return 1;
		}
	}
}

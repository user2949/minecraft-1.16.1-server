import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.tuple.Pair;

public class ayu extends ayp implements apn {
	private static final tq<String> bv = tt.a(ayu.class, ts.d);
	private aoe bw;
	private int bx;
	private UUID by;

	public ayu(aoq<? extends ayu> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	public float a(fu fu, bqd bqd) {
		return bqd.d_(fu.c()).a(bvs.dT) ? 10.0F : bqd.y(fu) - 0.5F;
	}

	public static boolean c(aoq<ayu> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return bqc.d_(fu.c()).a(bvs.dT) && bqc.b(fu, 0) > 8;
	}

	@Override
	public void a(aox aox) {
		UUID uUID3 = aox.bR();
		if (!uUID3.equals(this.by)) {
			this.a(this.eM() == ayu.a.RED ? ayu.a.BROWN : ayu.a.RED);
			this.by = uUID3;
			this.a(acl.hK, 2.0F, 1.0F);
		}
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, ayu.a.RED.c);
	}

	@Override
	public ang b(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (bki4.b() == bkk.kC && !this.x_()) {
			boolean boolean6 = false;
			bki bki5;
			if (this.bw != null) {
				boolean6 = true;
				bki5 = new bki(bkk.qQ);
				bll.a(bki5, this.bw, this.bx);
				this.bw = null;
				this.bx = 0;
			} else {
				bki5 = new bki(bkk.kD);
			}

			bki bki7 = bkj.a(bki4, bec, bki5);
			bec.a(anf, bki7);
			ack ack8;
			if (boolean6) {
				ack8 = acl.hN;
			} else {
				ack8 = acl.hM;
			}

			this.a(ack8, 1.0F, 1.0F);
			return ang.a(this.l.v);
		} else if (bki4.b() == bkk.ng && this.L_()) {
			this.a(acm.PLAYERS);
			if (!this.l.v) {
				bki4.a(1, bec, becx -> becx.d(anf));
			}

			return ang.a(this.l.v);
		} else if (this.eM() == ayu.a.BROWN && bki4.b().a(ada.H)) {
			if (this.bw != null) {
				for (int integer5 = 0; integer5 < 2; integer5++) {
					this.l.a(hh.S, this.cC() + this.J.nextDouble() / 2.0, this.e(0.5), this.cG() + this.J.nextDouble() / 2.0, 0.0, this.J.nextDouble() / 5.0, 0.0);
				}
			} else {
				Optional<Pair<aoe, Integer>> optional5 = this.l(bki4);
				if (!optional5.isPresent()) {
					return ang.PASS;
				}

				Pair<aoe, Integer> pair6 = (Pair<aoe, Integer>)optional5.get();
				if (!bec.bJ.d) {
					bki4.g(1);
				}

				for (int integer7 = 0; integer7 < 4; integer7++) {
					this.l.a(hh.p, this.cC() + this.J.nextDouble() / 2.0, this.e(0.5), this.cG() + this.J.nextDouble() / 2.0, 0.0, this.J.nextDouble() / 5.0, 0.0);
				}

				this.bw = pair6.getLeft();
				this.bx = pair6.getRight();
				this.a(acl.hL, 2.0F, 1.0F);
			}

			return ang.a(this.l.v);
		} else {
			return super.b(bec, anf);
		}
	}

	@Override
	public void a(acm acm) {
		this.l.a(null, this, acl.hO, acm, 1.0F, 1.0F);
		if (!this.l.s_()) {
			((zd)this.l).a(hh.w, this.cC(), this.e(0.5), this.cG(), 1, 0.0, 0.0, 0.0, 0.0);
			this.aa();
			ayp ayp3 = aoq.l.a(this.l);
			ayp3.b(this.cC(), this.cD(), this.cG(), this.p, this.q);
			ayp3.c(this.dj());
			ayp3.aH = this.aH;
			if (this.Q()) {
				ayp3.a(this.R());
				ayp3.n(this.bW());
			}

			if (this.ev()) {
				ayp3.et();
			}

			ayp3.m(this.bI());
			this.l.c(ayp3);

			for (int integer4 = 0; integer4 < 5; integer4++) {
				this.l.c(new bbg(this.l, this.cC(), this.e(1.0), this.cG(), new bki(this.eM().d.b())));
			}
		}
	}

	@Override
	public boolean L_() {
		return this.aU() && !this.x_();
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("Type", this.eM().c);
		if (this.bw != null) {
			le.a("EffectId", (byte)aoe.a(this.bw));
			le.b("EffectDuration", this.bx);
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.a(ayu.a.b(le.l("Type")));
		if (le.c("EffectId", 1)) {
			this.bw = aoe.a(le.f("EffectId"));
		}

		if (le.c("EffectDuration", 3)) {
			this.bx = le.h("EffectDuration");
		}
	}

	private Optional<Pair<aoe, Integer>> l(bki bki) {
		bke bke3 = bki.b();
		if (bke3 instanceof bim) {
			bvr bvr4 = ((bim)bke3).e();
			if (bvr4 instanceof bxx) {
				bxx bxx5 = (bxx)bvr4;
				return Optional.of(Pair.of(bxx5.c(), bxx5.d()));
			}
		}

		return Optional.empty();
	}

	private void a(ayu.a a) {
		this.S.b(bv, a.c);
	}

	public ayu.a eM() {
		return ayu.a.b(this.S.a(bv));
	}

	public ayu b(aok aok) {
		ayu ayu3 = aoq.ab.a(this.l);
		ayu3.a(this.a((ayu)aok));
		return ayu3;
	}

	private ayu.a a(ayu ayu) {
		ayu.a a3 = this.eM();
		ayu.a a4 = ayu.eM();
		ayu.a a5;
		if (a3 == a4 && this.J.nextInt(1024) == 0) {
			a5 = a3 == ayu.a.BROWN ? ayu.a.RED : ayu.a.BROWN;
		} else {
			a5 = this.J.nextBoolean() ? a3 : a4;
		}

		return a5;
	}

	public static enum a {
		RED("red", bvs.bD.n()),
		BROWN("brown", bvs.bC.n());

		private final String c;
		private final cfj d;

		private a(String string3, cfj cfj) {
			this.c = string3;
			this.d = cfj;
		}

		private static ayu.a b(String string) {
			for (ayu.a a5 : values()) {
				if (a5.c.equals(string)) {
					return a5;
				}
			}

			return RED;
		}
	}
}

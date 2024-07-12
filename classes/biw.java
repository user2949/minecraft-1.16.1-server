import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class biw extends bke implements blw {
	private static final Logger a = LogManager.getLogger();

	public biw(bke.a a) {
		super(a);
	}

	public static boolean d(bki bki) {
		le le2 = bki.o();
		return le2 != null && (le2.e("LodestoneDimension") || le2.e("LodestonePos"));
	}

	@Override
	public boolean e(bki bki) {
		return d(bki) || super.e(bki);
	}

	public static Optional<ug<bqb>> a(le le) {
		return bqb.f.parse(lp.a, le.c("LodestoneDimension")).result();
	}

	@Override
	public void a(bki bki, bqb bqb, aom aom, int integer, boolean boolean5) {
		if (!bqb.v) {
			if (d(bki)) {
				le le7 = bki.p();
				if (le7.e("LodestoneTracked") && !le7.q("LodestoneTracked")) {
					return;
				}

				Optional<ug<bqb>> optional8 = a(le7);
				if (optional8.isPresent() && optional8.get() == bqb.W() && le7.e("LodestonePos") && !((zd)bqb).x().a(ayc.w, lq.b(le7.p("LodestonePos")))) {
					le7.r("LodestonePos");
				}
			}
		}
	}

	@Override
	public ang a(blv blv) {
		fu fu3 = blv.d.a();
		if (!blv.e.d_(fu3).a(bvs.no)) {
			return super.a(blv);
		} else {
			blv.e.a(null, fu3, acl.hu, acm.PLAYERS, 1.0F, 1.0F);
			boolean boolean4 = !blv.b.bJ.d && blv.f.E() == 1;
			if (boolean4) {
				this.a(blv.e.W(), fu3, blv.f.p());
			} else {
				bki bki5 = new bki(bkk.mh, 1);
				le le6 = blv.f.n() ? blv.f.o().g() : new le();
				bki5.c(le6);
				if (!blv.b.bJ.d) {
					blv.f.g(1);
				}

				this.a(blv.e.W(), fu3, le6);
				if (!blv.b.bt.e(bki5)) {
					blv.b.a(bki5, false);
				}
			}

			return ang.a(blv.e.v);
		}
	}

	private void a(ug<bqb> ug, fu fu, le le) {
		le.a("LodestonePos", lq.a(fu));
		bqb.f.encodeStart(lp.a, ug).resultOrPartial(a::error).ifPresent(lu -> le.a("LodestoneDimension", lu));
		le.a("LodestoneTracked", true);
	}

	@Override
	public String f(bki bki) {
		return d(bki) ? "item.minecraft.lodestone_compass" : super.f(bki);
	}
}

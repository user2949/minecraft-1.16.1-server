import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class aqy extends aqh<bdp> {
	@Nullable
	private fu b;
	private long c;
	private int d;
	private final List<fu> e = Lists.<fu>newArrayList();

	public aqy() {
		super(ImmutableMap.of(awp.n, awq.VALUE_ABSENT, awp.m, awq.VALUE_ABSENT, awp.f, awq.VALUE_PRESENT));
	}

	protected boolean a(zd zd, bdp bdp) {
		if (!zd.S().b(bpx.b)) {
			return false;
		} else if (bdp.eY().b() != bds.f) {
			return false;
		} else {
			fu.a a4 = bdp.cA().i();
			this.e.clear();

			for (int integer5 = -1; integer5 <= 1; integer5++) {
				for (int integer6 = -1; integer6 <= 1; integer6++) {
					for (int integer7 = -1; integer7 <= 1; integer7++) {
						a4.c(bdp.cC() + (double)integer5, bdp.cD() + (double)integer6, bdp.cG() + (double)integer7);
						if (this.a(a4, zd)) {
							this.e.add(new fu(a4));
						}
					}
				}
			}

			this.b = this.a(zd);
			return this.b != null;
		}
	}

	@Nullable
	private fu a(zd zd) {
		return this.e.isEmpty() ? null : (fu)this.e.get(zd.v_().nextInt(this.e.size()));
	}

	private boolean a(fu fu, zd zd) {
		cfj cfj4 = zd.d_(fu);
		bvr bvr5 = cfj4.b();
		bvr bvr6 = zd.d_(fu.c()).b();
		return bvr5 instanceof bwv && ((bwv)bvr5).h(cfj4) || cfj4.g() && bvr6 instanceof bxs;
	}

	protected void a(zd zd, bdp bdp, long long3) {
		if (long3 > this.c && this.b != null) {
			bdp.cI().a(awp.n, new aqj(this.b));
			bdp.cI().a(awp.m, new awr(new aqj(this.b), 0.5F, 1));
		}
	}

	protected void c(zd zd, bdp bdp, long long3) {
		bdp.cI().b(awp.n);
		bdp.cI().b(awp.m);
		this.d = 0;
		this.c = long3 + 40L;
	}

	protected void c(zd zd, bdp bdp, long long3) {
		if (this.b == null || this.b.a(bdp.cz(), 1.0)) {
			if (this.b != null && long3 > this.c) {
				cfj cfj6 = zd.d_(this.b);
				bvr bvr7 = cfj6.b();
				bvr bvr8 = zd.d_(this.b.c()).b();
				if (bvr7 instanceof bwv && ((bwv)bvr7).h(cfj6)) {
					zd.a(this.b, true, bdp);
				}

				if (cfj6.g() && bvr8 instanceof bxs && bdp.fi()) {
					anm anm9 = bdp.eU();

					for (int integer10 = 0; integer10 < anm9.ab_(); integer10++) {
						bki bki11 = anm9.a(integer10);
						boolean boolean12 = false;
						if (!bki11.a()) {
							if (bki11.b() == bkk.kV) {
								zd.a(this.b, bvs.bW.n(), 3);
								boolean12 = true;
							} else if (bki11.b() == bkk.oY) {
								zd.a(this.b, bvs.eV.n(), 3);
								boolean12 = true;
							} else if (bki11.b() == bkk.oX) {
								zd.a(this.b, bvs.eU.n(), 3);
								boolean12 = true;
							} else if (bki11.b() == bkk.qf) {
								zd.a(this.b, bvs.iD.n(), 3);
								boolean12 = true;
							}
						}

						if (boolean12) {
							zd.a(null, (double)this.b.u(), (double)this.b.v(), (double)this.b.w(), acl.cr, acm.BLOCKS, 1.0F, 1.0F);
							bki11.g(1);
							if (bki11.a()) {
								anm9.a(integer10, bki.b);
							}
							break;
						}
					}
				}

				if (bvr7 instanceof bwv && !((bwv)bvr7).h(cfj6)) {
					this.e.remove(this.b);
					this.b = this.a(zd);
					if (this.b != null) {
						this.c = long3 + 20L;
						bdp.cI().a(awp.m, new awr(new aqj(this.b), 0.5F, 1));
						bdp.cI().a(awp.n, new aqj(this.b));
					}
				}
			}

			this.d++;
		}
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return this.d < 200;
	}
}

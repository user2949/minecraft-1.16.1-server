import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class aui extends avf {
	public aui(apg apg, double double2) {
		super(apg, double2, 240, false);
	}

	@Nullable
	@Override
	protected dem g() {
		float float3 = this.a.l.t.nextFloat();
		if (this.a.l.t.nextFloat() < 0.3F) {
			return this.j();
		} else {
			dem dem2;
			if (float3 < 0.7F) {
				dem2 = this.k();
				if (dem2 == null) {
					dem2 = this.l();
				}
			} else {
				dem2 = this.l();
				if (dem2 == null) {
					dem2 = this.k();
				}
			}

			return dem2 == null ? this.j() : dem2;
		}
	}

	@Nullable
	private dem j() {
		return axu.b(this.a, 10, 7);
	}

	@Nullable
	private dem k() {
		zd zd2 = (zd)this.a.l;
		List<bdp> list3 = zd2.a(aoq.aO, this.a.cb().g(32.0), this::a);
		if (list3.isEmpty()) {
			return null;
		} else {
			bdp bdp4 = (bdp)list3.get(this.a.l.t.nextInt(list3.size()));
			dem dem5 = bdp4.cz();
			return axu.a(this.a, 10, 7, dem5);
		}
	}

	@Nullable
	private dem l() {
		go go2 = this.m();
		if (go2 == null) {
			return null;
		} else {
			fu fu3 = this.a(go2);
			return fu3 == null ? null : axu.a(this.a, 10, 7, dem.c(fu3));
		}
	}

	@Nullable
	private go m() {
		zd zd2 = (zd)this.a.l;
		List<go> list3 = (List<go>)go.a(go.a(this.a), 2).filter(go -> zd2.b(go) == 0).collect(Collectors.toList());
		return list3.isEmpty() ? null : (go)list3.get(zd2.t.nextInt(list3.size()));
	}

	@Nullable
	private fu a(go go) {
		zd zd3 = (zd)this.a.l;
		axz axz4 = zd3.x();
		List<fu> list5 = (List<fu>)axz4.c(ayc -> true, go.q(), 8, axz.b.IS_OCCUPIED).map(aya::f).collect(Collectors.toList());
		return list5.isEmpty() ? null : (fu)list5.get(zd3.t.nextInt(list5.size()));
	}

	private boolean a(bdp bdp) {
		return bdp.a(this.a.l.Q());
	}
}

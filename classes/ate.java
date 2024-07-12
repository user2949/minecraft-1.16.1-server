import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class ate extends aqh<bdp> {
	private final float b;

	public ate(float float1) {
		super(ImmutableMap.of(awp.d, awq.VALUE_PRESENT, awp.c, awq.VALUE_ABSENT, awp.g, awq.VALUE_PRESENT));
		this.b = float1;
	}

	protected boolean a(zd zd, bdp bdp) {
		return bdp.x_() ? false : bdp.eY().b() == bds.a;
	}

	protected void a(zd zd, bdp bdp, long long3) {
		fu fu6 = ((gc)bdp.cI().c(awp.d).get()).b();
		Optional<ayc> optional7 = zd.x().c(fu6);
		if (optional7.isPresent()) {
			aqi.a(bdp, bdpx -> this.a((ayc)optional7.get(), bdpx, fu6)).findFirst().ifPresent(bdp4 -> this.a(zd, bdp, bdp4, fu6, bdp4.cI().c(awp.c).isPresent()));
		}
	}

	private boolean a(ayc ayc, bdp bdp, fu fu) {
		boolean boolean5 = bdp.cI().c(awp.d).isPresent();
		if (boolean5) {
			return false;
		} else {
			Optional<gc> optional6 = bdp.cI().c(awp.c);
			bds bds7 = bdp.eY().b();
			if (bdp.eY().b() == bds.a || !bds7.b().c().test(ayc)) {
				return false;
			} else {
				return !optional6.isPresent() ? this.a(bdp, fu, ayc) : ((gc)optional6.get()).b().equals(fu);
			}
		}
	}

	private void a(zd zd, bdp bdp2, bdp bdp3, fu fu, boolean boolean5) {
		this.a(bdp2);
		if (!boolean5) {
			aqi.a(bdp3, fu, this.b, 1);
			bdp3.cI().a(awp.d, gc.a(zd.W(), fu));
			qy.c(zd, fu);
		}
	}

	private boolean a(bdp bdp, fu fu, ayc ayc) {
		czf czf5 = bdp.x().a(fu, ayc.d());
		return czf5 != null && czf5.i();
	}

	private void a(bdp bdp) {
		bdp.cI().b(awp.m);
		bdp.cI().b(awp.n);
		bdp.cI().b(awp.d);
	}
}

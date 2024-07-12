import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class aqw extends aqh<bdp> {
	final float b;

	public aqw(float float1) {
		super(ImmutableMap.of(awp.d, awq.VALUE_PRESENT), 1200);
		this.b = float1;
	}

	protected boolean a(zd zd, bdp bdp) {
		return (Boolean)bdp.cI().f().map(bfl -> bfl == bfl.b || bfl == bfl.c || bfl == bfl.d).orElse(true);
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return bdp.cI().a(awp.d);
	}

	protected void d(zd zd, bdp bdp, long long3) {
		aqi.a(bdp, ((gc)bdp.cI().c(awp.d).get()).b(), this.b, 1);
	}

	protected void c(zd zd, bdp bdp, long long3) {
		Optional<gc> optional6 = bdp.cI().c(awp.d);
		optional6.ifPresent(gc -> {
			fu fu3 = gc.b();
			zd zd4 = zd.l().a(gc.a());
			if (zd4 != null) {
				axz axz5 = zd4.x();
				if (axz5.a(fu3, ayc -> true)) {
					axz5.b(fu3);
				}

				qy.c(zd, fu3);
			}
		});
		bdp.cI().b(awp.d);
	}
}

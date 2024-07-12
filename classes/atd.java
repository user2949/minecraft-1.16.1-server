import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class atd extends aqh<bdp> {
	private long b;

	public atd() {
		super(ImmutableMap.of(awp.c, awq.VALUE_PRESENT, awp.n, awq.REGISTERED));
	}

	protected boolean a(zd zd, bdp bdp) {
		if (zd.Q() - this.b < 300L) {
			return false;
		} else if (zd.t.nextInt(2) != 0) {
			return false;
		} else {
			this.b = zd.Q();
			gc gc4 = (gc)bdp.cI().c(awp.c).get();
			return gc4.a() == zd.W() && gc4.b().a(bdp.cz(), 1.73);
		}
	}

	protected void a(zd zd, bdp bdp, long long3) {
		apr<bdp> apr6 = bdp.cI();
		apr6.a(awp.H, long3);
		apr6.c(awp.c).ifPresent(gc -> apr6.a(awp.n, new aqj(gc.b())));
		bdp.fd();
		this.a(zd, bdp);
		if (bdp.fc()) {
			bdp.fb();
		}
	}

	protected void a(zd zd, bdp bdp) {
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		Optional<gc> optional6 = bdp.cI().c(awp.c);
		if (!optional6.isPresent()) {
			return false;
		} else {
			gc gc7 = (gc)optional6.get();
			return gc7.a() == zd.W() && gc7.b().a(bdp.cz(), 1.73);
		}
	}
}

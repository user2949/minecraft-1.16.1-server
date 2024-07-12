import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class asy extends aqh<bdp> {
	private long b;

	public asy() {
		super(ImmutableMap.of(awp.r, awq.VALUE_PRESENT, awp.h, awq.VALUE_PRESENT), 350, 350);
	}

	protected boolean a(zd zd, bdp bdp) {
		return this.a(bdp);
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return long3 <= this.b && this.a(bdp);
	}

	protected void a(zd zd, bdp bdp, long long3) {
		aok aok6 = (aok)bdp.cI().c(awp.r).get();
		aqi.a(bdp, aok6, 0.5F);
		zd.a(aok6, (byte)18);
		zd.a(bdp, (byte)18);
		int integer7 = 275 + bdp.cX().nextInt(50);
		this.b = long3 + (long)integer7;
	}

	protected void d(zd zd, bdp bdp, long long3) {
		bdp bdp6 = (bdp)bdp.cI().c(awp.r).get();
		if (!(bdp.h(bdp6) > 5.0)) {
			aqi.a(bdp, bdp6, 0.5F);
			if (long3 >= this.b) {
				bdp.ff();
				bdp6.ff();
				this.a(zd, bdp, bdp6);
			} else if (bdp.cX().nextInt(35) == 0) {
				zd.a(bdp6, (byte)12);
				zd.a(bdp, (byte)12);
			}
		}
	}

	private void a(zd zd, bdp bdp2, bdp bdp3) {
		Optional<fu> optional5 = this.b(zd, bdp2);
		if (!optional5.isPresent()) {
			zd.a(bdp3, (byte)13);
			zd.a(bdp2, (byte)13);
		} else {
			Optional<bdp> optional6 = this.a(bdp2, bdp3);
			if (optional6.isPresent()) {
				this.a(zd, (bdp)optional6.get(), (fu)optional5.get());
			} else {
				zd.x().b((fu)optional5.get());
				qy.c(zd, (fu)optional5.get());
			}
		}
	}

	protected void d(zd zd, bdp bdp, long long3) {
		bdp.cI().b(awp.r);
	}

	private boolean a(bdp bdp) {
		apr<bdp> apr3 = bdp.cI();
		Optional<aok> optional4 = apr3.c(awp.r).filter(aok -> aok.U() == aoq.aO);
		return !optional4.isPresent() ? false : aqi.a(apr3, awp.r, aoq.aO) && bdp.f() && ((aok)optional4.get()).f();
	}

	private Optional<fu> b(zd zd, bdp bdp) {
		return zd.x().a(ayc.r.c(), fu -> this.a(bdp, fu), bdp.cA(), 48);
	}

	private boolean a(bdp bdp, fu fu) {
		czf czf4 = bdp.x().a(fu, ayc.r.d());
		return czf4 != null && czf4.i();
	}

	private Optional<bdp> a(bdp bdp1, bdp bdp2) {
		bdp bdp4 = bdp1.b(bdp2);
		if (bdp4 == null) {
			return Optional.empty();
		} else {
			bdp1.c_(6000);
			bdp2.c_(6000);
			bdp4.c_(-24000);
			bdp4.b(bdp1.cC(), bdp1.cD(), bdp1.cG(), 0.0F, 0.0F);
			bdp1.l.c(bdp4);
			bdp1.l.a(bdp4, (byte)12);
			return Optional.of(bdp4);
		}
	}

	private void a(zd zd, bdp bdp, fu fu) {
		gc gc5 = gc.a(zd.W(), fu);
		bdp.cI().a(awp.b, gc5);
	}
}

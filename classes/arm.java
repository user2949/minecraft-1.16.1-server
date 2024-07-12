import com.google.common.collect.ImmutableMap;

public class arm extends aqh<bdp> {
	final bds b;

	public arm(bds bds) {
		super(ImmutableMap.of(awp.c, awq.VALUE_PRESENT, awp.g, awq.VALUE_PRESENT));
		this.b = bds;
	}

	protected void a(zd zd, bdp bdp, long long3) {
		gc gc6 = (gc)bdp.cI().c(awp.c).get();
		zd.x().c(gc6.b()).ifPresent(ayc -> aqi.a(bdp, bdpxx -> this.a(gc6, ayc, bdpxx)).reduce(bdp, arm::a));
	}

	private static bdp a(bdp bdp1, bdp bdp2) {
		bdp bdp3;
		bdp bdp4;
		if (bdp1.eM() > bdp2.eM()) {
			bdp3 = bdp1;
			bdp4 = bdp2;
		} else {
			bdp3 = bdp2;
			bdp4 = bdp1;
		}

		bdp4.cI().b(awp.c);
		return bdp3;
	}

	private boolean a(gc gc, ayc ayc, bdp bdp) {
		return this.a(bdp) && gc.equals(bdp.cI().c(awp.c).get()) && this.a(ayc, bdp.eY().b());
	}

	private boolean a(ayc ayc, bds bds) {
		return bds.b().c().test(ayc);
	}

	private boolean a(bdp bdp) {
		return bdp.cI().c(awp.c).isPresent();
	}
}

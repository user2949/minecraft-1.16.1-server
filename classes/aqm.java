import com.google.common.collect.ImmutableMap;

public class aqm<E extends aoz & bbo, T extends aoy> extends aqh<E> {
	private int b;
	private aqm.a c = aqm.a.UNCHARGED;

	public aqm() {
		super(ImmutableMap.of(awp.n, awq.REGISTERED, awp.o, awq.VALUE_PRESENT), 1200);
	}

	protected boolean a(zd zd, E aoz) {
		aoy aoy4 = a(aoz);
		return aoz.a(bkk.qP) && aqi.c(aoz, aoy4) && aqi.a(aoz, aoy4, 0);
	}

	protected boolean b(zd zd, E aoz, long long3) {
		return aoz.cI().a(awp.o) && this.a(zd, aoz);
	}

	protected void d(zd zd, E aoz, long long3) {
		aoy aoy6 = a(aoz);
		this.b(aoz, aoy6);
		this.a(aoz, aoy6);
	}

	protected void c(zd zd, E aoz, long long3) {
		if (aoz.dV()) {
			aoz.eb();
		}

		if (aoz.a(bkk.qP)) {
			aoz.b(false);
			biz.a(aoz.dX(), false);
		}
	}

	private void a(E aoz, aoy aoy) {
		if (this.c == aqm.a.UNCHARGED) {
			aoz.c(bet.a(aoz, bkk.qP));
			this.c = aqm.a.CHARGING;
			aoz.b(true);
		} else if (this.c == aqm.a.CHARGING) {
			if (!aoz.dV()) {
				this.c = aqm.a.UNCHARGED;
			}

			int integer4 = aoz.dZ();
			bki bki5 = aoz.dX();
			if (integer4 >= biz.g(bki5)) {
				aoz.ea();
				this.c = aqm.a.CHARGED;
				this.b = 20 + aoz.cX().nextInt(20);
				aoz.b(false);
			}
		} else if (this.c == aqm.a.CHARGED) {
			this.b--;
			if (this.b == 0) {
				this.c = aqm.a.READY_TO_ATTACK;
			}
		} else if (this.c == aqm.a.READY_TO_ATTACK) {
			aoz.a(aoy, 1.0F);
			bki bki4 = aoz.b(bet.a(aoz, bkk.qP));
			biz.a(bki4, false);
			this.c = aqm.a.UNCHARGED;
		}
	}

	private void b(aoz aoz, aoy aoy) {
		aoz.cI().a(awp.n, new aqp(aoy, true));
	}

	private static aoy a(aoy aoy) {
		return (aoy)aoy.cI().c(awp.o).get();
	}

	static enum a {
		UNCHARGED,
		CHARGING,
		CHARGED,
		READY_TO_ATTACK;
	}
}

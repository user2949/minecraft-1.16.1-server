import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class ass extends aqh<bdp> {
	private long b;
	private long c;
	private int d;
	private Optional<fu> e = Optional.empty();

	public ass() {
		super(ImmutableMap.of(awp.n, awq.VALUE_ABSENT, awp.m, awq.VALUE_ABSENT));
	}

	protected boolean a(zd zd, bdp bdp) {
		if (bdp.K % 10 == 0 && (this.c == 0L || this.c + 160L <= (long)bdp.K)) {
			if (bdp.eU().a(bkk.mG) <= 0) {
				return false;
			} else {
				this.e = this.b(zd, bdp);
				return this.e.isPresent();
			}
		} else {
			return false;
		}
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return this.d < 80 && this.e.isPresent();
	}

	private Optional<fu> b(zd zd, bdp bdp) {
		fu.a a4 = new fu.a();
		Optional<fu> optional5 = Optional.empty();
		int integer6 = 0;

		for (int integer7 = -1; integer7 <= 1; integer7++) {
			for (int integer8 = -1; integer8 <= 1; integer8++) {
				for (int integer9 = -1; integer9 <= 1; integer9++) {
					a4.a(bdp.cA(), integer7, integer8, integer9);
					if (this.a(a4, zd)) {
						if (zd.t.nextInt(++integer6) == 0) {
							optional5 = Optional.of(a4.h());
						}
					}
				}
			}
		}

		return optional5;
	}

	private boolean a(fu fu, zd zd) {
		cfj cfj4 = zd.d_(fu);
		bvr bvr5 = cfj4.b();
		return bvr5 instanceof bwv && !((bwv)bvr5).h(cfj4);
	}

	protected void a(zd zd, bdp bdp, long long3) {
		this.a(bdp);
		bdp.a(aor.MAINHAND, new bki(bkk.mG));
		this.b = long3;
		this.d = 0;
	}

	private void a(bdp bdp) {
		this.e.ifPresent(fu -> {
			aqj aqj3 = new aqj(fu);
			bdp.cI().a(awp.n, aqj3);
			bdp.cI().a(awp.m, new awr(aqj3, 0.5F, 1));
		});
	}

	protected void c(zd zd, bdp bdp, long long3) {
		bdp.a(aor.MAINHAND, bki.b);
		this.c = (long)bdp.K;
	}

	protected void d(zd zd, bdp bdp, long long3) {
		fu fu6 = (fu)this.e.get();
		if (long3 >= this.b && fu6.a(bdp.cz(), 1.0)) {
			bki bki7 = bki.b;
			anm anm8 = bdp.eU();
			int integer9 = anm8.ab_();

			for (int integer10 = 0; integer10 < integer9; integer10++) {
				bki bki11 = anm8.a(integer10);
				if (bki11.b() == bkk.mG) {
					bki7 = bki11;
					break;
				}
			}

			if (!bki7.a() && bip.a(bki7, zd, fu6)) {
				zd.c(2005, fu6, 0);
				this.e = this.b(zd, bdp);
				this.a(bdp);
				this.b = long3 + 40L;
			}

			this.d++;
		}
	}
}

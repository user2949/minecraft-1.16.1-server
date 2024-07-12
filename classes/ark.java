import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;

public class ark extends aqh<aoz> {
	@Nullable
	private czf b;
	@Nullable
	private fu c;
	private float d;
	private int e;

	public ark(int integer) {
		super(ImmutableMap.of(awp.D, awq.REGISTERED, awp.t, awq.VALUE_ABSENT, awp.m, awq.VALUE_PRESENT), integer);
	}

	protected boolean a(zd zd, aoz aoz) {
		apr<?> apr4 = aoz.cI();
		awr awr5 = (awr)apr4.c(awp.m).get();
		boolean boolean6 = this.a(aoz, awr5);
		if (!boolean6 && this.a(aoz, awr5, zd.Q())) {
			this.c = awr5.a().b();
			return true;
		} else {
			apr4.b(awp.m);
			if (boolean6) {
				apr4.b(awp.D);
			}

			return false;
		}
	}

	protected boolean b(zd zd, aoz aoz, long long3) {
		if (this.b != null && this.c != null) {
			Optional<awr> optional6 = aoz.cI().c(awp.m);
			awv awv7 = aoz.x();
			return !awv7.m() && optional6.isPresent() && !this.a(aoz, (awr)optional6.get());
		} else {
			return false;
		}
	}

	protected void c(zd zd, aoz aoz, long long3) {
		aoz.x().o();
		aoz.cI().b(awp.m);
		aoz.cI().b(awp.t);
		this.b = null;
	}

	protected void c(zd zd, aoz aoz, long long3) {
		aoz.cI().a(awp.t, this.b);
		aoz.x().a(this.b, (double)this.d);
		this.e = zd.v_().nextInt(10);
	}

	protected void d(zd zd, aoz aoz, long long3) {
		this.e--;
		if (this.e <= 0) {
			czf czf6 = aoz.x().k();
			apr<?> apr7 = aoz.cI();
			if (this.b != czf6) {
				this.b = czf6;
				apr7.a(awp.t, czf6);
			}

			if (czf6 != null && this.c != null) {
				awr awr8 = (awr)apr7.c(awp.m).get();
				if (awr8.a().b().j(this.c) > 4.0 && this.a(aoz, awr8, zd.Q())) {
					this.c = awr8.a().b();
					this.c(zd, aoz, long3);
				}
			}
		}
	}

	private boolean a(aoz aoz, awr awr, long long3) {
		fu fu6 = awr.a().b();
		this.b = aoz.x().a(fu6, 0);
		this.d = awr.b();
		apr<?> apr7 = aoz.cI();
		if (this.a(aoz, awr)) {
			apr7.b(awp.D);
		} else {
			boolean boolean8 = this.b != null && this.b.i();
			if (boolean8) {
				apr7.b(awp.D);
			} else if (!apr7.a(awp.D)) {
				apr7.a(awp.D, long3);
			}

			if (this.b != null) {
				return true;
			}

			dem dem9 = axu.b((apg)aoz, 10, 7, dem.c(fu6));
			if (dem9 != null) {
				this.b = aoz.x().a(dem9.b, dem9.c, dem9.d, 0);
				return this.b != null;
			}
		}

		return false;
	}

	private boolean a(aoz aoz, awr awr) {
		return awr.a().b().k(aoz.cA()) <= awr.c();
	}
}

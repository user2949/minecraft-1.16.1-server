import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;

public class arc extends aqh<aoz> {
	private final float b;
	@Nullable
	private fu c;
	private int d;
	private int e;
	private int f;

	public arc(float float1) {
		super(ImmutableMap.of(awp.w, awq.VALUE_PRESENT, awp.m, awq.VALUE_ABSENT));
		this.b = float1;
	}

	protected boolean a(zd zd, aoz aoz) {
		return aoz.x_() && this.b(zd, aoz);
	}

	protected void a(zd zd, aoz aoz, long long3) {
		super.a(zd, aoz, long3);
		this.a(aoz).ifPresent(fu -> {
			this.c = fu;
			this.d = 100;
			this.e = 3 + zd.t.nextInt(4);
			this.f = 0;
			this.a(aoz, fu);
		});
	}

	protected void c(zd zd, aoz aoz, long long3) {
		super.c(zd, aoz, long3);
		this.c = null;
		this.d = 0;
		this.e = 0;
		this.f = 0;
	}

	protected boolean b(zd zd, aoz aoz, long long3) {
		return aoz.x_() && this.c != null && this.a(zd, this.c) && !this.e(zd, aoz) && !this.f(zd, aoz);
	}

	@Override
	protected boolean a(long long1) {
		return false;
	}

	protected void d(zd zd, aoz aoz, long long3) {
		if (!this.c(zd, aoz)) {
			this.d--;
		} else if (this.f > 0) {
			this.f--;
		} else {
			if (this.d(zd, aoz)) {
				aoz.v().a();
				this.e--;
				this.f = 5;
			}
		}
	}

	private void a(aoz aoz, fu fu) {
		aoz.cI().a(awp.m, new awr(fu, this.b, 0));
	}

	private boolean b(zd zd, aoz aoz) {
		return this.c(zd, aoz) || this.a(aoz).isPresent();
	}

	private boolean c(zd zd, aoz aoz) {
		fu fu4 = aoz.cA();
		fu fu5 = fu4.c();
		return this.a(zd, fu4) || this.a(zd, fu5);
	}

	private boolean d(zd zd, aoz aoz) {
		return this.a(zd, aoz.cA());
	}

	private boolean a(zd zd, fu fu) {
		return zd.d_(fu).a(acx.K);
	}

	private Optional<fu> a(aoz aoz) {
		return aoz.cI().c(awp.w);
	}

	private boolean e(zd zd, aoz aoz) {
		return !this.c(zd, aoz) && this.d <= 0;
	}

	private boolean f(zd zd, aoz aoz) {
		return this.c(zd, aoz) && this.e <= 0;
	}
}

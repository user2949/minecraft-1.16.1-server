import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class asf extends aqh<bdp> {
	@Nullable
	private bki b;
	private final List<bki> c = Lists.<bki>newArrayList();
	private int d;
	private int e;
	private int f;

	public asf(int integer1, int integer2) {
		super(ImmutableMap.of(awp.q, awq.VALUE_PRESENT), integer1, integer2);
	}

	public boolean a(zd zd, bdp bdp) {
		apr<?> apr4 = bdp.cI();
		if (!apr4.c(awp.q).isPresent()) {
			return false;
		} else {
			aoy aoy5 = (aoy)apr4.c(awp.q).get();
			return aoy5.U() == aoq.bb && bdp.aU() && aoy5.aU() && !bdp.x_() && bdp.h((aom)aoy5) <= 17.0;
		}
	}

	public boolean b(zd zd, bdp bdp, long long3) {
		return this.a(zd, bdp) && this.f > 0 && bdp.cI().c(awp.q).isPresent();
	}

	public void a(zd zd, bdp bdp, long long3) {
		super.a(zd, bdp, long3);
		this.c(bdp);
		this.d = 0;
		this.e = 0;
		this.f = 40;
	}

	public void d(zd zd, bdp bdp, long long3) {
		aoy aoy6 = this.c(bdp);
		this.a(aoy6, bdp);
		if (!this.c.isEmpty()) {
			this.d(bdp);
		} else {
			bdp.a(aor.MAINHAND, bki.b);
			this.f = Math.min(this.f, 40);
		}

		this.f--;
	}

	public void d(zd zd, bdp bdp, long long3) {
		super.c(zd, bdp, long3);
		bdp.cI().b(awp.q);
		bdp.a(aor.MAINHAND, bki.b);
		this.b = null;
	}

	private void a(aoy aoy, bdp bdp) {
		boolean boolean4 = false;
		bki bki5 = aoy.dC();
		if (this.b == null || !bki.c(this.b, bki5)) {
			this.b = bki5;
			boolean4 = true;
			this.c.clear();
		}

		if (boolean4 && !this.b.a()) {
			this.b(bdp);
			if (!this.c.isEmpty()) {
				this.f = 900;
				this.a(bdp);
			}
		}
	}

	private void a(bdp bdp) {
		bdp.a(aor.MAINHAND, (bki)this.c.get(0));
	}

	private void b(bdp bdp) {
		for (boz boz4 : bdp.eP()) {
			if (!boz4.p() && this.a(boz4)) {
				this.c.add(boz4.d());
			}
		}
	}

	private boolean a(boz boz) {
		return bki.c(this.b, boz.b()) || bki.c(this.b, boz.c());
	}

	private aoy c(bdp bdp) {
		apr<?> apr3 = bdp.cI();
		aoy aoy4 = (aoy)apr3.c(awp.q).get();
		apr3.a(awp.n, new aqp(aoy4, true));
		return aoy4;
	}

	private void d(bdp bdp) {
		if (this.c.size() >= 2 && ++this.d >= 40) {
			this.e++;
			this.d = 0;
			if (this.e > this.c.size() - 1) {
				this.e = 0;
			}

			bdp.a(aor.MAINHAND, (bki)this.c.get(this.e));
		}
	}
}

import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class asd extends aqh<bdp> {
	private final awp<gc> b;
	private final float c;
	private final int d;
	private final int e;
	private final int f;

	public asd(awp<gc> awp, float float2, int integer3, int integer4, int integer5) {
		super(ImmutableMap.of(awp.D, awq.REGISTERED, awp.m, awq.VALUE_ABSENT, awp, awq.VALUE_PRESENT));
		this.b = awp;
		this.c = float2;
		this.d = integer3;
		this.e = integer4;
		this.f = integer5;
	}

	private void a(bdp bdp, long long2) {
		apr<?> apr5 = bdp.cI();
		bdp.a(this.b);
		apr5.b(this.b);
		apr5.a(awp.D, long2);
	}

	protected void a(zd zd, bdp bdp, long long3) {
		apr<?> apr6 = bdp.cI();
		apr6.c(this.b).ifPresent(gc -> {
			if (this.a(zd, bdp)) {
				this.a(bdp, long3);
			} else if (this.a(zd, bdp, gc)) {
				dem dem8 = null;
				int integer9 = 0;

				for (int integer10 = 1000; integer9 < 1000 && (dem8 == null || this.a(zd, bdp, gc.a(zd.W(), new fu(dem8)))); integer9++) {
					dem8 = axu.b(bdp, 15, 7, dem.c(gc.b()));
				}

				if (integer9 == 1000) {
					this.a(bdp, long3);
					return;
				}

				apr6.a(awp.m, new awr(dem8, this.c, this.d));
			} else if (!this.b(zd, bdp, gc)) {
				apr6.a(awp.m, new awr(gc.b(), this.c, this.d));
			}
		});
	}

	private boolean a(zd zd, bdp bdp) {
		Optional<Long> optional4 = bdp.cI().c(awp.D);
		return optional4.isPresent() ? zd.Q() - (Long)optional4.get() > (long)this.f : false;
	}

	private boolean a(zd zd, bdp bdp, gc gc) {
		return gc.a() != zd.W() || gc.b().k(bdp.cA()) > this.e;
	}

	private boolean b(zd zd, bdp bdp, gc gc) {
		return gc.a() == zd.W() && gc.b().k(bdp.cA()) <= this.d;
	}
}

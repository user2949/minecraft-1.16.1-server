import com.google.common.collect.ImmutableMap;
import java.util.function.Function;

public class asb<T> extends aqh<apg> {
	private final awp<T> b;
	private final float c;
	private final int d;
	private final Function<T, dem> e;

	public asb(awp<T> awp, float float2, int integer, boolean boolean4, Function<T, dem> function) {
		super(ImmutableMap.of(awp.m, boolean4 ? awq.REGISTERED : awq.VALUE_ABSENT, awp, awq.VALUE_PRESENT));
		this.b = awp;
		this.c = float2;
		this.d = integer;
		this.e = function;
	}

	public static asb<fu> a(awp<fu> awp, float float2, int integer, boolean boolean4) {
		return new asb<>(awp, float2, integer, boolean4, dem::c);
	}

	public static asb<? extends aom> b(awp<? extends aom> awp, float float2, int integer, boolean boolean4) {
		return new asb<>(awp, float2, integer, boolean4, aom::cz);
	}

	protected boolean a(zd zd, apg apg) {
		return this.b(apg) ? false : apg.cz().a(this.a(apg), (double)this.d);
	}

	private dem a(apg apg) {
		return (dem)this.e.apply(apg.cI().c(this.b).get());
	}

	private boolean b(apg apg) {
		if (!apg.cI().a(awp.m)) {
			return false;
		} else {
			awr awr3 = (awr)apg.cI().c(awp.m).get();
			if (awr3.b() != this.c) {
				return false;
			} else {
				dem dem4 = awr3.a().a().d(apg.cz());
				dem dem5 = this.a(apg).d(apg.cz());
				return dem4.b(dem5) < 0.0;
			}
		}
	}

	protected void a(zd zd, apg apg, long long3) {
		a(apg, this.a(apg), this.c);
	}

	private static void a(apg apg, dem dem, float float3) {
		for (int integer4 = 0; integer4 < 10; integer4++) {
			dem dem5 = axu.d(apg, 16, 7, dem);
			if (dem5 != null) {
				apg.cI().a(awp.m, new awr(dem5, float3, 0));
				return;
			}
		}
	}
}

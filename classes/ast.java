import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class ast extends aqh<aoy> {
	private final awp<gc> b;
	private final Predicate<ayc> c;

	public ast(ayc ayc, awp<gc> awp) {
		super(ImmutableMap.of(awp, awq.VALUE_PRESENT));
		this.c = ayc.c();
		this.b = awp;
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		gc gc4 = (gc)aoy.cI().c(this.b).get();
		return zd.W() == gc4.a() && gc4.b().a(aoy.cz(), 16.0);
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		gc gc7 = (gc)apr6.c(this.b).get();
		fu fu8 = gc7.b();
		zd zd9 = zd.l().a(gc7.a());
		if (zd9 == null || this.a(zd9, fu8)) {
			apr6.b(this.b);
		} else if (this.a(zd9, fu8, aoy)) {
			apr6.b(this.b);
			zd.x().b(fu8);
			qy.c(zd, fu8);
		}
	}

	private boolean a(zd zd, fu fu, aoy aoy) {
		cfj cfj5 = zd.d_(fu);
		return cfj5.b().a(acx.K) && (Boolean)cfj5.c(bvm.b) && !aoy.el();
	}

	private boolean a(zd zd, fu fu) {
		return !zd.x().a(fu, this.c);
	}
}

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;

public class aqn<E extends aoy, T extends aom> extends aqh<E> {
	private final int b;
	private final BiPredicate<E, aom> c;

	public aqn(int integer, BiPredicate<E, aom> biPredicate) {
		super(ImmutableMap.of(awp.s, awq.REGISTERED));
		this.b = integer;
		this.c = biPredicate;
	}

	@Override
	protected boolean a(zd zd, E aoy) {
		aom aom4 = aoy.cs();
		aom aom5 = (aom)aoy.cI().c(awp.s).orElse(null);
		if (aom4 == null && aom5 == null) {
			return false;
		} else {
			aom aom6 = aom4 == null ? aom5 : aom4;
			return !this.a(aoy, aom6) || this.c.test(aoy, aom6);
		}
	}

	private boolean a(E aoy, aom aom) {
		return aom.aU() && aom.a(aoy, (double)this.b) && aom.l == aoy.l;
	}

	@Override
	protected void a(zd zd, E aoy, long long3) {
		aoy.l();
		aoy.cI().b(awp.s);
	}
}

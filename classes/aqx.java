import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class aqx<E extends aoy> extends aqh<E> {
	private final Predicate<E> b;
	private final int c;
	private final float d;

	public aqx(float float1, boolean boolean2, int integer) {
		this(aoy -> true, float1, boolean2, integer);
	}

	public aqx(Predicate<E> predicate, float float2, boolean boolean3, int integer) {
		super(ImmutableMap.of(awp.n, awq.REGISTERED, awp.m, boolean3 ? awq.REGISTERED : awq.VALUE_ABSENT, awp.J, awq.VALUE_PRESENT));
		this.b = predicate;
		this.c = integer;
		this.d = float2;
	}

	@Override
	protected boolean a(zd zd, E aoy) {
		return this.b.test(aoy) && this.a(aoy).a(aoy, (double)this.c);
	}

	@Override
	protected void a(zd zd, E aoy, long long3) {
		aqi.a(aoy, this.a(aoy), this.d, 0);
	}

	private bbg a(E aoy) {
		return (bbg)aoy.cI().c(awp.J).get();
	}
}

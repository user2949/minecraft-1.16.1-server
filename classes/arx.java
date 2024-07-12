import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;

public class arx extends aqh<aoy> {
	private final Predicate<aoy> b;
	private final float c;

	public arx(apa apa, float float2) {
		this(aoy -> apa.equals(aoy.U().e()), float2);
	}

	public arx(aoq<?> aoq, float float2) {
		this(aoy -> aoq.equals(aoy.U()), float2);
	}

	public arx(float float1) {
		this(aoy -> true, float1);
	}

	public arx(Predicate<aoy> predicate, float float2) {
		super(ImmutableMap.of(awp.n, awq.VALUE_ABSENT, awp.h, awq.VALUE_PRESENT));
		this.b = predicate;
		this.c = float2 * float2;
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		return ((List)aoy.cI().c(awp.h).get()).stream().anyMatch(this.b);
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		apr6.c(awp.h)
			.ifPresent(
				list -> list.stream().filter(this.b).filter(aoy2 -> aoy2.h(aoy) <= (double)this.c).findFirst().ifPresent(aoyxx -> apr6.a(awp.n, new aqp(aoyxx, true)))
			);
	}
}

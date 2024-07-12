import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;

public class arz extends aqh<aoy> {
	private final aoq<?> b;
	private final int c;
	private final Predicate<aoy> d;
	private final Predicate<aoy> e;

	public arz(aoq<?> aoq, int integer, Predicate<aoy> predicate3, Predicate<aoy> predicate4) {
		super(ImmutableMap.of(awp.n, awq.REGISTERED, awp.q, awq.VALUE_ABSENT, awp.h, awq.VALUE_PRESENT));
		this.b = aoq;
		this.c = integer * integer;
		this.d = predicate4;
		this.e = predicate3;
	}

	public arz(aoq<?> aoq, int integer) {
		this(aoq, integer, aoy -> true, aoy -> true);
	}

	@Override
	public boolean a(zd zd, aoy aoy) {
		return this.e.test(aoy) && this.b(aoy).stream().anyMatch(this::a);
	}

	@Override
	public void a(zd zd, aoy aoy, long long3) {
		super.a(zd, aoy, long3);
		apr<?> apr6 = aoy.cI();
		apr6.c(awp.h).ifPresent(list -> list.stream().filter(aoy2 -> aoy2.h(aoy) <= (double)this.c).filter(this::a).findFirst().ifPresent(aoyxx -> {
				apr6.a(awp.q, aoyxx);
				apr6.a(awp.n, new aqp(aoyxx, true));
			}));
	}

	private boolean a(aoy aoy) {
		return this.b.equals(aoy.U()) && this.d.test(aoy);
	}

	private List<aoy> b(aoy aoy) {
		return (List<aoy>)aoy.cI().c(awp.h).get();
	}
}

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;

public class ara<E extends aoy, T extends aoy> extends aqh<E> {
	private final int b;
	private final float c;
	private final aoq<? extends T> d;
	private final int e;
	private final Predicate<T> f;
	private final Predicate<E> g;
	private final awp<T> h;

	public ara(aoq<? extends T> aoq, int integer2, Predicate<E> predicate3, Predicate<T> predicate4, awp<T> awp, float float6, int integer7) {
		super(ImmutableMap.of(awp.n, awq.REGISTERED, awp.m, awq.VALUE_ABSENT, awp.h, awq.VALUE_PRESENT));
		this.d = aoq;
		this.c = float6;
		this.e = integer2 * integer2;
		this.b = integer7;
		this.f = predicate4;
		this.g = predicate3;
		this.h = awp;
	}

	public static <T extends aoy> ara<aoy, T> a(aoq<? extends T> aoq, int integer2, awp<T> awp, float float4, int integer5) {
		return new ara<>(aoq, integer2, aoy -> true, aoy -> true, awp, float4, integer5);
	}

	@Override
	protected boolean a(zd zd, E aoy) {
		return this.g.test(aoy) && this.a(aoy);
	}

	private boolean a(E aoy) {
		List<aoy> list3 = (List<aoy>)aoy.cI().c(awp.h).get();
		return list3.stream().anyMatch(this::b);
	}

	private boolean b(aoy aoy) {
		return this.d.equals(aoy.U()) && this.f.test(aoy);
	}

	@Override
	protected void a(zd zd, E aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		apr6.c(awp.h)
			.ifPresent(
				list -> list.stream()
						.filter(aoyxx -> this.d.equals(aoyxx.U()))
						.map(aoyxx -> aoyxx)
						.filter(aoy2 -> aoy2.h(aoy) <= (double)this.e)
						.filter(this.f)
						.findFirst()
						.ifPresent(aoyxx -> {
							apr6.a(this.h, (T)aoyxx);
							apr6.a(awp.n, new aqp(aoyxx, true));
							apr6.a(awp.m, new awr(new aqp(aoyxx, false), this.c, this.b));
						})
			);
	}
}

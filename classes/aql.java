import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class aql<E extends aoz, T> extends aqh<E> {
	private final Predicate<E> b;
	private final awp<? extends T> c;
	private final awp<T> d;
	private final adx e;

	public aql(Predicate<E> predicate, awp<? extends T> awp2, awp<T> awp3, adx adx) {
		super(ImmutableMap.of(awp2, awq.VALUE_PRESENT, awp3, awq.VALUE_ABSENT));
		this.b = predicate;
		this.c = awp2;
		this.d = awp3;
		this.e = adx;
	}

	protected boolean a(zd zd, E aoz) {
		return this.b.test(aoz);
	}

	protected void a(zd zd, E aoz, long long3) {
		apr<?> apr6 = aoz.cI();
		apr6.a(this.d, (T)apr6.c(this.c).get(), (long)this.e.a(zd.t));
	}
}

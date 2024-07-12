import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;

public class aqq<E extends aoy> extends aqh<E> {
	private final Predicate<E> b;
	private final awp<?> c;

	public aqq(Predicate<E> predicate, awp<?> awp) {
		super(ImmutableMap.of(awp, awq.VALUE_PRESENT));
		this.b = predicate;
		this.c = awp;
	}

	@Override
	protected boolean a(zd zd, E aoy) {
		return this.b.test(aoy);
	}

	@Override
	protected void a(zd zd, E aoy, long long3) {
		aoy.cI().b(this.c);
	}
}

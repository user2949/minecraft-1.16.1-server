import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Predicate;

public class ask<E extends aoz> extends aqh<E> {
	private final Predicate<aoy> b;

	public ask(Predicate<aoy> predicate) {
		super(ImmutableMap.of(awp.o, awq.VALUE_PRESENT, awp.D, awq.REGISTERED));
		this.b = predicate;
	}

	public ask() {
		this(aoy -> false);
	}

	protected void a(zd zd, E aoz, long long3) {
		if (a(aoz)) {
			this.d(aoz);
		} else if (this.c(aoz)) {
			this.d(aoz);
		} else if (this.a(aoz)) {
			this.d(aoz);
		} else if (!aop.f.test(this.b(aoz))) {
			this.d(aoz);
		} else if (this.b.test(this.b(aoz))) {
			this.d(aoz);
		}
	}

	private boolean a(E aoz) {
		return this.b(aoz).l != aoz.l;
	}

	private aoy b(E aoz) {
		return (aoy)aoz.cI().c(awp.o).get();
	}

	private static <E extends aoy> boolean a(E aoy) {
		Optional<Long> optional2 = aoy.cI().c(awp.D);
		return optional2.isPresent() && aoy.l.Q() - (Long)optional2.get() > 200L;
	}

	private boolean c(E aoz) {
		Optional<aoy> optional3 = aoz.cI().c(awp.o);
		return optional3.isPresent() && !((aoy)optional3.get()).aU();
	}

	private void d(E aoz) {
		aoz.cI().b(awp.o);
	}
}

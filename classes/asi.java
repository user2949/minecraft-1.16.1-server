import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class asi<E extends aoz> extends aqh<E> {
	private final Predicate<E> b;
	private final Function<E, Optional<? extends aoy>> c;

	public asi(Predicate<E> predicate, Function<E, Optional<? extends aoy>> function) {
		super(ImmutableMap.of(awp.o, awq.VALUE_ABSENT, awp.D, awq.REGISTERED));
		this.b = predicate;
		this.c = function;
	}

	public asi(Function<E, Optional<? extends aoy>> function) {
		this(aoz -> true, function);
	}

	protected boolean a(zd zd, E aoz) {
		if (!this.b.test(aoz)) {
			return false;
		} else {
			Optional<? extends aoy> optional4 = (Optional<? extends aoy>)this.c.apply(aoz);
			return optional4.isPresent() && ((aoy)optional4.get()).aU();
		}
	}

	protected void a(zd zd, E aoz, long long3) {
		((Optional)this.c.apply(aoz)).ifPresent(aoy -> this.a(aoz, aoy));
	}

	private void a(E aoz, aoy aoy) {
		aoz.cI().a(awp.o, aoy);
		aoz.cI().b(awp.D);
	}
}

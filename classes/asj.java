import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;

public class asj extends aqh<aoy> {
	private final int b;
	private final BiPredicate<aoy, aoy> c;

	public asj(int integer, BiPredicate<aoy, aoy> biPredicate) {
		super(ImmutableMap.of(awp.o, awq.VALUE_PRESENT, awp.L, awq.REGISTERED, awp.Q, awq.VALUE_ABSENT, awp.R, awq.REGISTERED));
		this.b = integer;
		this.c = biPredicate;
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		return this.a(aoy).dk();
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		aoy aoy6 = this.a(aoy);
		if (this.c.test(aoy, aoy6)) {
			aoy.cI().a(awp.R, true, (long)this.b);
		}

		aoy.cI().a(awp.Q, aoy6.cA(), (long)this.b);
		if (aoy6.U() != aoq.bb || zd.S().b(bpx.F)) {
			aoy.cI().b(awp.o);
			aoy.cI().b(awp.L);
		}
	}

	private aoy a(aoy aoy) {
		return (aoy)aoy.cI().c(awp.o).get();
	}
}

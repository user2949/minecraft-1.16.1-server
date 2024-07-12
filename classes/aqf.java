import com.google.common.collect.ImmutableMap;
import java.util.List;

public class aqf<E extends aoz> extends aqh<E> {
	private final int b;
	private final float c;

	public aqf(int integer, float float2) {
		super(ImmutableMap.of(awp.m, awq.VALUE_ABSENT, awp.n, awq.REGISTERED, awp.o, awq.VALUE_PRESENT, awp.h, awq.VALUE_PRESENT));
		this.b = integer;
		this.c = float2;
	}

	protected boolean a(zd zd, E aoz) {
		return this.a(aoz) && this.b(aoz);
	}

	protected void a(zd zd, E aoz, long long3) {
		aoz.cI().a(awp.n, new aqp(this.c(aoz), true));
		aoz.u().a(-this.c, 0.0F);
		aoz.p = aec.b(aoz.p, aoz.aJ, 0.0F);
	}

	private boolean a(E aoz) {
		return ((List)aoz.cI().c(awp.h).get()).contains(this.c(aoz));
	}

	private boolean b(E aoz) {
		return this.c(aoz).a(aoz, (double)this.b);
	}

	private aoy c(E aoz) {
		return (aoy)aoz.cI().c(awp.o).get();
	}
}

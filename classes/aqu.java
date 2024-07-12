import com.google.common.collect.ImmutableMap;
import java.util.Random;

public class aqu<E extends aoz> extends aqh<E> {
	private final int b;
	private final float c;

	public aqu(int integer, float float2) {
		super(ImmutableMap.of(awp.Q, awq.VALUE_PRESENT, awp.o, awq.VALUE_ABSENT, awp.m, awq.VALUE_ABSENT, awp.n, awq.REGISTERED));
		this.b = integer;
		this.c = float2;
	}

	protected void a(zd zd, aoz aoz, long long3) {
		fu fu6 = a(aoz);
		boolean boolean7 = fu6.a(aoz.cA(), (double)this.b);
		if (!boolean7) {
			aqi.a(aoz, a(aoz, fu6), this.c, this.b);
		}
	}

	private static fu a(aoz aoz, fu fu) {
		Random random3 = aoz.l.t;
		return fu.b(a(random3), 0, a(random3));
	}

	private static int a(Random random) {
		return random.nextInt(3) - 1;
	}

	private static fu a(aoz aoz) {
		return (fu)aoz.cI().c(awp.Q).get();
	}
}

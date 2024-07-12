import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;

public class aqc extends aqh<ayk> {
	private final aoq<? extends ayk> b;
	private final float c;
	private long d;

	public aqc(aoq<? extends ayk> aoq, float float2) {
		super(ImmutableMap.of(awp.h, awq.VALUE_PRESENT, awp.r, awq.VALUE_ABSENT, awp.m, awq.REGISTERED, awp.n, awq.REGISTERED), 325);
		this.b = aoq;
		this.c = float2;
	}

	protected boolean a(zd zd, ayk ayk) {
		return ayk.eT() && this.c(ayk).isPresent();
	}

	protected void a(zd zd, ayk ayk, long long3) {
		ayk ayk6 = (ayk)this.c(ayk).get();
		ayk.cI().a(awp.r, ayk6);
		ayk6.cI().a(awp.r, ayk);
		aqi.a(ayk, ayk6, this.c);
		int integer7 = 275 + ayk.cX().nextInt(50);
		this.d = long3 + (long)integer7;
	}

	protected boolean b(zd zd, ayk ayk, long long3) {
		if (!this.b(ayk)) {
			return false;
		} else {
			ayk ayk6 = this.a(ayk);
			return ayk6.aU() && ayk.a(ayk6) && aqi.a(ayk.cI(), ayk6) && long3 <= this.d;
		}
	}

	protected void d(zd zd, ayk ayk, long long3) {
		ayk ayk6 = this.a(ayk);
		aqi.a(ayk, ayk6, this.c);
		if (ayk.a(ayk6, 3.0)) {
			if (long3 >= this.d) {
				ayk.a(zd, ayk6);
				ayk.cI().b(awp.r);
				ayk6.cI().b(awp.r);
			}
		}
	}

	protected void d(zd zd, ayk ayk, long long3) {
		ayk.cI().b(awp.r);
		ayk.cI().b(awp.m);
		ayk.cI().b(awp.n);
		this.d = 0L;
	}

	private ayk a(ayk ayk) {
		return (ayk)ayk.cI().c(awp.r).get();
	}

	private boolean b(ayk ayk) {
		apr<?> apr3 = ayk.cI();
		return apr3.a(awp.r) && ((aok)apr3.c(awp.r).get()).U() == this.b;
	}

	private Optional<? extends ayk> c(ayk ayk) {
		return ((List)ayk.cI().c(awp.h).get()).stream().filter(aoy -> aoy.U() == this.b).map(aoy -> (ayk)aoy).filter(ayk::a).findFirst();
	}
}

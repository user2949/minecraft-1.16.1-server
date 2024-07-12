import com.google.common.collect.ImmutableMap;

public class bde<E extends bdc> extends aqh<E> {
	public bde() {
		super(ImmutableMap.of(awp.o, awq.VALUE_PRESENT, awp.P, awq.REGISTERED));
	}

	protected void a(zd zd, E bdc, long long3) {
		if (this.a(bdc)) {
			bdd.j(bdc);
		}
	}

	private boolean a(E bdc) {
		aoy aoy3 = (aoy)bdc.cI().c(awp.o).get();
		return aoy3.U() == aoq.G && aoy3.dk();
	}
}

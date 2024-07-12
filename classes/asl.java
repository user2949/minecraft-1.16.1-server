import com.google.common.collect.ImmutableMap;

public class asl<E extends aoz> extends aqh<E> {
	public asl() {
		super(ImmutableMap.of(awp.L, awq.VALUE_PRESENT));
	}

	protected void a(zd zd, E aoz, long long3) {
		aqi.a(aoz, awp.L).ifPresent(aoy -> {
			if (aoy.dk() && (aoy.U() != aoq.bb || zd.S().b(bpx.F))) {
				aoz.cI().b(awp.L);
			}
		});
	}
}

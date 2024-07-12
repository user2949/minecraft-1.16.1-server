import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;

public class ash extends aqh<aoy> {
	public ash() {
		super(ImmutableMap.of(awp.m, awq.REGISTERED, awp.n, awq.REGISTERED, awp.e, awq.VALUE_PRESENT, awp.h, awq.VALUE_PRESENT, awp.q, awq.VALUE_ABSENT));
	}

	@Override
	protected boolean a(zd zd, aoy aoy) {
		apr<?> apr4 = aoy.cI();
		Optional<gc> optional5 = apr4.c(awp.e);
		return zd.v_().nextInt(100) == 0
			&& optional5.isPresent()
			&& zd.W() == ((gc)optional5.get()).a()
			&& ((gc)optional5.get()).b().a(aoy.cz(), 4.0)
			&& ((List)apr4.c(awp.h).get()).stream().anyMatch(aoyx -> aoq.aO.equals(aoyx.U()));
	}

	@Override
	protected void a(zd zd, aoy aoy, long long3) {
		apr<?> apr6 = aoy.cI();
		apr6.c(awp.h).ifPresent(list -> list.stream().filter(aoyxx -> aoq.aO.equals(aoyxx.U())).filter(aoy2 -> aoy2.h(aoy) <= 32.0).findFirst().ifPresent(aoyxx -> {
				apr6.a(awp.q, aoyxx);
				apr6.a(awp.n, new aqp(aoyxx, true));
				apr6.a(awp.m, new awr(new aqp(aoyxx, false), 0.3F, 1));
			}));
	}
}

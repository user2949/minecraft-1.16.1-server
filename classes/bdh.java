import com.google.common.collect.ImmutableMap;
import java.util.Optional;

public class bdh<E extends bdc> extends aqh<E> {
	private final int b;

	public bdh(int integer) {
		super(ImmutableMap.of(awp.N, awq.VALUE_PRESENT, awp.J, awq.REGISTERED));
		this.b = integer;
	}

	protected boolean a(zd zd, E bdc) {
		if (!bdc.dD().a()) {
			return false;
		} else {
			Optional<bbg> optional4 = bdc.cI().c(awp.J);
			return !optional4.isPresent() ? true : !((bbg)optional4.get()).a(bdc, (double)this.b);
		}
	}

	protected void a(zd zd, E bdc, long long3) {
		bdc.cI().b(awp.N);
	}
}

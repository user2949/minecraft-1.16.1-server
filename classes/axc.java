import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class axc extends axn<aoy> {
	public axc() {
		this(200);
	}

	public axc(int integer) {
		super(integer);
	}

	@Override
	protected void a(zd zd, aoy aoy) {
		a(zd.Q(), aoy);
	}

	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.g);
	}

	public static void a(long long1, aoy aoy) {
		apr<?> apr4 = aoy.cI();
		Optional<List<aoy>> optional5 = apr4.c(awp.g);
		if (optional5.isPresent()) {
			boolean boolean6 = ((List)optional5.get()).stream().anyMatch(aoyx -> aoyx.U().equals(aoq.K));
			if (boolean6) {
				apr4.a(awp.E, long1);
			}
		}
	}
}

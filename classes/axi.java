import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class axi extends axn<aoy> {
	private static final axs a = new axs().a(16.0).b().d();

	@Override
	protected void a(zd zd, aoy aoy) {
		List<aoy> list4 = zd.a(aoy.class, aoy.cb().c(16.0, 16.0, 16.0), aoy2 -> aoy2 != aoy && aoy2.aU());
		list4.sort(Comparator.comparingDouble(aoy::h));
		apr<?> apr5 = aoy.cI();
		apr5.a(awp.g, list4);
		apr5.a(awp.h, (List<aoy>)list4.stream().filter(aoy2 -> a.a(aoy, aoy2)).collect(Collectors.toList()));
	}

	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.g, awp.h);
	}
}

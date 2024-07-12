import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class axk extends axn<aoy> {
	private static final axs a = new axs().a(16.0).b().d();

	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.j, awp.k, awp.l);
	}

	@Override
	protected void a(zd zd, aoy aoy) {
		List<bec> list4 = (List<bec>)zd.w()
			.stream()
			.filter(aop.g)
			.filter(ze -> aoy.h(ze) < 256.0)
			.sorted(Comparator.comparingDouble(aoy::h))
			.collect(Collectors.toList());
		apr<?> apr5 = aoy.cI();
		apr5.a(awp.j, list4);
		List<bec> list6 = (List<bec>)list4.stream().filter(bec -> a.a(aoy, bec)).collect(Collectors.toList());
		apr5.a(awp.k, list6.isEmpty() ? null : (bec)list6.get(0));
		Optional<bec> optional7 = list6.stream().filter(aop.f).findFirst();
		apr5.a(awp.l, optional7);
	}
}

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class axh extends axn<aoz> {
	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.J);
	}

	protected void a(zd zd, aoz aoz) {
		apr<?> apr4 = aoz.cI();
		List<bbg> list5 = zd.a(bbg.class, aoz.cb().c(8.0, 4.0, 8.0), bbg -> true);
		list5.sort(Comparator.comparingDouble(aoz::h));
		Optional<bbg> optional6 = list5.stream().filter(bbg -> aoz.i(bbg.g())).filter(bbg -> bbg.a(aoz, 9.0)).filter(aoz::D).findFirst();
		apr4.a(awp.J, optional6);
	}
}

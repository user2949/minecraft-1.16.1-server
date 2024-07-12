import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class axp extends axn<aoy> {
	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.i);
	}

	@Override
	protected void a(zd zd, aoy aoy) {
		aoy.cI().a(awp.i, this.a(aoy));
	}

	private List<aoy> a(aoy aoy) {
		return (List<aoy>)this.c(aoy).stream().filter(this::b).collect(Collectors.toList());
	}

	private boolean b(aoy aoy) {
		return aoy.U() == aoq.aO && aoy.x_();
	}

	private List<aoy> c(aoy aoy) {
		return (List<aoy>)aoy.cI().c(awp.h).orElse(Lists.newArrayList());
	}
}

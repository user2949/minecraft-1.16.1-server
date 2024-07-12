import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class axa extends axn<aok> {
	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.I, awp.h);
	}

	protected void a(zd zd, aok aok) {
		aok.cI().c(awp.h).ifPresent(list -> this.a(aok, list));
	}

	private void a(aok aok, List<aoy> list) {
		Optional<aok> optional4 = list.stream().filter(aoy -> aoy.U() == aok.U()).map(aoy -> (aok)aoy).filter(aokx -> !aokx.x_()).findFirst();
		aok.cI().a(awp.I, optional4);
	}
}

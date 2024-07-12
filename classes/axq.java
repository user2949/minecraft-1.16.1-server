import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class axq extends axn<aoy> {
	private static final ImmutableMap<aoq<?>, Float> a = ImmutableMap.<aoq<?>, Float>builder()
		.put(aoq.q, 8.0F)
		.put(aoq.w, 12.0F)
		.put(aoq.I, 8.0F)
		.put(aoq.J, 12.0F)
		.put(aoq.aj, 15.0F)
		.put(aoq.ao, 12.0F)
		.put(aoq.aN, 8.0F)
		.put(aoq.aP, 10.0F)
		.put(aoq.aW, 10.0F)
		.put(aoq.aX, 8.0F)
		.put(aoq.aZ, 8.0F)
		.build();

	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.A);
	}

	@Override
	protected void a(zd zd, aoy aoy) {
		aoy.cI().a(awp.A, this.a(aoy));
	}

	private Optional<aoy> a(aoy aoy) {
		return this.b(aoy).flatMap(list -> list.stream().filter(this::c).filter(aoy2 -> this.a(aoy, aoy2)).min((aoy2, aoy3) -> this.a(aoy, aoy2, aoy3)));
	}

	private Optional<List<aoy>> b(aoy aoy) {
		return aoy.cI().c(awp.h);
	}

	private int a(aoy aoy1, aoy aoy2, aoy aoy3) {
		return aec.c(aoy2.h(aoy1) - aoy3.h(aoy1));
	}

	private boolean a(aoy aoy1, aoy aoy2) {
		float float4 = a.get(aoy2.U());
		return aoy2.h(aoy1) <= (double)(float4 * float4);
	}

	private boolean c(aoy aoy) {
		return a.containsKey(aoy.U());
	}
}

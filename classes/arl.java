import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class arl extends aqh<apg> {
	public arl() {
		super(ImmutableMap.of(awp.i, awq.VALUE_PRESENT, awp.m, awq.VALUE_ABSENT, awp.n, awq.REGISTERED, awp.q, awq.REGISTERED));
	}

	protected boolean a(zd zd, apg apg) {
		return zd.v_().nextInt(10) == 0 && this.e(apg);
	}

	protected void a(zd zd, apg apg, long long3) {
		aoy aoy6 = this.b((aoy)apg);
		if (aoy6 != null) {
			this.a(zd, apg, aoy6);
		} else {
			Optional<aoy> optional7 = this.b(apg);
			if (optional7.isPresent()) {
				a(apg, (aoy)optional7.get());
			} else {
				this.a(apg).ifPresent(aoy -> a(apg, aoy));
			}
		}
	}

	private void a(zd zd, apg apg, aoy aoy) {
		for (int integer5 = 0; integer5 < 10; integer5++) {
			dem dem6 = axu.b(apg, 20, 8);
			if (dem6 != null && zd.b_(new fu(dem6))) {
				apg.cI().a(awp.m, new awr(dem6, 0.6F, 0));
				return;
			}
		}
	}

	private static void a(apg apg, aoy aoy) {
		apr<?> apr3 = apg.cI();
		apr3.a(awp.q, aoy);
		apr3.a(awp.n, new aqp(aoy, true));
		apr3.a(awp.m, new awr(new aqp(aoy, false), 0.6F, 1));
	}

	private Optional<aoy> a(apg apg) {
		return this.d(apg).stream().findAny();
	}

	private Optional<aoy> b(apg apg) {
		Map<aoy, Integer> map3 = this.c(apg);
		return map3.entrySet()
			.stream()
			.sorted(Comparator.comparingInt(Entry::getValue))
			.filter(entry -> (Integer)entry.getValue() > 0 && (Integer)entry.getValue() <= 5)
			.map(Entry::getKey)
			.findFirst();
	}

	private Map<aoy, Integer> c(apg apg) {
		Map<aoy, Integer> map3 = Maps.<aoy, Integer>newHashMap();
		this.d(apg).stream().filter(this::c).forEach(aoy -> {
			Integer var10000 = (Integer)map3.compute(this.a(aoy), (aoyx, integer) -> integer == null ? 1 : integer + 1);
		});
		return map3;
	}

	private List<aoy> d(apg apg) {
		return (List<aoy>)apg.cI().c(awp.i).get();
	}

	private aoy a(aoy aoy) {
		return (aoy)aoy.cI().c(awp.q).get();
	}

	@Nullable
	private aoy b(aoy aoy) {
		return (aoy)((List)aoy.cI().c(awp.i).get()).stream().filter(aoy2 -> this.a(aoy, aoy2)).findAny().orElse(null);
	}

	private boolean c(aoy aoy) {
		return aoy.cI().c(awp.q).isPresent();
	}

	private boolean a(aoy aoy1, aoy aoy2) {
		return aoy2.cI().c(awp.q).filter(aoy2x -> aoy2x == aoy1).isPresent();
	}

	private boolean e(apg apg) {
		return apg.cI().a(awp.i);
	}
}

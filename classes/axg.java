import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class axg extends axn<aoz> {
	private final Long2LongMap a = new Long2LongOpenHashMap();
	private int b;
	private long c;

	public axg() {
		super(20);
	}

	@Override
	public Set<awp<?>> a() {
		return ImmutableSet.of(awp.w);
	}

	protected void a(zd zd, aoz aoz) {
		if (aoz.x_()) {
			this.b = 0;
			this.c = zd.Q() + (long)zd.v_().nextInt(20);
			axz axz4 = zd.x();
			Predicate<fu> predicate5 = fu -> {
				long long3 = fu.a();
				if (this.a.containsKey(long3)) {
					return false;
				} else if (++this.b >= 5) {
					return false;
				} else {
					this.a.put(long3, this.c + 40L);
					return true;
				}
			};
			Stream<fu> stream6 = axz4.a(ayc.r.c(), predicate5, aoz.cA(), 48, axz.b.ANY);
			czf czf7 = aoz.x().a(stream6, ayc.r.d());
			if (czf7 != null && czf7.i()) {
				fu fu8 = czf7.m();
				Optional<ayc> optional9 = axz4.c(fu8);
				if (optional9.isPresent()) {
					aoz.cI().a(awp.w, fu8);
				}
			} else if (this.b < 5) {
				this.a.long2LongEntrySet().removeIf(entry -> entry.getLongValue() < this.c);
			}
		}
	}
}

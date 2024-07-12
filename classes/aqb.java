import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class aqb extends aqh<apg> {
	private final ayc b;
	private final awp<gc> c;
	private final boolean d;
	private long e;
	private final Long2ObjectMap<aqb.a> f = new Long2ObjectOpenHashMap<>();

	public aqb(ayc ayc, awp<gc> awp2, awp<gc> awp3, boolean boolean4) {
		super(a(awp2, awp3));
		this.b = ayc;
		this.c = awp3;
		this.d = boolean4;
	}

	public aqb(ayc ayc, awp<gc> awp, boolean boolean3) {
		this(ayc, awp, awp, boolean3);
	}

	private static ImmutableMap<awp<?>, awq> a(awp<gc> awp1, awp<gc> awp2) {
		Builder<awp<?>, awq> builder3 = ImmutableMap.builder();
		builder3.put(awp1, awq.VALUE_ABSENT);
		if (awp2 != awp1) {
			builder3.put(awp2, awq.VALUE_ABSENT);
		}

		return builder3.build();
	}

	protected boolean a(zd zd, apg apg) {
		if (this.d && apg.x_()) {
			return false;
		} else if (this.e == 0L) {
			this.e = apg.l.Q() + (long)zd.t.nextInt(20);
			return false;
		} else {
			return zd.Q() >= this.e;
		}
	}

	protected void a(zd zd, apg apg, long long3) {
		this.e = long3 + 20L + (long)zd.v_().nextInt(20);
		axz axz6 = zd.x();
		this.f.long2ObjectEntrySet().removeIf(entry -> !((aqb.a)entry.getValue()).b(long3));
		Predicate<fu> predicate7 = fu -> {
			aqb.a a5 = this.f.get(fu.a());
			if (a5 == null) {
				return true;
			} else if (!a5.c(long3)) {
				return false;
			} else {
				a5.a(long3);
				return true;
			}
		};
		Set<fu> set8 = (Set<fu>)axz6.a(this.b.c(), predicate7, apg.cA(), 48, axz.b.HAS_SPACE).limit(5L).collect(Collectors.toSet());
		czf czf9 = apg.x().a(set8, this.b.d());
		if (czf9 != null && czf9.i()) {
			fu fu10 = czf9.m();
			axz6.c(fu10).ifPresent(ayc -> {
				axz6.a(this.b.c(), fu2 -> fu2.equals(fu10), fu10, 1);
				apg.cI().a(this.c, gc.a(zd.W(), fu10));
				this.f.clear();
				qy.c(zd, fu10);
			});
		} else {
			for (fu fu11 : set8) {
				this.f.computeIfAbsent(fu11.a(), long3x -> new aqb.a(apg.l.t, long3));
			}
		}
	}

	static class a {
		private final Random a;
		private long b;
		private long c;
		private int d;

		a(Random random, long long2) {
			this.a = random;
			this.a(long2);
		}

		public void a(long long1) {
			this.b = long1;
			int integer4 = this.d + this.a.nextInt(40) + 40;
			this.d = Math.min(integer4, 400);
			this.c = long1 + (long)this.d;
		}

		public boolean b(long long1) {
			return long1 - this.b < 400L;
		}

		public boolean c(long long1) {
			return long1 >= this.c;
		}

		public String toString() {
			return "RetryMarker{, previousAttemptAt=" + this.b + ", nextScheduledAttemptAt=" + this.c + ", currentDelay=" + this.d + '}';
		}
	}
}

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class aqr<E extends aoy> extends aqh<E> {
	private final Set<awp<?>> b;
	private final aqr.a c;
	private final aqr.b d;
	private final atb<aqh<? super E>> e = new atb<>();

	public aqr(Map<awp<?>, awq> map, Set<awp<?>> set, aqr.a a, aqr.b b, List<Pair<aqh<? super E>, Integer>> list) {
		super(map);
		this.b = set;
		this.c = a;
		this.d = b;
		list.forEach(pair -> this.e.a((aqh<? super E>)pair.getFirst(), (Integer)pair.getSecond()));
	}

	@Override
	protected boolean b(zd zd, E aoy, long long3) {
		return this.e.c().filter(aqh -> aqh.a() == aqh.a.RUNNING).anyMatch(aqh -> aqh.b(zd, aoy, long3));
	}

	@Override
	protected boolean a(long long1) {
		return false;
	}

	@Override
	protected void a(zd zd, E aoy, long long3) {
		this.c.a(this.e);
		this.d.a(this.e, zd, aoy, long3);
	}

	@Override
	protected void d(zd zd, E aoy, long long3) {
		this.e.c().filter(aqh -> aqh.a() == aqh.a.RUNNING).forEach(aqh -> aqh.f(zd, aoy, long3));
	}

	@Override
	protected void c(zd zd, E aoy, long long3) {
		this.e.c().filter(aqh -> aqh.a() == aqh.a.RUNNING).forEach(aqh -> aqh.g(zd, aoy, long3));
		this.b.forEach(aoy.cI()::b);
	}

	@Override
	public String toString() {
		Set<? extends aqh<? super E>> set2 = (Set<? extends aqh<? super E>>)this.e.c().filter(aqh -> aqh.a() == aqh.a.RUNNING).collect(Collectors.toSet());
		return "(" + this.getClass().getSimpleName() + "): " + set2;
	}

	static enum a {
		ORDERED(atb -> {
		}),
		SHUFFLED(atb::a);

		private final Consumer<atb<?>> c;

		private a(Consumer<atb<?>> consumer) {
			this.c = consumer;
		}

		public void a(atb<?> atb) {
			this.c.accept(atb);
		}
	}

	static enum b {
		RUN_ONE {
			@Override
			public <E extends aoy> void a(atb<aqh<? super E>> atb, zd zd, E aoy, long long4) {
				atb.c().filter(aqh -> aqh.a() == aqh.a.STOPPED).filter(aqh -> aqh.e(zd, aoy, long4)).findFirst();
			}
		},
		TRY_ALL {
			@Override
			public <E extends aoy> void a(atb<aqh<? super E>> atb, zd zd, E aoy, long long4) {
				atb.c().filter(aqh -> aqh.a() == aqh.a.STOPPED).forEach(aqh -> aqh.e(zd, aoy, long4));
			}
		};

		private b() {
		}

		public abstract <E extends aoy> void a(atb<aqh<? super E>> atb, zd zd, E aoy, long long4);
	}
}

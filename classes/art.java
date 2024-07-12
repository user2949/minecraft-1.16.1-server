import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.Predicate;

public class art<E extends aoy> extends aqh<E> {
	private final Predicate<E> b;
	private final aqh<? super E> c;
	private final boolean d;

	public art(Map<awp<?>, awq> map, Predicate<E> predicate, aqh<? super E> aqh, boolean boolean4) {
		super(a(map, aqh.a));
		this.b = predicate;
		this.c = aqh;
		this.d = boolean4;
	}

	private static Map<awp<?>, awq> a(Map<awp<?>, awq> map1, Map<awp<?>, awq> map2) {
		Map<awp<?>, awq> map3 = Maps.<awp<?>, awq>newHashMap();
		map3.putAll(map1);
		map3.putAll(map2);
		return map3;
	}

	public art(Predicate<E> predicate, aqh<? super E> aqh) {
		this(ImmutableMap.of(), predicate, aqh, false);
	}

	@Override
	protected boolean a(zd zd, E aoy) {
		return this.b.test(aoy) && this.c.a(zd, aoy);
	}

	@Override
	protected boolean b(zd zd, E aoy, long long3) {
		return this.d && this.b.test(aoy) && this.c.b(zd, aoy, long3);
	}

	@Override
	protected boolean a(long long1) {
		return false;
	}

	@Override
	protected void a(zd zd, E aoy, long long3) {
		this.c.a(zd, aoy, long3);
	}

	@Override
	protected void d(zd zd, E aoy, long long3) {
		this.c.d(zd, aoy, long3);
	}

	@Override
	protected void c(zd zd, E aoy, long long3) {
		this.c.c(zd, aoy, long3);
	}

	@Override
	public String toString() {
		return "RunIf: " + this.c;
	}
}

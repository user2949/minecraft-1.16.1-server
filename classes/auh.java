import com.google.common.collect.Sets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class auh {
	private static final Logger a = LogManager.getLogger();
	private static final avx b = new avx(Integer.MAX_VALUE, new aug() {
		@Override
		public boolean a() {
			return false;
		}
	}) {
		@Override
		public boolean g() {
			return false;
		}
	};
	private final Map<aug.a, avx> c = new EnumMap(aug.a.class);
	private final Set<avx> d = Sets.<avx>newLinkedHashSet();
	private final Supplier<ami> e;
	private final EnumSet<aug.a> f = EnumSet.noneOf(aug.a.class);
	private int g = 3;

	public auh(Supplier<ami> supplier) {
		this.e = supplier;
	}

	public void a(int integer, aug aug) {
		this.d.add(new avx(integer, aug));
	}

	public void a(aug aug) {
		this.d.stream().filter(avx -> avx.j() == aug).filter(avx::g).forEach(avx::d);
		this.d.removeIf(avx -> avx.j() == aug);
	}

	public void b() {
		ami ami2 = (ami)this.e.get();
		ami2.a("goalCleanup");
		this.d().filter(avx -> !avx.g() || avx.i().stream().anyMatch(this.f::contains) || !avx.b()).forEach(aug::d);
		this.c.forEach((a, avx) -> {
			if (!avx.g()) {
				this.c.remove(a);
			}
		});
		ami2.c();
		ami2.a("goalUpdate");
		this.d
			.stream()
			.filter(avx -> !avx.g())
			.filter(avx -> avx.i().stream().noneMatch(this.f::contains))
			.filter(avx -> avx.i().stream().allMatch(a -> ((avx)this.c.getOrDefault(a, b)).a(avx)))
			.filter(avx::a)
			.forEach(avx -> {
				avx.i().forEach(a -> {
					avx avx4 = (avx)this.c.getOrDefault(a, b);
					avx4.d();
					this.c.put(a, avx);
				});
				avx.c();
			});
		ami2.c();
		ami2.a("goalTick");
		this.d().forEach(avx::e);
		ami2.c();
	}

	public Stream<avx> d() {
		return this.d.stream().filter(avx::g);
	}

	public void a(aug.a a) {
		this.f.add(a);
	}

	public void b(aug.a a) {
		this.f.remove(a);
	}

	public void a(aug.a a, boolean boolean2) {
		if (boolean2) {
			this.b(a);
		} else {
			this.a(a);
		}
	}
}

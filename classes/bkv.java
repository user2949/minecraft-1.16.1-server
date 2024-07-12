import java.util.function.Predicate;

public abstract class bkv extends bke {
	public static final Predicate<bki> a = bki -> bki.b().a(ada.X);
	public static final Predicate<bki> b = a.or(bki -> bki.b() == bkk.pn);

	public bkv(bke.a a) {
		super(a);
	}

	public Predicate<bki> e() {
		return this.b();
	}

	public abstract Predicate<bki> b();

	public static bki a(aoy aoy, Predicate<bki> predicate) {
		if (predicate.test(aoy.b(anf.OFF_HAND))) {
			return aoy.b(anf.OFF_HAND);
		} else {
			return predicate.test(aoy.b(anf.MAIN_HAND)) ? aoy.b(anf.MAIN_HAND) : bki.b;
		}
	}

	@Override
	public int c() {
		return 1;
	}

	public abstract int d();
}

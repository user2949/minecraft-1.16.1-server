import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.function.Function;
import java.util.function.Predicate;

public class chs<T> implements bqr<T> {
	protected final Predicate<T> a;
	private final bph b;
	private final ShortList[] c = new ShortList[16];

	public chs(Predicate<T> predicate, bph bph) {
		this(predicate, bph, new lk());
	}

	public chs(Predicate<T> predicate, bph bph, lk lk) {
		this.a = predicate;
		this.b = bph;

		for (int integer5 = 0; integer5 < lk.size(); integer5++) {
			lk lk6 = lk.b(integer5);

			for (int integer7 = 0; integer7 < lk6.size(); integer7++) {
				cgy.a(this.c, integer5).add(lk6.d(integer7));
			}
		}
	}

	public lk b() {
		return chv.a(this.c);
	}

	public void a(bqr<T> bqr, Function<fu, T> function) {
		for (int integer4 = 0; integer4 < this.c.length; integer4++) {
			if (this.c[integer4] != null) {
				for (Short short6 : this.c[integer4]) {
					fu fu7 = chr.a(short6, integer4, this.b);
					bqr.a(fu7, (T)function.apply(fu7), 0);
				}

				this.c[integer4].clear();
			}
		}
	}

	@Override
	public boolean a(fu fu, T object) {
		return false;
	}

	@Override
	public void a(fu fu, T object, int integer, bqt bqt) {
		cgy.a(this.c, fu.v() >> 4).add(chr.k(fu));
	}

	@Override
	public boolean b(fu fu, T object) {
		return false;
	}
}

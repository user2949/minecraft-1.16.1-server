import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class chh<T> implements cho<T> {
	private final ge<T> a;
	private final adm<T> b;
	private final chp<T> c;
	private final Function<le, T> d;
	private final Function<T, le> e;
	private final int f;

	public chh(ge<T> ge, int integer, chp<T> chp, Function<le, T> function4, Function<T, le> function5) {
		this.a = ge;
		this.f = integer;
		this.c = chp;
		this.d = function4;
		this.e = function5;
		this.b = new adm<>(1 << integer);
	}

	@Override
	public int a(T object) {
		int integer3 = this.b.a(object);
		if (integer3 == -1) {
			integer3 = this.b.c(object);
			if (integer3 >= 1 << this.f) {
				integer3 = this.c.onResize(this.f + 1, object);
			}
		}

		return integer3;
	}

	@Override
	public boolean a(Predicate<T> predicate) {
		for (int integer3 = 0; integer3 < this.b(); integer3++) {
			if (predicate.test(this.b.a(integer3))) {
				return true;
			}
		}

		return false;
	}

	@Nullable
	@Override
	public T a(int integer) {
		return this.b.a(integer);
	}

	@Override
	public void b(mg mg) {
		int integer3 = this.b();
		mg.d(integer3);

		for (int integer4 = 0; integer4 < integer3; integer4++) {
			mg.d(this.a.a(this.b.a(integer4)));
		}
	}

	@Override
	public int a() {
		int integer2 = mg.a(this.b());

		for (int integer3 = 0; integer3 < this.b(); integer3++) {
			integer2 += mg.a(this.a.a(this.b.a(integer3)));
		}

		return integer2;
	}

	public int b() {
		return this.b.b();
	}

	@Override
	public void a(lk lk) {
		this.b.a();

		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			this.b.c((T)this.d.apply(lk.a(integer3)));
		}
	}

	public void b(lk lk) {
		for (int integer3 = 0; integer3 < this.b(); integer3++) {
			lk.add(this.e.apply(this.b.a(integer3)));
		}
	}
}

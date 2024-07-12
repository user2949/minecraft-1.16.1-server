import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class chm<T> implements cho<T> {
	private final ge<T> a;
	private final T[] b;
	private final chp<T> c;
	private final Function<le, T> d;
	private final int e;
	private int f;

	public chm(ge<T> ge, int integer, chp<T> chp, Function<le, T> function) {
		this.a = ge;
		this.b = (T[])(new Object[1 << integer]);
		this.e = integer;
		this.c = chp;
		this.d = function;
	}

	@Override
	public int a(T object) {
		for (int integer3 = 0; integer3 < this.f; integer3++) {
			if (this.b[integer3] == object) {
				return integer3;
			}
		}

		int integer3x = this.f;
		if (integer3x < this.b.length) {
			this.b[integer3x] = object;
			this.f++;
			return integer3x;
		} else {
			return this.c.onResize(this.e + 1, object);
		}
	}

	@Override
	public boolean a(Predicate<T> predicate) {
		for (int integer3 = 0; integer3 < this.f; integer3++) {
			if (predicate.test(this.b[integer3])) {
				return true;
			}
		}

		return false;
	}

	@Nullable
	@Override
	public T a(int integer) {
		return integer >= 0 && integer < this.f ? this.b[integer] : null;
	}

	@Override
	public void b(mg mg) {
		mg.d(this.f);

		for (int integer3 = 0; integer3 < this.f; integer3++) {
			mg.d(this.a.a(this.b[integer3]));
		}
	}

	@Override
	public int a() {
		int integer2 = mg.a(this.b());

		for (int integer3 = 0; integer3 < this.b(); integer3++) {
			integer2 += mg.a(this.a.a(this.b[integer3]));
		}

		return integer2;
	}

	public int b() {
		return this.f;
	}

	@Override
	public void a(lk lk) {
		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			this.b[integer3] = (T)this.d.apply(lk.a(integer3));
		}

		this.f = lk.size();
	}
}

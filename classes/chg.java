import java.util.function.Predicate;

public class chg<T> implements cho<T> {
	private final ge<T> a;
	private final T b;

	public chg(ge<T> ge, T object) {
		this.a = ge;
		this.b = object;
	}

	@Override
	public int a(T object) {
		int integer3 = this.a.a(object);
		return integer3 == -1 ? 0 : integer3;
	}

	@Override
	public boolean a(Predicate<T> predicate) {
		return true;
	}

	@Override
	public T a(int integer) {
		T object3 = this.a.a(integer);
		return object3 == null ? this.b : object3;
	}

	@Override
	public void b(mg mg) {
	}

	@Override
	public int a() {
		return mg.a(0);
	}

	@Override
	public void a(lk lk) {
	}
}

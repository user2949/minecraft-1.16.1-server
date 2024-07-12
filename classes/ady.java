import java.util.function.Supplier;

public class ady<T> {
	private Supplier<T> a;
	private T b;

	public ady(Supplier<T> supplier) {
		this.a = supplier;
	}

	public T a() {
		Supplier<T> supplier2 = this.a;
		if (supplier2 != null) {
			this.b = (T)supplier2.get();
			this.a = null;
		}

		return this.b;
	}
}

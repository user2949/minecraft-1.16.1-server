public class anh<T> {
	private final ang a;
	private final T b;

	public anh(ang ang, T object) {
		this.a = ang;
		this.b = object;
	}

	public ang a() {
		return this.a;
	}

	public T b() {
		return this.b;
	}

	public static <T> anh<T> a(T object) {
		return new anh<>(ang.SUCCESS, object);
	}

	public static <T> anh<T> b(T object) {
		return new anh<>(ang.CONSUME, object);
	}

	public static <T> anh<T> c(T object) {
		return new anh<>(ang.PASS, object);
	}

	public static <T> anh<T> d(T object) {
		return new anh<>(ang.FAIL, object);
	}

	public static <T> anh<T> a(T object, boolean boolean2) {
		return boolean2 ? a(object) : b(object);
	}
}

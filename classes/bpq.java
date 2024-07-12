public class bpq<T> implements bqr<T> {
	private static final bpq<Object> a = new bpq<>();

	public static <T> bpq<T> b() {
		return (bpq<T>)a;
	}

	@Override
	public boolean a(fu fu, T object) {
		return false;
	}

	@Override
	public void a(fu fu, T object, int integer) {
	}

	@Override
	public void a(fu fu, T object, int integer, bqt bqt) {
	}

	@Override
	public boolean b(fu fu, T object) {
		return false;
	}
}

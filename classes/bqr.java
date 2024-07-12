public interface bqr<T> {
	boolean a(fu fu, T object);

	default void a(fu fu, T object, int integer) {
		this.a(fu, object, integer, bqt.NORMAL);
	}

	void a(fu fu, T object, int integer, bqt bqt);

	boolean b(fu fu, T object);
}

public interface dam extends dab {
	void b(int integer);

	void c(int integer);

	void d(int integer);

	default void a(fu fu) {
		this.b(fu.u());
		this.c(fu.v());
		this.d(fu.w());
	}
}

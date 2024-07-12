public class s extends RuntimeException {
	private final j a;

	public s(j j) {
		this.a = j;
	}

	public j a() {
		return this.a;
	}

	public Throwable getCause() {
		return this.a.b();
	}

	public String getMessage() {
		return this.a.a();
	}
}

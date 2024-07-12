public class t extends RuntimeException {
	public t(String string) {
		super(string);
	}

	public t(String string, Throwable throwable) {
		super(string, throwable);
	}
}

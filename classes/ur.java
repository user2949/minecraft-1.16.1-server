public final class ur extends RuntimeException {
	public static final ur a = new ur();

	private ur() {
		this.setStackTrace(new StackTraceElement[0]);
	}

	public synchronized Throwable fillInStackTrace() {
		this.setStackTrace(new StackTraceElement[0]);
		return this;
	}
}

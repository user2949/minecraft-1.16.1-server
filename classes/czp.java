public class czp implements Runnable {
	private final czq a;

	public czp(czq czq) {
		this.a = czq;
	}

	public void run() {
		this.a.b();
	}
}

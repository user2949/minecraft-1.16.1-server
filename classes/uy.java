public class uy implements Runnable {
	private final int a;
	private final Runnable b;

	public uy(int integer, Runnable runnable) {
		this.a = integer;
		this.b = runnable;
	}

	public int a() {
		return this.a;
	}

	public void run() {
		this.b.run();
	}
}

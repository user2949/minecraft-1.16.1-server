public class bhv implements bgr {
	private final int[] a;

	public bhv(int integer) {
		this.a = new int[integer];
	}

	@Override
	public int a(int integer) {
		return this.a[integer];
	}

	@Override
	public void a(int integer1, int integer2) {
		this.a[integer1] = integer2;
	}

	@Override
	public int a() {
		return this.a.length;
	}
}

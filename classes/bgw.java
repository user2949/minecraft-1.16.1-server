public abstract class bgw {
	private int a;

	public static bgw a(bgr bgr, int integer) {
		return new bgw() {
			@Override
			public int b() {
				return bgr.a(integer);
			}

			@Override
			public void a(int integer) {
				bgr.a(integer, integer);
			}
		};
	}

	public static bgw a(int[] arr, int integer) {
		return new bgw() {
			@Override
			public int b() {
				return arr[integer];
			}

			@Override
			public void a(int integer) {
				arr[integer] = integer;
			}
		};
	}

	public static bgw a() {
		return new bgw() {
			private int a;

			@Override
			public int b() {
				return this.a;
			}

			@Override
			public void a(int integer) {
				this.a = integer;
			}
		};
	}

	public abstract int b();

	public abstract void a(int integer);

	public boolean c() {
		int integer2 = this.b();
		boolean boolean3 = integer2 != this.a;
		this.a = integer2;
		return boolean3;
	}
}

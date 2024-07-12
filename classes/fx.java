public class fx {
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	private int f;
	private int g;
	private int h;
	private int i;
	private int j;
	private int k;

	public fx(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		this.a = integer1;
		this.b = integer2;
		this.c = integer3;
		this.d = integer4 - integer1 + 1;
		this.e = integer5 - integer2 + 1;
		this.f = integer6 - integer3 + 1;
		this.g = this.d * this.e * this.f;
	}

	public boolean a() {
		if (this.h == this.g) {
			return false;
		} else {
			this.i = this.h % this.d;
			int integer2 = this.h / this.d;
			this.j = integer2 % this.e;
			this.k = integer2 / this.e;
			this.h++;
			return true;
		}
	}

	public int b() {
		return this.a + this.i;
	}

	public int c() {
		return this.b + this.j;
	}

	public int d() {
		return this.c + this.k;
	}

	public int e() {
		int integer2 = 0;
		if (this.i == 0 || this.i == this.d - 1) {
			integer2++;
		}

		if (this.j == 0 || this.j == this.e - 1) {
			integer2++;
		}

		if (this.k == 0 || this.k == this.f - 1) {
			integer2++;
		}

		return integer2;
	}
}

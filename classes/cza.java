public class cza {
	private czd[] a = new czd[128];
	private int b;

	public czd a(czd czd) {
		if (czd.d >= 0) {
			throw new IllegalStateException("OW KNOWS!");
		} else {
			if (this.b == this.a.length) {
				czd[] arr3 = new czd[this.b << 1];
				System.arraycopy(this.a, 0, arr3, 0, this.b);
				this.a = arr3;
			}

			this.a[this.b] = czd;
			czd.d = this.b;
			this.a(this.b++);
			return czd;
		}
	}

	public void a() {
		this.b = 0;
	}

	public czd c() {
		czd czd2 = this.a[0];
		this.a[0] = this.a[--this.b];
		this.a[this.b] = null;
		if (this.b > 0) {
			this.b(0);
		}

		czd2.d = -1;
		return czd2;
	}

	public void a(czd czd, float float2) {
		float float4 = czd.g;
		czd.g = float2;
		if (float2 < float4) {
			this.a(czd.d);
		} else {
			this.b(czd.d);
		}
	}

	private void a(int integer) {
		czd czd3 = this.a[integer];
		float float4 = czd3.g;

		while (integer > 0) {
			int integer5 = integer - 1 >> 1;
			czd czd6 = this.a[integer5];
			if (!(float4 < czd6.g)) {
				break;
			}

			this.a[integer] = czd6;
			czd6.d = integer;
			integer = integer5;
		}

		this.a[integer] = czd3;
		czd3.d = integer;
	}

	private void b(int integer) {
		czd czd3 = this.a[integer];
		float float4 = czd3.g;

		while (true) {
			int integer5 = 1 + (integer << 1);
			int integer6 = integer5 + 1;
			if (integer5 >= this.b) {
				break;
			}

			czd czd7 = this.a[integer5];
			float float8 = czd7.g;
			czd czd9;
			float float10;
			if (integer6 >= this.b) {
				czd9 = null;
				float10 = Float.POSITIVE_INFINITY;
			} else {
				czd9 = this.a[integer6];
				float10 = czd9.g;
			}

			if (float8 < float10) {
				if (!(float8 < float4)) {
					break;
				}

				this.a[integer] = czd7;
				czd7.d = integer;
				integer = integer5;
			} else {
				if (!(float10 < float4)) {
					break;
				}

				this.a[integer] = czd9;
				czd9.d = integer;
				integer = integer6;
			}
		}

		this.a[integer] = czd3;
		czd3.d = integer;
	}

	public boolean e() {
		return this.b == 0;
	}
}

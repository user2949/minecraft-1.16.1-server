import javax.annotation.Nullable;

public class chd {
	@Nullable
	protected byte[] a;

	public chd() {
	}

	public chd(byte[] arr) {
		this.a = arr;
		if (arr.length != 2048) {
			throw (IllegalArgumentException)v.c(new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + arr.length));
		}
	}

	protected chd(int integer) {
		this.a = new byte[integer];
	}

	public int a(int integer1, int integer2, int integer3) {
		return this.b(this.b(integer1, integer2, integer3));
	}

	public void a(int integer1, int integer2, int integer3, int integer4) {
		this.a(this.b(integer1, integer2, integer3), integer4);
	}

	protected int b(int integer1, int integer2, int integer3) {
		return integer2 << 8 | integer3 << 4 | integer1;
	}

	private int b(int integer) {
		if (this.a == null) {
			return 0;
		} else {
			int integer3 = this.d(integer);
			return this.c(integer) ? this.a[integer3] & 15 : this.a[integer3] >> 4 & 15;
		}
	}

	private void a(int integer1, int integer2) {
		if (this.a == null) {
			this.a = new byte[2048];
		}

		int integer4 = this.d(integer1);
		if (this.c(integer1)) {
			this.a[integer4] = (byte)(this.a[integer4] & 240 | integer2 & 15);
		} else {
			this.a[integer4] = (byte)(this.a[integer4] & 15 | (integer2 & 15) << 4);
		}
	}

	private boolean c(int integer) {
		return (integer & 1) == 0;
	}

	private int d(int integer) {
		return integer >> 1;
	}

	public byte[] a() {
		if (this.a == null) {
			this.a = new byte[2048];
		}

		return this.a;
	}

	public chd b() {
		return this.a == null ? new chd() : new chd((byte[])this.a.clone());
	}

	public String toString() {
		StringBuilder stringBuilder2 = new StringBuilder();

		for (int integer3 = 0; integer3 < 4096; integer3++) {
			stringBuilder2.append(Integer.toHexString(this.b(integer3)));
			if ((integer3 & 15) == 15) {
				stringBuilder2.append("\n");
			}

			if ((integer3 & 0xFF) == 255) {
				stringBuilder2.append("\n");
			}
		}

		return stringBuilder2.toString();
	}

	public boolean c() {
		return this.a == null;
	}
}

public class chn {
	public final byte[] a;
	private final int b;
	private final int c;

	public chn(byte[] arr, int integer) {
		this.a = arr;
		this.b = integer;
		this.c = integer + 4;
	}

	public int a(int integer1, int integer2, int integer3) {
		int integer5 = integer1 << this.c | integer3 << this.b | integer2;
		int integer6 = integer5 >> 1;
		int integer7 = integer5 & 1;
		return integer7 == 0 ? this.a[integer6] & 15 : this.a[integer6] >> 4 & 15;
	}
}

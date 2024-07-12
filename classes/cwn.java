public class cwn extends chd {
	public cwn() {
		super(128);
	}

	public cwn(chd chd, int integer) {
		super(128);
		System.arraycopy(chd.a(), integer * 128, this.a, 0, 128);
	}

	@Override
	protected int b(int integer1, int integer2, int integer3) {
		return integer3 << 4 | integer1;
	}

	@Override
	public byte[] a() {
		byte[] arr2 = new byte[2048];

		for (int integer3 = 0; integer3 < 16; integer3++) {
			System.arraycopy(this.a, 0, arr2, integer3 * 128, 128);
		}

		return arr2;
	}
}

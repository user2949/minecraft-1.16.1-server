public class boj extends bnw {
	public final boj.a a;

	public boj(bnw.a a, boj.a a, aor... arr) {
		super(a, a == boj.a.FALL ? bnx.ARMOR_FEET : bnx.ARMOR, arr);
		this.a = a;
	}

	@Override
	public int a(int integer) {
		return this.a.b() + (integer - 1) * this.a.c();
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + this.a.c();
	}

	@Override
	public int a() {
		return 4;
	}

	@Override
	public int a(int integer, anw anw) {
		if (anw.h()) {
			return 0;
		} else if (this.a == boj.a.ALL) {
			return integer;
		} else if (this.a == boj.a.FIRE && anw.p()) {
			return integer * 2;
		} else if (this.a == boj.a.FALL && anw == anw.k) {
			return integer * 3;
		} else if (this.a == boj.a.EXPLOSION && anw.d()) {
			return integer * 2;
		} else {
			return this.a == boj.a.PROJECTILE && anw.b() ? integer * 2 : 0;
		}
	}

	@Override
	public boolean a(bnw bnw) {
		if (bnw instanceof boj) {
			boj boj3 = (boj)bnw;
			return this.a == boj3.a ? false : this.a == boj.a.FALL || boj3.a == boj.a.FALL;
		} else {
			return super.a(bnw);
		}
	}

	public static int a(aoy aoy, int integer) {
		int integer3 = bny.a(boa.b, aoy);
		if (integer3 > 0) {
			integer -= aec.d((float)integer * (float)integer3 * 0.15F);
		}

		return integer;
	}

	public static double a(aoy aoy, double double2) {
		int integer4 = bny.a(boa.d, aoy);
		if (integer4 > 0) {
			double2 -= (double)aec.c(double2 * (double)((float)integer4 * 0.15F));
		}

		return double2;
	}

	public static enum a {
		ALL("all", 1, 11),
		FIRE("fire", 10, 8),
		FALL("fall", 5, 6),
		EXPLOSION("explosion", 5, 8),
		PROJECTILE("projectile", 3, 6);

		private final String f;
		private final int g;
		private final int h;

		private a(String string3, int integer4, int integer5) {
			this.f = string3;
			this.g = integer4;
			this.h = integer5;
		}

		public int b() {
			return this.g;
		}

		public int c() {
			return this.h;
		}
	}
}

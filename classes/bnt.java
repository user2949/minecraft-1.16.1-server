public class bnt extends bnw {
	private static final String[] d = new String[]{"all", "undead", "arthropods"};
	private static final int[] e = new int[]{1, 5, 5};
	private static final int[] f = new int[]{11, 8, 8};
	private static final int[] g = new int[]{20, 20, 20};
	public final int a;

	public bnt(bnw.a a, int integer, aor... arr) {
		super(a, bnx.WEAPON, arr);
		this.a = integer;
	}

	@Override
	public int a(int integer) {
		return e[this.a] + (integer - 1) * f[this.a];
	}

	@Override
	public int b(int integer) {
		return this.a(integer) + g[this.a];
	}

	@Override
	public int a() {
		return 5;
	}

	@Override
	public float a(int integer, apc apc) {
		if (this.a == 0) {
			return 1.0F + (float)Math.max(0, integer - 1) * 0.5F;
		} else if (this.a == 1 && apc == apc.b) {
			return (float)integer * 2.5F;
		} else {
			return this.a == 2 && apc == apc.c ? (float)integer * 2.5F : 0.0F;
		}
	}

	@Override
	public boolean a(bnw bnw) {
		return !(bnw instanceof bnt);
	}

	@Override
	public boolean a(bki bki) {
		return bki.b() instanceof bii ? true : super.a(bki);
	}

	@Override
	public void a(aoy aoy, aom aom, int integer) {
		if (aom instanceof aoy) {
			aoy aoy5 = (aoy)aom;
			if (this.a == 2 && aoy5.dB() == apc.c) {
				int integer6 = 20 + aoy.cX().nextInt(10 * integer);
				aoy5.c(new aog(aoi.b, integer6, 3));
			}
		}
	}
}

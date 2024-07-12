import java.util.function.Supplier;

public enum bif implements bie {
	LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, acl.P, 0.0F, 0.0F, () -> bmr.a(bkk.lS)),
	CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, acl.J, 0.0F, 0.0F, () -> bmr.a(bkk.kk)),
	IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, acl.O, 0.0F, 0.0F, () -> bmr.a(bkk.kk)),
	GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, acl.N, 0.0F, 0.0F, () -> bmr.a(bkk.kl)),
	DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, acl.K, 2.0F, 0.0F, () -> bmr.a(bkk.kj)),
	TURTLE("turtle", 25, new int[]{2, 5, 6, 2}, 9, acl.R, 0.0F, 0.0F, () -> bmr.a(bkk.jZ)),
	NETHERITE("netherite", 37, new int[]{3, 6, 8, 3}, 15, acl.Q, 3.0F, 0.1F, () -> bmr.a(bkk.km));

	private static final int[] h = new int[]{13, 15, 16, 11};
	private final String i;
	private final int j;
	private final int[] k;
	private final int l;
	private final ack m;
	private final float n;
	private final float o;
	private final ady<bmr> p;

	private bif(String string3, int integer4, int[] arr, int integer6, ack ack, float float8, float float9, Supplier<bmr> supplier) {
		this.i = string3;
		this.j = integer4;
		this.k = arr;
		this.l = integer6;
		this.m = ack;
		this.n = float8;
		this.o = float9;
		this.p = new ady<>(supplier);
	}

	@Override
	public int a(aor aor) {
		return h[aor.b()] * this.j;
	}

	@Override
	public int b(aor aor) {
		return this.k[aor.b()];
	}

	@Override
	public int a() {
		return this.l;
	}

	@Override
	public ack b() {
		return this.m;
	}

	@Override
	public bmr c() {
		return this.p.a();
	}

	@Override
	public float e() {
		return this.n;
	}

	@Override
	public float f() {
		return this.o;
	}
}

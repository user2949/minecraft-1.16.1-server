import java.util.function.Supplier;

public enum blq implements blo {
	WOOD(0, 59, 2.0F, 0.0F, 15, () -> bmr.a(ada.b)),
	STONE(1, 131, 4.0F, 1.0F, 5, () -> bmr.a(ada.aa)),
	IRON(2, 250, 6.0F, 2.0F, 14, () -> bmr.a(bkk.kk)),
	DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> bmr.a(bkk.kj)),
	GOLD(0, 32, 12.0F, 0.0F, 22, () -> bmr.a(bkk.kl)),
	NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> bmr.a(bkk.km));

	private final int g;
	private final int h;
	private final float i;
	private final float j;
	private final int k;
	private final ady<bmr> l;

	private blq(int integer3, int integer4, float float5, float float6, int integer7, Supplier<bmr> supplier) {
		this.g = integer3;
		this.h = integer4;
		this.i = float5;
		this.j = float6;
		this.k = integer7;
		this.l = new ady<>(supplier);
	}

	@Override
	public int a() {
		return this.h;
	}

	@Override
	public float b() {
		return this.i;
	}

	@Override
	public float c() {
		return this.j;
	}

	@Override
	public int d() {
		return this.g;
	}

	@Override
	public int e() {
		return this.k;
	}

	@Override
	public bmr f() {
		return this.l.a();
	}
}

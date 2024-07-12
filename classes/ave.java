import java.util.EnumSet;

public class ave extends aug {
	private final aoz a;
	private double b;
	private double c;
	private int d;

	public ave(aoz aoz) {
		this.a = aoz;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
	}

	@Override
	public boolean a() {
		return this.a.cX().nextFloat() < 0.02F;
	}

	@Override
	public boolean b() {
		return this.d >= 0;
	}

	@Override
	public void c() {
		double double2 = (Math.PI * 2) * this.a.cX().nextDouble();
		this.b = Math.cos(double2);
		this.c = Math.sin(double2);
		this.d = 20 + this.a.cX().nextInt(20);
	}

	@Override
	public void e() {
		this.d--;
		this.a.t().a(this.a.cC() + this.b, this.a.cF(), this.a.cG() + this.c);
	}
}

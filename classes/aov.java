import java.util.Random;

public class aov {
	private final tt d;
	private final tq<Integer> e;
	private final tq<Boolean> f;
	public boolean a;
	public int b;
	public int c;

	public aov(tt tt, tq<Integer> tq2, tq<Boolean> tq3) {
		this.d = tt;
		this.e = tq2;
		this.f = tq3;
	}

	public void a() {
		this.a = true;
		this.b = 0;
		this.c = this.d.a(this.e);
	}

	public boolean a(Random random) {
		if (this.a) {
			return false;
		} else {
			this.a = true;
			this.b = 0;
			this.c = random.nextInt(841) + 140;
			this.d.b(this.e, this.c);
			return true;
		}
	}

	public void a(le le) {
		le.a("Saddle", this.b());
	}

	public void b(le le) {
		this.a(le.q("Saddle"));
	}

	public void a(boolean boolean1) {
		this.d.b(this.f, boolean1);
	}

	public boolean b() {
		return this.d.a(this.f);
	}
}

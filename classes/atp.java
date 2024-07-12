import java.util.EnumSet;

public class atp extends aug {
	private final azk a;
	private bec b;
	private final bqb c;
	private final float d;
	private int e;
	private final axs f;

	public atp(azk azk, float float2) {
		this.a = azk;
		this.c = azk.l;
		this.d = float2;
		this.f = new axs().a((double)float2).a().b().d();
		this.a(EnumSet.of(aug.a.LOOK));
	}

	@Override
	public boolean a() {
		this.b = this.c.a(this.f, this.a);
		return this.b == null ? false : this.a(this.b);
	}

	@Override
	public boolean b() {
		if (!this.b.aU()) {
			return false;
		} else {
			return this.a.h(this.b) > (double)(this.d * this.d) ? false : this.e > 0 && this.a(this.b);
		}
	}

	@Override
	public void c() {
		this.a.x(true);
		this.e = 40 + this.a.cX().nextInt(40);
	}

	@Override
	public void d() {
		this.a.x(false);
		this.b = null;
	}

	@Override
	public void e() {
		this.a.t().a(this.b.cC(), this.b.cF(), this.b.cG(), 10.0F, (float)this.a.eo());
		this.e--;
	}

	private boolean a(bec bec) {
		for (anf anf6 : anf.values()) {
			bki bki7 = bec.b(anf6);
			if (this.a.eL() && bki7.b() == bkk.mL) {
				return true;
			}

			if (this.a.k(bki7)) {
				return true;
			}
		}

		return false;
	}
}

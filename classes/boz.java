public class boz {
	private final bki a;
	private final bki b;
	private final bki c;
	private int d;
	private final int e;
	private boolean f = true;
	private int g;
	private int h;
	private float i;
	private int j = 1;

	public boz(le le) {
		this.a = bki.a(le.p("buy"));
		this.b = bki.a(le.p("buyB"));
		this.c = bki.a(le.p("sell"));
		this.d = le.h("uses");
		if (le.c("maxUses", 99)) {
			this.e = le.h("maxUses");
		} else {
			this.e = 4;
		}

		if (le.c("rewardExp", 1)) {
			this.f = le.q("rewardExp");
		}

		if (le.c("xp", 3)) {
			this.j = le.h("xp");
		}

		if (le.c("priceMultiplier", 5)) {
			this.i = le.j("priceMultiplier");
		}

		this.g = le.h("specialPrice");
		this.h = le.h("demand");
	}

	public boz(bki bki1, bki bki2, int integer3, int integer4, float float5) {
		this(bki1, bki.b, bki2, integer3, integer4, float5);
	}

	public boz(bki bki1, bki bki2, bki bki3, int integer4, int integer5, float float6) {
		this(bki1, bki2, bki3, 0, integer4, integer5, float6);
	}

	public boz(bki bki1, bki bki2, bki bki3, int integer4, int integer5, int integer6, float float7) {
		this(bki1, bki2, bki3, integer4, integer5, integer6, float7, 0);
	}

	public boz(bki bki1, bki bki2, bki bki3, int integer4, int integer5, int integer6, float float7, int integer8) {
		this.a = bki1;
		this.b = bki2;
		this.c = bki3;
		this.d = integer4;
		this.e = integer5;
		this.j = integer6;
		this.i = float7;
		this.h = integer8;
	}

	public bki a() {
		return this.a;
	}

	public bki b() {
		int integer2 = this.a.E();
		bki bki3 = this.a.i();
		int integer4 = Math.max(0, aec.d((float)(integer2 * this.h) * this.i));
		bki3.e(aec.a(integer2 + integer4 + this.g, 1, this.a.b().i()));
		return bki3;
	}

	public bki c() {
		return this.b;
	}

	public bki d() {
		return this.c;
	}

	public void e() {
		this.h = this.h + this.d - (this.e - this.d);
	}

	public bki f() {
		return this.c.i();
	}

	public int g() {
		return this.d;
	}

	public void h() {
		this.d = 0;
	}

	public int i() {
		return this.e;
	}

	public void j() {
		this.d++;
	}

	public int k() {
		return this.h;
	}

	public void a(int integer) {
		this.g += integer;
	}

	public void l() {
		this.g = 0;
	}

	public int m() {
		return this.g;
	}

	public void b(int integer) {
		this.g = integer;
	}

	public float n() {
		return this.i;
	}

	public int o() {
		return this.j;
	}

	public boolean p() {
		return this.d >= this.e;
	}

	public void q() {
		this.d = this.e;
	}

	public boolean r() {
		return this.d > 0;
	}

	public boolean s() {
		return this.f;
	}

	public le t() {
		le le2 = new le();
		le2.a("buy", this.a.b(new le()));
		le2.a("sell", this.c.b(new le()));
		le2.a("buyB", this.b.b(new le()));
		le2.b("uses", this.d);
		le2.b("maxUses", this.e);
		le2.a("rewardExp", this.f);
		le2.b("xp", this.j);
		le2.a("priceMultiplier", this.i);
		le2.b("specialPrice", this.g);
		le2.b("demand", this.h);
		return le2;
	}

	public boolean a(bki bki1, bki bki2) {
		return this.c(bki1, this.b()) && bki1.E() >= this.b().E() && this.c(bki2, this.b) && bki2.E() >= this.b.E();
	}

	private boolean c(bki bki1, bki bki2) {
		if (bki2.a() && bki1.a()) {
			return true;
		} else {
			bki bki4 = bki1.i();
			if (bki4.b().k()) {
				bki4.b(bki4.g());
			}

			return bki.c(bki4, bki2) && (!bki2.n() || bki4.n() && lq.a(bki2.o(), bki4.o(), false));
		}
	}

	public boolean b(bki bki1, bki bki2) {
		if (!this.a(bki1, bki2)) {
			return false;
		} else {
			bki1.g(this.b().E());
			if (!this.c().a()) {
				bki2.g(this.c().E());
			}

			return true;
		}
	}
}

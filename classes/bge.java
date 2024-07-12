public class bge {
	private int a = 20;
	private float b;
	private float c;
	private int d;
	private int e = 20;

	public bge() {
		this.b = 5.0F;
	}

	public void a(int integer, float float2) {
		this.a = Math.min(integer + this.a, 20);
		this.b = Math.min(this.b + (float)integer * float2 * 2.0F, (float)this.a);
	}

	public void a(bke bke, bki bki) {
		if (bke.s()) {
			bgf bgf4 = bke.t();
			this.a(bgf4.a(), bgf4.b());
		}
	}

	public void a(bec bec) {
		and and3 = bec.l.ac();
		this.e = this.a;
		if (this.c > 4.0F) {
			this.c -= 4.0F;
			if (this.b > 0.0F) {
				this.b = Math.max(this.b - 1.0F, 0.0F);
			} else if (and3 != and.PEACEFUL) {
				this.a = Math.max(this.a - 1, 0);
			}
		}

		boolean boolean4 = bec.l.S().b(bpx.i);
		if (boolean4 && this.b > 0.0F && bec.eI() && this.a >= 20) {
			this.d++;
			if (this.d >= 10) {
				float float5 = Math.min(this.b, 6.0F);
				bec.b(float5 / 6.0F);
				this.a(float5);
				this.d = 0;
			}
		} else if (boolean4 && this.a >= 18 && bec.eI()) {
			this.d++;
			if (this.d >= 80) {
				bec.b(1.0F);
				this.a(6.0F);
				this.d = 0;
			}
		} else if (this.a <= 0) {
			this.d++;
			if (this.d >= 80) {
				if (bec.dj() > 10.0F || and3 == and.HARD || bec.dj() > 1.0F && and3 == and.NORMAL) {
					bec.a(anw.i, 1.0F);
				}

				this.d = 0;
			}
		} else {
			this.d = 0;
		}
	}

	public void a(le le) {
		if (le.c("foodLevel", 99)) {
			this.a = le.h("foodLevel");
			this.d = le.h("foodTickTimer");
			this.b = le.j("foodSaturationLevel");
			this.c = le.j("foodExhaustionLevel");
		}
	}

	public void b(le le) {
		le.b("foodLevel", this.a);
		le.b("foodTickTimer", this.d);
		le.a("foodSaturationLevel", this.b);
		le.a("foodExhaustionLevel", this.c);
	}

	public int a() {
		return this.a;
	}

	public boolean c() {
		return this.a < 20;
	}

	public void a(float float1) {
		this.c = Math.min(this.c + float1, 40.0F);
	}

	public float e() {
		return this.b;
	}

	public void a(int integer) {
		this.a = integer;
	}
}

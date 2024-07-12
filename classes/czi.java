import javax.annotation.Nullable;

public class czi extends cze {
	private final boolean j;

	public czi(boolean boolean1) {
		this.j = boolean1;
	}

	@Override
	public czd b() {
		return super.a(aec.c(this.b.cb().a), aec.c(this.b.cb().b + 0.5), aec.c(this.b.cb().c));
	}

	@Override
	public czj a(double double1, double double2, double double3) {
		return new czj(super.a(aec.c(double1 - (double)(this.b.cx() / 2.0F)), aec.c(double2 + 0.5), aec.c(double3 - (double)(this.b.cx() / 2.0F))));
	}

	@Override
	public int a(czd[] arr, czd czd) {
		int integer4 = 0;

		for (fz fz8 : fz.values()) {
			czd czd9 = this.b(czd.a + fz8.i(), czd.b + fz8.j(), czd.c + fz8.k());
			if (czd9 != null && !czd9.i) {
				arr[integer4++] = czd9;
			}
		}

		return integer4;
	}

	@Override
	public czb a(bpg bpg, int integer2, int integer3, int integer4, aoz aoz, int integer6, int integer7, int integer8, boolean boolean9, boolean boolean10) {
		return this.a(bpg, integer2, integer3, integer4);
	}

	@Override
	public czb a(bpg bpg, int integer2, int integer3, int integer4) {
		fu fu6 = new fu(integer2, integer3, integer4);
		cxa cxa7 = bpg.b(fu6);
		cfj cfj8 = bpg.d_(fu6);
		if (cxa7.c() && cfj8.a(bpg, fu6.c(), czg.WATER) && cfj8.g()) {
			return czb.BREACH;
		} else {
			return cxa7.a(acz.a) && cfj8.a(bpg, fu6, czg.WATER) ? czb.WATER : czb.BLOCKED;
		}
	}

	@Nullable
	private czd b(int integer1, int integer2, int integer3) {
		czb czb5 = this.c(integer1, integer2, integer3);
		return (!this.j || czb5 != czb.BREACH) && czb5 != czb.WATER ? null : this.a(integer1, integer2, integer3);
	}

	@Nullable
	@Override
	protected czd a(int integer1, int integer2, int integer3) {
		czd czd5 = null;
		czb czb6 = this.a(this.b.l, integer1, integer2, integer3);
		float float7 = this.b.a(czb6);
		if (float7 >= 0.0F) {
			czd5 = super.a(integer1, integer2, integer3);
			czd5.l = czb6;
			czd5.k = Math.max(czd5.k, float7);
			if (this.a.b(new fu(integer1, integer2, integer3)).c()) {
				czd5.k += 8.0F;
			}
		}

		return czb6 == czb.OPEN ? czd5 : czd5;
	}

	private czb c(int integer1, int integer2, int integer3) {
		fu.a a5 = new fu.a();

		for (int integer6 = integer1; integer6 < integer1 + this.d; integer6++) {
			for (int integer7 = integer2; integer7 < integer2 + this.e; integer7++) {
				for (int integer8 = integer3; integer8 < integer3 + this.f; integer8++) {
					cxa cxa9 = this.a.b(a5.d(integer6, integer7, integer8));
					cfj cfj10 = this.a.d_(a5.d(integer6, integer7, integer8));
					if (cxa9.c() && cfj10.a(this.a, a5.c(), czg.WATER) && cfj10.g()) {
						return czb.BREACH;
					}

					if (!cxa9.a(acz.a)) {
						return czb.BLOCKED;
					}
				}
			}
		}

		cfj cfj6 = this.a.d_(a5);
		return cfj6.a(this.a, a5, czg.WATER) ? czb.WATER : czb.BLOCKED;
	}
}

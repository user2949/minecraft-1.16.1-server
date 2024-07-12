import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class bdw implements bpm {
	private final Random a = new Random();
	private final dak b;
	private int c;
	private int d;
	private int e;

	public bdw(dak dak) {
		this.b = dak;
		this.c = 1200;
		this.d = dak.u();
		this.e = dak.v();
		if (this.d == 0 && this.e == 0) {
			this.d = 24000;
			dak.g(this.d);
			this.e = 25;
			dak.h(this.e);
		}
	}

	@Override
	public int a(zd zd, boolean boolean2, boolean boolean3) {
		if (!zd.S().b(bpx.E)) {
			return 0;
		} else if (--this.c > 0) {
			return 0;
		} else {
			this.c = 1200;
			this.d -= 1200;
			this.b.g(this.d);
			if (this.d > 0) {
				return 0;
			} else {
				this.d = 24000;
				if (!zd.S().b(bpx.d)) {
					return 0;
				} else {
					int integer5 = this.e;
					this.e = aec.a(this.e + 25, 25, 75);
					this.b.h(this.e);
					if (this.a.nextInt(100) > integer5) {
						return 0;
					} else if (this.a(zd)) {
						this.e = 25;
						return 1;
					} else {
						return 0;
					}
				}
			}
		}
	}

	private boolean a(zd zd) {
		bec bec3 = zd.h();
		if (bec3 == null) {
			return true;
		} else if (this.a.nextInt(10) != 0) {
			return false;
		} else {
			fu fu4 = bec3.cA();
			int integer5 = 48;
			axz axz6 = zd.x();
			Optional<fu> optional7 = axz6.b(ayc.s.c(), fu -> true, fu4, 48, axz.b.ANY);
			fu fu8 = (fu)optional7.orElse(fu4);
			fu fu9 = this.a(zd, fu8, 48);
			if (fu9 != null && this.a(zd, fu9)) {
				if (zd.v(fu9) == brk.aa) {
					return false;
				}

				bdv bdv10 = aoq.aQ.a(zd, null, null, null, fu9, apb.EVENT, false, false);
				if (bdv10 != null) {
					for (int integer11 = 0; integer11 < 2; integer11++) {
						this.a(bdv10, 4);
					}

					this.b.a(bdv10.bR());
					bdv10.u(48000);
					bdv10.g(fu8);
					bdv10.a(fu8, 16);
					return true;
				}
			}

			return false;
		}
	}

	private void a(bdv bdv, int integer) {
		fu fu4 = this.a(bdv.l, bdv.cA(), integer);
		if (fu4 != null) {
			azu azu5 = aoq.aK.a(bdv.l, null, null, null, fu4, apb.EVENT, false, false);
			if (azu5 != null) {
				azu5.b(bdv, true);
			}
		}
	}

	@Nullable
	private fu a(bqd bqd, fu fu, int integer) {
		fu fu5 = null;

		for (int integer6 = 0; integer6 < 10; integer6++) {
			int integer7 = fu.u() + this.a.nextInt(integer * 2) - integer;
			int integer8 = fu.w() + this.a.nextInt(integer * 2) - integer;
			int integer9 = bqd.a(cio.a.WORLD_SURFACE, integer7, integer8);
			fu fu10 = new fu(integer7, integer9, integer8);
			if (bqj.a(app.c.ON_GROUND, bqd, fu10, aoq.aQ)) {
				fu5 = fu10;
				break;
			}
		}

		return fu5;
	}

	private boolean a(bpg bpg, fu fu) {
		for (fu fu5 : fu.a(fu, fu.b(1, 2, 1))) {
			if (!bpg.d_(fu5).k(bpg, fu5).b()) {
				return false;
			}
		}

		return true;
	}
}

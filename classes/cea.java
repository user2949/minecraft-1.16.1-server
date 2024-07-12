import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class cea extends cef implements cdz, ceo {
	private gi<bki> i = gi.a(5, bki.b);
	private int j = -1;
	private long k;

	public cea() {
		super(cdm.q);
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.i = gi.a(this.ab_(), bki.b);
		if (!this.b(le)) {
			ana.b(le, this.i);
		}

		this.j = le.h("TransferCooldown");
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (!this.c(le)) {
			ana.a(le, this.i);
		}

		le.b("TransferCooldown", this.j);
		return le;
	}

	@Override
	public int ab_() {
		return this.i.size();
	}

	@Override
	public bki a(int integer1, int integer2) {
		this.d(null);
		return ana.a(this.f(), integer1, integer2);
	}

	@Override
	public void a(int integer, bki bki) {
		this.d(null);
		this.f().set(integer, bki);
		if (bki.E() > this.X_()) {
			bki.e(this.X_());
		}
	}

	@Override
	protected mr g() {
		return new ne("container.hopper");
	}

	@Override
	public void al_() {
		if (this.d != null && !this.d.v) {
			this.j--;
			this.k = this.d.Q();
			if (!this.m()) {
				this.c(0);
				this.a(() -> a(this));
			}
		}
	}

	private boolean a(Supplier<Boolean> supplier) {
		if (this.d != null && !this.d.v) {
			if (!this.m() && (Boolean)this.p().c(byo.b)) {
				boolean boolean3 = false;
				if (!this.c()) {
					boolean3 = this.k();
				}

				if (!this.j()) {
					boolean3 |= supplier.get();
				}

				if (boolean3) {
					this.c(8);
					this.Z_();
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	private boolean j() {
		for (bki bki3 : this.i) {
			if (bki3.a() || bki3.E() != bki3.c()) {
				return false;
			}
		}

		return true;
	}

	private boolean k() {
		amz amz2 = this.l();
		if (amz2 == null) {
			return false;
		} else {
			fz fz3 = ((fz)this.p().c(byo.a)).f();
			if (this.b(amz2, fz3)) {
				return false;
			} else {
				for (int integer4 = 0; integer4 < this.ab_(); integer4++) {
					if (!this.a(integer4).a()) {
						bki bki5 = this.a(integer4).i();
						bki bki6 = a(this, amz2, this.a(integer4, 1), fz3);
						if (bki6.a()) {
							amz2.Z_();
							return true;
						}

						this.a(integer4, bki5);
					}
				}

				return false;
			}
		}
	}

	private static IntStream a(amz amz, fz fz) {
		return amz instanceof anq ? IntStream.of(((anq)amz).a(fz)) : IntStream.range(0, amz.ab_());
	}

	private boolean b(amz amz, fz fz) {
		return a(amz, fz).allMatch(integer -> {
			bki bki3 = amz.a(integer);
			return bki3.E() >= bki3.c();
		});
	}

	private static boolean c(amz amz, fz fz) {
		return a(amz, fz).allMatch(integer -> amz.a(integer).a());
	}

	public static boolean a(cdz cdz) {
		amz amz2 = b(cdz);
		if (amz2 != null) {
			fz fz3 = fz.DOWN;
			return c(amz2, fz3) ? false : a(amz2, fz3).anyMatch(integer -> a(cdz, amz2, integer, fz3));
		} else {
			for (bbg bbg4 : c(cdz)) {
				if (a(cdz, bbg4)) {
					return true;
				}
			}

			return false;
		}
	}

	private static boolean a(cdz cdz, amz amz, int integer, fz fz) {
		bki bki5 = amz.a(integer);
		if (!bki5.a() && b(amz, bki5, integer, fz)) {
			bki bki6 = bki5.i();
			bki bki7 = a(amz, cdz, amz.a(integer, 1), null);
			if (bki7.a()) {
				amz.Z_();
				return true;
			}

			amz.a(integer, bki6);
		}

		return false;
	}

	public static boolean a(amz amz, bbg bbg) {
		boolean boolean3 = false;
		bki bki4 = bbg.g().i();
		bki bki5 = a(null, amz, bki4, null);
		if (bki5.a()) {
			boolean3 = true;
			bbg.aa();
		} else {
			bbg.b(bki5);
		}

		return boolean3;
	}

	public static bki a(@Nullable amz amz1, amz amz2, bki bki, @Nullable fz fz) {
		if (amz2 instanceof anq && fz != null) {
			anq anq5 = (anq)amz2;
			int[] arr6 = anq5.a(fz);

			for (int integer7 = 0; integer7 < arr6.length && !bki.a(); integer7++) {
				bki = a(amz1, amz2, bki, arr6[integer7], fz);
			}
		} else {
			int integer5 = amz2.ab_();

			for (int integer6 = 0; integer6 < integer5 && !bki.a(); integer6++) {
				bki = a(amz1, amz2, bki, integer6, fz);
			}
		}

		return bki;
	}

	private static boolean a(amz amz, bki bki, int integer, @Nullable fz fz) {
		return !amz.b(integer, bki) ? false : !(amz instanceof anq) || ((anq)amz).a(integer, bki, fz);
	}

	private static boolean b(amz amz, bki bki, int integer, fz fz) {
		return !(amz instanceof anq) || ((anq)amz).b(integer, bki, fz);
	}

	private static bki a(@Nullable amz amz1, amz amz2, bki bki, int integer, @Nullable fz fz) {
		bki bki6 = amz2.a(integer);
		if (a(amz2, bki, integer, fz)) {
			boolean boolean7 = false;
			boolean boolean8 = amz2.c();
			if (bki6.a()) {
				amz2.a(integer, bki);
				bki = bki.b;
				boolean7 = true;
			} else if (a(bki6, bki)) {
				int integer9 = bki.c() - bki6.E();
				int integer10 = Math.min(bki.E(), integer9);
				bki.g(integer10);
				bki6.f(integer10);
				boolean7 = integer10 > 0;
			}

			if (boolean7) {
				if (boolean8 && amz2 instanceof cea) {
					cea cea9 = (cea)amz2;
					if (!cea9.y()) {
						int integer10 = 0;
						if (amz1 instanceof cea) {
							cea cea11 = (cea)amz1;
							if (cea9.k >= cea11.k) {
								integer10 = 1;
							}
						}

						cea9.c(8 - integer10);
					}
				}

				amz2.Z_();
			}
		}

		return bki;
	}

	@Nullable
	private amz l() {
		fz fz2 = this.p().c(byo.a);
		return b(this.v(), this.e.a(fz2));
	}

	@Nullable
	public static amz b(cdz cdz) {
		return a(cdz.v(), cdz.x(), cdz.z() + 1.0, cdz.A());
	}

	public static List<bbg> c(cdz cdz) {
		return (List<bbg>)cdz.ac_()
			.d()
			.stream()
			.flatMap(deg -> cdz.v().a(bbg.class, deg.d(cdz.x() - 0.5, cdz.z() - 0.5, cdz.A() - 0.5), aop.a).stream())
			.collect(Collectors.toList());
	}

	@Nullable
	public static amz b(bqb bqb, fu fu) {
		return a(bqb, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5);
	}

	@Nullable
	public static amz a(bqb bqb, double double2, double double3, double double4) {
		amz amz8 = null;
		fu fu9 = new fu(double2, double3, double4);
		cfj cfj10 = bqb.d_(fu9);
		bvr bvr11 = cfj10.b();
		if (bvr11 instanceof anr) {
			amz8 = ((anr)bvr11).a(cfj10, bqb, fu9);
		} else if (bvr11.q()) {
			cdl cdl12 = bqb.c(fu9);
			if (cdl12 instanceof amz) {
				amz8 = (amz)cdl12;
				if (amz8 instanceof cdp && bvr11 instanceof bwh) {
					amz8 = bwh.a((bwh)bvr11, cfj10, bqb, fu9, true);
				}
			}
		}

		if (amz8 == null) {
			List<aom> list12 = bqb.a((aom)null, new deg(double2 - 0.5, double3 - 0.5, double4 - 0.5, double2 + 0.5, double3 + 0.5, double4 + 0.5), aop.d);
			if (!list12.isEmpty()) {
				amz8 = (amz)list12.get(bqb.t.nextInt(list12.size()));
			}
		}

		return amz8;
	}

	private static boolean a(bki bki1, bki bki2) {
		if (bki1.b() != bki2.b()) {
			return false;
		} else if (bki1.g() != bki2.g()) {
			return false;
		} else {
			return bki1.E() > bki1.c() ? false : bki.a(bki1, bki2);
		}
	}

	@Override
	public double x() {
		return (double)this.e.u() + 0.5;
	}

	@Override
	public double z() {
		return (double)this.e.v() + 0.5;
	}

	@Override
	public double A() {
		return (double)this.e.w() + 0.5;
	}

	private void c(int integer) {
		this.j = integer;
	}

	private boolean m() {
		return this.j > 0;
	}

	private boolean y() {
		return this.j > 8;
	}

	@Override
	protected gi<bki> f() {
		return this.i;
	}

	@Override
	protected void a(gi<bki> gi) {
		this.i = gi;
	}

	public void a(aom aom) {
		if (aom instanceof bbg) {
			fu fu3 = this.o();
			if (dfd.c(dfd.a(aom.cb().d((double)(-fu3.u()), (double)(-fu3.v()), (double)(-fu3.w()))), this.ac_(), deq.i)) {
				this.a(() -> a(this, (bbg)aom));
			}
		}
	}

	@Override
	protected bgi a(int integer, beb beb) {
		return new bhd(integer, beb, this);
	}
}

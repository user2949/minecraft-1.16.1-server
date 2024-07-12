import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;

public class beb implements amz, ank {
	public final gi<bki> a = gi.a(36, bki.b);
	public final gi<bki> b = gi.a(4, bki.b);
	public final gi<bki> c = gi.a(1, bki.b);
	private final List<gi<bki>> f = ImmutableList.of(this.a, this.b, this.c);
	public int d;
	public final bec e;
	private bki g = bki.b;
	private int h;

	public beb(bec bec) {
		this.e = bec;
	}

	public bki f() {
		return d(this.d) ? this.a.get(this.d) : bki.b;
	}

	public static int g() {
		return 9;
	}

	private boolean a(bki bki1, bki bki2) {
		return !bki1.a() && this.b(bki1, bki2) && bki1.d() && bki1.E() < bki1.c() && bki1.E() < this.X_();
	}

	private boolean b(bki bki1, bki bki2) {
		return bki1.b() == bki2.b() && bki.a(bki1, bki2);
	}

	public int h() {
		for (int integer2 = 0; integer2 < this.a.size(); integer2++) {
			if (this.a.get(integer2).a()) {
				return integer2;
			}
		}

		return -1;
	}

	public void c(int integer) {
		this.d = this.i();
		bki bki3 = this.a.get(this.d);
		this.a.set(this.d, this.a.get(integer));
		this.a.set(integer, bki3);
	}

	public static boolean d(int integer) {
		return integer >= 0 && integer < 9;
	}

	public int c(bki bki) {
		for (int integer3 = 0; integer3 < this.a.size(); integer3++) {
			bki bki4 = this.a.get(integer3);
			if (!this.a.get(integer3).a() && this.b(bki, this.a.get(integer3)) && !this.a.get(integer3).f() && !bki4.x() && !bki4.t()) {
				return integer3;
			}
		}

		return -1;
	}

	public int i() {
		for (int integer2 = 0; integer2 < 9; integer2++) {
			int integer3 = (this.d + integer2) % 9;
			if (this.a.get(integer3).a()) {
				return integer3;
			}
		}

		for (int integer2x = 0; integer2x < 9; integer2x++) {
			int integer3 = (this.d + integer2x) % 9;
			if (!this.a.get(integer3).x()) {
				return integer3;
			}
		}

		return this.d;
	}

	public int a(Predicate<bki> predicate, int integer, amz amz) {
		int integer5 = 0;
		boolean boolean6 = integer == 0;
		integer5 += ana.a(this, predicate, integer - integer5, boolean6);
		integer5 += ana.a(amz, predicate, integer - integer5, boolean6);
		integer5 += ana.a(this.g, predicate, integer - integer5, boolean6);
		if (this.g.a()) {
			this.g = bki.b;
		}

		return integer5;
	}

	private int i(bki bki) {
		int integer3 = this.d(bki);
		if (integer3 == -1) {
			integer3 = this.h();
		}

		return integer3 == -1 ? bki.E() : this.d(integer3, bki);
	}

	private int d(int integer, bki bki) {
		bke bke4 = bki.b();
		int integer5 = bki.E();
		bki bki6 = this.a(integer);
		if (bki6.a()) {
			bki6 = new bki(bke4, 0);
			if (bki.n()) {
				bki6.c(bki.o().g());
			}

			this.a(integer, bki6);
		}

		int integer7 = integer5;
		if (integer5 > bki6.c() - bki6.E()) {
			integer7 = bki6.c() - bki6.E();
		}

		if (integer7 > this.X_() - bki6.E()) {
			integer7 = this.X_() - bki6.E();
		}

		if (integer7 == 0) {
			return integer5;
		} else {
			integer5 -= integer7;
			bki6.f(integer7);
			bki6.d(5);
			return integer5;
		}
	}

	public int d(bki bki) {
		if (this.a(this.a(this.d), bki)) {
			return this.d;
		} else if (this.a(this.a(40), bki)) {
			return 40;
		} else {
			for (int integer3 = 0; integer3 < this.a.size(); integer3++) {
				if (this.a(this.a.get(integer3), bki)) {
					return integer3;
				}
			}

			return -1;
		}
	}

	public void j() {
		for (gi<bki> gi3 : this.f) {
			for (int integer4 = 0; integer4 < gi3.size(); integer4++) {
				if (!gi3.get(integer4).a()) {
					gi3.get(integer4).a(this.e.l, this.e, integer4, this.d == integer4);
				}
			}
		}
	}

	public boolean e(bki bki) {
		return this.c(-1, bki);
	}

	public boolean c(int integer, bki bki) {
		if (bki.a()) {
			return false;
		} else {
			try {
				if (bki.f()) {
					if (integer == -1) {
						integer = this.h();
					}

					if (integer >= 0) {
						this.a.set(integer, bki.i());
						this.a.get(integer).d(5);
						bki.e(0);
						return true;
					} else if (this.e.bJ.d) {
						bki.e(0);
						return true;
					} else {
						return false;
					}
				} else {
					int integer4;
					do {
						integer4 = bki.E();
						if (integer == -1) {
							bki.e(this.i(bki));
						} else {
							bki.e(this.d(integer, bki));
						}
					} while (!bki.a() && bki.E() < integer4);

					if (bki.E() == integer4 && this.e.bJ.d) {
						bki.e(0);
						return true;
					} else {
						return bki.E() < integer4;
					}
				}
			} catch (Throwable var6) {
				j j5 = j.a(var6, "Adding item to inventory");
				k k6 = j5.a("Item being added");
				k6.a("Item ID", bke.a(bki.b()));
				k6.a("Item data", bki.g());
				k6.a("Item name", (l<String>)(() -> bki.r().getString()));
				throw new s(j5);
			}
		}
	}

	public void a(bqb bqb, bki bki) {
		if (!bqb.v) {
			while (!bki.a()) {
				int integer4 = this.d(bki);
				if (integer4 == -1) {
					integer4 = this.h();
				}

				if (integer4 == -1) {
					this.e.a(bki, false);
					break;
				}

				int integer5 = bki.c() - this.a(integer4).E();
				if (this.c(integer4, bki.a(integer5))) {
					((ze)this.e).b.a(new oi(-2, integer4, this.a(integer4)));
				}
			}
		}
	}

	@Override
	public bki a(int integer1, int integer2) {
		List<bki> list4 = null;

		for (gi<bki> gi6 : this.f) {
			if (integer1 < gi6.size()) {
				list4 = gi6;
				break;
			}

			integer1 -= gi6.size();
		}

		return list4 != null && !((bki)list4.get(integer1)).a() ? ana.a(list4, integer1, integer2) : bki.b;
	}

	public void f(bki bki) {
		for (gi<bki> gi4 : this.f) {
			for (int integer5 = 0; integer5 < gi4.size(); integer5++) {
				if (gi4.get(integer5) == bki) {
					gi4.set(integer5, bki.b);
					break;
				}
			}
		}
	}

	@Override
	public bki b(int integer) {
		gi<bki> gi3 = null;

		for (gi<bki> gi5 : this.f) {
			if (integer < gi5.size()) {
				gi3 = gi5;
				break;
			}

			integer -= gi5.size();
		}

		if (gi3 != null && !gi3.get(integer).a()) {
			bki bki4 = gi3.get(integer);
			gi3.set(integer, bki.b);
			return bki4;
		} else {
			return bki.b;
		}
	}

	@Override
	public void a(int integer, bki bki) {
		gi<bki> gi4 = null;

		for (gi<bki> gi6 : this.f) {
			if (integer < gi6.size()) {
				gi4 = gi6;
				break;
			}

			integer -= gi6.size();
		}

		if (gi4 != null) {
			gi4.set(integer, bki);
		}
	}

	public float a(cfj cfj) {
		return this.a.get(this.d).a(cfj);
	}

	public lk a(lk lk) {
		for (int integer3 = 0; integer3 < this.a.size(); integer3++) {
			if (!this.a.get(integer3).a()) {
				le le4 = new le();
				le4.a("Slot", (byte)integer3);
				this.a.get(integer3).b(le4);
				lk.add(le4);
			}
		}

		for (int integer3x = 0; integer3x < this.b.size(); integer3x++) {
			if (!this.b.get(integer3x).a()) {
				le le4 = new le();
				le4.a("Slot", (byte)(integer3x + 100));
				this.b.get(integer3x).b(le4);
				lk.add(le4);
			}
		}

		for (int integer3xx = 0; integer3xx < this.c.size(); integer3xx++) {
			if (!this.c.get(integer3xx).a()) {
				le le4 = new le();
				le4.a("Slot", (byte)(integer3xx + 150));
				this.c.get(integer3xx).b(le4);
				lk.add(le4);
			}
		}

		return lk;
	}

	public void b(lk lk) {
		this.a.clear();
		this.b.clear();
		this.c.clear();

		for (int integer3 = 0; integer3 < lk.size(); integer3++) {
			le le4 = lk.a(integer3);
			int integer5 = le4.f("Slot") & 255;
			bki bki6 = bki.a(le4);
			if (!bki6.a()) {
				if (integer5 >= 0 && integer5 < this.a.size()) {
					this.a.set(integer5, bki6);
				} else if (integer5 >= 100 && integer5 < this.b.size() + 100) {
					this.b.set(integer5 - 100, bki6);
				} else if (integer5 >= 150 && integer5 < this.c.size() + 150) {
					this.c.set(integer5 - 150, bki6);
				}
			}
		}
	}

	@Override
	public int ab_() {
		return this.a.size() + this.b.size() + this.c.size();
	}

	@Override
	public boolean c() {
		for (bki bki3 : this.a) {
			if (!bki3.a()) {
				return false;
			}
		}

		for (bki bki3x : this.b) {
			if (!bki3x.a()) {
				return false;
			}
		}

		for (bki bki3xx : this.c) {
			if (!bki3xx.a()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public bki a(int integer) {
		List<bki> list3 = null;

		for (gi<bki> gi5 : this.f) {
			if (integer < gi5.size()) {
				list3 = gi5;
				break;
			}

			integer -= gi5.size();
		}

		return list3 == null ? bki.b : (bki)list3.get(integer);
	}

	@Override
	public mr P() {
		return new ne("container.inventory");
	}

	public void a(anw anw, float float2) {
		if (!(float2 <= 0.0F)) {
			float2 /= 4.0F;
			if (float2 < 1.0F) {
				float2 = 1.0F;
			}

			for (int integer4 = 0; integer4 < this.b.size(); integer4++) {
				bki bki5 = this.b.get(integer4);
				if ((!anw.p() || !bki5.b().u()) && bki5.b() instanceof bid) {
					int integer6 = integer4;
					bki5.a((int)float2, this.e, bec -> bec.c(aor.a(aor.a.ARMOR, integer6)));
				}
			}
		}
	}

	public void k() {
		for (List<bki> list3 : this.f) {
			for (int integer4 = 0; integer4 < list3.size(); integer4++) {
				bki bki5 = (bki)list3.get(integer4);
				if (!bki5.a()) {
					this.e.a(bki5, true, false);
					list3.set(integer4, bki.b);
				}
			}
		}
	}

	@Override
	public void Z_() {
		this.h++;
	}

	public void g(bki bki) {
		this.g = bki;
	}

	public bki m() {
		return this.g;
	}

	@Override
	public boolean a(bec bec) {
		return this.e.y ? false : !(bec.h(this.e) > 64.0);
	}

	public boolean h(bki bki) {
		for (List<bki> list4 : this.f) {
			for (bki bki6 : list4) {
				if (!bki6.a() && bki6.a(bki)) {
					return true;
				}
			}
		}

		return false;
	}

	public void a(beb beb) {
		for (int integer3 = 0; integer3 < this.ab_(); integer3++) {
			this.a(integer3, beb.a(integer3));
		}

		this.d = beb.d;
	}

	@Override
	public void aa_() {
		for (List<bki> list3 : this.f) {
			list3.clear();
		}
	}

	public void a(bee bee) {
		for (bki bki4 : this.a) {
			bee.a(bki4);
		}
	}
}

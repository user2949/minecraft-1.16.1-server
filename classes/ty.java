import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ty<C extends amz> implements tx<Integer> {
	protected static final Logger a = LogManager.getLogger();
	protected final bee b = new bee();
	protected beb c;
	protected bhp<C> d;

	public ty(bhp<C> bhp) {
		this.d = bhp;
	}

	public void a(ze ze, @Nullable bmu<C> bmu, boolean boolean3) {
		if (bmu != null && ze.B().b(bmu)) {
			this.c = ze.bt;
			if (this.b() || ze.b_()) {
				this.b.a();
				ze.bt.a(this.b);
				this.d.a(this.b);
				if (this.b.a(bmu, null)) {
					this.a(bmu, boolean3);
				} else {
					this.a();
					ze.b.a(new pf(ze.bw.b, bmu));
				}

				ze.bt.Z_();
			}
		}
	}

	protected void a() {
		for (int integer2 = 0; integer2 < this.d.g() * this.d.h() + 1; integer2++) {
			if (integer2 != this.d.f() || !(this.d instanceof bgv) && !(this.d instanceof bhf)) {
				this.a(integer2);
			}
		}

		this.d.e();
	}

	protected void a(int integer) {
		bki bki3 = this.d.a(integer).e();
		if (!bki3.a()) {
			for (; bki3.E() > 0; this.d.a(integer).a(1)) {
				int integer4 = this.c.d(bki3);
				if (integer4 == -1) {
					integer4 = this.c.h();
				}

				bki bki5 = bki3.i();
				bki5.e(1);
				if (!this.c.c(integer4, bki5)) {
					a.error("Can't find any space for item in the inventory");
				}
			}
		}
	}

	protected void a(bmu<C> bmu, boolean boolean2) {
		boolean boolean4 = this.d.a(bmu);
		int integer5 = this.b.b(bmu, null);
		if (boolean4) {
			for (int integer6 = 0; integer6 < this.d.h() * this.d.g() + 1; integer6++) {
				if (integer6 != this.d.f()) {
					bki bki7 = this.d.a(integer6).e();
					if (!bki7.a() && Math.min(integer5, bki7.c()) < bki7.E() + 1) {
						return;
					}
				}
			}
		}

		int integer6x = this.a(boolean2, integer5, boolean4);
		IntList intList7 = new IntArrayList();
		if (this.b.a(bmu, intList7, integer6x)) {
			int integer8 = integer6x;

			for (int integer10 : intList7) {
				int integer11 = bee.a(integer10).c();
				if (integer11 < integer8) {
					integer8 = integer11;
				}
			}

			if (this.b.a(bmu, intList7, integer8)) {
				this.a();
				this.a(this.d.g(), this.d.h(), this.d.f(), bmu, intList7.iterator(), integer8);
			}
		}
	}

	@Override
	public void a(Iterator<Integer> iterator, int integer2, int integer3, int integer4, int integer5) {
		bhw bhw7 = this.d.a(integer2);
		bki bki8 = bee.a((Integer)iterator.next());
		if (!bki8.a()) {
			for (int integer9 = 0; integer9 < integer3; integer9++) {
				this.a(bhw7, bki8);
			}
		}
	}

	protected int a(boolean boolean1, int integer, boolean boolean3) {
		int integer5 = 1;
		if (boolean1) {
			integer5 = integer;
		} else if (boolean3) {
			integer5 = 64;

			for (int integer6 = 0; integer6 < this.d.g() * this.d.h() + 1; integer6++) {
				if (integer6 != this.d.f()) {
					bki bki7 = this.d.a(integer6).e();
					if (!bki7.a() && integer5 > bki7.E()) {
						integer5 = bki7.E();
					}
				}
			}

			if (integer5 < 64) {
				integer5++;
			}
		}

		return integer5;
	}

	protected void a(bhw bhw, bki bki) {
		int integer4 = this.c.c(bki);
		if (integer4 != -1) {
			bki bki5 = this.c.a(integer4).i();
			if (!bki5.a()) {
				if (bki5.E() > 1) {
					this.c.a(integer4, 1);
				} else {
					this.c.b(integer4);
				}

				bki5.e(1);
				if (bhw.e().a()) {
					bhw.d(bki5);
				} else {
					bhw.e().f(1);
				}
			}
		}
	}

	private boolean b() {
		List<bki> list2 = Lists.<bki>newArrayList();
		int integer3 = this.c();

		for (int integer4 = 0; integer4 < this.d.g() * this.d.h() + 1; integer4++) {
			if (integer4 != this.d.f()) {
				bki bki5 = this.d.a(integer4).e().i();
				if (!bki5.a()) {
					int integer6 = this.c.d(bki5);
					if (integer6 == -1 && list2.size() <= integer3) {
						for (bki bki8 : list2) {
							if (bki8.a(bki5) && bki8.E() != bki8.c() && bki8.E() + bki5.E() <= bki8.c()) {
								bki8.f(bki5.E());
								bki5.e(0);
								break;
							}
						}

						if (!bki5.a()) {
							if (list2.size() >= integer3) {
								return false;
							}

							list2.add(bki5);
						}
					} else if (integer6 == -1) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private int c() {
		int integer2 = 0;

		for (bki bki4 : this.c.a) {
			if (bki4.a()) {
				integer2++;
			}
		}

		return integer2;
	}
}

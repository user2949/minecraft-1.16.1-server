import javax.annotation.Nullable;

public class bhl implements amz {
	private final boy a;
	private final gi<bki> b = gi.a(3, bki.b);
	@Nullable
	private boz c;
	private int d;
	private int e;

	public bhl(boy boy) {
		this.a = boy;
	}

	@Override
	public int ab_() {
		return this.b.size();
	}

	@Override
	public boolean c() {
		for (bki bki3 : this.b) {
			if (!bki3.a()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public bki a(int integer) {
		return this.b.get(integer);
	}

	@Override
	public bki a(int integer1, int integer2) {
		bki bki4 = this.b.get(integer1);
		if (integer1 == 2 && !bki4.a()) {
			return ana.a(this.b, integer1, bki4.E());
		} else {
			bki bki5 = ana.a(this.b, integer1, integer2);
			if (!bki5.a() && this.d(integer1)) {
				this.f();
			}

			return bki5;
		}
	}

	private boolean d(int integer) {
		return integer == 0 || integer == 1;
	}

	@Override
	public bki b(int integer) {
		return ana.a(this.b, integer);
	}

	@Override
	public void a(int integer, bki bki) {
		this.b.set(integer, bki);
		if (!bki.a() && bki.E() > this.X_()) {
			bki.e(this.X_());
		}

		if (this.d(integer)) {
			this.f();
		}
	}

	@Override
	public boolean a(bec bec) {
		return this.a.eN() == bec;
	}

	@Override
	public void Z_() {
		this.f();
	}

	public void f() {
		this.c = null;
		bki bki2;
		bki bki3;
		if (this.b.get(0).a()) {
			bki2 = this.b.get(1);
			bki3 = bki.b;
		} else {
			bki2 = this.b.get(0);
			bki3 = this.b.get(1);
		}

		if (bki2.a()) {
			this.a(2, bki.b);
			this.e = 0;
		} else {
			bpa bpa4 = this.a.eP();
			if (!bpa4.isEmpty()) {
				boz boz5 = bpa4.a(bki2, bki3, this.d);
				if (boz5 == null || boz5.p()) {
					this.c = boz5;
					boz5 = bpa4.a(bki3, bki2, this.d);
				}

				if (boz5 != null && !boz5.p()) {
					this.c = boz5;
					this.a(2, boz5.f());
					this.e = boz5.o();
				} else {
					this.a(2, bki.b);
					this.e = 0;
				}
			}

			this.a.k(this.a(2));
		}
	}

	@Nullable
	public boz g() {
		return this.c;
	}

	public void c(int integer) {
		this.d = integer;
		this.f();
	}

	@Override
	public void aa_() {
		this.b.clear();
	}
}

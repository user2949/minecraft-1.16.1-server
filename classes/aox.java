import java.util.List;
import javax.annotation.Nullable;

public class aox extends aom {
	private int c;
	public long b;
	private int d;
	private boolean e;
	@Nullable
	private ze f;

	public aox(aoq<? extends aox> aoq, bqb bqb) {
		super(aoq, bqb);
		this.ac = true;
		this.c = 2;
		this.b = this.J.nextLong();
		this.d = this.J.nextInt(3) + 1;
	}

	public void a(boolean boolean1) {
		this.e = boolean1;
	}

	@Override
	public acm ct() {
		return acm.WEATHER;
	}

	public void d(@Nullable ze ze) {
		this.f = ze;
	}

	@Override
	public void j() {
		super.j();
		if (this.c == 2) {
			and and2 = this.l.ac();
			if (and2 == and.NORMAL || and2 == and.HARD) {
				this.a(4);
			}

			this.l.a(null, this.cC(), this.cD(), this.cG(), acl.hd, acm.WEATHER, 10000.0F, 0.8F + this.J.nextFloat() * 0.2F);
			this.l.a(null, this.cC(), this.cD(), this.cG(), acl.hc, acm.WEATHER, 2.0F, 0.5F + this.J.nextFloat() * 0.2F);
		}

		this.c--;
		if (this.c < 0) {
			if (this.d == 0) {
				this.aa();
			} else if (this.c < -this.J.nextInt(10)) {
				this.d--;
				this.c = 1;
				this.b = this.J.nextLong();
				this.a(0);
			}
		}

		if (this.c >= 0) {
			if (this.l.v) {
				this.l.c(2);
			} else if (!this.e) {
				double double2 = 3.0;
				List<aom> list4 = this.l
					.a(this, new deg(this.cC() - 3.0, this.cD() - 3.0, this.cG() - 3.0, this.cC() + 3.0, this.cD() + 6.0 + 3.0, this.cG() + 3.0), aom::aU);

				for (aom aom6 : list4) {
					aom6.a(this);
				}

				if (this.f != null) {
					aa.E.a(this.f, list4);
				}
			}
		}
	}

	private void a(int integer) {
		if (!this.e && !this.l.v && this.l.S().b(bpx.a)) {
			fu fu3 = this.cA();
			cfj cfj4 = bvh.a((bpg)this.l, fu3);
			if (this.l.d_(fu3).g() && cfj4.a((bqd)this.l, fu3)) {
				this.l.a(fu3, cfj4);
			}

			for (int integer5 = 0; integer5 < integer; integer5++) {
				fu fu6 = fu3.b(this.J.nextInt(3) - 1, this.J.nextInt(3) - 1, this.J.nextInt(3) - 1);
				cfj4 = bvh.a((bpg)this.l, fu6);
				if (this.l.d_(fu6).g() && cfj4.a((bqd)this.l, fu6)) {
					this.l.a(fu6, cfj4);
				}
			}
		}
	}

	@Override
	protected void e() {
	}

	@Override
	protected void a(le le) {
	}

	@Override
	protected void b(le le) {
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}

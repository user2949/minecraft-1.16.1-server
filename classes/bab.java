import java.util.Optional;
import javax.annotation.Nullable;

public class bab extends aom {
	private static final tq<Optional<fu>> c = tt.a(bab.class, ts.m);
	private static final tq<Boolean> d = tt.a(bab.class, ts.i);
	public int b;

	public bab(aoq<? extends bab> aoq, bqb bqb) {
		super(aoq, bqb);
		this.i = true;
		this.b = this.J.nextInt(100000);
	}

	public bab(bqb bqb, double double2, double double3, double double4) {
		this(aoq.s, bqb);
		this.d(double2, double3, double4);
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	protected void e() {
		this.Y().a(c, Optional.empty());
		this.Y().a(d, true);
	}

	@Override
	public void j() {
		this.b++;
		if (this.l instanceof zd) {
			fu fu2 = this.cA();
			if (((zd)this.l).C() != null && this.l.d_(fu2).g()) {
				this.l.a(fu2, bvh.a((bpg)this.l, fu2));
			}
		}
	}

	@Override
	protected void b(le le) {
		if (this.g() != null) {
			le.a("BeamTarget", lq.a(this.g()));
		}

		le.a("ShowBottom", this.h());
	}

	@Override
	protected void a(le le) {
		if (le.c("BeamTarget", 10)) {
			this.a(lq.b(le.p("BeamTarget")));
		}

		if (le.c("ShowBottom", 1)) {
			this.a(le.q("ShowBottom"));
		}
	}

	@Override
	public boolean aQ() {
		return true;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else if (anw.k() instanceof bac) {
			return false;
		} else {
			if (!this.y && !this.l.v) {
				this.aa();
				if (!anw.d()) {
					this.l.a(null, this.cC(), this.cD(), this.cG(), 6.0F, bpt.a.DESTROY);
				}

				this.a(anw);
			}

			return true;
		}
	}

	@Override
	public void X() {
		this.a(anw.n);
		super.X();
	}

	private void a(anw anw) {
		if (this.l instanceof zd) {
			cii cii3 = ((zd)this.l).C();
			if (cii3 != null) {
				cii3.a(this, anw);
			}
		}
	}

	public void a(@Nullable fu fu) {
		this.Y().b(c, Optional.ofNullable(fu));
	}

	@Nullable
	public fu g() {
		return (fu)this.Y().a(c).orElse(null);
	}

	public void a(boolean boolean1) {
		this.Y().b(d, boolean1);
	}

	public boolean h() {
		return this.Y().a(d);
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class ceg extends cef implements anq, ceo {
	private static final int[] a = IntStream.range(0, 27).toArray();
	private gi<bki> b = gi.a(27, bki.b);
	private int c;
	private ceg.a i = ceg.a.CLOSED;
	private float j;
	private float k;
	@Nullable
	private bje l;
	private boolean m;

	public ceg(@Nullable bje bje) {
		super(cdm.w);
		this.l = bje;
	}

	public ceg() {
		this(null);
		this.m = true;
	}

	@Override
	public void al_() {
		this.h();
		if (this.i == ceg.a.OPENING || this.i == ceg.a.CLOSING) {
			this.m();
		}
	}

	protected void h() {
		this.k = this.j;
		switch (this.i) {
			case CLOSED:
				this.j = 0.0F;
				break;
			case OPENING:
				this.j += 0.1F;
				if (this.j >= 1.0F) {
					this.m();
					this.i = ceg.a.OPENED;
					this.j = 1.0F;
					this.x();
				}
				break;
			case CLOSING:
				this.j -= 0.1F;
				if (this.j <= 0.0F) {
					this.i = ceg.a.CLOSED;
					this.j = 0.0F;
					this.x();
				}
				break;
			case OPENED:
				this.j = 1.0F;
		}
	}

	public ceg.a j() {
		return this.i;
	}

	public deg a(cfj cfj) {
		return this.b(cfj.c(cav.a));
	}

	public deg b(fz fz) {
		float float3 = this.a(1.0F);
		return dfd.b().a().b((double)(0.5F * float3 * (float)fz.i()), (double)(0.5F * float3 * (float)fz.j()), (double)(0.5F * float3 * (float)fz.k()));
	}

	private deg c(fz fz) {
		fz fz3 = fz.f();
		return this.b(fz).a((double)fz3.i(), (double)fz3.j(), (double)fz3.k());
	}

	private void m() {
		cfj cfj2 = this.d.d_(this.o());
		if (cfj2.b() instanceof cav) {
			fz fz3 = cfj2.c(cav.a);
			deg deg4 = this.c(fz3).a(this.e);
			List<aom> list5 = this.d.a(null, deg4);
			if (!list5.isEmpty()) {
				for (int integer6 = 0; integer6 < list5.size(); integer6++) {
					aom aom7 = (aom)list5.get(integer6);
					if (aom7.z_() != cxf.IGNORE) {
						double double8 = 0.0;
						double double10 = 0.0;
						double double12 = 0.0;
						deg deg14 = aom7.cb();
						switch (fz3.n()) {
							case X:
								if (fz3.e() == fz.b.POSITIVE) {
									double8 = deg4.d - deg14.a;
								} else {
									double8 = deg14.d - deg4.a;
								}

								double8 += 0.01;
								break;
							case Y:
								if (fz3.e() == fz.b.POSITIVE) {
									double10 = deg4.e - deg14.b;
								} else {
									double10 = deg14.e - deg4.b;
								}

								double10 += 0.01;
								break;
							case Z:
								if (fz3.e() == fz.b.POSITIVE) {
									double12 = deg4.f - deg14.c;
								} else {
									double12 = deg14.f - deg4.c;
								}

								double12 += 0.01;
						}

						aom7.a(apd.SHULKER_BOX, new dem(double8 * (double)fz3.i(), double10 * (double)fz3.j(), double12 * (double)fz3.k()));
					}
				}
			}
		}
	}

	@Override
	public int ab_() {
		return this.b.size();
	}

	@Override
	public boolean a_(int integer1, int integer2) {
		if (integer1 == 1) {
			this.c = integer2;
			if (integer2 == 0) {
				this.i = ceg.a.CLOSING;
				this.x();
			}

			if (integer2 == 1) {
				this.i = ceg.a.OPENING;
				this.x();
			}

			return true;
		} else {
			return super.a_(integer1, integer2);
		}
	}

	private void x() {
		this.p().a(this.v(), this.o(), 3);
	}

	@Override
	public void c_(bec bec) {
		if (!bec.a_()) {
			if (this.c < 0) {
				this.c = 0;
			}

			this.c++;
			this.d.a(this.e, this.p().b(), 1, this.c);
			if (this.c == 1) {
				this.d.a(null, this.e, acl.mX, acm.BLOCKS, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	public void b_(bec bec) {
		if (!bec.a_()) {
			this.c--;
			this.d.a(this.e, this.p().b(), 1, this.c);
			if (this.c <= 0) {
				this.d.a(null, this.e, acl.mW, acm.BLOCKS, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
			}
		}
	}

	@Override
	protected mr g() {
		return new ne("container.shulkerBox");
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.d(le);
	}

	@Override
	public le a(le le) {
		super.a(le);
		return this.e(le);
	}

	public void d(le le) {
		this.b = gi.a(this.ab_(), bki.b);
		if (!this.b(le) && le.c("Items", 9)) {
			ana.b(le, this.b);
		}
	}

	public le e(le le) {
		if (!this.c(le)) {
			ana.a(le, this.b, false);
		}

		return le;
	}

	@Override
	protected gi<bki> f() {
		return this.b;
	}

	@Override
	protected void a(gi<bki> gi) {
		this.b = gi;
	}

	@Override
	public int[] a(fz fz) {
		return a;
	}

	@Override
	public boolean a(int integer, bki bki, @Nullable fz fz) {
		return !(bvr.a(bki.b()) instanceof cav);
	}

	@Override
	public boolean b(int integer, bki bki, fz fz) {
		return true;
	}

	public float a(float float1) {
		return aec.g(float1, this.k, this.j);
	}

	@Override
	protected bgi a(int integer, beb beb) {
		return new bht(integer, beb, this);
	}

	public boolean l() {
		return this.i == ceg.a.CLOSED;
	}

	public static enum a {
		CLOSED,
		OPENING,
		OPENED,
		CLOSING;
	}
}

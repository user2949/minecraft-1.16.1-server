import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;

public class aut extends aug {
	protected final apg a;
	private final double b;
	private czf c;
	private fu d;
	private final boolean e;
	private final List<fu> f = Lists.<fu>newArrayList();
	private final int g;
	private final BooleanSupplier h;

	public aut(apg apg, double double2, boolean boolean3, int integer, BooleanSupplier booleanSupplier) {
		this.a = apg;
		this.b = double2;
		this.e = boolean3;
		this.g = integer;
		this.h = booleanSupplier;
		this.a(EnumSet.of(aug.a.MOVE));
		if (!this.h()) {
			throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
		}
	}

	@Override
	public boolean a() {
		if (!this.h()) {
			return false;
		} else {
			this.g();
			if (this.e && this.a.l.J()) {
				return false;
			} else {
				zd zd2 = (zd)this.a.l;
				fu fu3 = this.a.cA();
				if (!zd2.a(fu3, 6)) {
					return false;
				} else {
					dem dem4 = axu.a(this.a, 15, 7, fu3x -> {
						if (!zd2.b_(fu3x)) {
							return Double.NEGATIVE_INFINITY;
						} else {
							Optional<fu> optional5x = zd2.x().b(ayc.b, this::a, fu3x, 10, axz.b.IS_OCCUPIED);
							return !optional5x.isPresent() ? Double.NEGATIVE_INFINITY : -((fu)optional5x.get()).j(fu3);
						}
					});
					if (dem4 == null) {
						return false;
					} else {
						Optional<fu> optional5 = zd2.x().b(ayc.b, this::a, new fu(dem4), 10, axz.b.IS_OCCUPIED);
						if (!optional5.isPresent()) {
							return false;
						} else {
							this.d = ((fu)optional5.get()).h();
							awu awu6 = (awu)this.a.x();
							boolean boolean7 = awu6.f();
							awu6.a(this.h.getAsBoolean());
							this.c = awu6.a(this.d, 0);
							awu6.a(boolean7);
							if (this.c == null) {
								dem dem8 = axu.b(this.a, 10, 7, dem.c(this.d));
								if (dem8 == null) {
									return false;
								}

								awu6.a(this.h.getAsBoolean());
								this.c = this.a.x().a(dem8.b, dem8.c, dem8.d, 0);
								awu6.a(boolean7);
								if (this.c == null) {
									return false;
								}
							}

							for (int integer8 = 0; integer8 < this.c.e(); integer8++) {
								czd czd9 = this.c.a(integer8);
								fu fu10 = new fu(czd9.a, czd9.b + 1, czd9.c);
								if (bxe.a(this.a.l, fu10)) {
									this.c = this.a.x().a((double)czd9.a, (double)czd9.b, (double)czd9.c, 0);
									break;
								}
							}

							return this.c != null;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean b() {
		return this.a.x().m() ? false : !this.d.a(this.a.cz(), (double)(this.a.cx() + (float)this.g));
	}

	@Override
	public void c() {
		this.a.x().a(this.c, this.b);
	}

	@Override
	public void d() {
		if (this.a.x().m() || this.d.a(this.a.cz(), (double)this.g)) {
			this.f.add(this.d);
		}
	}

	private boolean a(fu fu) {
		for (fu fu4 : this.f) {
			if (Objects.equals(fu, fu4)) {
				return false;
			}
		}

		return true;
	}

	private void g() {
		if (this.f.size() > 15) {
			this.f.remove(0);
		}
	}

	private boolean h() {
		return this.a.x() instanceof awu;
	}
}

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class cae {
	private final bqb a;
	private final fu b;
	private final bvj c;
	private cfj d;
	private final boolean e;
	private final List<fu> f = Lists.<fu>newArrayList();

	public cae(bqb bqb, fu fu, cfj cfj) {
		this.a = bqb;
		this.b = fu;
		this.d = cfj;
		this.c = (bvj)cfj.b();
		cgm cgm5 = cfj.c(this.c.d());
		this.e = this.c.c();
		this.a(cgm5);
	}

	public List<fu> a() {
		return this.f;
	}

	private void a(cgm cgm) {
		this.f.clear();
		switch (cgm) {
			case NORTH_SOUTH:
				this.f.add(this.b.d());
				this.f.add(this.b.e());
				break;
			case EAST_WEST:
				this.f.add(this.b.f());
				this.f.add(this.b.g());
				break;
			case ASCENDING_EAST:
				this.f.add(this.b.f());
				this.f.add(this.b.g().b());
				break;
			case ASCENDING_WEST:
				this.f.add(this.b.f().b());
				this.f.add(this.b.g());
				break;
			case ASCENDING_NORTH:
				this.f.add(this.b.d().b());
				this.f.add(this.b.e());
				break;
			case ASCENDING_SOUTH:
				this.f.add(this.b.d());
				this.f.add(this.b.e().b());
				break;
			case SOUTH_EAST:
				this.f.add(this.b.g());
				this.f.add(this.b.e());
				break;
			case SOUTH_WEST:
				this.f.add(this.b.f());
				this.f.add(this.b.e());
				break;
			case NORTH_WEST:
				this.f.add(this.b.f());
				this.f.add(this.b.d());
				break;
			case NORTH_EAST:
				this.f.add(this.b.g());
				this.f.add(this.b.d());
		}
	}

	private void d() {
		for (int integer2 = 0; integer2 < this.f.size(); integer2++) {
			cae cae3 = this.b((fu)this.f.get(integer2));
			if (cae3 != null && cae3.a(this)) {
				this.f.set(integer2, cae3.b);
			} else {
				this.f.remove(integer2--);
			}
		}
	}

	private boolean a(fu fu) {
		return bvj.a(this.a, fu) || bvj.a(this.a, fu.b()) || bvj.a(this.a, fu.c());
	}

	@Nullable
	private cae b(fu fu) {
		cfj cfj4 = this.a.d_(fu);
		if (bvj.g(cfj4)) {
			return new cae(this.a, fu, cfj4);
		} else {
			fu fu3 = fu.b();
			cfj4 = this.a.d_(fu3);
			if (bvj.g(cfj4)) {
				return new cae(this.a, fu3, cfj4);
			} else {
				fu3 = fu.c();
				cfj4 = this.a.d_(fu3);
				return bvj.g(cfj4) ? new cae(this.a, fu3, cfj4) : null;
			}
		}
	}

	private boolean a(cae cae) {
		return this.c(cae.b);
	}

	private boolean c(fu fu) {
		for (int integer3 = 0; integer3 < this.f.size(); integer3++) {
			fu fu4 = (fu)this.f.get(integer3);
			if (fu4.u() == fu.u() && fu4.w() == fu.w()) {
				return true;
			}
		}

		return false;
	}

	protected int b() {
		int integer2 = 0;

		for (fz fz4 : fz.c.HORIZONTAL) {
			if (this.a(this.b.a(fz4))) {
				integer2++;
			}
		}

		return integer2;
	}

	private boolean b(cae cae) {
		return this.a(cae) || this.f.size() != 2;
	}

	private void c(cae cae) {
		this.f.add(cae.b);
		fu fu3 = this.b.d();
		fu fu4 = this.b.e();
		fu fu5 = this.b.f();
		fu fu6 = this.b.g();
		boolean boolean7 = this.c(fu3);
		boolean boolean8 = this.c(fu4);
		boolean boolean9 = this.c(fu5);
		boolean boolean10 = this.c(fu6);
		cgm cgm11 = null;
		if (boolean7 || boolean8) {
			cgm11 = cgm.NORTH_SOUTH;
		}

		if (boolean9 || boolean10) {
			cgm11 = cgm.EAST_WEST;
		}

		if (!this.e) {
			if (boolean8 && boolean10 && !boolean7 && !boolean9) {
				cgm11 = cgm.SOUTH_EAST;
			}

			if (boolean8 && boolean9 && !boolean7 && !boolean10) {
				cgm11 = cgm.SOUTH_WEST;
			}

			if (boolean7 && boolean9 && !boolean8 && !boolean10) {
				cgm11 = cgm.NORTH_WEST;
			}

			if (boolean7 && boolean10 && !boolean8 && !boolean9) {
				cgm11 = cgm.NORTH_EAST;
			}
		}

		if (cgm11 == cgm.NORTH_SOUTH) {
			if (bvj.a(this.a, fu3.b())) {
				cgm11 = cgm.ASCENDING_NORTH;
			}

			if (bvj.a(this.a, fu4.b())) {
				cgm11 = cgm.ASCENDING_SOUTH;
			}
		}

		if (cgm11 == cgm.EAST_WEST) {
			if (bvj.a(this.a, fu6.b())) {
				cgm11 = cgm.ASCENDING_EAST;
			}

			if (bvj.a(this.a, fu5.b())) {
				cgm11 = cgm.ASCENDING_WEST;
			}
		}

		if (cgm11 == null) {
			cgm11 = cgm.NORTH_SOUTH;
		}

		this.d = this.d.a(this.c.d(), cgm11);
		this.a.a(this.b, this.d, 3);
	}

	private boolean d(fu fu) {
		cae cae3 = this.b(fu);
		if (cae3 == null) {
			return false;
		} else {
			cae3.d();
			return cae3.b(this);
		}
	}

	public cae a(boolean boolean1, boolean boolean2, cgm cgm) {
		fu fu5 = this.b.d();
		fu fu6 = this.b.e();
		fu fu7 = this.b.f();
		fu fu8 = this.b.g();
		boolean boolean9 = this.d(fu5);
		boolean boolean10 = this.d(fu6);
		boolean boolean11 = this.d(fu7);
		boolean boolean12 = this.d(fu8);
		cgm cgm13 = null;
		boolean boolean14 = boolean9 || boolean10;
		boolean boolean15 = boolean11 || boolean12;
		if (boolean14 && !boolean15) {
			cgm13 = cgm.NORTH_SOUTH;
		}

		if (boolean15 && !boolean14) {
			cgm13 = cgm.EAST_WEST;
		}

		boolean boolean16 = boolean10 && boolean12;
		boolean boolean17 = boolean10 && boolean11;
		boolean boolean18 = boolean9 && boolean12;
		boolean boolean19 = boolean9 && boolean11;
		if (!this.e) {
			if (boolean16 && !boolean9 && !boolean11) {
				cgm13 = cgm.SOUTH_EAST;
			}

			if (boolean17 && !boolean9 && !boolean12) {
				cgm13 = cgm.SOUTH_WEST;
			}

			if (boolean19 && !boolean10 && !boolean12) {
				cgm13 = cgm.NORTH_WEST;
			}

			if (boolean18 && !boolean10 && !boolean11) {
				cgm13 = cgm.NORTH_EAST;
			}
		}

		if (cgm13 == null) {
			if (boolean14 && boolean15) {
				cgm13 = cgm;
			} else if (boolean14) {
				cgm13 = cgm.NORTH_SOUTH;
			} else if (boolean15) {
				cgm13 = cgm.EAST_WEST;
			}

			if (!this.e) {
				if (boolean1) {
					if (boolean16) {
						cgm13 = cgm.SOUTH_EAST;
					}

					if (boolean17) {
						cgm13 = cgm.SOUTH_WEST;
					}

					if (boolean18) {
						cgm13 = cgm.NORTH_EAST;
					}

					if (boolean19) {
						cgm13 = cgm.NORTH_WEST;
					}
				} else {
					if (boolean19) {
						cgm13 = cgm.NORTH_WEST;
					}

					if (boolean18) {
						cgm13 = cgm.NORTH_EAST;
					}

					if (boolean17) {
						cgm13 = cgm.SOUTH_WEST;
					}

					if (boolean16) {
						cgm13 = cgm.SOUTH_EAST;
					}
				}
			}
		}

		if (cgm13 == cgm.NORTH_SOUTH) {
			if (bvj.a(this.a, fu5.b())) {
				cgm13 = cgm.ASCENDING_NORTH;
			}

			if (bvj.a(this.a, fu6.b())) {
				cgm13 = cgm.ASCENDING_SOUTH;
			}
		}

		if (cgm13 == cgm.EAST_WEST) {
			if (bvj.a(this.a, fu8.b())) {
				cgm13 = cgm.ASCENDING_EAST;
			}

			if (bvj.a(this.a, fu7.b())) {
				cgm13 = cgm.ASCENDING_WEST;
			}
		}

		if (cgm13 == null) {
			cgm13 = cgm;
		}

		this.a(cgm13);
		this.d = this.d.a(this.c.d(), cgm13);
		if (boolean2 || this.a.d_(this.b) != this.d) {
			this.a.a(this.b, this.d, 3);

			for (int integer20 = 0; integer20 < this.f.size(); integer20++) {
				cae cae21 = this.b((fu)this.f.get(integer20));
				if (cae21 != null) {
					cae21.d();
					if (cae21.b(this)) {
						cae21.c(this);
					}
				}
			}
		}

		return this;
	}

	public cfj c() {
		return this.d;
	}
}

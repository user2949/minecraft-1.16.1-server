import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class cdi extends cdl implements ceo {
	private final List<cdi.a> a = Lists.<cdi.a>newArrayList();
	@Nullable
	private fu b = null;

	public cdi() {
		super(cdm.G);
	}

	@Override
	public void Z_() {
		if (this.d()) {
			this.a(null, this.d.d_(this.o()), cdi.b.EMERGENCY);
		}

		super.Z_();
	}

	public boolean d() {
		if (this.d == null) {
			return false;
		} else {
			for (fu fu3 : fu.a(this.e.b(-1, -1, -1), this.e.b(1, 1, 1))) {
				if (this.d.d_(fu3).b() instanceof bxv) {
					return true;
				}
			}

			return false;
		}
	}

	public boolean f() {
		return this.a.isEmpty();
	}

	public boolean h() {
		return this.a.size() == 3;
	}

	public void a(@Nullable bec bec, cfj cfj, cdi.b b) {
		List<aom> list5 = this.a(cfj, b);
		if (bec != null) {
			for (aom aom7 : list5) {
				if (aom7 instanceof ayl) {
					ayl ayl8 = (ayl)aom7;
					if (bec.cz().g(aom7.cz()) <= 16.0) {
						if (!this.k()) {
							ayl8.i(bec);
						} else {
							ayl8.t(400);
						}
					}
				}
			}
		}
	}

	private List<aom> a(cfj cfj, cdi.b b) {
		List<aom> list4 = Lists.<aom>newArrayList();
		this.a.removeIf(a -> this.a(cfj, a, list4, b));
		return list4;
	}

	public void a(aom aom, boolean boolean2) {
		this.a(aom, boolean2, 0);
	}

	public int j() {
		return this.a.size();
	}

	public static int a(cfj cfj) {
		return (Integer)cfj.c(bvn.b);
	}

	public boolean k() {
		return bwb.a(this.d, this.o());
	}

	protected void l() {
		qy.a(this);
	}

	public void a(aom aom, boolean boolean2, int integer) {
		if (this.a.size() < 3) {
			aom.l();
			aom.ba();
			le le5 = new le();
			aom.d(le5);
			this.a.add(new cdi.a(le5, integer, boolean2 ? 2400 : 600));
			if (this.d != null) {
				if (aom instanceof ayl) {
					ayl ayl6 = (ayl)aom;
					if (ayl6.eM() && (!this.x() || this.d.t.nextBoolean())) {
						this.b = ayl6.eL();
					}
				}

				fu fu6 = this.o();
				this.d.a(null, (double)fu6.u(), (double)fu6.v(), (double)fu6.w(), acl.aF, acm.BLOCKS, 1.0F, 1.0F);
			}

			aom.aa();
		}
	}

	private boolean a(cfj cfj, cdi.a a, @Nullable List<aom> list, cdi.b b) {
		if ((this.d.K() || this.d.U()) && b != cdi.b.EMERGENCY) {
			return false;
		} else {
			fu fu6 = this.o();
			le le7 = a.a;
			le7.r("Passengers");
			le7.r("Leash");
			le7.r("UUID");
			fz fz8 = cfj.c(bvn.a);
			fu fu9 = fu6.a(fz8);
			boolean boolean10 = !this.d.d_(fu9).k(this.d, fu9).b();
			if (boolean10 && b != cdi.b.EMERGENCY) {
				return false;
			} else {
				aom aom11 = aoq.a(le7, this.d, aom -> aom);
				if (aom11 != null) {
					if (!aom11.U().a(acy.c)) {
						return false;
					} else {
						if (aom11 instanceof ayl) {
							ayl ayl12 = (ayl)aom11;
							if (this.x() && !ayl12.eM() && this.d.t.nextFloat() < 0.9F) {
								ayl12.g(this.b);
							}

							if (b == cdi.b.HONEY_DELIVERED) {
								ayl12.fc();
								if (cfj.b().a(acx.ai)) {
									int integer13 = a(cfj);
									if (integer13 < 5) {
										int integer14 = this.d.t.nextInt(100) == 0 ? 2 : 1;
										if (integer13 + integer14 > 5) {
											integer14--;
										}

										this.d.a(this.o(), cfj.a(bvn.b, Integer.valueOf(integer13 + integer14)));
									}
								}
							}

							this.a(a.b, ayl12);
							if (list != null) {
								list.add(ayl12);
							}

							float float13 = aom11.cx();
							double double14 = boolean10 ? 0.0 : 0.55 + (double)(float13 / 2.0F);
							double double16 = (double)fu6.u() + 0.5 + double14 * (double)fz8.i();
							double double18 = (double)fu6.v() + 0.5 - (double)(aom11.cy() / 2.0F);
							double double20 = (double)fu6.w() + 0.5 + double14 * (double)fz8.k();
							aom11.b(double16, double18, double20, aom11.p, aom11.q);
						}

						this.d.a(null, fu6, acl.aG, acm.BLOCKS, 1.0F, 1.0F);
						return this.d.c(aom11);
					}
				} else {
					return false;
				}
			}
		}
	}

	private void a(int integer, ayl ayl) {
		int integer4 = ayl.i();
		if (integer4 < 0) {
			ayl.c_(Math.min(0, integer4 + integer));
		} else if (integer4 > 0) {
			ayl.c_(Math.max(0, integer4 - integer));
		}

		ayl.s(Math.max(0, ayl.eR() - integer));
		ayl.eP();
	}

	private boolean x() {
		return this.b != null;
	}

	private void y() {
		Iterator<cdi.a> iterator2 = this.a.iterator();
		cfj cfj3 = this.p();

		while (iterator2.hasNext()) {
			cdi.a a4 = (cdi.a)iterator2.next();
			if (a4.b > a4.c) {
				cdi.b b5 = a4.a.q("HasNectar") ? cdi.b.HONEY_DELIVERED : cdi.b.BEE_RELEASED;
				if (this.a(cfj3, a4, null, b5)) {
					iterator2.remove();
				}
			}

			a4.b++;
		}
	}

	@Override
	public void al_() {
		if (!this.d.v) {
			this.y();
			fu fu2 = this.o();
			if (this.a.size() > 0 && this.d.v_().nextDouble() < 0.005) {
				double double3 = (double)fu2.u() + 0.5;
				double double5 = (double)fu2.v();
				double double7 = (double)fu2.w() + 0.5;
				this.d.a(null, double3, double5, double7, acl.aI, acm.BLOCKS, 1.0F, 1.0F);
			}

			this.l();
		}
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a.clear();
		lk lk4 = le.d("Bees", 10);

		for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
			le le6 = lk4.a(integer5);
			cdi.a a7 = new cdi.a(le6.p("EntityData"), le6.h("TicksInHive"), le6.h("MinOccupationTicks"));
			this.a.add(a7);
		}

		this.b = null;
		if (le.e("FlowerPos")) {
			this.b = lq.b(le.p("FlowerPos"));
		}
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.a("Bees", this.m());
		if (this.x()) {
			le.a("FlowerPos", lq.a(this.b));
		}

		return le;
	}

	public lk m() {
		lk lk2 = new lk();

		for (cdi.a a4 : this.a) {
			a4.a.r("UUID");
			le le5 = new le();
			le5.a("EntityData", a4.a);
			le5.b("TicksInHive", a4.b);
			le5.b("MinOccupationTicks", a4.c);
			lk2.add(le5);
		}

		return lk2;
	}

	static class a {
		private final le a;
		private int b;
		private final int c;

		private a(le le, int integer2, int integer3) {
			le.r("UUID");
			this.a = le;
			this.b = integer2;
			this.c = integer3;
		}
	}

	public static enum b {
		HONEY_DELIVERED,
		BEE_RELEASED,
		EMERGENCY;
	}
}

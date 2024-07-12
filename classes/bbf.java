import com.google.common.collect.Lists;
import java.util.List;

public class bbf extends aom {
	private cfj f = bvs.C.n();
	public int b;
	public boolean c = true;
	private boolean g;
	private boolean an;
	private int ao = 40;
	private float ap = 2.0F;
	public le d;
	protected static final tq<fu> e = tt.a(bbf.class, ts.l);

	public bbf(aoq<? extends bbf> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bbf(bqb bqb, double double2, double double3, double double4, cfj cfj) {
		this(aoq.A, bqb);
		this.f = cfj;
		this.i = true;
		this.d(double2, double3 + (double)((1.0F - this.cy()) / 2.0F), double4);
		this.e(dem.a);
		this.m = double2;
		this.n = double3;
		this.o = double4;
		this.a(this.cA());
	}

	@Override
	public boolean bH() {
		return false;
	}

	public void a(fu fu) {
		this.S.b(e, fu);
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	protected void e() {
		this.S.a(e, fu.b);
	}

	@Override
	public boolean aQ() {
		return !this.y;
	}

	@Override
	public void j() {
		if (this.f.g()) {
			this.aa();
		} else {
			bvr bvr2 = this.f.b();
			if (this.b++ == 0) {
				fu fu3 = this.cA();
				if (this.l.d_(fu3).a(bvr2)) {
					this.l.a(fu3, false);
				} else if (!this.l.v) {
					this.aa();
					return;
				}
			}

			if (!this.aw()) {
				this.e(this.cB().b(0.0, -0.04, 0.0));
			}

			this.a(apd.SELF, this.cB());
			if (!this.l.v) {
				fu fu3 = this.cA();
				boolean boolean4 = this.f.b() instanceof bwo;
				boolean boolean5 = boolean4 && this.l.b(fu3).a(acz.a);
				double double6 = this.cB().g();
				if (boolean4 && double6 > 1.0) {
					deh deh8 = this.l.a(new bpj(new dem(this.m, this.n, this.o), this.cz(), bpj.a.COLLIDER, bpj.b.SOURCE_ONLY, this));
					if (deh8.c() != dej.a.MISS && this.l.b(deh8.a()).a(acz.a)) {
						fu3 = deh8.a();
						boolean5 = true;
					}
				}

				if (this.t || boolean5) {
					cfj cfj8 = this.l.d_(fu3);
					this.e(this.cB().d(0.7, -0.5, 0.7));
					if (!cfj8.a(bvs.bo)) {
						this.aa();
						if (!this.g) {
							boolean boolean9 = cfj8.a(new bjc(this.l, fu3, fz.DOWN, bki.b, fz.UP));
							boolean boolean10 = bxr.h(this.l.d_(fu3.c())) && (!boolean4 || !boolean5);
							boolean boolean11 = this.f.a((bqd)this.l, fu3) && !boolean10;
							if (boolean9 && boolean11) {
								if (this.f.b(cfz.C) && this.l.b(fu3).a() == cxb.c) {
									this.f = this.f.a(cfz.C, Boolean.valueOf(true));
								}

								if (this.l.a(fu3, this.f, 3)) {
									if (bvr2 instanceof bxr) {
										((bxr)bvr2).a(this.l, fu3, this.f, cfj8, this);
									}

									if (this.d != null && bvr2 instanceof bxp) {
										cdl cdl12 = this.l.c(fu3);
										if (cdl12 != null) {
											le le13 = cdl12.a(new le());

											for (String string15 : this.d.d()) {
												lu lu16 = this.d.c(string15);
												if (!"x".equals(string15) && !"y".equals(string15) && !"z".equals(string15)) {
													le13.a(string15, lu16.c());
												}
											}

											cdl12.a(this.f, le13);
											cdl12.Z_();
										}
									}
								} else if (this.c && this.l.S().b(bpx.g)) {
									this.a(bvr2);
								}
							} else if (this.c && this.l.S().b(bpx.g)) {
								this.a(bvr2);
							}
						} else if (bvr2 instanceof bxr) {
							((bxr)bvr2).a(this.l, fu3, this);
						}
					}
				} else if (!this.l.v && (this.b > 100 && (fu3.v() < 1 || fu3.v() > 256) || this.b > 600)) {
					if (this.c && this.l.S().b(bpx.g)) {
						this.a(bvr2);
					}

					this.aa();
				}
			}

			this.e(this.cB().a(0.98));
		}
	}

	@Override
	public boolean b(float float1, float float2) {
		if (this.an) {
			int integer4 = aec.f(float1 - 1.0F);
			if (integer4 > 0) {
				List<aom> list5 = Lists.<aom>newArrayList(this.l.a(this, this.cb()));
				boolean boolean6 = this.f.a(acx.F);
				anw anw7 = boolean6 ? anw.q : anw.r;

				for (aom aom9 : list5) {
					aom9.a(anw7, (float)Math.min(aec.d((float)integer4 * this.ap), this.ao));
				}

				if (boolean6 && (double)this.J.nextFloat() < 0.05F + (double)integer4 * 0.05) {
					cfj cfj8 = buv.c(this.f);
					if (cfj8 == null) {
						this.g = true;
					} else {
						this.f = cfj8;
					}
				}
			}
		}

		return false;
	}

	@Override
	protected void b(le le) {
		le.a("BlockState", lq.a(this.f));
		le.b("Time", this.b);
		le.a("DropItem", this.c);
		le.a("HurtEntities", this.an);
		le.a("FallHurtAmount", this.ap);
		le.b("FallHurtMax", this.ao);
		if (this.d != null) {
			le.a("TileEntityData", this.d);
		}
	}

	@Override
	protected void a(le le) {
		this.f = lq.c(le.p("BlockState"));
		this.b = le.h("Time");
		if (le.c("HurtEntities", 99)) {
			this.an = le.q("HurtEntities");
			this.ap = le.j("FallHurtAmount");
			this.ao = le.h("FallHurtMax");
		} else if (this.f.a(acx.F)) {
			this.an = true;
		}

		if (le.c("DropItem", 99)) {
			this.c = le.q("DropItem");
		}

		if (le.c("TileEntityData", 10)) {
			this.d = le.p("TileEntityData");
		}

		if (this.f.g()) {
			this.f = bvs.C.n();
		}
	}

	public void a(boolean boolean1) {
		this.an = boolean1;
	}

	@Override
	public void a(k k) {
		super.a(k);
		k.a("Immitating BlockState", this.f.toString());
	}

	public cfj i() {
		return this.f;
	}

	@Override
	public boolean ci() {
		return true;
	}

	@Override
	public ni<?> O() {
		return new nm(this, bvr.i(this.i()));
	}
}

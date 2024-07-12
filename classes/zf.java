import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zf {
	private static final Logger c = LogManager.getLogger();
	public zd a;
	public ze b;
	private bpy d = bpy.NOT_SET;
	private bpy e = bpy.NOT_SET;
	private boolean f;
	private int g;
	private fu h = fu.b;
	private int i;
	private boolean j;
	private fu k = fu.b;
	private int l;
	private int m = -1;

	public zf(zd zd) {
		this.a = zd;
	}

	public void a(bpy bpy) {
		this.a(bpy, bpy != this.d ? this.d : this.e);
	}

	public void a(bpy bpy1, bpy bpy2) {
		this.e = bpy2;
		this.d = bpy1;
		bpy1.a(this.b.bJ);
		this.b.t();
		this.b.c.ac().a(new pi(pi.a.UPDATE_GAME_MODE, this.b));
		this.a.n_();
	}

	public bpy b() {
		return this.d;
	}

	public bpy c() {
		return this.e;
	}

	public boolean d() {
		return this.d.f();
	}

	public boolean e() {
		return this.d.e();
	}

	public void b(bpy bpy) {
		if (this.d == bpy.NOT_SET) {
			this.d = bpy;
		}

		this.a(this.d);
	}

	public void a() {
		this.i++;
		if (this.j) {
			cfj cfj2 = this.a.d_(this.k);
			if (cfj2.g()) {
				this.j = false;
			} else {
				float float3 = this.a(cfj2, this.k, this.l);
				if (float3 >= 1.0F) {
					this.j = false;
					this.a(this.k);
				}
			}
		} else if (this.f) {
			cfj cfj2 = this.a.d_(this.h);
			if (cfj2.g()) {
				this.a.a(this.b.V(), this.h, -1);
				this.m = -1;
				this.f = false;
			} else {
				this.a(cfj2, this.h, this.g);
			}
		}
	}

	private float a(cfj cfj, fu fu, int integer) {
		int integer5 = this.i - integer;
		float float6 = cfj.a(this.b, this.b.l, fu) * (float)(integer5 + 1);
		int integer7 = (int)(float6 * 10.0F);
		if (integer7 != this.m) {
			this.a.a(this.b.V(), fu, integer7);
			this.m = integer7;
		}

		return float6;
	}

	public void a(fu fu, ry.a a, fz fz, int integer) {
		double double6 = this.b.cC() - ((double)fu.u() + 0.5);
		double double8 = this.b.cD() - ((double)fu.v() + 0.5) + 1.5;
		double double10 = this.b.cG() - ((double)fu.w() + 0.5);
		double double12 = double6 * double6 + double8 * double8 + double10 * double10;
		if (double12 > 36.0) {
			this.b.b.a(new nt(fu, this.a.d_(fu), a, false, "too far"));
		} else if (fu.v() >= integer) {
			this.b.b.a(new nt(fu, this.a.d_(fu), a, false, "too high"));
		} else {
			if (a == ry.a.START_DESTROY_BLOCK) {
				if (!this.a.a(this.b, fu)) {
					this.b.b.a(new nt(fu, this.a.d_(fu), a, false, "may not interact"));
					return;
				}

				if (this.e()) {
					this.a(fu, a, "creative destroy");
					return;
				}

				if (this.b.a(this.a, fu, this.d)) {
					this.b.b.a(new nt(fu, this.a.d_(fu), a, false, "block action restricted"));
					return;
				}

				this.g = this.i;
				float float14 = 1.0F;
				cfj cfj15 = this.a.d_(fu);
				if (!cfj15.g()) {
					cfj15.a(this.a, fu, this.b);
					float14 = cfj15.a(this.b, this.b.l, fu);
				}

				if (!cfj15.g() && float14 >= 1.0F) {
					this.a(fu, a, "insta mine");
				} else {
					if (this.f) {
						this.b
							.b
							.a(new nt(this.h, this.a.d_(this.h), ry.a.START_DESTROY_BLOCK, false, "abort destroying since another started (client insta mine, server disagreed)"));
					}

					this.f = true;
					this.h = fu.h();
					int integer16 = (int)(float14 * 10.0F);
					this.a.a(this.b.V(), fu, integer16);
					this.b.b.a(new nt(fu, this.a.d_(fu), a, true, "actual start of destroying"));
					this.m = integer16;
				}
			} else if (a == ry.a.STOP_DESTROY_BLOCK) {
				if (fu.equals(this.h)) {
					int integer14 = this.i - this.g;
					cfj cfj15x = this.a.d_(fu);
					if (!cfj15x.g()) {
						float float16 = cfj15x.a(this.b, this.b.l, fu) * (float)(integer14 + 1);
						if (float16 >= 0.7F) {
							this.f = false;
							this.a.a(this.b.V(), fu, -1);
							this.a(fu, a, "destroyed");
							return;
						}

						if (!this.j) {
							this.f = false;
							this.j = true;
							this.k = fu;
							this.l = this.g;
						}
					}
				}

				this.b.b.a(new nt(fu, this.a.d_(fu), a, true, "stopped destroying"));
			} else if (a == ry.a.ABORT_DESTROY_BLOCK) {
				this.f = false;
				if (!Objects.equals(this.h, fu)) {
					c.warn("Mismatch in destroy block pos: " + this.h + " " + fu);
					this.a.a(this.b.V(), this.h, -1);
					this.b.b.a(new nt(this.h, this.a.d_(this.h), a, true, "aborted mismatched destroying"));
				}

				this.a.a(this.b.V(), fu, -1);
				this.b.b.a(new nt(fu, this.a.d_(fu), a, true, "aborted destroying"));
			}
		}
	}

	public void a(fu fu, ry.a a, String string) {
		if (this.a(fu)) {
			this.b.b.a(new nt(fu, this.a.d_(fu), a, true, string));
		} else {
			this.b.b.a(new nt(fu, this.a.d_(fu), a, false, string));
		}
	}

	public boolean a(fu fu) {
		cfj cfj3 = this.a.d_(fu);
		if (!this.b.dC().b().a(cfj3, this.a, fu, this.b)) {
			return false;
		} else {
			cdl cdl4 = this.a.c(fu);
			bvr bvr5 = cfj3.b();
			if ((bvr5 instanceof bwl || bvr5 instanceof cbt || bvr5 instanceof byu) && !this.b.eV()) {
				this.a.a(fu, cfj3, cfj3, 3);
				return false;
			} else if (this.b.a(this.a, fu, this.d)) {
				return false;
			} else {
				bvr5.a(this.a, fu, cfj3, this.b);
				boolean boolean6 = this.a.a(fu, false);
				if (boolean6) {
					bvr5.a(this.a, fu, cfj3);
				}

				if (this.e()) {
					return true;
				} else {
					bki bki7 = this.b.dC();
					bki bki8 = bki7.i();
					boolean boolean9 = this.b.d(cfj3);
					bki7.a(this.a, cfj3, fu, this.b);
					if (boolean6 && boolean9) {
						bvr5.a(this.a, this.b, fu, cfj3, cdl4, bki8);
					}

					return true;
				}
			}
		}
	}

	public ang a(ze ze, bqb bqb, bki bki, anf anf) {
		if (this.d == bpy.SPECTATOR) {
			return ang.PASS;
		} else if (ze.eT().a(bki.b())) {
			return ang.PASS;
		} else {
			int integer6 = bki.E();
			int integer7 = bki.g();
			anh<bki> anh8 = bki.a(bqb, ze, anf);
			bki bki9 = anh8.b();
			if (bki9 == bki && bki9.E() == integer6 && bki9.k() <= 0 && bki9.g() == integer7) {
				return anh8.a();
			} else if (anh8.a() == ang.FAIL && bki9.k() > 0 && !ze.dV()) {
				return anh8.a();
			} else {
				ze.a(anf, bki9);
				if (this.e()) {
					bki9.e(integer6);
					if (bki9.e() && bki9.g() != integer7) {
						bki9.b(integer7);
					}
				}

				if (bki9.a()) {
					ze.a(anf, bki.b);
				}

				if (!ze.dV()) {
					ze.a(ze.bv);
				}

				return anh8.a();
			}
		}
	}

	public ang a(ze ze, bqb bqb, bki bki, anf anf, deh deh) {
		fu fu7 = deh.a();
		cfj cfj8 = bqb.d_(fu7);
		if (this.d == bpy.SPECTATOR) {
			anj anj9 = cfj8.b(bqb, fu7);
			if (anj9 != null) {
				ze.a(anj9);
				return ang.SUCCESS;
			} else {
				return ang.PASS;
			}
		} else {
			boolean boolean9 = !ze.dC().a() || !ze.dD().a();
			boolean boolean10 = ze.ep() && boolean9;
			bki bki11 = bki.i();
			if (!boolean10) {
				ang ang12 = cfj8.a(bqb, ze, anf, deh);
				if (ang12.a()) {
					aa.M.a(ze, fu7, bki11);
					return ang12;
				}
			}

			if (!bki.a() && !ze.eT().a(bki.b())) {
				blv blv12 = new blv(ze, anf, deh);
				ang ang13;
				if (this.e()) {
					int integer14 = bki.E();
					ang13 = bki.a(blv12);
					bki.e(integer14);
				} else {
					ang13 = bki.a(blv12);
				}

				if (ang13.a()) {
					aa.M.a(ze, fu7, bki11);
				}

				return ang13;
			} else {
				return ang.PASS;
			}
		}
	}

	public void a(zd zd) {
		this.a = zd;
	}
}

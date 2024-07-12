import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import it.unimi.dsi.fastutil.ints.Int2ShortMap;
import it.unimi.dsi.fastutil.ints.Int2ShortOpenHashMap;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zv implements qz {
	private static final Logger c = LogManager.getLogger();
	public final me a;
	private final MinecraftServer d;
	public ze b;
	private int e;
	private long f;
	private boolean g;
	private long h;
	private int i;
	private int j;
	private final Int2ShortMap k = new Int2ShortOpenHashMap();
	private double l;
	private double m;
	private double n;
	private double o;
	private double p;
	private double q;
	private aom r;
	private double s;
	private double t;
	private double u;
	private double v;
	private double w;
	private double x;
	private dem y;
	private int z;
	private int A;
	private boolean B;
	private int C;
	private boolean D;
	private int E;
	private int F;
	private int G;

	public zv(MinecraftServer minecraftServer, me me, ze ze) {
		this.d = minecraftServer;
		this.a = me;
		me.a(this);
		this.b = ze;
		ze.b = this;
	}

	public void b() {
		this.c();
		this.b.m = this.b.cC();
		this.b.n = this.b.cD();
		this.b.o = this.b.cG();
		this.b.w_();
		this.b.a(this.l, this.m, this.n, this.b.p, this.b.q);
		this.e++;
		this.G = this.F;
		if (this.B && !this.b.el()) {
			if (++this.C > 80) {
				c.warn("{} was kicked for floating too long!", this.b.P().getString());
				this.b(new ne("multiplayer.disconnect.flying"));
				return;
			}
		} else {
			this.B = false;
			this.C = 0;
		}

		this.r = this.b.cq();
		if (this.r != this.b && this.r.cl() == this.b) {
			this.s = this.r.cC();
			this.t = this.r.cD();
			this.u = this.r.cG();
			this.v = this.r.cC();
			this.w = this.r.cD();
			this.x = this.r.cG();
			if (this.D && this.b.cq().cl() == this.b) {
				if (++this.E > 80) {
					c.warn("{} was kicked for floating a vehicle too long!", this.b.P().getString());
					this.b(new ne("multiplayer.disconnect.flying"));
					return;
				}
			} else {
				this.D = false;
				this.E = 0;
			}
		} else {
			this.r = null;
			this.D = false;
			this.E = 0;
		}

		this.d.aO().a("keepAlive");
		long long2 = v.b();
		if (long2 - this.f >= 15000L) {
			if (this.g) {
				this.b(new ne("disconnect.timeout"));
			} else {
				this.g = true;
				this.f = long2;
				this.h = long2;
				this.a(new os(this.h));
			}
		}

		this.d.aO().c();
		if (this.i > 0) {
			this.i--;
		}

		if (this.j > 0) {
			this.j--;
		}

		if (this.b.F() > 0L && this.d.am() > 0 && v.b() - this.b.F() > (long)(this.d.am() * 1000 * 60)) {
			this.b(new ne("multiplayer.disconnect.idling"));
		}
	}

	public void c() {
		this.l = this.b.cC();
		this.m = this.b.cD();
		this.n = this.b.cG();
		this.o = this.b.cC();
		this.p = this.b.cD();
		this.q = this.b.cG();
	}

	@Override
	public me a() {
		return this.a;
	}

	private boolean d() {
		return this.d.a(this.b.ez());
	}

	public void b(mr mr) {
		this.a.a(new om(mr), future -> this.a.a(mr));
		this.a.k();
		this.d.g(this.a::l);
	}

	@Override
	public void a(sa sa) {
		nk.a(sa, this, this.b.u());
		this.b.a(sa.b(), sa.c(), sa.d(), sa.e());
	}

	private static boolean b(rs rs) {
		return Doubles.isFinite(rs.a(0.0))
				&& Doubles.isFinite(rs.b(0.0))
				&& Doubles.isFinite(rs.c(0.0))
				&& Floats.isFinite(rs.b(0.0F))
				&& Floats.isFinite(rs.a(0.0F))
			? Math.abs(rs.a(0.0)) > 3.0E7 || Math.abs(rs.b(0.0)) > 3.0E7 || Math.abs(rs.c(0.0)) > 3.0E7
			: true;
	}

	private static boolean b(rt rt) {
		return !Doubles.isFinite(rt.b()) || !Doubles.isFinite(rt.c()) || !Doubles.isFinite(rt.d()) || !Floats.isFinite(rt.f()) || !Floats.isFinite(rt.e());
	}

	@Override
	public void a(rt rt) {
		nk.a(rt, this, this.b.u());
		if (b(rt)) {
			this.b(new ne("multiplayer.disconnect.invalid_vehicle_movement"));
		} else {
			aom aom3 = this.b.cq();
			if (aom3 != this.b && aom3.cl() == this.b && aom3 == this.r) {
				zd zd4 = this.b.u();
				double double5 = aom3.cC();
				double double7 = aom3.cD();
				double double9 = aom3.cG();
				double double11 = rt.b();
				double double13 = rt.c();
				double double15 = rt.d();
				float float17 = rt.e();
				float float18 = rt.f();
				double double19 = double11 - this.s;
				double double21 = double13 - this.t;
				double double23 = double15 - this.u;
				double double25 = aom3.cB().g();
				double double27 = double19 * double19 + double21 * double21 + double23 * double23;
				if (double27 - double25 > 100.0 && !this.d()) {
					c.warn("{} (vehicle of {}) moved too quickly! {},{},{}", aom3.P().getString(), this.b.P().getString(), double19, double21, double23);
					this.a.a(new pb(aom3));
					return;
				}

				boolean boolean29 = zd4.a_(aom3, aom3.cb().h(0.0625));
				double19 = double11 - this.v;
				double21 = double13 - this.w - 1.0E-6;
				double23 = double15 - this.x;
				aom3.a(apd.PLAYER, new dem(double19, double21, double23));
				double19 = double11 - aom3.cC();
				double21 = double13 - aom3.cD();
				if (double21 > -0.5 || double21 < 0.5) {
					double21 = 0.0;
				}

				double23 = double15 - aom3.cG();
				double27 = double19 * double19 + double21 * double21 + double23 * double23;
				boolean boolean32 = false;
				if (double27 > 0.0625) {
					boolean32 = true;
					c.warn("{} (vehicle of {}) moved wrongly! {}", aom3.P().getString(), this.b.P().getString(), Math.sqrt(double27));
				}

				aom3.a(double11, double13, double15, float17, float18);
				boolean boolean33 = zd4.a_(aom3, aom3.cb().h(0.0625));
				if (boolean29 && (boolean32 || !boolean33)) {
					aom3.a(double5, double7, double9, float17, float18);
					this.a.a(new pb(aom3));
					return;
				}

				this.b.u().i().a(this.b);
				this.b.o(this.b.cC() - double5, this.b.cD() - double7, this.b.cG() - double9);
				this.D = double21 >= -0.03125 && !this.d.Y() && this.a(aom3);
				this.v = aom3.cC();
				this.w = aom3.cD();
				this.x = aom3.cG();
			}
		}
	}

	private boolean a(aom aom) {
		return aom.l.a(aom.cb().g(0.0625).b(0.0, -0.55, 0.0)).allMatch(cfi.a::g);
	}

	@Override
	public void a(ra ra) {
		nk.a(ra, this, this.b.u());
		if (ra.b() == this.z) {
			this.b.a(this.y.b, this.y.c, this.y.d, this.b.p, this.b.q);
			this.o = this.y.b;
			this.p = this.y.c;
			this.q = this.y.d;
			if (this.b.H()) {
				this.b.I();
			}

			this.y = null;
		}
	}

	@Override
	public void a(sb sb) {
		nk.a(sb, this, this.b.u());
		if (sb.b() == sb.a.SHOWN) {
			this.d.aD().a(sb.c()).ifPresent(this.b.B()::e);
		} else if (sb.b() == sb.a.SETTINGS) {
			this.b.B().a(sb.d());
			this.b.B().b(sb.e());
			this.b.B().c(sb.f());
			this.b.B().d(sb.g());
			this.b.B().e(sb.h());
			this.b.B().f(sb.i());
			this.b.B().g(sb.j());
			this.b.B().h(sb.k());
		}
	}

	@Override
	public void a(se se) {
		nk.a(se, this, this.b.u());
		if (se.c() == se.a.OPENED_TAB) {
			uh uh3 = se.d();
			w w4 = this.d.ay().a(uh3);
			if (w4 != null) {
				this.b.J().a(w4);
			}
		}
	}

	@Override
	public void a(rg rg) {
		nk.a(rg, this, this.b.u());
		StringReader stringReader3 = new StringReader(rg.c());
		if (stringReader3.canRead() && stringReader3.peek() == '/') {
			stringReader3.skip();
		}

		ParseResults<cz> parseResults4 = this.d.aB().a().parse(stringReader3, this.b.cv());
		this.d.aB().a().getCompletionSuggestions(parseResults4).thenAccept(suggestions -> this.a.a(new oc(rg.b(), suggestions)));
	}

	@Override
	public void a(si si) {
		nk.a(si, this, this.b.u());
		if (!this.d.l()) {
			this.b.a(new ne("advMode.notEnabled"), v.b);
		} else if (!this.b.eV()) {
			this.b.a(new ne("advMode.notAllowed"), v.b);
		} else {
			bpc bpc3 = null;
			cdq cdq4 = null;
			fu fu5 = si.b();
			cdl cdl6 = this.b.l.c(fu5);
			if (cdl6 instanceof cdq) {
				cdq4 = (cdq)cdl6;
				bpc3 = cdq4.d();
			}

			String string7 = si.c();
			boolean boolean8 = si.d();
			if (bpc3 != null) {
				cdq.a a9 = cdq4.m();
				fz fz10 = this.b.l.d_(fu5).c(bwl.a);
				switch (si.g()) {
					case SEQUENCE: {
						cfj cfj11 = bvs.iH.n();
						this.b.l.a(fu5, cfj11.a(bwl.a, fz10).a(bwl.b, Boolean.valueOf(si.e())), 2);
						break;
					}
					case AUTO: {
						cfj cfj11 = bvs.iG.n();
						this.b.l.a(fu5, cfj11.a(bwl.a, fz10).a(bwl.b, Boolean.valueOf(si.e())), 2);
						break;
					}
					case REDSTONE:
					default: {
						cfj cfj11 = bvs.er.n();
						this.b.l.a(fu5, cfj11.a(bwl.a, fz10).a(bwl.b, Boolean.valueOf(si.e())), 2);
					}
				}

				cdl6.r();
				this.b.l.a(fu5, cdl6);
				bpc3.a(string7);
				bpc3.a(boolean8);
				if (!boolean8) {
					bpc3.b(null);
				}

				cdq4.b(si.f());
				if (a9 != si.g()) {
					cdq4.h();
				}

				bpc3.e();
				if (!aei.b(string7)) {
					this.b.a(new ne("advMode.setCommand.success", string7), v.b);
				}
			}
		}
	}

	@Override
	public void a(sj sj) {
		nk.a(sj, this, this.b.u());
		if (!this.d.l()) {
			this.b.a(new ne("advMode.notEnabled"), v.b);
		} else if (!this.b.eV()) {
			this.b.a(new ne("advMode.notAllowed"), v.b);
		} else {
			bpc bpc3 = sj.a(this.b.l);
			if (bpc3 != null) {
				bpc3.a(sj.b());
				bpc3.a(sj.c());
				if (!sj.c()) {
					bpc3.b(null);
				}

				bpc3.e();
				this.b.a(new ne("advMode.setCommand.success", sj.b()), v.b);
			}
		}
	}

	@Override
	public void a(rv rv) {
		nk.a(rv, this, this.b.u());
		this.b.bt.c(rv.b());
		this.b.b.a(new oi(-2, this.b.bt.d, this.b.bt.a(this.b.bt.d)));
		this.b.b.a(new oi(-2, rv.b(), this.b.bt.a(rv.b())));
		this.b.b.a(new pu(this.b.bt.d));
	}

	@Override
	public void a(sc sc) {
		nk.a(sc, this, this.b.u());
		if (this.b.bw instanceof bgk) {
			bgk bgk3 = (bgk)this.b.bw;
			String string4 = u.a(sc.b());
			if (string4.length() <= 35) {
				bgk3.a(string4);
			}
		}
	}

	@Override
	public void a(sg sg) {
		nk.a(sg, this, this.b.u());
		if (this.b.bw instanceof bgl) {
			((bgl)this.b.bw).c(sg.b(), sg.c());
		}
	}

	@Override
	public void a(sm sm) {
		nk.a(sm, this, this.b.u());
		if (this.b.eV()) {
			fu fu3 = sm.b();
			cfj cfj4 = this.b.l.d_(fu3);
			cdl cdl5 = this.b.l.c(fu3);
			if (cdl5 instanceof cel) {
				cel cel6 = (cel)cdl5;
				cel6.a(sm.d());
				cel6.a(sm.e());
				cel6.b(sm.f());
				cel6.c(sm.g());
				cel6.b(sm.h());
				cel6.b(sm.i());
				cel6.b(sm.j());
				cel6.a(sm.k());
				cel6.e(sm.l());
				cel6.f(sm.m());
				cel6.a(sm.n());
				cel6.a(sm.o());
				if (cel6.g()) {
					String string7 = cel6.d();
					if (sm.c() == cel.a.SAVE_AREA) {
						if (cel6.D()) {
							this.b.a(new ne("structure_block.save_success", string7), false);
						} else {
							this.b.a(new ne("structure_block.save_failure", string7), false);
						}
					} else if (sm.c() == cel.a.LOAD_AREA) {
						if (!cel6.G()) {
							this.b.a(new ne("structure_block.load_not_found", string7), false);
						} else if (cel6.E()) {
							this.b.a(new ne("structure_block.load_success", string7), false);
						} else {
							this.b.a(new ne("structure_block.load_prepare", string7), false);
						}
					} else if (sm.c() == cel.a.SCAN_AREA) {
						if (cel6.C()) {
							this.b.a(new ne("structure_block.size_success", string7), false);
						} else {
							this.b.a(new ne("structure_block.size_failure"), false);
						}
					}
				} else {
					this.b.a(new ne("structure_block.invalid_structure_name", sm.e()), false);
				}

				cel6.Z_();
				this.b.l.a(fu3, cfj4, cfj4, 3);
			}
		}
	}

	@Override
	public void a(sl sl) {
		nk.a(sl, this, this.b.u());
		if (this.b.eV()) {
			fu fu3 = sl.b();
			cfj cfj4 = this.b.l.d_(fu3);
			cdl cdl5 = this.b.l.c(fu3);
			if (cdl5 instanceof ceb) {
				ceb ceb6 = (ceb)cdl5;
				ceb6.a(sl.c());
				ceb6.b(sl.d());
				ceb6.c(sl.e());
				ceb6.a(sl.f());
				ceb6.a(sl.g());
				ceb6.Z_();
				this.b.l.a(fu3, cfj4, cfj4, 3);
			}
		}
	}

	@Override
	public void a(rp rp) {
		nk.a(rp, this, this.b.u());
		if (this.b.eV()) {
			fu fu3 = rp.b();
			cdl cdl4 = this.b.l.c(fu3);
			if (cdl4 instanceof ceb) {
				ceb ceb5 = (ceb)cdl4;
				ceb5.a(this.b.u(), rp.c(), rp.d());
			}
		}
	}

	@Override
	public void a(sf sf) {
		nk.a(sf, this, this.b.u());
		int integer3 = sf.b();
		bgi bgi4 = this.b.bw;
		if (bgi4 instanceof bhm) {
			bhm bhm5 = (bhm)bgi4;
			bhm5.d(integer3);
			bhm5.g(integer3);
		}
	}

	@Override
	public void a(rm rm) {
		nk.a(rm, this, this.b.u());
		bki bki3 = rm.b();
		if (!bki3.a()) {
			if (blz.a(bki3.o())) {
				bki bki4 = this.b.b(rm.d());
				if (bki3.b() == bkk.oS && bki4.b() == bkk.oS) {
					if (rm.c()) {
						bki bki5 = new bki(bkk.oT);
						le le6 = bki4.o();
						if (le6 != null) {
							bki5.c(le6.g());
						}

						bki5.a("author", lt.a(this.b.P().getString()));
						bki5.a("title", lt.a(bki3.o().l("title")));
						lk lk7 = bki3.o().d("pages", 8);

						for (int integer8 = 0; integer8 < lk7.size(); integer8++) {
							String string9 = lk7.j(integer8);
							mr mr10 = new nd(string9);
							string9 = mr.a.a(mr10);
							lk7.d(integer8, lt.a(string9));
						}

						bki5.a("pages", lk7);
						this.b.a(rm.d(), bki5);
					} else {
						bki4.a("pages", bki3.o().d("pages", 8));
					}
				}
			}
		}
	}

	@Override
	public void a(rn rn) {
		nk.a(rn, this, this.b.u());
		if (this.b.k(2)) {
			aom aom3 = this.b.u().a(rn.c());
			if (aom3 != null) {
				le le4 = aom3.e(new le());
				this.b.b.a(new qp(rn.b(), le4));
			}
		}
	}

	@Override
	public void a(rb rb) {
		nk.a(rb, this, this.b.u());
		if (this.b.k(2)) {
			cdl cdl3 = this.b.u().c(rb.c());
			le le4 = cdl3 != null ? cdl3.a(new le()) : null;
			this.b.b.a(new qp(rb.b(), le4));
		}
	}

	@Override
	public void a(rs rs) {
		nk.a(rs, this, this.b.u());
		if (b(rs)) {
			this.b(new ne("multiplayer.disconnect.invalid_player_movement"));
		} else {
			zd zd3 = this.b.u();
			if (!this.b.g) {
				if (this.e == 0) {
					this.c();
				}

				if (this.y != null) {
					if (this.e - this.A > 20) {
						this.A = this.e;
						this.a(this.y.b, this.y.c, this.y.d, this.b.p, this.b.q);
					}
				} else {
					this.A = this.e;
					if (this.b.bn()) {
						this.b.a(this.b.cC(), this.b.cD(), this.b.cG(), rs.a(this.b.p), rs.b(this.b.q));
						this.b.u().i().a(this.b);
					} else {
						double double4 = this.b.cC();
						double double6 = this.b.cD();
						double double8 = this.b.cG();
						double double10 = this.b.cD();
						double double12 = rs.a(this.b.cC());
						double double14 = rs.b(this.b.cD());
						double double16 = rs.c(this.b.cG());
						float float18 = rs.a(this.b.p);
						float float19 = rs.b(this.b.q);
						double double20 = double12 - this.l;
						double double22 = double14 - this.m;
						double double24 = double16 - this.n;
						double double26 = this.b.cB().g();
						double double28 = double20 * double20 + double22 * double22 + double24 * double24;
						if (this.b.el()) {
							if (double28 > 1.0) {
								this.a(this.b.cC(), this.b.cD(), this.b.cG(), rs.a(this.b.p), rs.b(this.b.q));
							}
						} else {
							this.F++;
							int integer30 = this.F - this.G;
							if (integer30 > 5) {
								c.debug("{} is sending move packets too frequently ({} packets since last tick)", this.b.P().getString(), integer30);
								integer30 = 1;
							}

							if (!this.b.H() && (!this.b.u().S().b(bpx.r) || !this.b.ee())) {
								float float31 = this.b.ee() ? 300.0F : 100.0F;
								if (double28 - double26 > (double)(float31 * (float)integer30) && !this.d()) {
									c.warn("{} moved too quickly! {},{},{}", this.b.P().getString(), double20, double22, double24);
									this.a(this.b.cC(), this.b.cD(), this.b.cG(), this.b.p, this.b.q);
									return;
								}
							}

							deg deg31 = this.b.cb();
							double20 = double12 - this.o;
							double22 = double14 - this.p;
							double24 = double16 - this.q;
							boolean boolean32 = double22 > 0.0;
							if (this.b.aj() && !rs.b() && boolean32) {
								this.b.dJ();
							}

							this.b.a(apd.PLAYER, new dem(double20, double22, double24));
							double20 = double12 - this.b.cC();
							double22 = double14 - this.b.cD();
							if (double22 > -0.5 || double22 < 0.5) {
								double22 = 0.0;
							}

							double24 = double16 - this.b.cG();
							double28 = double20 * double20 + double22 * double22 + double24 * double24;
							boolean boolean35 = false;
							if (!this.b.H() && double28 > 0.0625 && !this.b.el() && !this.b.d.e() && this.b.d.b() != bpy.SPECTATOR) {
								boolean35 = true;
								c.warn("{} moved wrongly!", this.b.P().getString());
							}

							this.b.a(double12, double14, double16, float18, float19);
							if (this.b.H || this.b.el() || (!boolean35 || !zd3.a_(this.b, deg31)) && !this.a(zd3, deg31)) {
								this.B = double22 >= -0.03125 && this.b.d.b() != bpy.SPECTATOR && !this.d.Y() && !this.b.bJ.c && !this.b.a(aoi.y) && !this.b.ee() && this.a(this.b);
								this.b.u().i().a(this.b);
								this.b.a(this.b.cD() - double10, rs.b());
								this.b.c(rs.b());
								if (boolean32) {
									this.b.C = 0.0F;
								}

								this.b.o(this.b.cC() - double4, this.b.cD() - double6, this.b.cG() - double8);
								this.o = this.b.cC();
								this.p = this.b.cD();
								this.q = this.b.cG();
							} else {
								this.a(double4, double6, double8, float18, float19);
							}
						}
					}
				}
			}
		}
	}

	private boolean a(bqd bqd, deg deg) {
		Stream<dfg> stream4 = bqd.d(this.b, this.b.cb().h(1.0E-5F), aom -> true);
		dfg dfg5 = dfd.a(deg.h(1.0E-5F));
		return stream4.anyMatch(dfg2 -> !dfd.c(dfg2, dfg5, deq.i));
	}

	public void a(double double1, double double2, double double3, float float4, float float5) {
		this.a(double1, double2, double3, float4, float5, Collections.emptySet());
	}

	public void a(double double1, double double2, double double3, float float4, float float5, Set<pk.a> set) {
		double double11 = set.contains(pk.a.X) ? this.b.cC() : 0.0;
		double double13 = set.contains(pk.a.Y) ? this.b.cD() : 0.0;
		double double15 = set.contains(pk.a.Z) ? this.b.cG() : 0.0;
		float float17 = set.contains(pk.a.Y_ROT) ? this.b.p : 0.0F;
		float float18 = set.contains(pk.a.X_ROT) ? this.b.q : 0.0F;
		this.y = new dem(double1, double2, double3);
		if (++this.z == Integer.MAX_VALUE) {
			this.z = 0;
		}

		this.A = this.e;
		this.b.a(double1, double2, double3, float4, float5);
		this.b.b.a(new pk(double1 - double11, double2 - double13, double3 - double15, float4 - float17, float5 - float18, set, this.z));
	}

	@Override
	public void a(ry ry) {
		nk.a(ry, this, this.b.u());
		fu fu3 = ry.b();
		this.b.z();
		ry.a a4 = ry.d();
		switch (a4) {
			case SWAP_ITEM_WITH_OFFHAND:
				if (!this.b.a_()) {
					bki bki5 = this.b.b(anf.OFF_HAND);
					this.b.a(anf.OFF_HAND, this.b.b(anf.MAIN_HAND));
					this.b.a(anf.MAIN_HAND, bki5);
					this.b.eb();
				}

				return;
			case DROP_ITEM:
				if (!this.b.a_()) {
					this.b.a(false);
				}

				return;
			case DROP_ALL_ITEMS:
				if (!this.b.a_()) {
					this.b.a(true);
				}

				return;
			case RELEASE_USE_ITEM:
				this.b.ea();
				return;
			case START_DESTROY_BLOCK:
			case ABORT_DESTROY_BLOCK:
			case STOP_DESTROY_BLOCK:
				this.b.d.a(fu3, a4, ry.c(), this.d.aa());
				return;
			default:
				throw new IllegalArgumentException("Invalid player action");
		}
	}

	private static boolean a(ze ze, bki bki) {
		if (bki.a()) {
			return false;
		} else {
			bke bke3 = bki.b();
			return (bke3 instanceof bim || bke3 instanceof biu) && !ze.eT().a(bke3);
		}
	}

	@Override
	public void a(sq sq) {
		nk.a(sq, this, this.b.u());
		zd zd3 = this.b.u();
		anf anf4 = sq.b();
		bki bki5 = this.b.b(anf4);
		deh deh6 = sq.c();
		fu fu7 = deh6.a();
		fz fz8 = deh6.b();
		this.b.z();
		if (fu7.v() < this.d.aa()) {
			if (this.y == null && this.b.g((double)fu7.u() + 0.5, (double)fu7.v() + 0.5, (double)fu7.w() + 0.5) < 64.0 && zd3.a(this.b, fu7)) {
				ang ang9 = this.b.d.a(this.b, zd3, bki5, anf4, deh6);
				if (fz8 == fz.UP && !ang9.a() && fu7.v() >= this.d.aa() - 1 && a(this.b, bki5)) {
					mr mr10 = new ne("build.tooHigh", this.d.aa()).a(i.RED);
					this.b.b.a(new oa(mr10, mo.GAME_INFO, v.b));
				} else if (ang9.b()) {
					this.b.a(anf4, true);
				}
			}
		} else {
			mr mr9 = new ne("build.tooHigh", this.d.aa()).a(i.RED);
			this.b.b.a(new oa(mr9, mo.GAME_INFO, v.b));
		}

		this.b.b.a(new nx(zd3, fu7));
		this.b.b.a(new nx(zd3, fu7.a(fz8)));
	}

	@Override
	public void a(sr sr) {
		nk.a(sr, this, this.b.u());
		zd zd3 = this.b.u();
		anf anf4 = sr.b();
		bki bki5 = this.b.b(anf4);
		this.b.z();
		if (!bki5.a()) {
			ang ang6 = this.b.d.a(this.b, zd3, bki5, anf4);
			if (ang6.b()) {
				this.b.a(anf4, true);
			}
		}
	}

	@Override
	public void a(sp sp) {
		nk.a(sp, this, this.b.u());
		if (this.b.a_()) {
			for (zd zd4 : this.d.F()) {
				aom aom5 = sp.a(zd4);
				if (aom5 != null) {
					this.b.a(zd4, aom5.cC(), aom5.cD(), aom5.cG(), aom5.p, aom5.q);
					return;
				}
			}
		}
	}

	@Override
	public void a(sd sd) {
	}

	@Override
	public void a(ru ru) {
		nk.a(ru, this, this.b.u());
		aom aom3 = this.b.cs();
		if (aom3 instanceof bft) {
			((bft)aom3).a(ru.b(), ru.c());
		}
	}

	@Override
	public void a(mr mr) {
		c.info("{} lost connection: {}", this.b.P().getString(), mr.getString());
		this.d.ar();
		this.d.ac().a(new ne("multiplayer.player.left", this.b.d()).a(i.YELLOW), mo.SYSTEM, v.b);
		this.b.p();
		this.d.ac().c(this.b);
		if (this.d()) {
			c.info("Stopping singleplayer server as player logged out");
			this.d.a(false);
		}
	}

	public void a(ni<?> ni) {
		this.a(ni, null);
	}

	public void a(ni<?> ni, @Nullable GenericFutureListener<? extends Future<? super Void>> genericFutureListener) {
		if (ni instanceof oa) {
			oa oa4 = (oa)ni;
			bea bea5 = this.b.x();
			if (bea5 == bea.HIDDEN && oa4.d() != mo.GAME_INFO) {
				return;
			}

			if (bea5 == bea.SYSTEM && !oa4.c()) {
				return;
			}
		}

		try {
			this.a.a(ni, genericFutureListener);
		} catch (Throwable var6) {
			j j5 = j.a(var6, "Sending packet");
			k k6 = j5.a("Packet being sent");
			k6.a("Packet class", (l<String>)(() -> ni.getClass().getCanonicalName()));
			throw new s(j5);
		}
	}

	@Override
	public void a(sh sh) {
		nk.a(sh, this, this.b.u());
		if (sh.b() >= 0 && sh.b() < beb.g()) {
			if (this.b.bt.d != sh.b() && this.b.dW() == anf.MAIN_HAND) {
				this.b.eb();
			}

			this.b.bt.d = sh.b();
			this.b.z();
		} else {
			c.warn("{} tried to set an invalid carried item", this.b.P().getString());
		}
	}

	@Override
	public void a(rd rd) {
		nk.a(rd, this, this.b.u());
		if (this.b.x() == bea.HIDDEN) {
			this.a(new oa(new ne("chat.cannotSend").a(i.RED), mo.SYSTEM, v.b));
		} else {
			this.b.z();
			String string3 = StringUtils.normalizeSpace(rd.b());

			for (int integer4 = 0; integer4 < string3.length(); integer4++) {
				if (!u.a(string3.charAt(integer4))) {
					this.b(new ne("multiplayer.disconnect.illegal_characters"));
					return;
				}
			}

			if (string3.startsWith("/")) {
				this.c(string3);
			} else {
				mr mr4 = new ne("chat.type.text", this.b.d(), string3);
				this.d.ac().a(mr4, mo.CHAT, this.b.bR());
			}

			this.i += 20;
			if (this.i > 200 && !this.d.ac().h(this.b.ez())) {
				this.b(new ne("disconnect.spam"));
			}
		}
	}

	private void c(String string) {
		this.d.aB().a(this.b.cv(), string);
	}

	@Override
	public void a(so so) {
		nk.a(so, this, this.b.u());
		this.b.z();
		this.b.a(so.b());
	}

	@Override
	public void a(rz rz) {
		nk.a(rz, this, this.b.u());
		this.b.z();
		switch (rz.c()) {
			case PRESS_SHIFT_KEY:
				this.b.f(true);
				break;
			case RELEASE_SHIFT_KEY:
				this.b.f(false);
				break;
			case START_SPRINTING:
				this.b.g(true);
				break;
			case STOP_SPRINTING:
				this.b.g(false);
				break;
			case STOP_SLEEPING:
				if (this.b.el()) {
					this.b.a(false, true);
					this.y = this.b.cz();
				}
				break;
			case START_RIDING_JUMP:
				if (this.b.cs() instanceof api) {
					api api3 = (api)this.b.cs();
					int integer4 = rz.d();
					if (api3.Q_() && integer4 > 0) {
						api3.b(integer4);
					}
				}
				break;
			case STOP_RIDING_JUMP:
				if (this.b.cs() instanceof api) {
					api api3 = (api)this.b.cs();
					api3.c();
				}
				break;
			case OPEN_INVENTORY:
				if (this.b.cs() instanceof azm) {
					((azm)this.b.cs()).f(this.b);
				}
				break;
			case START_FALL_FLYING:
				if (!this.b.eC()) {
					this.b.eE();
				}
				break;
			default:
				throw new IllegalArgumentException("Invalid client command!");
		}
	}

	@Override
	public void a(ro ro) {
		nk.a(ro, this, this.b.u());
		zd zd3 = this.b.u();
		aom aom4 = ro.a(zd3);
		this.b.z();
		this.b.f(ro.e());
		if (aom4 != null) {
			double double5 = 36.0;
			if (this.b.h(aom4) < 36.0) {
				anf anf7 = ro.c();
				Optional<ang> optional8 = Optional.empty();
				if (ro.b() == ro.a.INTERACT) {
					optional8 = Optional.of(this.b.a(aom4, anf7));
				} else if (ro.b() == ro.a.INTERACT_AT) {
					optional8 = Optional.of(aom4.a(this.b, ro.d(), anf7));
				} else if (ro.b() == ro.a.ATTACK) {
					if (aom4 instanceof bbg || aom4 instanceof aos || aom4 instanceof beg || aom4 == this.b) {
						this.b(new ne("multiplayer.disconnect.invalid_entity_attacked"));
						c.warn("Player {} tried to attack an invalid entity", this.b.P().getString());
						return;
					}

					this.b.f(aom4);
				}

				if (optional8.isPresent() && ((ang)optional8.get()).a()) {
					aa.P.a(this.b, this.b.b(anf7), aom4);
					if (((ang)optional8.get()).b()) {
						this.b.a(anf7, true);
					}
				}
			}
		}
	}

	@Override
	public void a(re re) {
		nk.a(re, this, this.b.u());
		this.b.z();
		re.a a3 = re.b();
		switch (a3) {
			case PERFORM_RESPAWN:
				if (this.b.g) {
					this.b.g = false;
					this.b = this.d.ac().a(this.b, true);
					aa.v.a(this.b, bqb.i, bqb.g);
				} else {
					if (this.b.dj() > 0.0F) {
						return;
					}

					this.b = this.d.ac().a(this.b, false);
					if (this.d.f()) {
						this.b.a(bpy.SPECTATOR);
						this.b.u().S().a(bpx.p).a(false, this.d);
					}
				}
				break;
			case REQUEST_STATS:
				this.b.A().a(this.b);
		}
	}

	@Override
	public void a(rk rk) {
		nk.a(rk, this, this.b.u());
		this.b.o();
	}

	@Override
	public void a(rj rj) {
		nk.a(rj, this, this.b.u());
		this.b.z();
		if (this.b.bw.b == rj.b() && this.b.bw.c(this.b)) {
			if (this.b.a_()) {
				gi<bki> gi3 = gi.a();

				for (int integer4 = 0; integer4 < this.b.bw.a.size(); integer4++) {
					gi3.add(((bhw)this.b.bw.a.get(integer4)).e());
				}

				this.b.a(this.b.bw, gi3);
			} else {
				bki bki3 = this.b.bw.a(rj.c(), rj.d(), rj.g(), this.b);
				if (bki.b(rj.f(), bki3)) {
					this.b.b.a(new oe(rj.b(), rj.e(), true));
					this.b.e = true;
					this.b.bw.c();
					this.b.n();
					this.b.e = false;
				} else {
					this.k.put(this.b.bw.b, rj.e());
					this.b.b.a(new oe(rj.b(), rj.e(), false));
					this.b.bw.a(this.b, false);
					gi<bki> gi4 = gi.a();

					for (int integer5 = 0; integer5 < this.b.bw.a.size(); integer5++) {
						bki bki6 = ((bhw)this.b.bw.a.get(integer5)).e();
						gi4.add(bki6.a() ? bki.b : bki6);
					}

					this.b.a(this.b.bw, gi4);
				}
			}
		}
	}

	@Override
	public void a(rw rw) {
		nk.a(rw, this, this.b.u());
		this.b.z();
		if (!this.b.a_() && this.b.bw.b == rw.b() && this.b.bw.c(this.b) && this.b.bw instanceof bhp) {
			this.d.aD().a(rw.c()).ifPresent(bmu -> ((bhp)this.b.bw).a(rw.d(), bmu, this.b));
		}
	}

	@Override
	public void a(ri ri) {
		nk.a(ri, this, this.b.u());
		this.b.z();
		if (this.b.bw.b == ri.b() && this.b.bw.c(this.b) && !this.b.a_()) {
			this.b.bw.a(this.b, ri.c());
			this.b.bw.c();
		}
	}

	@Override
	public void a(sk sk) {
		nk.a(sk, this, this.b.u());
		if (this.b.d.e()) {
			boolean boolean3 = sk.b() < 0;
			bki bki4 = sk.c();
			le le5 = bki4.b("BlockEntityTag");
			if (!bki4.a() && le5 != null && le5.e("x") && le5.e("y") && le5.e("z")) {
				fu fu6 = new fu(le5.h("x"), le5.h("y"), le5.h("z"));
				cdl cdl7 = this.b.l.c(fu6);
				if (cdl7 != null) {
					le le8 = cdl7.a(new le());
					le8.r("x");
					le8.r("y");
					le8.r("z");
					bki4.a("BlockEntityTag", le8);
				}
			}

			boolean boolean6 = sk.b() >= 1 && sk.b() <= 45;
			boolean boolean7 = bki4.a() || bki4.g() >= 0 && bki4.E() <= 64 && !bki4.a();
			if (boolean6 && boolean7) {
				if (bki4.a()) {
					this.b.bv.a(sk.b(), bki.b);
				} else {
					this.b.bv.a(sk.b(), bki4);
				}

				this.b.bv.a(this.b, true);
				this.b.bv.c();
			} else if (boolean3 && boolean7 && this.j < 200) {
				this.j += 20;
				this.b.a(bki4, true);
			}
		}
	}

	@Override
	public void a(rh rh) {
		nk.a(rh, this, this.b.u());
		int integer3 = this.b.bw.b;
		if (integer3 == rh.b() && this.k.getOrDefault(integer3, (short)(rh.c() + 1)) == rh.c() && !this.b.bw.c(this.b) && !this.b.a_()) {
			this.b.bw.a(this.b, true);
		}
	}

	@Override
	public void a(sn sn) {
		nk.a(sn, this, this.b.u());
		this.b.z();
		zd zd3 = this.b.u();
		fu fu4 = sn.b();
		if (zd3.C(fu4)) {
			cfj cfj5 = zd3.d_(fu4);
			cdl cdl6 = zd3.c(fu4);
			if (!(cdl6 instanceof ceh)) {
				return;
			}

			ceh ceh7 = (ceh)cdl6;
			if (!ceh7.d() || ceh7.f() != this.b) {
				c.warn("Player {} just tried to change non-editable sign", this.b.P().getString());
				return;
			}

			String[] arr8 = sn.c();

			for (int integer9 = 0; integer9 < arr8.length; integer9++) {
				ceh7.a(integer9, new nd(i.a(arr8[integer9])));
			}

			ceh7.Z_();
			zd3.a(fu4, cfj5, cfj5, 3);
		}
	}

	@Override
	public void a(rq rq) {
		if (this.g && rq.b() == this.h) {
			int integer3 = (int)(v.b() - this.f);
			this.b.f = (this.b.f * 3 + integer3) / 4;
			this.g = false;
		} else if (!this.d()) {
			this.b(new ne("disconnect.timeout"));
		}
	}

	@Override
	public void a(rx rx) {
		nk.a(rx, this, this.b.u());
		this.b.bJ.b = rx.b() && this.b.bJ.c;
	}

	@Override
	public void a(rf rf) {
		nk.a(rf, this, this.b.u());
		this.b.a(rf);
	}

	@Override
	public void a(rl rl) {
	}

	@Override
	public void a(rc rc) {
		nk.a(rc, this, this.b.u());
		if (this.b.k(2) || this.d()) {
			this.d.a(rc.b(), false);
		}
	}

	@Override
	public void a(rr rr) {
		nk.a(rr, this, this.b.u());
		if (this.b.k(2) || this.d()) {
			this.d.b(rr.b());
		}
	}
}

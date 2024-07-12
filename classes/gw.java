import java.util.List;
import java.util.Random;

public interface gw {
	gw a = (fv, bki) -> bki;

	bki dispense(fv fv, bki bki);

	static void c() {
		bxd.a(bkk.kg, new gt() {
			@Override
			protected bes a(bqb bqb, gj gj, bki bki) {
				bei bei5 = new bei(bqb, gj.a(), gj.b(), gj.c());
				bei5.d = beg.a.ALLOWED;
				return bei5;
			}
		});
		bxd.a(bkk.qk, new gt() {
			@Override
			protected bes a(bqb bqb, gj gj, bki bki) {
				bei bei5 = new bei(bqb, gj.a(), gj.b(), gj.c());
				bei5.b(bki);
				bei5.d = beg.a.ALLOWED;
				return bei5;
			}
		});
		bxd.a(bkk.qj, new gt() {
			@Override
			protected bes a(bqb bqb, gj gj, bki bki) {
				beg beg5 = new bex(bqb, gj.a(), gj.b(), gj.c());
				beg5.d = beg.a.ALLOWED;
				return beg5;
			}
		});
		bxd.a(bkk.mg, new gt() {
			@Override
			protected bes a(bqb bqb, gj gj, bki bki) {
				return v.a(new bfa(bqb, gj.a(), gj.b(), gj.c()), bfa -> bfa.b(bki));
			}
		});
		bxd.a(bkk.lQ, new gt() {
			@Override
			protected bes a(bqb bqb, gj gj, bki bki) {
				return v.a(new bew(bqb, gj.a(), gj.b(), gj.c()), bew -> bew.b(bki));
			}
		});
		bxd.a(bkk.oQ, new gt() {
			@Override
			protected bes a(bqb bqb, gj gj, bki bki) {
				return v.a(new bfc(bqb, gj.a(), gj.b(), gj.c()), bfc -> bfc.b(bki));
			}

			@Override
			protected float a() {
				return super.a() * 0.5F;
			}

			@Override
			protected float b() {
				return super.b() * 1.25F;
			}
		});
		bxd.a(bkk.qi, new gw() {
			@Override
			public bki dispense(fv fv, bki bki) {
				return (new gt() {
					@Override
					protected bes a(bqb bqb, gj gj, bki bki) {
						return v.a(new bfd(bqb, gj.a(), gj.b(), gj.c()), bfd -> bfd.b(bki));
					}

					@Override
					protected float a() {
						return super.a() * 0.5F;
					}

					@Override
					protected float b() {
						return super.b() * 1.25F;
					}
				}).dispense(fv, bki);
			}
		});
		bxd.a(bkk.ql, new gw() {
			@Override
			public bki dispense(fv fv, bki bki) {
				return (new gt() {
					@Override
					protected bes a(bqb bqb, gj gj, bki bki) {
						return v.a(new bfd(bqb, gj.a(), gj.b(), gj.c()), bfd -> bfd.b(bki));
					}

					@Override
					protected float a() {
						return super.a() * 0.5F;
					}

					@Override
					protected float b() {
						return super.b() * 1.25F;
					}
				}).dispense(fv, bki);
			}
		});
		gv gv1 = new gv() {
			@Override
			public bki a(fv fv, bki bki) {
				fz fz4 = fv.e().c(bxd.a);
				aoq<?> aoq5 = ((blh)bki.b()).a(bki.o());
				aoq5.a(fv.h(), bki, null, fv.d().a(fz4), apb.DISPENSER, fz4 != fz.UP, false);
				bki.g(1);
				return bki;
			}
		};

		for (blh blh3 : blh.f()) {
			bxd.a(blh3, gv1);
		}

		bxd.a(bkk.pB, new gv() {
			@Override
			public bki a(fv fv, bki bki) {
				fz fz4 = fv.e().c(bxd.a);
				fu fu5 = fv.d().a(fz4);
				bqb bqb6 = fv.h();
				bay bay7 = new bay(bqb6, (double)fu5.u() + 0.5, (double)fu5.v(), (double)fu5.w() + 0.5);
				aoq.a(bqb6, null, bay7, bki.o());
				bay7.p = fz4.o();
				bqb6.c(bay7);
				bki.g(1);
				return bki;
			}
		});
		bxd.a(bkk.lO, new gx() {
			@Override
			public bki a(fv fv, bki bki) {
				fu fu4 = fv.d().a(fv.e().c(bxd.a));
				List<aoy> list5 = fv.h().a(aoy.class, new deg(fu4), aoy -> {
					if (!(aoy instanceof apm)) {
						return false;
					} else {
						apm apm2 = (apm)aoy;
						return !apm2.N_() && apm2.M_();
					}
				});
				if (!list5.isEmpty()) {
					((apm)list5.get(0)).a(acm.BLOCKS);
					bki.g(1);
					this.a(true);
					return bki;
				} else {
					return super.a(fv, bki);
				}
			}
		});
		gv gv2 = new gx() {
			@Override
			protected bki a(fv fv, bki bki) {
				fu fu4 = fv.d().a(fv.e().c(bxd.a));

				for (azm azm7 : fv.h().a(azm.class, new deg(fu4), azm -> azm.aU() && azm.ft())) {
					if (azm7.l(bki) && !azm7.fu() && azm7.eX()) {
						azm7.a_(401, bki.a(1));
						this.a(true);
						return bki;
					}
				}

				return super.a(fv, bki);
			}
		};
		bxd.a(bkk.pF, gv2);
		bxd.a(bkk.pC, gv2);
		bxd.a(bkk.pD, gv2);
		bxd.a(bkk.pE, gv2);
		bxd.a(bkk.fM, gv2);
		bxd.a(bkk.fN, gv2);
		bxd.a(bkk.fV, gv2);
		bxd.a(bkk.fX, gv2);
		bxd.a(bkk.fY, gv2);
		bxd.a(bkk.gb, gv2);
		bxd.a(bkk.fT, gv2);
		bxd.a(bkk.fZ, gv2);
		bxd.a(bkk.fP, gv2);
		bxd.a(bkk.fU, gv2);
		bxd.a(bkk.fR, gv2);
		bxd.a(bkk.fO, gv2);
		bxd.a(bkk.fS, gv2);
		bxd.a(bkk.fW, gv2);
		bxd.a(bkk.ga, gv2);
		bxd.a(bkk.fQ, gv2);
		bxd.a(bkk.cy, new gx() {
			@Override
			public bki a(fv fv, bki bki) {
				fu fu4 = fv.d().a(fv.e().c(bxd.a));

				for (azl azl7 : fv.h().a(azl.class, new deg(fu4), azl -> azl.aU() && !azl.eN())) {
					if (azl7.eX() && azl7.a_(499, bki)) {
						bki.g(1);
						this.a(true);
						return bki;
					}
				}

				return super.a(fv, bki);
			}
		});
		bxd.a(bkk.pn, new gv() {
			@Override
			public bki a(fv fv, bki bki) {
				fz fz4 = fv.e().c(bxd.a);
				ben ben5 = new ben(fv.h(), bki, fv.a(), fv.b(), fv.a(), true);
				gw.a(fv, ben5, fz4);
				ben5.c((double)fz4.i(), (double)fz4.j(), (double)fz4.k(), 0.5F, 1.0F);
				fv.h().c(ben5);
				bki.g(1);
				return bki;
			}

			@Override
			protected void a(fv fv) {
				fv.h().c(1004, fv.d(), 0);
			}
		});
		bxd.a(bkk.oR, new gv() {
			@Override
			public bki a(fv fv, bki bki) {
				fz fz4 = fv.e().c(bxd.a);
				gj gj5 = bxd.a(fv);
				double double6 = gj5.a() + (double)((float)fz4.i() * 0.3F);
				double double8 = gj5.b() + (double)((float)fz4.j() * 0.3F);
				double double10 = gj5.c() + (double)((float)fz4.k() * 0.3F);
				bqb bqb12 = fv.h();
				Random random13 = bqb12.t;
				double double14 = random13.nextGaussian() * 0.05 + (double)fz4.i();
				double double16 = random13.nextGaussian() * 0.05 + (double)fz4.j();
				double double18 = random13.nextGaussian() * 0.05 + (double)fz4.k();
				bqb12.c(v.a(new bev(bqb12, double6, double8, double10, double14, double16, double18), bev -> bev.b(bki)));
				bki.g(1);
				return bki;
			}

			@Override
			protected void a(fv fv) {
				fv.h().c(1018, fv.d(), 0);
			}
		});
		bxd.a(bkk.lR, new gu(bft.b.OAK));
		bxd.a(bkk.qo, new gu(bft.b.SPRUCE));
		bxd.a(bkk.qp, new gu(bft.b.BIRCH));
		bxd.a(bkk.qq, new gu(bft.b.JUNGLE));
		bxd.a(bkk.qs, new gu(bft.b.DARK_OAK));
		bxd.a(bkk.qr, new gu(bft.b.ACACIA));
		gw gw3 = new gv() {
			private final gv b = new gv();

			@Override
			public bki a(fv fv, bki bki) {
				biu biu4 = (biu)bki.b();
				fu fu5 = fv.d().a(fv.e().c(bxd.a));
				bqb bqb6 = fv.h();
				if (biu4.a(null, bqb6, fu5, null)) {
					biu4.a(bqb6, bki, fu5);
					return new bki(bkk.lK);
				} else {
					return this.b.dispense(fv, bki);
				}
			}
		};
		bxd.a(bkk.lM, gw3);
		bxd.a(bkk.lL, gw3);
		bxd.a(bkk.lV, gw3);
		bxd.a(bkk.lW, gw3);
		bxd.a(bkk.lU, gw3);
		bxd.a(bkk.lX, gw3);
		bxd.a(bkk.lK, new gv() {
			private final gv b = new gv();

			@Override
			public bki a(fv fv, bki bki) {
				bqc bqc4 = fv.h();
				fu fu5 = fv.d().a(fv.e().c(bxd.a));
				cfj cfj6 = bqc4.d_(fu5);
				bvr bvr7 = cfj6.b();
				if (bvr7 instanceof bvw) {
					cwz cwz9 = ((bvw)bvr7).b(bqc4, fu5, cfj6);
					if (!(cwz9 instanceof cwy)) {
						return super.a(fv, bki);
					} else {
						bke bke8 = cwz9.a();
						bki.g(1);
						if (bki.a()) {
							return new bki(bke8);
						} else {
							if (fv.<cdu>g().a(new bki(bke8)) < 0) {
								this.b.dispense(fv, new bki(bke8));
							}

							return bki;
						}
					}
				} else {
					return super.a(fv, bki);
				}
			}
		});
		bxd.a(bkk.kd, new gx() {
			@Override
			protected bki a(fv fv, bki bki) {
				bqb bqb4 = fv.h();
				this.a(true);
				fu fu5 = fv.d().a(fv.e().c(bxd.a));
				cfj cfj6 = bqb4.d_(fu5);
				if (bvh.a((bqc)bqb4, fu5)) {
					bqb4.a(fu5, bvh.a((bpg)bqb4, fu5));
				} else if (bwb.h(cfj6)) {
					bqb4.a(fu5, cfj6.a(cfz.r, Boolean.valueOf(true)));
				} else if (cfj6.b() instanceof ccb) {
					ccb.a(bqb4, fu5);
					bqb4.a(fu5, false);
				} else {
					this.a(false);
				}

				if (this.a() && bki.a(1, bqb4.t, null)) {
					bki.e(0);
				}

				return bki;
			}
		});
		bxd.a(bkk.mG, new gx() {
			@Override
			protected bki a(fv fv, bki bki) {
				this.a(true);
				bqb bqb4 = fv.h();
				fu fu5 = fv.d().a(fv.e().c(bxd.a));
				if (!bip.a(bki, bqb4, fu5) && !bip.a(bki, bqb4, fu5, null)) {
					this.a(false);
				} else if (!bqb4.v) {
					bqb4.c(2005, fu5, 0);
				}

				return bki;
			}
		});
		bxd.a(bvs.bH, new gv() {
			@Override
			protected bki a(fv fv, bki bki) {
				bqb bqb4 = fv.h();
				fu fu5 = fv.d().a(fv.e().c(bxd.a));
				bbh bbh6 = new bbh(bqb4, (double)fu5.u() + 0.5, (double)fu5.v(), (double)fu5.w() + 0.5, null);
				bqb4.c(bbh6);
				bqb4.a(null, bbh6.cC(), bbh6.cD(), bbh6.cG(), acl.oU, acm.BLOCKS, 1.0F, 1.0F);
				bki.g(1);
				return bki;
			}
		});
		gw gw4 = new gx() {
			@Override
			protected bki a(fv fv, bki bki) {
				this.a(bid.a(fv, bki));
				return bki;
			}
		};
		bxd.a(bkk.ph, gw4);
		bxd.a(bkk.pg, gw4);
		bxd.a(bkk.pi, gw4);
		bxd.a(bkk.pd, gw4);
		bxd.a(bkk.pf, gw4);
		bxd.a(bkk.pe, new gx() {
			@Override
			protected bki a(fv fv, bki bki) {
				bqb bqb4 = fv.h();
				fz fz5 = fv.e().c(bxd.a);
				fu fu6 = fv.d().a(fz5);
				if (bqb4.w(fu6) && ccx.b(bqb4, fu6, bki)) {
					bqb4.a(fu6, bvs.fe.n().a(cay.a, Integer.valueOf(fz5.n() == fz.a.Y ? 0 : fz5.f().d() * 4)), 3);
					cdl cdl7 = bqb4.c(fu6);
					if (cdl7 instanceof cei) {
						ccx.a(bqb4, fu6, (cei)cdl7);
					}

					bki.g(1);
					this.a(true);
				} else {
					this.a(bid.a(fv, bki));
				}

				return bki;
			}
		});
		bxd.a(bvs.cU, new gx() {
			@Override
			protected bki a(fv fv, bki bki) {
				bqb bqb4 = fv.h();
				fu fu5 = fv.d().a(fv.e().c(bxd.a));
				bwe bwe6 = (bwe)bvs.cU;
				if (bqb4.w(fu5) && bwe6.a(bqb4, fu5)) {
					if (!bqb4.v) {
						bqb4.a(fu5, bwe6.n(), 3);
					}

					bki.g(1);
					this.a(true);
				} else {
					this.a(bid.a(fv, bki));
				}

				return bki;
			}
		});
		bxd.a(bvs.iP.h(), new gz());

		for (bje bje8 : bje.values()) {
			bxd.a(cav.a(bje8).h(), new gz());
		}

		bxd.a(bkk.nw.h(), new gx() {
			private final gv b = new gv();

			private bki a(fv fv, bki bki2, bki bki3) {
				bki2.g(1);
				if (bki2.a()) {
					return bki3.i();
				} else {
					if (fv.<cdu>g().a(bki3.i()) < 0) {
						this.b.dispense(fv, bki3.i());
					}

					return bki2;
				}
			}

			@Override
			public bki a(fv fv, bki bki) {
				this.a(false);
				bqc bqc4 = fv.h();
				fu fu5 = fv.d().a(fv.e().c(bxd.a));
				cfj cfj6 = bqc4.d_(fu5);
				if (cfj6.a(acx.ai, a -> a.b(bvn.b)) && (Integer)cfj6.c(bvn.b) >= 5) {
					((bvn)cfj6.b()).a(bqc4.n(), cfj6, fu5, null, cdi.b.BEE_RELEASED);
					this.a(true);
					return this.a(fv, bki, new bki(bkk.rs));
				} else if (bqc4.b(fu5).a(acz.a)) {
					this.a(true);
					return this.a(fv, bki, bmd.a(new bki(bkk.nv), bme.b));
				} else {
					return super.a(fv, bki);
				}
			}
		});
		bxd.a(bkk.dq, new gx() {
			@Override
			public bki a(fv fv, bki bki) {
				fz fz4 = fv.e().c(bxd.a);
				fu fu5 = fv.d().a(fz4);
				bqb bqb6 = fv.h();
				cfj cfj7 = bqb6.d_(fu5);
				this.a(true);
				if (cfj7.a(bvs.nj)) {
					if ((Integer)cfj7.c(cam.a) != 4) {
						cam.a(bqb6, fu5, cfj7);
						bki.g(1);
					} else {
						this.a(false);
					}

					return bki;
				} else {
					return super.a(fv, bki);
				}
			}
		});
		bxd.a(bkk.ng.h(), new gy());
	}

	static void a(fv fv, aom aom, fz fz) {
		aom.d(
			fv.a() + (double)fz.i() * (0.5000099999997474 - (double)aom.cx() / 2.0),
			fv.b() + (double)fz.j() * (0.5000099999997474 - (double)aom.cy() / 2.0) - (double)aom.cy() / 2.0,
			fv.c() + (double)fz.k() * (0.5000099999997474 - (double)aom.cx() / 2.0)
		);
	}
}

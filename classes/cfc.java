import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class cfc extends bxc {
	public static final cga b = cfz.g;
	protected static final dfg c = bvr.a(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
	protected static final dfg d = bvr.a(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg e = bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
	protected static final dfg f = bvr.a(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
	protected static final dfg g = bvr.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
	protected static final dfg h = bvr.a(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
	private final boolean i;

	public cfc(boolean boolean1, cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)));
		this.i = boolean1;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		if ((Boolean)cfj.c(b)) {
			switch ((fz)cfj.c(a)) {
				case DOWN:
					return h;
				case UP:
				default:
					return g;
				case NORTH:
					return f;
				case SOUTH:
					return e;
				case WEST:
					return d;
				case EAST:
					return c;
			}
		} else {
			return dfd.b();
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (!bqb.v) {
			this.a(bqb, fu, cfj);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (!bqb.v) {
			this.a(bqb, fu3, cfj);
		}
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			if (!bqb.v && bqb.c(fu) == null) {
				this.a(bqb, fu, cfj1);
			}
		}
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, bin.d().f()).a(b, Boolean.valueOf(false));
	}

	private void a(bqb bqb, fu fu, cfj cfj) {
		fz fz5 = cfj.c(a);
		boolean boolean6 = this.a(bqb, fu, fz5);
		if (boolean6 && !(Boolean)cfj.c(b)) {
			if (new cfg(bqb, fu, fz5, true).a()) {
				bqb.a(fu, this, 0, fz5.c());
			}
		} else if (!boolean6 && (Boolean)cfj.c(b)) {
			fu fu7 = fu.a(fz5, 2);
			cfj cfj8 = bqb.d_(fu7);
			int integer9 = 1;
			if (cfj8.a(bvs.bo) && cfj8.c(a) == fz5) {
				cdl cdl10 = bqb.c(fu7);
				if (cdl10 instanceof cff) {
					cff cff11 = (cff)cdl10;
					if (cff11.d() && (cff11.a(0.0F) < 0.5F || bqb.Q() == cff11.m() || ((zd)bqb).m_())) {
						integer9 = 2;
					}
				}
			}

			bqb.a(fu, this, integer9, fz5.c());
		}
	}

	private boolean a(bqb bqb, fu fu, fz fz) {
		for (fz fz8 : fz.values()) {
			if (fz8 != fz && bqb.a(fu.a(fz8), fz8)) {
				return true;
			}
		}

		if (bqb.a(fu, fz.DOWN)) {
			return true;
		} else {
			fu fu5 = fu.b();

			for (fz fz9 : fz.values()) {
				if (fz9 != fz.DOWN && bqb.a(fu5.a(fz9), fz9)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public boolean a(cfj cfj, bqb bqb, fu fu, int integer4, int integer5) {
		fz fz7 = cfj.c(a);
		if (!bqb.v) {
			boolean boolean8 = this.a(bqb, fu, fz7);
			if (boolean8 && (integer4 == 1 || integer4 == 2)) {
				bqb.a(fu, cfj.a(b, Boolean.valueOf(true)), 2);
				return false;
			}

			if (!boolean8 && integer4 == 0) {
				return false;
			}
		}

		if (integer4 == 0) {
			if (!this.a(bqb, fu, fz7, true)) {
				return false;
			}

			bqb.a(fu, cfj.a(b, Boolean.valueOf(true)), 67);
			bqb.a(null, fu, acl.lg, acm.BLOCKS, 0.5F, bqb.t.nextFloat() * 0.25F + 0.6F);
		} else if (integer4 == 1 || integer4 == 2) {
			cdl cdl8 = bqb.c(fu.a(fz7));
			if (cdl8 instanceof cff) {
				((cff)cdl8).l();
			}

			cfj cfj9 = bvs.bo.n().a(cfb.a, fz7).a(cfb.b, this.i ? cgk.STICKY : cgk.DEFAULT);
			bqb.a(fu, cfj9, 20);
			bqb.a(fu, cfb.a(this.n().a(a, fz.a(integer5 & 7)), fz7, false, true));
			bqb.a(fu, cfj9.b());
			cfj9.a(bqb, fu, 2);
			if (this.i) {
				fu fu10 = fu.b(fz7.i() * 2, fz7.j() * 2, fz7.k() * 2);
				cfj cfj11 = bqb.d_(fu10);
				boolean boolean12 = false;
				if (cfj11.a(bvs.bo)) {
					cdl cdl13 = bqb.c(fu10);
					if (cdl13 instanceof cff) {
						cff cff14 = (cff)cdl13;
						if (cff14.f() == fz7 && cff14.d()) {
							cff14.l();
							boolean12 = true;
						}
					}
				}

				if (!boolean12) {
					if (integer4 != 1 || cfj11.g() || !a(cfj11, bqb, fu10, fz7.f(), false, fz7) || cfj11.k() != cxf.NORMAL && !cfj11.a(bvs.aW) && !cfj11.a(bvs.aP)) {
						bqb.a(fu.a(fz7), false);
					} else {
						this.a(bqb, fu, fz7, false);
					}
				}
			} else {
				bqb.a(fu.a(fz7), false);
			}

			bqb.a(null, fu, acl.lf, acm.BLOCKS, 0.5F, bqb.t.nextFloat() * 0.15F + 0.6F);
		}

		return true;
	}

	public static boolean a(cfj cfj, bqb bqb, fu fu, fz fz4, boolean boolean5, fz fz6) {
		if (!cfj.a(bvs.bK) && !cfj.a(bvs.ni) && !cfj.a(bvs.nj)) {
			if (!bqb.f().a(fu)) {
				return false;
			} else if (fu.v() >= 0 && (fz4 != fz.DOWN || fu.v() != 0)) {
				if (fu.v() <= bqb.I() - 1 && (fz4 != fz.UP || fu.v() != bqb.I() - 1)) {
					if (!cfj.a(bvs.aW) && !cfj.a(bvs.aP)) {
						if (cfj.h(bqb, fu) == -1.0F) {
							return false;
						}

						switch (cfj.k()) {
							case BLOCK:
								return false;
							case DESTROY:
								return boolean5;
							case PUSH_ONLY:
								return fz4 == fz6;
						}
					} else if ((Boolean)cfj.c(b)) {
						return false;
					}

					return !cfj.b().q();
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean a(bqb bqb, fu fu, fz fz, boolean boolean4) {
		fu fu6 = fu.a(fz);
		if (!boolean4 && bqb.d_(fu6).a(bvs.aX)) {
			bqb.a(fu6, bvs.a.n(), 20);
		}

		cfg cfg7 = new cfg(bqb, fu, fz, boolean4);
		if (!cfg7.a()) {
			return false;
		} else {
			Map<fu, cfj> map8 = Maps.<fu, cfj>newHashMap();
			List<fu> list9 = cfg7.c();
			List<cfj> list10 = Lists.<cfj>newArrayList();

			for (int integer11 = 0; integer11 < list9.size(); integer11++) {
				fu fu12 = (fu)list9.get(integer11);
				cfj cfj13 = bqb.d_(fu12);
				list10.add(cfj13);
				map8.put(fu12, cfj13);
			}

			List<fu> list11 = cfg7.d();
			cfj[] arr12 = new cfj[list9.size() + list11.size()];
			fz fz13 = boolean4 ? fz : fz.f();
			int integer14 = 0;

			for (int integer15 = list11.size() - 1; integer15 >= 0; integer15--) {
				fu fu16 = (fu)list11.get(integer15);
				cfj cfj17 = bqb.d_(fu16);
				cdl cdl18 = cfj17.b().q() ? bqb.c(fu16) : null;
				a(cfj17, bqb, fu16, cdl18);
				bqb.a(fu16, bvs.a.n(), 18);
				arr12[integer14++] = cfj17;
			}

			for (int integer15 = list9.size() - 1; integer15 >= 0; integer15--) {
				fu fu16 = (fu)list9.get(integer15);
				cfj cfj17 = bqb.d_(fu16);
				fu16 = fu16.a(fz13);
				map8.remove(fu16);
				bqb.a(fu16, bvs.bo.n().a(a, fz), 68);
				bqb.a(fu16, cfb.a((cfj)list10.get(integer15), fz, boolean4, false));
				arr12[integer14++] = cfj17;
			}

			if (boolean4) {
				cgk cgk15 = this.i ? cgk.STICKY : cgk.DEFAULT;
				cfj cfj16 = bvs.aX.n().a(cfd.a, fz).a(cfd.b, cgk15);
				cfj cfj17 = bvs.bo.n().a(cfb.a, fz).a(cfb.b, this.i ? cgk.STICKY : cgk.DEFAULT);
				map8.remove(fu6);
				bqb.a(fu6, cfj17, 68);
				bqb.a(fu6, cfb.a(cfj16, fz, true, true));
			}

			cfj cfj15 = bvs.a.n();

			for (fu fu17 : map8.keySet()) {
				bqb.a(fu17, cfj15, 82);
			}

			for (Entry<fu, cfj> entry17 : map8.entrySet()) {
				fu fu18 = (fu)entry17.getKey();
				cfj cfj19 = (cfj)entry17.getValue();
				cfj19.b(bqb, fu18, 2);
				cfj15.a(bqb, fu18, 2);
				cfj15.b(bqb, fu18, 2);
			}

			integer14 = 0;

			for (int integer16 = list11.size() - 1; integer16 >= 0; integer16--) {
				cfj cfj17 = arr12[integer14++];
				fu fu18 = (fu)list11.get(integer16);
				cfj17.b(bqb, fu18, 2);
				bqb.b(fu18, cfj17.b());
			}

			for (int integer16 = list9.size() - 1; integer16 >= 0; integer16--) {
				bqb.b((fu)list9.get(integer16), arr12[integer14++].b());
			}

			if (boolean4) {
				bqb.b(fu6, bvs.aX);
			}

			return true;
		}
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cfc.a, b);
	}

	@Override
	public boolean c_(cfj cfj) {
		return (Boolean)cfj.c(b);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}

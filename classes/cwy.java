import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class cwy extends cwz {
	public static final cga a = cfz.i;
	public static final cgi b = cfz.at;
	private static final ThreadLocal<Object2ByteLinkedOpenHashMap<bvr.a>> e = ThreadLocal.withInitial(() -> {
		Object2ByteLinkedOpenHashMap<bvr.a> object2ByteLinkedOpenHashMap1 = new Object2ByteLinkedOpenHashMap<bvr.a>(200) {
			@Override
			protected void rehash(int integer) {
			}
		};
		object2ByteLinkedOpenHashMap1.defaultReturnValue((byte)127);
		return object2ByteLinkedOpenHashMap1;
	});
	private final Map<cxa, dfg> f = Maps.<cxa, dfg>newIdentityHashMap();

	@Override
	protected void a(cfk.a<cwz, cxa> a) {
		a.a(cwy.a);
	}

	@Override
	public dem a(bpg bpg, fu fu, cxa cxa) {
		double double5 = 0.0;
		double double7 = 0.0;
		fu.a a9 = new fu.a();

		for (fz fz11 : fz.c.HORIZONTAL) {
			a9.a(fu, fz11);
			cxa cxa12 = bpg.b(a9);
			if (this.g(cxa12)) {
				float float13 = cxa12.d();
				float float14 = 0.0F;
				if (float13 == 0.0F) {
					if (!bpg.d_(a9).c().c()) {
						fu fu15 = a9.c();
						cxa cxa16 = bpg.b(fu15);
						if (this.g(cxa16)) {
							float13 = cxa16.d();
							if (float13 > 0.0F) {
								float14 = cxa.d() - (float13 - 0.8888889F);
							}
						}
					}
				} else if (float13 > 0.0F) {
					float14 = cxa.d() - float13;
				}

				if (float14 != 0.0F) {
					double5 += (double)((float)fz11.i() * float14);
					double7 += (double)((float)fz11.k() * float14);
				}
			}
		}

		dem dem10 = new dem(double5, 0.0, double7);
		if ((Boolean)cxa.c(a)) {
			for (fz fz12 : fz.c.HORIZONTAL) {
				a9.a(fu, fz12);
				if (this.a(bpg, a9, fz12) || this.a(bpg, a9.b(), fz12)) {
					dem10 = dem10.d().b(0.0, -6.0, 0.0);
					break;
				}
			}
		}

		return dem10.d();
	}

	private boolean g(cxa cxa) {
		return cxa.c() || cxa.a().a(this);
	}

	protected boolean a(bpg bpg, fu fu, fz fz) {
		cfj cfj5 = bpg.d_(fu);
		cxa cxa6 = bpg.b(fu);
		if (cxa6.a().a(this)) {
			return false;
		} else if (fz == fz.UP) {
			return true;
		} else {
			return cfj5.c() == cxd.F ? false : cfj5.d(bpg, fu, fz);
		}
	}

	protected void a(bqc bqc, fu fu, cxa cxa) {
		if (!cxa.c()) {
			cfj cfj5 = bqc.d_(fu);
			fu fu6 = fu.c();
			cfj cfj7 = bqc.d_(fu6);
			cxa cxa8 = this.a((bqd)bqc, fu6, cfj7);
			if (this.a(bqc, fu, cfj5, fz.DOWN, fu6, cfj7, bqc.b(fu6), cxa8.a())) {
				this.a(bqc, fu6, cfj7, fz.DOWN, cxa8);
				if (this.a(bqc, fu) >= 3) {
					this.a(bqc, fu, cxa, cfj5);
				}
			} else if (cxa.b() || !this.a(bqc, cxa8.a(), fu, cfj5, fu6, cfj7)) {
				this.a(bqc, fu, cxa, cfj5);
			}
		}
	}

	private void a(bqc bqc, fu fu, cxa cxa, cfj cfj) {
		int integer6 = cxa.e() - this.c(bqc);
		if ((Boolean)cxa.c(a)) {
			integer6 = 7;
		}

		if (integer6 > 0) {
			Map<fz, cxa> map7 = this.b(bqc, fu, cfj);

			for (Entry<fz, cxa> entry9 : map7.entrySet()) {
				fz fz10 = (fz)entry9.getKey();
				cxa cxa11 = (cxa)entry9.getValue();
				fu fu12 = fu.a(fz10);
				cfj cfj13 = bqc.d_(fu12);
				if (this.a(bqc, fu, cfj, fz10, fu12, cfj13, bqc.b(fu12), cxa11.a())) {
					this.a(bqc, fu12, cfj13, fz10, cxa11);
				}
			}
		}
	}

	protected cxa a(bqd bqd, fu fu, cfj cfj) {
		int integer5 = 0;
		int integer6 = 0;

		for (fz fz8 : fz.c.HORIZONTAL) {
			fu fu9 = fu.a(fz8);
			cfj cfj10 = bqd.d_(fu9);
			cxa cxa11 = cfj10.m();
			if (cxa11.a().a(this) && this.a(fz8, bqd, fu, cfj, fu9, cfj10)) {
				if (cxa11.b()) {
					integer6++;
				}

				integer5 = Math.max(integer5, cxa11.e());
			}
		}

		if (this.f() && integer6 >= 2) {
			cfj cfj7 = bqd.d_(fu.c());
			cxa cxa8 = cfj7.m();
			if (cfj7.c().b() || this.h(cxa8)) {
				return this.a(false);
			}
		}

		fu fu7 = fu.b();
		cfj cfj8 = bqd.d_(fu7);
		cxa cxa9 = cfj8.m();
		if (!cxa9.c() && cxa9.a().a(this) && this.a(fz.UP, bqd, fu, cfj, fu7, cfj8)) {
			return this.a(8, true);
		} else {
			int integer10 = integer5 - this.c(bqd);
			return integer10 <= 0 ? cxb.a.h() : this.a(integer10, false);
		}
	}

	private boolean a(fz fz, bpg bpg, fu fu3, cfj cfj4, fu fu5, cfj cfj6) {
		Object2ByteLinkedOpenHashMap<bvr.a> object2ByteLinkedOpenHashMap8;
		if (!cfj4.b().o() && !cfj6.b().o()) {
			object2ByteLinkedOpenHashMap8 = (Object2ByteLinkedOpenHashMap<bvr.a>)e.get();
		} else {
			object2ByteLinkedOpenHashMap8 = null;
		}

		bvr.a a9;
		if (object2ByteLinkedOpenHashMap8 != null) {
			a9 = new bvr.a(cfj4, cfj6, fz);
			byte byte10 = object2ByteLinkedOpenHashMap8.getAndMoveToFirst(a9);
			if (byte10 != 127) {
				return byte10 != 0;
			}
		} else {
			a9 = null;
		}

		dfg dfg10 = cfj4.k(bpg, fu3);
		dfg dfg11 = cfj6.k(bpg, fu5);
		boolean boolean12 = !dfd.b(dfg10, dfg11, fz);
		if (object2ByteLinkedOpenHashMap8 != null) {
			if (object2ByteLinkedOpenHashMap8.size() == 200) {
				object2ByteLinkedOpenHashMap8.removeLastByte();
			}

			object2ByteLinkedOpenHashMap8.putAndMoveToFirst(a9, (byte)(boolean12 ? 1 : 0));
		}

		return boolean12;
	}

	public abstract cwz d();

	public cxa a(int integer, boolean boolean2) {
		return this.d().h().a(b, integer).a(a, boolean2);
	}

	public abstract cwz e();

	public cxa a(boolean boolean1) {
		return this.e().h().a(a, boolean1);
	}

	protected abstract boolean f();

	protected void a(bqc bqc, fu fu, cfj cfj, fz fz, cxa cxa) {
		if (cfj.b() instanceof bzf) {
			((bzf)cfj.b()).a(bqc, fu, cfj, cxa);
		} else {
			if (!cfj.g()) {
				this.a(bqc, fu, cfj);
			}

			bqc.a(fu, cxa.g(), 3);
		}
	}

	protected abstract void a(bqc bqc, fu fu, cfj cfj);

	private static short a(fu fu1, fu fu2) {
		int integer3 = fu2.u() - fu1.u();
		int integer4 = fu2.w() - fu1.w();
		return (short)((integer3 + 128 & 0xFF) << 8 | integer4 + 128 & 0xFF);
	}

	protected int a(bqd bqd, fu fu2, int integer, fz fz, cfj cfj, fu fu6, Short2ObjectMap<Pair<cfj, cxa>> short2ObjectMap, Short2BooleanMap short2BooleanMap) {
		int integer10 = 1000;

		for (fz fz12 : fz.c.HORIZONTAL) {
			if (fz12 != fz) {
				fu fu13 = fu2.a(fz12);
				short short14 = a(fu6, fu13);
				Pair<cfj, cxa> pair15 = short2ObjectMap.computeIfAbsent(short14, integerx -> {
					cfj cfj4 = bqd.d_(fu13);
					return Pair.of(cfj4, cfj4.m());
				});
				cfj cfj16 = pair15.getFirst();
				cxa cxa17 = pair15.getSecond();
				if (this.a(bqd, this.d(), fu2, cfj, fz12, fu13, cfj16, cxa17)) {
					boolean boolean18 = short2BooleanMap.computeIfAbsent(short14, integerx -> {
						fu fu6x = fu13.c();
						cfj cfj7 = bqd.d_(fu6x);
						return this.a(bqd, this.d(), fu13, cfj16, fu6x, cfj7);
					});
					if (boolean18) {
						return integer;
					}

					if (integer < this.b(bqd)) {
						int integer19 = this.a(bqd, fu13, integer + 1, fz12.f(), cfj16, fu6, short2ObjectMap, short2BooleanMap);
						if (integer19 < integer10) {
							integer10 = integer19;
						}
					}
				}
			}
		}

		return integer10;
	}

	private boolean a(bpg bpg, cwz cwz, fu fu3, cfj cfj4, fu fu5, cfj cfj6) {
		if (!this.a(fz.DOWN, bpg, fu3, cfj4, fu5, cfj6)) {
			return false;
		} else {
			return cfj6.m().a().a(this) ? true : this.a(bpg, fu5, cfj6, cwz);
		}
	}

	private boolean a(bpg bpg, cwz cwz, fu fu3, cfj cfj4, fz fz, fu fu6, cfj cfj7, cxa cxa) {
		return !this.h(cxa) && this.a(fz, bpg, fu3, cfj4, fu6, cfj7) && this.a(bpg, fu6, cfj7, cwz);
	}

	private boolean h(cxa cxa) {
		return cxa.a().a(this) && cxa.b();
	}

	protected abstract int b(bqd bqd);

	private int a(bqd bqd, fu fu) {
		int integer4 = 0;

		for (fz fz6 : fz.c.HORIZONTAL) {
			fu fu7 = fu.a(fz6);
			cxa cxa8 = bqd.b(fu7);
			if (this.h(cxa8)) {
				integer4++;
			}
		}

		return integer4;
	}

	protected Map<fz, cxa> b(bqd bqd, fu fu, cfj cfj) {
		int integer5 = 1000;
		Map<fz, cxa> map6 = Maps.newEnumMap(fz.class);
		Short2ObjectMap<Pair<cfj, cxa>> short2ObjectMap7 = new Short2ObjectOpenHashMap<>();
		Short2BooleanMap short2BooleanMap8 = new Short2BooleanOpenHashMap();

		for (fz fz10 : fz.c.HORIZONTAL) {
			fu fu11 = fu.a(fz10);
			short short12 = a(fu, fu11);
			Pair<cfj, cxa> pair13 = short2ObjectMap7.computeIfAbsent(short12, integer -> {
				cfj cfj4 = bqd.d_(fu11);
				return Pair.of(cfj4, cfj4.m());
			});
			cfj cfj14 = pair13.getFirst();
			cxa cxa15 = pair13.getSecond();
			cxa cxa16 = this.a(bqd, fu11, cfj14);
			if (this.a(bqd, cxa16.a(), fu, cfj, fz10, fu11, cfj14, cxa15)) {
				fu fu18 = fu11.c();
				boolean boolean19 = short2BooleanMap8.computeIfAbsent(short12, integer -> {
					cfj cfj7 = bqd.d_(fu18);
					return this.a(bqd, this.d(), fu11, cfj14, fu18, cfj7);
				});
				int integer17;
				if (boolean19) {
					integer17 = 0;
				} else {
					integer17 = this.a(bqd, fu11, 1, fz10.f(), cfj14, fu, short2ObjectMap7, short2BooleanMap8);
				}

				if (integer17 < integer5) {
					map6.clear();
				}

				if (integer17 <= integer5) {
					map6.put(fz10, cxa16);
					integer5 = integer17;
				}
			}
		}

		return map6;
	}

	private boolean a(bpg bpg, fu fu, cfj cfj, cwz cwz) {
		bvr bvr6 = cfj.b();
		if (bvr6 instanceof bzf) {
			return ((bzf)bvr6).a(bpg, fu, cfj, cwz);
		} else if (!(bvr6 instanceof bxe) && !bvr6.a(acx.ae) && bvr6 != bvs.cg && bvr6 != bvs.cH && bvr6 != bvs.lc) {
			cxd cxd7 = cfj.c();
			return cxd7 != cxd.c && cxd7 != cxd.b && cxd7 != cxd.f && cxd7 != cxd.h ? !cxd7.c() : false;
		} else {
			return false;
		}
	}

	protected boolean a(bpg bpg, fu fu2, cfj cfj3, fz fz, fu fu5, cfj cfj6, cxa cxa, cwz cwz) {
		return cxa.a(bpg, fu5, cwz, fz) && this.a(fz, bpg, fu2, cfj3, fu5, cfj6) && this.a(bpg, fu5, cfj6, cwz);
	}

	protected abstract int c(bqd bqd);

	protected int a(bqb bqb, fu fu, cxa cxa3, cxa cxa4) {
		return this.a(bqb);
	}

	@Override
	public void a(bqb bqb, fu fu, cxa cxa) {
		if (!cxa.b()) {
			cxa cxa5 = this.a((bqd)bqb, fu, bqb.d_(fu));
			int integer6 = this.a(bqb, fu, cxa, cxa5);
			if (cxa5.c()) {
				cxa = cxa5;
				bqb.a(fu, bvs.a.n(), 3);
			} else if (!cxa5.equals(cxa)) {
				cxa = cxa5;
				cfj cfj7 = cxa5.g();
				bqb.a(fu, cfj7, 2);
				bqb.F().a(fu, cxa5.a(), integer6);
				bqb.b(fu, cfj7.b());
			}
		}

		this.a((bqc)bqb, fu, cxa);
	}

	protected static int e(cxa cxa) {
		return cxa.b() ? 0 : 8 - Math.min(cxa.e(), 8) + (cxa.c(a) ? 8 : 0);
	}

	private static boolean c(cxa cxa, bpg bpg, fu fu) {
		return cxa.a().a(bpg.b(fu.b()).a());
	}

	@Override
	public float a(cxa cxa, bpg bpg, fu fu) {
		return c(cxa, bpg, fu) ? 1.0F : cxa.d();
	}

	@Override
	public float a(cxa cxa) {
		return (float)cxa.e() / 9.0F;
	}

	@Override
	public dfg b(cxa cxa, bpg bpg, fu fu) {
		return cxa.e() == 9 && c(cxa, bpg, fu) ? dfd.b() : (dfg)this.f.computeIfAbsent(cxa, cxax -> dfd.a(0.0, 0.0, 0.0, 1.0, (double)cxax.a(bpg, fu), 1.0));
	}
}

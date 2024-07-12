import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class cdb extends cdf implements anq, bhq, bhz, ceo {
	private static final int[] g = new int[]{0};
	private static final int[] h = new int[]{2, 1};
	private static final int[] i = new int[]{1};
	protected gi<bki> a = gi.a(3, bki.b);
	private int j;
	private int k;
	private int l;
	private int m;
	protected final bgr b = new bgr() {
		@Override
		public int a(int integer) {
			switch (integer) {
				case 0:
					return cdb.this.j;
				case 1:
					return cdb.this.k;
				case 2:
					return cdb.this.l;
				case 3:
					return cdb.this.m;
				default:
					return 0;
			}
		}

		@Override
		public void a(int integer1, int integer2) {
			switch (integer1) {
				case 0:
					cdb.this.j = integer2;
					break;
				case 1:
					cdb.this.k = integer2;
					break;
				case 2:
					cdb.this.l = integer2;
					break;
				case 3:
					cdb.this.m = integer2;
			}
		}

		@Override
		public int a() {
			return 4;
		}
	};
	private final Object2IntOpenHashMap<uh> n = new Object2IntOpenHashMap<>();
	protected final bmx<? extends bmg> c;

	protected cdb(cdm<?> cdm, bmx<? extends bmg> bmx) {
		super(cdm);
		this.c = bmx;
	}

	public static Map<bke, Integer> f() {
		Map<bke, Integer> map1 = Maps.<bke, Integer>newLinkedHashMap();
		a(map1, bkk.lM, 20000);
		a(map1, bvs.gS, 16000);
		a(map1, bkk.nr, 2400);
		a(map1, bkk.kh, 1600);
		a(map1, bkk.ki, 1600);
		a(map1, ada.p, 300);
		a(map1, ada.b, 300);
		a(map1, ada.h, 300);
		a(map1, ada.i, 150);
		a(map1, ada.l, 300);
		a(map1, ada.k, 300);
		a(map1, bvs.cJ, 300);
		a(map1, bvs.in, 300);
		a(map1, bvs.im, 300);
		a(map1, bvs.io, 300);
		a(map1, bvs.iq, 300);
		a(map1, bvs.ip, 300);
		a(map1, bvs.dQ, 300);
		a(map1, bvs.ii, 300);
		a(map1, bvs.ih, 300);
		a(map1, bvs.ij, 300);
		a(map1, bvs.il, 300);
		a(map1, bvs.ik, 300);
		a(map1, bvs.aw, 300);
		a(map1, bvs.bI, 300);
		a(map1, bvs.lY, 300);
		a(map1, bvs.cI, 300);
		a(map1, bvs.bR, 300);
		a(map1, bvs.fr, 300);
		a(map1, bvs.bV, 300);
		a(map1, bvs.fv, 300);
		a(map1, ada.y, 300);
		a(map1, bkk.kf, 300);
		a(map1, bkk.mi, 300);
		a(map1, bvs.cg, 300);
		a(map1, ada.T, 200);
		a(map1, bkk.kq, 200);
		a(map1, bkk.kp, 200);
		a(map1, bkk.kP, 200);
		a(map1, bkk.ks, 200);
		a(map1, bkk.kr, 200);
		a(map1, ada.g, 200);
		a(map1, ada.R, 1200);
		a(map1, ada.a, 100);
		a(map1, ada.d, 100);
		a(map1, bkk.kB, 100);
		a(map1, ada.n, 100);
		a(map1, bkk.kC, 100);
		a(map1, ada.f, 67);
		a(map1, bvs.ke, 4001);
		a(map1, bkk.qP, 300);
		a(map1, bvs.kY, 50);
		a(map1, bvs.aT, 100);
		a(map1, bvs.lQ, 400);
		a(map1, bvs.lR, 300);
		a(map1, bvs.lS, 300);
		a(map1, bvs.lV, 300);
		a(map1, bvs.lW, 300);
		a(map1, bvs.lZ, 300);
		a(map1, bvs.na, 300);
		return map1;
	}

	private static boolean b(bke bke) {
		return ada.P.a(bke);
	}

	private static void a(Map<bke, Integer> map, adf<bke> adf, int integer) {
		for (bke bke5 : adf.b()) {
			if (!b(bke5)) {
				map.put(bke5, integer);
			}
		}
	}

	private static void a(Map<bke, Integer> map, bqa bqa, int integer) {
		bke bke4 = bqa.h();
		if (b(bke4)) {
			if (u.d) {
				throw (IllegalStateException)v.c(
					new IllegalStateException("A developer tried to explicitly make fire resistant item " + bke4.h(null).getString() + " a furnace fuel. That will not work!")
				);
			}
		} else {
			map.put(bke4, integer);
		}
	}

	private boolean j() {
		return this.j > 0;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a = gi.a(this.ab_(), bki.b);
		ana.b(le, this.a);
		this.j = le.g("BurnTime");
		this.l = le.g("CookTime");
		this.m = le.g("CookTimeTotal");
		this.k = this.a(this.a.get(1));
		le le4 = le.p("RecipesUsed");

		for (String string6 : le4.d()) {
			this.n.put(new uh(string6), le4.h(string6));
		}
	}

	@Override
	public le a(le le) {
		super.a(le);
		le.a("BurnTime", (short)this.j);
		le.a("CookTime", (short)this.l);
		le.a("CookTimeTotal", (short)this.m);
		ana.a(le, this.a);
		le le3 = new le();
		this.n.forEach((uh, integer) -> le3.b(uh.toString(), integer));
		le.a("RecipesUsed", le3);
		return le;
	}

	@Override
	public void al_() {
		boolean boolean2 = this.j();
		boolean boolean3 = false;
		if (this.j()) {
			this.j--;
		}

		if (!this.d.v) {
			bki bki4 = this.a.get(1);
			if (this.j() || !bki4.a() && !this.a.get(0).a()) {
				bmu<?> bmu5 = (bmu<?>)this.d.o().a(this.c, this, this.d).orElse(null);
				if (!this.j() && this.b(bmu5)) {
					this.j = this.a(bki4);
					this.k = this.j;
					if (this.j()) {
						boolean3 = true;
						if (!bki4.a()) {
							bke bke6 = bki4.b();
							bki4.g(1);
							if (bki4.a()) {
								bke bke7 = bke6.o();
								this.a.set(1, bke7 == null ? bki.b : new bki(bke7));
							}
						}
					}
				}

				if (this.j() && this.b(bmu5)) {
					this.l++;
					if (this.l == this.m) {
						this.l = 0;
						this.m = this.h();
						this.c(bmu5);
						boolean3 = true;
					}
				} else {
					this.l = 0;
				}
			} else if (!this.j() && this.l > 0) {
				this.l = aec.a(this.l - 2, 0, this.m);
			}

			if (boolean2 != this.j()) {
				boolean3 = true;
				this.d.a(this.e, this.d.d_(this.e).a(bur.b, Boolean.valueOf(this.j())), 3);
			}
		}

		if (boolean3) {
			this.Z_();
		}
	}

	protected boolean b(@Nullable bmu<?> bmu) {
		if (!this.a.get(0).a() && bmu != null) {
			bki bki3 = bmu.c();
			if (bki3.a()) {
				return false;
			} else {
				bki bki4 = this.a.get(2);
				if (bki4.a()) {
					return true;
				} else if (!bki4.a(bki3)) {
					return false;
				} else {
					return bki4.E() < this.X_() && bki4.E() < bki4.c() ? true : bki4.E() < bki3.c();
				}
			}
		} else {
			return false;
		}
	}

	private void c(@Nullable bmu<?> bmu) {
		if (bmu != null && this.b(bmu)) {
			bki bki3 = this.a.get(0);
			bki bki4 = bmu.c();
			bki bki5 = this.a.get(2);
			if (bki5.a()) {
				this.a.set(2, bki4.i());
			} else if (bki5.b() == bki4.b()) {
				bki5.f(1);
			}

			if (!this.d.v) {
				this.a(bmu);
			}

			if (bki3.b() == bvs.ao.h() && !this.a.get(1).a() && this.a.get(1).b() == bkk.lK) {
				this.a.set(1, new bki(bkk.lL));
			}

			bki3.g(1);
		}
	}

	protected int a(bki bki) {
		if (bki.a()) {
			return 0;
		} else {
			bke bke3 = bki.b();
			return (Integer)f().getOrDefault(bke3, 0);
		}
	}

	protected int h() {
		return (Integer)this.d.o().a(this.c, this, this.d).map(bmg::e).orElse(200);
	}

	public static boolean b(bki bki) {
		return f().containsKey(bki.b());
	}

	@Override
	public int[] a(fz fz) {
		if (fz == fz.DOWN) {
			return h;
		} else {
			return fz == fz.UP ? g : i;
		}
	}

	@Override
	public boolean a(int integer, bki bki, @Nullable fz fz) {
		return this.b(integer, bki);
	}

	@Override
	public boolean b(int integer, bki bki, fz fz) {
		if (fz == fz.DOWN && integer == 1) {
			bke bke5 = bki.b();
			if (bke5 != bkk.lL && bke5 != bkk.lK) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int ab_() {
		return this.a.size();
	}

	@Override
	public boolean c() {
		for (bki bki3 : this.a) {
			if (!bki3.a()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public bki a(int integer) {
		return this.a.get(integer);
	}

	@Override
	public bki a(int integer1, int integer2) {
		return ana.a(this.a, integer1, integer2);
	}

	@Override
	public bki b(int integer) {
		return ana.a(this.a, integer);
	}

	@Override
	public void a(int integer, bki bki) {
		bki bki4 = this.a.get(integer);
		boolean boolean5 = !bki.a() && bki.a(bki4) && bki.a(bki, bki4);
		this.a.set(integer, bki);
		if (bki.E() > this.X_()) {
			bki.e(this.X_());
		}

		if (integer == 0 && !boolean5) {
			this.m = this.h();
			this.l = 0;
			this.Z_();
		}
	}

	@Override
	public boolean a(bec bec) {
		return this.d.c(this.e) != this ? false : bec.g((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5) <= 64.0;
	}

	@Override
	public boolean b(int integer, bki bki) {
		if (integer == 2) {
			return false;
		} else if (integer != 1) {
			return true;
		} else {
			bki bki4 = this.a.get(1);
			return b(bki) || bki.b() == bkk.lK && bki4.b() != bkk.lK;
		}
	}

	@Override
	public void aa_() {
		this.a.clear();
	}

	@Override
	public void a(@Nullable bmu<?> bmu) {
		if (bmu != null) {
			uh uh3 = bmu.f();
			this.n.addTo(uh3, 1);
		}
	}

	@Nullable
	@Override
	public bmu<?> am_() {
		return null;
	}

	@Override
	public void b(bec bec) {
	}

	public void d(bec bec) {
		List<bmu<?>> list3 = this.a(bec.l, bec.cz());
		bec.a(list3);
		this.n.clear();
	}

	public List<bmu<?>> a(bqb bqb, dem dem) {
		List<bmu<?>> list4 = Lists.<bmu<?>>newArrayList();

		for (Entry<uh> entry6 : this.n.object2IntEntrySet()) {
			bqb.o().a((uh)entry6.getKey()).ifPresent(bmu -> {
				list4.add(bmu);
				a(bqb, dem, entry6.getIntValue(), ((bmg)bmu).b());
			});
		}

		return list4;
	}

	private static void a(bqb bqb, dem dem, int integer, float float4) {
		int integer5 = aec.d((float)integer * float4);
		float float6 = aec.h((float)integer * float4);
		if (float6 != 0.0F && Math.random() < (double)float6) {
			integer5++;
		}

		while (integer5 > 0) {
			int integer7 = aos.a(integer5);
			integer5 -= integer7;
			bqb.c(new aos(bqb, dem.b, dem.c, dem.d, integer7));
		}
	}

	@Override
	public void a(bee bee) {
		for (bki bki4 : this.a) {
			bee.b(bki4);
		}
	}
}

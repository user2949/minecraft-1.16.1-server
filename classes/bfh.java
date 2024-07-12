import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class bfh {
	private static final ne a = new ne("event.minecraft.raid");
	private static final ne b = new ne("event.minecraft.raid.victory");
	private static final ne c = new ne("event.minecraft.raid.defeat");
	private static final mr d = a.e().c(" - ").a(b);
	private static final mr e = a.e().c(" - ").a(c);
	private final Map<Integer, bfi> f = Maps.<Integer, bfi>newHashMap();
	private final Map<Integer, Set<bfi>> g = Maps.<Integer, Set<bfi>>newHashMap();
	private final Set<UUID> h = Sets.<UUID>newHashSet();
	private long i;
	private fu j;
	private final zd k;
	private boolean l;
	private final int m;
	private float n;
	private int o;
	private boolean p;
	private int q;
	private final za r = new za(a, amw.a.RED, amw.b.NOTCHED_10);
	private int s;
	private int t;
	private final Random u = new Random();
	private final int v;
	private bfh.a w;
	private int x;
	private Optional<fu> y = Optional.empty();

	public bfh(int integer, zd zd, fu fu) {
		this.m = integer;
		this.k = zd;
		this.p = true;
		this.t = 300;
		this.r.a(0.0F);
		this.j = fu;
		this.v = this.a(zd.ac());
		this.w = bfh.a.ONGOING;
	}

	public bfh(zd zd, le le) {
		this.k = zd;
		this.m = le.h("Id");
		this.l = le.q("Started");
		this.p = le.q("Active");
		this.i = le.i("TicksActive");
		this.o = le.h("BadOmenLevel");
		this.q = le.h("GroupsSpawned");
		this.t = le.h("PreRaidTicks");
		this.s = le.h("PostRaidTicks");
		this.n = le.j("TotalHealth");
		this.j = new fu(le.h("CX"), le.h("CY"), le.h("CZ"));
		this.v = le.h("NumGroups");
		this.w = bfh.a.b(le.l("Status"));
		this.h.clear();
		if (le.c("HeroesOfTheVillage", 9)) {
			lk lk4 = le.d("HeroesOfTheVillage", 11);

			for (int integer5 = 0; integer5 < lk4.size(); integer5++) {
				this.h.add(lq.a(lk4.k(integer5)));
			}
		}
	}

	public boolean a() {
		return this.e() || this.f();
	}

	public boolean b() {
		return this.c() && this.r() == 0 && this.t > 0;
	}

	public boolean c() {
		return this.q > 0;
	}

	public boolean d() {
		return this.w == bfh.a.STOPPED;
	}

	public boolean e() {
		return this.w == bfh.a.VICTORY;
	}

	public boolean f() {
		return this.w == bfh.a.LOSS;
	}

	public bqb i() {
		return this.k;
	}

	public boolean j() {
		return this.l;
	}

	public int k() {
		return this.q;
	}

	private Predicate<ze> x() {
		return ze -> {
			fu fu3 = ze.cA();
			return ze.aU() && this.k.c_(fu3) == this;
		};
	}

	private void y() {
		Set<ze> set2 = Sets.<ze>newHashSet(this.r.h());
		List<ze> list3 = this.k.a(this.x());

		for (ze ze5 : list3) {
			if (!set2.contains(ze5)) {
				this.r.a(ze5);
			}
		}

		for (ze ze5x : set2) {
			if (!list3.contains(ze5x)) {
				this.r.b(ze5x);
			}
		}
	}

	public int l() {
		return 5;
	}

	public int m() {
		return this.o;
	}

	public void a(bec bec) {
		if (bec.a(aoi.E)) {
			this.o = this.o + bec.b(aoi.E).c() + 1;
			this.o = aec.a(this.o, 0, this.l());
		}

		bec.d(aoi.E);
	}

	public void n() {
		this.p = false;
		this.r.b();
		this.w = bfh.a.STOPPED;
	}

	public void o() {
		if (!this.d()) {
			if (this.w == bfh.a.ONGOING) {
				boolean boolean2 = this.p;
				this.p = this.k.C(this.j);
				if (this.k.ac() == and.PEACEFUL) {
					this.n();
					return;
				}

				if (boolean2 != this.p) {
					this.r.d(this.p);
				}

				if (!this.p) {
					return;
				}

				if (!this.k.b_(this.j)) {
					this.z();
				}

				if (!this.k.b_(this.j)) {
					if (this.q > 0) {
						this.w = bfh.a.LOSS;
					} else {
						this.n();
					}
				}

				this.i++;
				if (this.i >= 48000L) {
					this.n();
					return;
				}

				int integer3 = this.r();
				if (integer3 == 0 && this.A()) {
					if (this.t <= 0) {
						if (this.t == 0 && this.q > 0) {
							this.t = 300;
							this.r.a(a);
							return;
						}
					} else {
						boolean boolean4 = this.y.isPresent();
						boolean boolean5 = !boolean4 && this.t % 5 == 0;
						if (boolean4 && !this.k.i().a(new bph((fu)this.y.get()))) {
							boolean5 = true;
						}

						if (boolean5) {
							int integer6 = 0;
							if (this.t < 100) {
								integer6 = 1;
							} else if (this.t < 40) {
								integer6 = 2;
							}

							this.y = this.d(integer6);
						}

						if (this.t == 300 || this.t % 20 == 0) {
							this.y();
						}

						this.t--;
						this.r.a(aec.a((float)(300 - this.t) / 300.0F, 0.0F, 1.0F));
					}
				}

				if (this.i % 20L == 0L) {
					this.y();
					this.F();
					if (integer3 > 0) {
						if (integer3 <= 2) {
							this.r.a(a.e().c(" - ").a(new ne("event.minecraft.raid.raiders_remaining", integer3)));
						} else {
							this.r.a(a);
						}
					} else {
						this.r.a(a);
					}
				}

				boolean boolean4x = false;
				int integer5 = 0;

				while (this.G()) {
					fu fu6 = this.y.isPresent() ? (fu)this.y.get() : this.a(integer5, 20);
					if (fu6 != null) {
						this.l = true;
						this.b(fu6);
						if (!boolean4x) {
							this.a(fu6);
							boolean4x = true;
						}
					} else {
						integer5++;
					}

					if (integer5 > 3) {
						this.n();
						break;
					}
				}

				if (this.j() && !this.A() && integer3 == 0) {
					if (this.s < 40) {
						this.s++;
					} else {
						this.w = bfh.a.VICTORY;

						for (UUID uUID7 : this.h) {
							aom aom8 = this.k.a(uUID7);
							if (aom8 instanceof aoy && !aom8.a_()) {
								aoy aoy9 = (aoy)aom8;
								aoy9.c(new aog(aoi.F, 48000, this.o - 1, false, false, true));
								if (aoy9 instanceof ze) {
									ze ze10 = (ze)aoy9;
									ze10.a(acu.aA);
									aa.H.a(ze10);
								}
							}
						}
					}
				}

				this.H();
			} else if (this.a()) {
				this.x++;
				if (this.x >= 600) {
					this.n();
					return;
				}

				if (this.x % 20 == 0) {
					this.y();
					this.r.d(true);
					if (this.e()) {
						this.r.a(0.0F);
						this.r.a(d);
					} else {
						this.r.a(e);
					}
				}
			}
		}
	}

	private void z() {
		Stream<go> stream2 = go.a(go.a(this.j), 2);
		stream2.filter(this.k::a).map(go::q).min(Comparator.comparingDouble(fu -> fu.j(this.j))).ifPresent(this::c);
	}

	private Optional<fu> d(int integer) {
		for (int integer3 = 0; integer3 < 3; integer3++) {
			fu fu4 = this.a(integer, 1);
			if (fu4 != null) {
				return Optional.of(fu4);
			}
		}

		return Optional.empty();
	}

	private boolean A() {
		return this.C() ? !this.D() : !this.B();
	}

	private boolean B() {
		return this.k() == this.v;
	}

	private boolean C() {
		return this.o > 1;
	}

	private boolean D() {
		return this.k() > this.v;
	}

	private boolean E() {
		return this.B() && this.r() == 0 && this.C();
	}

	private void F() {
		Iterator<Set<bfi>> iterator2 = this.g.values().iterator();
		Set<bfi> set3 = Sets.<bfi>newHashSet();

		while (iterator2.hasNext()) {
			Set<bfi> set4 = (Set<bfi>)iterator2.next();

			for (bfi bfi6 : set4) {
				fu fu7 = bfi6.cA();
				if (bfi6.y || bfi6.l.W() != this.k.W() || this.j.j(fu7) >= 12544.0) {
					set3.add(bfi6);
				} else if (bfi6.K > 600) {
					if (this.k.a(bfi6.bR()) == null) {
						set3.add(bfi6);
					}

					if (!this.k.b_(fu7) && bfi6.dc() > 2400) {
						bfi6.b(bfi6.ff() + 1);
					}

					if (bfi6.ff() >= 30) {
						set3.add(bfi6);
					}
				}
			}
		}

		for (bfi bfi5 : set3) {
			this.a(bfi5, true);
		}
	}

	private void a(fu fu) {
		float float3 = 13.0F;
		int integer4 = 64;
		Collection<ze> collection5 = this.r.h();

		for (ze ze7 : this.k.w()) {
			dem dem8 = ze7.cz();
			dem dem9 = dem.a(fu);
			float float10 = aec.a((dem9.b - dem8.b) * (dem9.b - dem8.b) + (dem9.d - dem8.d) * (dem9.d - dem8.d));
			double double11 = dem8.b + (double)(13.0F / float10) * (dem9.b - dem8.b);
			double double13 = dem8.d + (double)(13.0F / float10) * (dem9.d - dem8.d);
			if (float10 <= 64.0F || collection5.contains(ze7)) {
				ze7.b.a(new qm(acl.lW, acm.NEUTRAL, double11, ze7.cD(), double13, 64.0F, 1.0F));
			}
		}
	}

	private void b(fu fu) {
		boolean boolean3 = false;
		int integer4 = this.q + 1;
		this.n = 0.0F;
		ane ane5 = this.k.d(fu);
		boolean boolean6 = this.E();

		for (bfh.b b10 : bfh.b.f) {
			int integer11 = this.a(b10, integer4, boolean6) + this.a(b10, this.u, integer4, ane5, boolean6);
			int integer12 = 0;

			for (int integer13 = 0; integer13 < integer11; integer13++) {
				bfi bfi14 = b10.g.a(this.k);
				if (!boolean3 && bfi14.eO()) {
					bfi14.t(true);
					this.a(integer4, bfi14);
					boolean3 = true;
				}

				this.a(integer4, bfi14, fu, false);
				if (b10.g == aoq.ao) {
					bfi bfi15 = null;
					if (integer4 == this.a(and.NORMAL)) {
						bfi15 = aoq.aj.a(this.k);
					} else if (integer4 >= this.a(and.HARD)) {
						if (integer12 == 0) {
							bfi15 = aoq.w.a(this.k);
						} else {
							bfi15 = aoq.aP.a(this.k);
						}
					}

					integer12++;
					if (bfi15 != null) {
						this.a(integer4, bfi15, fu, false);
						bfi15.a(fu, 0.0F, 0.0F);
						bfi15.m(bfi14);
					}
				}
			}
		}

		this.y = Optional.empty();
		this.q++;
		this.p();
		this.H();
	}

	public void a(int integer, bfi bfi, @Nullable fu fu, boolean boolean4) {
		boolean boolean6 = this.b(integer, bfi);
		if (boolean6) {
			bfi.a(this);
			bfi.a(integer);
			bfi.w(true);
			bfi.b(0);
			if (!boolean4 && fu != null) {
				bfi.d((double)fu.u() + 0.5, (double)fu.v() + 1.0, (double)fu.w() + 0.5);
				bfi.a(this.k, this.k.d(fu), apb.EVENT, null, null);
				bfi.a(integer, false);
				bfi.c(true);
				this.k.c(bfi);
			}
		}
	}

	public void p() {
		this.r.a(aec.a(this.q() / this.n, 0.0F, 1.0F));
	}

	public float q() {
		float float2 = 0.0F;

		for (Set<bfi> set4 : this.g.values()) {
			for (bfi bfi6 : set4) {
				float2 += bfi6.dj();
			}
		}

		return float2;
	}

	private boolean G() {
		return this.t == 0 && (this.q < this.v || this.E()) && this.r() == 0;
	}

	public int r() {
		return this.g.values().stream().mapToInt(Set::size).sum();
	}

	public void a(bfi bfi, boolean boolean2) {
		Set<bfi> set4 = (Set<bfi>)this.g.get(bfi.fd());
		if (set4 != null) {
			boolean boolean5 = set4.remove(bfi);
			if (boolean5) {
				if (boolean2) {
					this.n = this.n - bfi.dj();
				}

				bfi.a(null);
				this.p();
				this.H();
			}
		}
	}

	private void H() {
		this.k.y().b();
	}

	public static bki s() {
		bki bki1 = new bki(bkk.pL);
		le le2 = bki1.a("BlockEntityTag");
		lk lk3 = new cdd.a()
			.a(cdd.RHOMBUS_MIDDLE, bje.CYAN)
			.a(cdd.STRIPE_BOTTOM, bje.LIGHT_GRAY)
			.a(cdd.STRIPE_CENTER, bje.GRAY)
			.a(cdd.BORDER, bje.LIGHT_GRAY)
			.a(cdd.STRIPE_MIDDLE, bje.BLACK)
			.a(cdd.HALF_HORIZONTAL, bje.LIGHT_GRAY)
			.a(cdd.CIRCLE_MIDDLE, bje.LIGHT_GRAY)
			.a(cdd.BORDER, bje.BLACK)
			.a();
		le2.a("Patterns", lk3);
		bki1.p().b("HideFlags", 32);
		bki1.a(new ne("block.minecraft.ominous_banner").a(i.GOLD));
		return bki1;
	}

	@Nullable
	public bfi b(int integer) {
		return (bfi)this.f.get(integer);
	}

	@Nullable
	private fu a(int integer1, int integer2) {
		int integer4 = integer1 == 0 ? 2 : 2 - integer1;
		fu.a a8 = new fu.a();

		for (int integer9 = 0; integer9 < integer2; integer9++) {
			float float10 = this.k.t.nextFloat() * (float) (Math.PI * 2);
			int integer5 = this.j.u() + aec.d(aec.b(float10) * 32.0F * (float)integer4) + this.k.t.nextInt(5);
			int integer7 = this.j.w() + aec.d(aec.a(float10) * 32.0F * (float)integer4) + this.k.t.nextInt(5);
			int integer6 = this.k.a(cio.a.WORLD_SURFACE, integer5, integer7);
			a8.d(integer5, integer6, integer7);
			if ((!this.k.b_(a8) || integer1 >= 2)
				&& this.k.a(a8.u() - 10, a8.v() - 10, a8.w() - 10, a8.u() + 10, a8.v() + 10, a8.w() + 10)
				&& this.k.i().a(new bph(a8))
				&& (bqj.a(app.c.ON_GROUND, this.k, a8, aoq.ao) || this.k.d_(a8.c()).a(bvs.cC) && this.k.d_(a8).g())) {
				return a8;
			}
		}

		return null;
	}

	private boolean b(int integer, bfi bfi) {
		return this.a(integer, bfi, true);
	}

	public boolean a(int integer, bfi bfi, boolean boolean3) {
		this.g.computeIfAbsent(integer, integerx -> Sets.newHashSet());
		Set<bfi> set5 = (Set<bfi>)this.g.get(integer);
		bfi bfi6 = null;

		for (bfi bfi8 : set5) {
			if (bfi8.bR().equals(bfi.bR())) {
				bfi6 = bfi8;
				break;
			}
		}

		if (bfi6 != null) {
			set5.remove(bfi6);
			set5.add(bfi);
		}

		set5.add(bfi);
		if (boolean3) {
			this.n = this.n + bfi.dj();
		}

		this.p();
		this.H();
		return true;
	}

	public void a(int integer, bfi bfi) {
		this.f.put(integer, bfi);
		bfi.a(aor.HEAD, s());
		bfi.a(aor.HEAD, 2.0F);
	}

	public void c(int integer) {
		this.f.remove(integer);
	}

	public fu t() {
		return this.j;
	}

	private void c(fu fu) {
		this.j = fu;
	}

	public int u() {
		return this.m;
	}

	private int a(bfh.b b, int integer, boolean boolean3) {
		return boolean3 ? b.h[this.v] : b.h[integer];
	}

	private int a(bfh.b b, Random random, int integer, ane ane, boolean boolean5) {
		and and7 = ane.a();
		boolean boolean8 = and7 == and.EASY;
		boolean boolean9 = and7 == and.NORMAL;
		int integer10;
		switch (b) {
			case WITCH:
				if (boolean8 || integer <= 2 || integer == 4) {
					return 0;
				}

				integer10 = 1;
				break;
			case PILLAGER:
			case VINDICATOR:
				if (boolean8) {
					integer10 = random.nextInt(2);
				} else if (boolean9) {
					integer10 = 1;
				} else {
					integer10 = 2;
				}
				break;
			case RAVAGER:
				integer10 = !boolean8 && boolean5 ? 1 : 0;
				break;
			default:
				return 0;
		}

		return integer10 > 0 ? random.nextInt(integer10 + 1) : 0;
	}

	public boolean v() {
		return this.p;
	}

	public le a(le le) {
		le.b("Id", this.m);
		le.a("Started", this.l);
		le.a("Active", this.p);
		le.a("TicksActive", this.i);
		le.b("BadOmenLevel", this.o);
		le.b("GroupsSpawned", this.q);
		le.b("PreRaidTicks", this.t);
		le.b("PostRaidTicks", this.s);
		le.a("TotalHealth", this.n);
		le.b("NumGroups", this.v);
		le.a("Status", this.w.a());
		le.b("CX", this.j.u());
		le.b("CY", this.j.v());
		le.b("CZ", this.j.w());
		lk lk3 = new lk();

		for (UUID uUID5 : this.h) {
			lk3.add(lq.a(uUID5));
		}

		le.a("HeroesOfTheVillage", lk3);
		return le;
	}

	public int a(and and) {
		switch (and) {
			case EASY:
				return 3;
			case NORMAL:
				return 5;
			case HARD:
				return 7;
			default:
				return 0;
		}
	}

	public float w() {
		int integer2 = this.m();
		if (integer2 == 2) {
			return 0.1F;
		} else if (integer2 == 3) {
			return 0.25F;
		} else if (integer2 == 4) {
			return 0.5F;
		} else {
			return integer2 == 5 ? 0.75F : 0.0F;
		}
	}

	public void a(aom aom) {
		this.h.add(aom.bR());
	}

	static enum a {
		ONGOING,
		VICTORY,
		LOSS,
		STOPPED;

		private static final bfh.a[] e = values();

		private static bfh.a b(String string) {
			for (bfh.a a5 : e) {
				if (string.equalsIgnoreCase(a5.name())) {
					return a5;
				}
			}

			return ONGOING;
		}

		public String a() {
			return this.name().toLowerCase(Locale.ROOT);
		}
	}

	static enum b {
		VINDICATOR(aoq.aP, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
		EVOKER(aoq.w, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
		PILLAGER(aoq.aj, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
		WITCH(aoq.aR, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
		RAVAGER(aoq.ao, new int[]{0, 0, 0, 1, 0, 1, 0, 2});

		private static final bfh.b[] f = values();
		private final aoq<? extends bfi> g;
		private final int[] h;

		private b(aoq<? extends bfi> aoq, int[] arr) {
			this.g = aoq;
			this.h = arr;
		}
	}
}

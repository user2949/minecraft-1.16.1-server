import bre.f;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zd extends bqb implements bqu {
	public static final fu a = new fu(100, 50, 0);
	private static final Logger x = LogManager.getLogger();
	private final Int2ObjectMap<aom> y = new Int2ObjectLinkedOpenHashMap<>();
	private final Map<UUID, aom> z = Maps.<UUID, aom>newHashMap();
	private final Queue<aom> A = Queues.<aom>newArrayDeque();
	private final List<ze> B = Lists.<ze>newArrayList();
	private final zb C;
	boolean b;
	private final MinecraftServer D;
	private final dak E;
	public boolean c;
	private boolean F;
	private int G;
	private final bqm H;
	private final bqo<bvr> I = new bqo<>(this, bvr -> bvr == null || bvr.n().g(), gl.aj::b, this::b);
	private final bqo<cwz> J = new bqo<>(this, cwz -> cwz == null || cwz == cxb.a, gl.ah::b, this::a);
	private final Set<awv> K = Sets.<awv>newHashSet();
	protected final bfj d;
	private final ObjectLinkedOpenHashSet<bpf> L = new ObjectLinkedOpenHashSet<>();
	private boolean M;
	private final List<bpm> N;
	@Nullable
	private final cii O;
	private final bqq P;
	private final boolean Q;

	public zd(
		MinecraftServer minecraftServer,
		Executor executor,
		dae.a a,
		dak dak,
		ug<bqb> ug5,
		ug<cif> ug6,
		cif cif,
		zm zm,
		cha cha,
		boolean boolean10,
		long long11,
		List<bpm> list,
		boolean boolean13
	) {
		super(dak, ug5, ug6, cif, minecraftServer::aO, false, boolean10, long11);
		this.Q = boolean13;
		this.D = minecraftServer;
		this.N = list;
		this.E = dak;
		this.C = new zb(
			this, a, minecraftServer.ax(), minecraftServer.aU(), executor, cha, minecraftServer.ac().p(), minecraftServer.aT(), zm, () -> minecraftServer.D().s()
		);
		this.H = new bqm(this);
		this.N();
		this.O();
		this.f().a(minecraftServer.as());
		this.d = this.s().a(() -> new bfj(this), bfj.a(this.m()));
		if (!minecraftServer.N()) {
			dak.a(minecraftServer.r());
		}

		this.P = new bqq(this, minecraftServer.aV().z());
		if (this.m().n()) {
			this.O = new cii(this, minecraftServer.aV().z().b(), minecraftServer.aV().B());
		} else {
			this.O = null;
		}
	}

	public void a(int integer1, int integer2, boolean boolean3, boolean boolean4) {
		this.E.a(integer1);
		this.E.f(integer2);
		this.E.e(integer2);
		this.E.b(boolean3);
		this.E.a(boolean4);
	}

	@Override
	public bre a(int integer1, int integer2, int integer3) {
		return this.i().g().d().b(integer1, integer2, integer3);
	}

	public bqq a() {
		return this.P;
	}

	public void a(BooleanSupplier booleanSupplier) {
		ami ami3 = this.X();
		this.M = true;
		ami3.a("world border");
		this.f().s();
		ami3.b("weather");
		boolean boolean4 = this.U();
		if (this.m().d()) {
			if (this.S().b(bpx.t)) {
				int integer5 = this.E.g();
				int integer6 = this.E.i();
				int integer7 = this.E.k();
				boolean boolean8 = this.u.h();
				boolean boolean9 = this.u.j();
				if (integer5 > 0) {
					integer5--;
					integer6 = boolean8 ? 0 : 1;
					integer7 = boolean9 ? 0 : 1;
					boolean8 = false;
					boolean9 = false;
				} else {
					if (integer6 > 0) {
						if (--integer6 == 0) {
							boolean8 = !boolean8;
						}
					} else if (boolean8) {
						integer6 = this.t.nextInt(12000) + 3600;
					} else {
						integer6 = this.t.nextInt(168000) + 12000;
					}

					if (integer7 > 0) {
						if (--integer7 == 0) {
							boolean9 = !boolean9;
						}
					} else if (boolean9) {
						integer7 = this.t.nextInt(12000) + 12000;
					} else {
						integer7 = this.t.nextInt(168000) + 12000;
					}
				}

				this.E.e(integer6);
				this.E.f(integer7);
				this.E.a(integer5);
				this.E.a(boolean8);
				this.E.b(boolean9);
			}

			this.r = this.s;
			if (this.u.h()) {
				this.s = (float)((double)this.s + 0.01);
			} else {
				this.s = (float)((double)this.s - 0.01);
			}

			this.s = aec.a(this.s, 0.0F, 1.0F);
			this.p = this.q;
			if (this.u.j()) {
				this.q = (float)((double)this.q + 0.01);
			} else {
				this.q = (float)((double)this.q - 0.01);
			}

			this.q = aec.a(this.q, 0.0F, 1.0F);
		}

		if (this.p != this.q) {
			this.D.ac().a(new oq(oq.h, this.q), this.W());
		}

		if (this.r != this.s) {
			this.D.ac().a(new oq(oq.i, this.s), this.W());
		}

		if (boolean4 != this.U()) {
			if (boolean4) {
				this.D.ac().a(new oq(oq.c, 0.0F));
			} else {
				this.D.ac().a(new oq(oq.b, 0.0F));
			}

			this.D.ac().a(new oq(oq.h, this.q));
			this.D.ac().a(new oq(oq.i, this.s));
		}

		if (this.F && this.B.stream().noneMatch(ze -> !ze.a_() && !ze.eA())) {
			this.F = false;
			if (this.S().b(bpx.j)) {
				long long5 = this.u.e() + 24000L;
				this.a(long5 - long5 % 24000L);
			}

			this.ae();
			if (this.S().b(bpx.t)) {
				this.af();
			}
		}

		this.N();
		this.b();
		ami3.b("chunkSource");
		this.i().a(booleanSupplier);
		ami3.b("tickPending");
		if (!this.Z()) {
			this.I.b();
			this.J.b();
		}

		ami3.b("raid");
		this.d.a();
		ami3.b("blockEvents");
		this.ah();
		this.M = false;
		ami3.b("entities");
		boolean boolean5 = !this.B.isEmpty() || !this.v().isEmpty();
		if (boolean5) {
			this.p_();
		}

		if (boolean5 || this.G++ < 300) {
			if (this.O != null) {
				this.O.b();
			}

			this.b = true;
			ObjectIterator<Entry<aom>> objectIterator6 = this.y.int2ObjectEntrySet().iterator();

			while (objectIterator6.hasNext()) {
				Entry<aom> entry7 = (Entry<aom>)objectIterator6.next();
				aom aom8 = (aom)entry7.getValue();
				aom aom9 = aom8.cs();
				if (!this.D.V() && (aom8 instanceof ayk || aom8 instanceof azj)) {
					aom8.aa();
				}

				if (!this.D.W() && aom8 instanceof bdo) {
					aom8.aa();
				}

				ami3.a("checkDespawn");
				if (!aom8.y) {
					aom8.cH();
				}

				ami3.c();
				if (aom9 != null) {
					if (!aom9.y && aom9.w(aom8)) {
						continue;
					}

					aom8.l();
				}

				ami3.a("tick");
				if (!aom8.y && !(aom8 instanceof baa)) {
					this.a(this::a, aom8);
				}

				ami3.c();
				ami3.a("remove");
				if (aom8.y) {
					this.n(aom8);
					objectIterator6.remove();
					this.g(aom8);
				}

				ami3.c();
			}

			this.b = false;

			aom aom7;
			while ((aom7 = (aom)this.A.poll()) != null) {
				this.m(aom7);
			}

			this.L();
		}

		ami3.c();
	}

	protected void b() {
		if (this.Q) {
			long long2 = this.u.d() + 1L;
			this.E.a(long2);
			this.E.t().a(this.D, long2);
			if (this.u.p().b(bpx.j)) {
				this.a(this.u.e() + 1L);
			}
		}
	}

	public void a(long long1) {
		this.E.b(long1);
	}

	public void a(boolean boolean1, boolean boolean2) {
		for (bpm bpm5 : this.N) {
			bpm5.a(this, boolean1, boolean2);
		}
	}

	private void ae() {
		((List)this.B.stream().filter(aoy::el).collect(Collectors.toList())).forEach(ze -> ze.a(false, false));
	}

	public void a(chj chj, int integer) {
		bph bph4 = chj.g();
		boolean boolean5 = this.U();
		int integer6 = bph4.d();
		int integer7 = bph4.e();
		ami ami8 = this.X();
		ami8.a("thunder");
		if (boolean5 && this.T() && this.t.nextInt(100000) == 0) {
			fu fu9 = this.a(this.a(integer6, 0, integer7, 15));
			if (this.t(fu9)) {
				ane ane10 = this.d(fu9);
				boolean boolean11 = this.S().b(bpx.d) && this.t.nextDouble() < (double)ane10.b() * 0.01;
				if (boolean11) {
					azs azs12 = aoq.av.a(this);
					azs12.t(true);
					azs12.c_(0);
					azs12.d((double)fu9.u(), (double)fu9.v(), (double)fu9.w());
					this.c(azs12);
				}

				aox aox12 = aoq.P.a(this);
				aox12.c(dem.c(fu9));
				aox12.a(boolean11);
				this.c(aox12);
			}
		}

		ami8.b("iceandsnow");
		if (this.t.nextInt(16) == 0) {
			fu fu9 = this.a(cio.a.MOTION_BLOCKING, this.a(integer6, 0, integer7, 15));
			fu fu10 = fu9.c();
			bre bre11 = this.v(fu9);
			if (bre11.a(this, fu10)) {
				this.a(fu10, bvs.cD.n());
			}

			if (boolean5 && bre11.b(this, fu9)) {
				this.a(fu9, bvs.cC.n());
			}

			if (boolean5 && this.v(fu10).d() == bre.f.RAIN) {
				this.d_(fu10).b().c((bqb)this, fu10);
			}
		}

		ami8.b("tickBlocks");
		if (integer > 0) {
			for (chk chk12 : chj.d()) {
				if (chk12 != chj.a && chk12.d()) {
					int integer13 = chk12.g();

					for (int integer14 = 0; integer14 < integer; integer14++) {
						fu fu15 = this.a(integer6, integer13, integer7, 15);
						ami8.a("randomTick");
						cfj cfj16 = chk12.a(fu15.u() - integer6, fu15.v() - integer13, fu15.w() - integer7);
						if (cfj16.n()) {
							cfj16.b(this, fu15, this.t);
						}

						cxa cxa17 = cfj16.m();
						if (cxa17.f()) {
							cxa17.b(this, fu15, this.t);
						}

						ami8.c();
					}
				}
			}
		}

		ami8.c();
	}

	protected fu a(fu fu) {
		fu fu3 = this.a(cio.a.MOTION_BLOCKING, fu);
		deg deg4 = new deg(fu3, new fu(fu3.u(), this.I(), fu3.w())).g(3.0);
		List<aoy> list5 = this.a(aoy.class, deg4, aoy -> aoy != null && aoy.aU() && this.f(aoy.cA()));
		if (!list5.isEmpty()) {
			return ((aoy)list5.get(this.t.nextInt(list5.size()))).cA();
		} else {
			if (fu3.v() == -1) {
				fu3 = fu3.b(2);
			}

			return fu3;
		}
	}

	public boolean m_() {
		return this.M;
	}

	public void n_() {
		this.F = false;
		if (!this.B.isEmpty()) {
			int integer2 = 0;
			int integer3 = 0;

			for (ze ze5 : this.B) {
				if (ze5.a_()) {
					integer2++;
				} else if (ze5.el()) {
					integer3++;
				}
			}

			this.F = integer3 > 0 && integer3 >= this.B.size() - integer2;
		}
	}

	public ux D() {
		return this.D.aF();
	}

	private void af() {
		this.E.f(0);
		this.E.b(false);
		this.E.e(0);
		this.E.a(false);
	}

	public void p_() {
		this.G = 0;
	}

	private void a(bqs<cwz> bqs) {
		cxa cxa3 = this.b(bqs.a);
		if (cxa3.a() == bqs.b()) {
			cxa3.a((bqb)this, bqs.a);
		}
	}

	private void b(bqs<bvr> bqs) {
		cfj cfj3 = this.d_(bqs.a);
		if (cfj3.a(bqs.b())) {
			cfj3.a(this, bqs.a, this.t);
		}
	}

	public void a(aom aom) {
		if (!(aom instanceof bec) && !this.i().a(aom)) {
			this.b(aom);
		} else {
			aom.f(aom.cC(), aom.cD(), aom.cG());
			aom.r = aom.p;
			aom.s = aom.q;
			if (aom.V) {
				aom.K++;
				ami ami3 = this.X();
				ami3.a((Supplier<String>)(() -> gl.al.b(aom.U()).toString()));
				ami3.c("tickNonPassenger");
				aom.j();
				ami3.c();
			}

			this.b(aom);
			if (aom.V) {
				for (aom aom4 : aom.cm()) {
					this.a(aom, aom4);
				}
			}
		}
	}

	public void a(aom aom1, aom aom2) {
		if (aom2.y || aom2.cs() != aom1) {
			aom2.l();
		} else if (aom2 instanceof bec || this.i().a(aom2)) {
			aom2.f(aom2.cC(), aom2.cD(), aom2.cG());
			aom2.r = aom2.p;
			aom2.s = aom2.q;
			if (aom2.V) {
				aom2.K++;
				ami ami4 = this.X();
				ami4.a((Supplier<String>)(() -> gl.al.b(aom2.U()).toString()));
				ami4.c("tickPassenger");
				aom2.aW();
				ami4.c();
			}

			this.b(aom2);
			if (aom2.V) {
				for (aom aom5 : aom2.cm()) {
					this.a(aom2, aom5);
				}
			}
		}
	}

	public void b(aom aom) {
		if (aom.ck()) {
			this.X().a("chunkCheck");
			int integer3 = aec.c(aom.cC() / 16.0);
			int integer4 = aec.c(aom.cD() / 16.0);
			int integer5 = aec.c(aom.cG() / 16.0);
			if (!aom.V || aom.W != integer3 || aom.X != integer4 || aom.Y != integer5) {
				if (aom.V && this.b(aom.W, aom.Y)) {
					this.d(aom.W, aom.Y).a(aom, aom.X);
				}

				if (!aom.cj() && !this.b(integer3, integer5)) {
					if (aom.V) {
						x.warn("Entity {} left loaded chunk area", aom);
					}

					aom.V = false;
				} else {
					this.d(integer3, integer5).a(aom);
				}
			}

			this.X().c();
		}
	}

	@Override
	public boolean a(bec bec, fu fu) {
		return !this.D.a(this, fu, bec) && this.f().a(fu);
	}

	public void a(@Nullable aed aed, boolean boolean2, boolean boolean3) {
		zb zb5 = this.i();
		if (!boolean3) {
			if (aed != null) {
				aed.a(new ne("menu.savingLevel"));
			}

			this.ag();
			if (aed != null) {
				aed.c(new ne("menu.savingChunks"));
			}

			zb5.a(boolean2);
		}
	}

	private void ag() {
		if (this.O != null) {
			this.D.aV().a(this.O.a());
		}

		this.i().i().a();
	}

	public List<aom> a(@Nullable aoq<?> aoq, Predicate<? super aom> predicate) {
		List<aom> list4 = Lists.<aom>newArrayList();
		zb zb5 = this.i();

		for (aom aom7 : this.y.values()) {
			if ((aoq == null || aom7.U() == aoq) && zb5.b(aec.c(aom7.cC()) >> 4, aec.c(aom7.cG()) >> 4) && predicate.test(aom7)) {
				list4.add(aom7);
			}
		}

		return list4;
	}

	public List<bac> g() {
		List<bac> list2 = Lists.<bac>newArrayList();

		for (aom aom4 : this.y.values()) {
			if (aom4 instanceof bac && aom4.aU()) {
				list2.add((bac)aom4);
			}
		}

		return list2;
	}

	public List<ze> a(Predicate<? super ze> predicate) {
		List<ze> list3 = Lists.<ze>newArrayList();

		for (ze ze5 : this.B) {
			if (predicate.test(ze5)) {
				list3.add(ze5);
			}
		}

		return list3;
	}

	@Nullable
	public ze h() {
		List<ze> list2 = this.a(aoy::aU);
		return list2.isEmpty() ? null : (ze)list2.get(this.t.nextInt(list2.size()));
	}

	@Override
	public boolean c(aom aom) {
		return this.k(aom);
	}

	public boolean d(aom aom) {
		return this.k(aom);
	}

	public void e(aom aom) {
		boolean boolean3 = aom.k;
		aom.k = true;
		this.d(aom);
		aom.k = boolean3;
		this.b(aom);
	}

	public void a(ze ze) {
		this.f(ze);
		this.b((aom)ze);
	}

	public void b(ze ze) {
		this.f(ze);
		this.b((aom)ze);
	}

	public void c(ze ze) {
		this.f(ze);
	}

	public void d(ze ze) {
		this.f(ze);
	}

	private void f(ze ze) {
		aom aom3 = (aom)this.z.get(ze.bR());
		if (aom3 != null) {
			x.warn("Force-added player with duplicate UUID {}", ze.bR().toString());
			aom3.T();
			this.e((ze)aom3);
		}

		this.B.add(ze);
		this.n_();
		cgy cgy4 = this.a(aec.c(ze.cC() / 16.0), aec.c(ze.cG() / 16.0), chc.m, true);
		if (cgy4 instanceof chj) {
			cgy4.a(ze);
		}

		this.m(ze);
	}

	private boolean k(aom aom) {
		if (aom.y) {
			x.warn("Tried to add entity {} but it was marked as removed already", aoq.a(aom.U()));
			return false;
		} else if (this.l(aom)) {
			return false;
		} else {
			cgy cgy3 = this.a(aec.c(aom.cC() / 16.0), aec.c(aom.cG() / 16.0), chc.m, aom.k);
			if (!(cgy3 instanceof chj)) {
				return false;
			} else {
				cgy3.a(aom);
				this.m(aom);
				return true;
			}
		}
	}

	public boolean f(aom aom) {
		if (this.l(aom)) {
			return false;
		} else {
			this.m(aom);
			return true;
		}
	}

	private boolean l(aom aom) {
		aom aom3 = (aom)this.z.get(aom.bR());
		if (aom3 == null) {
			return false;
		} else {
			x.warn("Keeping entity {} that already exists with UUID {}", aoq.a(aom3.U()), aom.bR().toString());
			return true;
		}
	}

	public void a(chj chj) {
		this.m.addAll(chj.y().values());
		adk[] var2 = chj.z();
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; var4++) {
			for (aom aom8 : var2[var4]) {
				if (!(aom8 instanceof ze)) {
					if (this.b) {
						throw (IllegalStateException)v.c(new IllegalStateException("Removing entity while ticking!"));
					}

					this.y.remove(aom8.V());
					this.g(aom8);
				}
			}
		}
	}

	public void g(aom aom) {
		if (aom instanceof bac) {
			for (baa baa6 : ((bac)aom).eK()) {
				baa6.aa();
			}
		}

		this.z.remove(aom.bR());
		this.i().b(aom);
		if (aom instanceof ze) {
			ze ze3 = (ze)aom;
			this.B.remove(ze3);
		}

		this.o_().a(aom);
		if (aom instanceof aoz) {
			this.K.remove(((aoz)aom).x());
		}
	}

	private void m(aom aom) {
		if (this.b) {
			this.A.add(aom);
		} else {
			this.y.put(aom.V(), aom);
			if (aom instanceof bac) {
				for (baa baa6 : ((bac)aom).eK()) {
					this.y.put(baa6.V(), baa6);
				}
			}

			this.z.put(aom.bR(), aom);
			this.i().c(aom);
			if (aom instanceof aoz) {
				this.K.add(((aoz)aom).x());
			}
		}
	}

	public void h(aom aom) {
		if (this.b) {
			throw (IllegalStateException)v.c(new IllegalStateException("Removing entity while ticking!"));
		} else {
			this.n(aom);
			this.y.remove(aom.V());
			this.g(aom);
		}
	}

	private void n(aom aom) {
		cgy cgy3 = this.a(aom.W, aom.Y, chc.m, false);
		if (cgy3 instanceof chj) {
			((chj)cgy3).b(aom);
		}
	}

	public void e(ze ze) {
		ze.aa();
		this.h((aom)ze);
		this.n_();
	}

	@Override
	public void a(int integer1, fu fu, int integer3) {
		for (ze ze6 : this.D.ac().s()) {
			if (ze6 != null && ze6.l == this && ze6.V() != integer1) {
				double double7 = (double)fu.u() - ze6.cC();
				double double9 = (double)fu.v() - ze6.cD();
				double double11 = (double)fu.w() - ze6.cG();
				if (double7 * double7 + double9 * double9 + double11 * double11 < 1024.0) {
					ze6.b.a(new nu(integer1, fu, integer3));
				}
			}
		}
	}

	@Override
	public void a(@Nullable bec bec, double double2, double double3, double double4, ack ack, acm acm, float float7, float float8) {
		this.D
			.ac()
			.a(bec, double2, double3, double4, float7 > 1.0F ? (double)(16.0F * float7) : 16.0, this.W(), new qm(ack, acm, double2, double3, double4, float7, float8));
	}

	@Override
	public void a(@Nullable bec bec, aom aom, ack ack, acm acm, float float5, float float6) {
		this.D.ac().a(bec, aom.cC(), aom.cD(), aom.cG(), float5 > 1.0F ? (double)(16.0F * float5) : 16.0, this.W(), new ql(ack, acm, aom, float5, float6));
	}

	@Override
	public void b(int integer1, fu fu, int integer3) {
		this.D.ac().a(new ou(integer1, fu, integer3, true));
	}

	@Override
	public void a(@Nullable bec bec, int integer2, fu fu, int integer4) {
		this.D.ac().a(bec, (double)fu.u(), (double)fu.v(), (double)fu.w(), 64.0, this.W(), new ou(integer2, fu, integer4, false));
	}

	@Override
	public void a(fu fu, cfj cfj2, cfj cfj3, int integer) {
		this.i().b(fu);
		dfg dfg6 = cfj2.k(this, fu);
		dfg dfg7 = cfj3.k(this, fu);
		if (dfd.c(dfg6, dfg7, deq.g)) {
			for (awv awv9 : this.K) {
				if (!awv9.i()) {
					awv9.b(fu);
				}
			}
		}
	}

	@Override
	public void a(aom aom, byte byte2) {
		this.i().a(aom, new on(aom, byte2));
	}

	public zb E() {
		return this.C;
	}

	@Override
	public bpt a(@Nullable aom aom, @Nullable anw anw, @Nullable bpu bpu, double double4, double double5, double double6, float float7, boolean boolean8, bpt.a a) {
		bpt bpt14 = new bpt(this, aom, anw, bpu, double4, double5, double6, float7, boolean8, a);
		bpt14.a();
		bpt14.a(false);
		if (a == bpt.a.NONE) {
			bpt14.e();
		}

		for (ze ze16 : this.B) {
			if (ze16.g(double4, double5, double6) < 4096.0) {
				ze16.b.a(new oo(double4, double5, double6, float7, bpt14.f(), (dem)bpt14.c().get(ze16)));
			}
		}

		return bpt14;
	}

	@Override
	public void a(fu fu, bvr bvr, int integer3, int integer4) {
		this.L.add(new bpf(fu, bvr, integer3, integer4));
	}

	private void ah() {
		while (!this.L.isEmpty()) {
			bpf bpf2 = this.L.removeFirst();
			if (this.a(bpf2)) {
				this.D.ac().a(null, (double)bpf2.a().u(), (double)bpf2.a().v(), (double)bpf2.a().w(), 64.0, this.W(), new nw(bpf2.a(), bpf2.b(), bpf2.c(), bpf2.d()));
			}
		}
	}

	private boolean a(bpf bpf) {
		cfj cfj3 = this.d_(bpf.a());
		return cfj3.a(bpf.b()) ? cfj3.a(this, bpf.a(), bpf.c(), bpf.d()) : false;
	}

	public bqo<bvr> G() {
		return this.I;
	}

	public bqo<cwz> F() {
		return this.J;
	}

	@Nonnull
	@Override
	public MinecraftServer l() {
		return this.D;
	}

	public bqm q_() {
		return this.H;
	}

	public cva r_() {
		return this.D.aU();
	}

	public <T extends hf> int a(T hf, double double2, double double3, double double4, int integer, double double6, double double7, double double8, double double9) {
		ov ov18 = new ov(hf, false, double2, double3, double4, (float)double6, (float)double7, (float)double8, (float)double9, integer);
		int integer19 = 0;

		for (int integer20 = 0; integer20 < this.B.size(); integer20++) {
			ze ze21 = (ze)this.B.get(integer20);
			if (this.a(ze21, false, double2, double3, double4, ov18)) {
				integer19++;
			}
		}

		return integer19;
	}

	public <T extends hf> boolean a(
		ze ze, T hf, boolean boolean3, double double4, double double5, double double6, int integer, double double8, double double9, double double10, double double11
	) {
		ni<?> ni20 = new ov(hf, boolean3, double4, double5, double6, (float)double8, (float)double9, (float)double10, (float)double11, integer);
		return this.a(ze, boolean3, double4, double5, double6, ni20);
	}

	private boolean a(ze ze, boolean boolean2, double double3, double double4, double double5, ni<?> ni) {
		if (ze.u() != this) {
			return false;
		} else {
			fu fu11 = ze.cA();
			if (fu11.a(new dem(double3, double4, double5), boolean2 ? 512.0 : 32.0)) {
				ze.b.a(ni);
				return true;
			} else {
				return false;
			}
		}
	}

	@Nullable
	@Override
	public aom a(int integer) {
		return this.y.get(integer);
	}

	@Nullable
	public aom a(UUID uUID) {
		return (aom)this.z.get(uUID);
	}

	@Nullable
	public fu a(cml<?> cml, fu fu, int integer, boolean boolean4) {
		return !this.D.aV().z().c() ? null : this.i().g().a(this, cml, fu, integer, boolean4);
	}

	@Nullable
	public fu a(bre bre, fu fu, int integer3, int integer4) {
		return this.i().g().d().a(fu.u(), fu.v(), fu.w(), integer3, integer4, ImmutableList.of(bre), this.t, true);
	}

	@Override
	public bmv o() {
		return this.D.aD();
	}

	@Override
	public adh p() {
		return this.D.aE();
	}

	@Override
	public boolean q() {
		return this.c;
	}

	public daa s() {
		return this.i().i();
	}

	@Nullable
	@Override
	public czv a(String string) {
		return this.l().D().s().b(() -> new czv(string), string);
	}

	@Override
	public void a(czv czv) {
		this.l().D().s().a(czv);
	}

	@Override
	public int t() {
		return this.l().D().s().<czu>a(czu::new, "idcounts").a();
	}

	public void a_(fu fu) {
		bph bph3 = new bph(new fu(this.u.a(), 0, this.u.c()));
		this.u.a(fu);
		this.i().b(zi.a, bph3, 11, ael.INSTANCE);
		this.i().a(zi.a, new bph(fu), 11, ael.INSTANCE);
		this.l().ac().a(new px(fu));
	}

	public fu u() {
		fu fu2 = new fu(this.u.a(), this.u.b(), this.u.c());
		if (!this.f().a(fu2)) {
			fu2 = this.a(cio.a.MOTION_BLOCKING, new fu(this.f().a(), 0.0, this.f().b()));
		}

		return fu2;
	}

	public LongSet v() {
		bpw bpw2 = this.s().b(bpw::new, "chunks");
		return (LongSet)(bpw2 != null ? LongSets.unmodifiable(bpw2.a()) : LongSets.EMPTY_SET);
	}

	public boolean a(int integer1, int integer2, boolean boolean3) {
		bpw bpw5 = this.s().a(bpw::new, "chunks");
		bph bph6 = new bph(integer1, integer2);
		long long7 = bph6.a();
		boolean boolean9;
		if (boolean3) {
			boolean9 = bpw5.a().add(long7);
			if (boolean9) {
				this.d(integer1, integer2);
			}
		} else {
			boolean9 = bpw5.a().remove(long7);
		}

		bpw5.a(boolean9);
		if (boolean9) {
			this.i().a(bph6, boolean3);
		}

		return boolean9;
	}

	@Override
	public List<ze> w() {
		return this.B;
	}

	@Override
	public void a(fu fu, cfj cfj2, cfj cfj3) {
		Optional<ayc> optional5 = ayc.b(cfj2);
		Optional<ayc> optional6 = ayc.b(cfj3);
		if (!Objects.equals(optional5, optional6)) {
			fu fu7 = fu.h();
			optional5.ifPresent(ayc -> this.l().execute(() -> {
					this.x().a(fu7);
					qy.b(this, fu7);
				}));
			optional6.ifPresent(ayc -> this.l().execute(() -> {
					this.x().a(fu7, ayc);
					qy.a(this, fu7);
				}));
		}
	}

	public axz x() {
		return this.i().j();
	}

	public boolean b_(fu fu) {
		return this.a(fu, 1);
	}

	public boolean a(go go) {
		return this.b_(go.q());
	}

	public boolean a(fu fu, int integer) {
		return integer > 6 ? false : this.b(go.a(fu)) <= integer;
	}

	public int b(go go) {
		return this.x().a(go);
	}

	public bfj y() {
		return this.d;
	}

	@Nullable
	public bfh c_(fu fu) {
		return this.d.a(fu, 9216);
	}

	public boolean e(fu fu) {
		return this.c_(fu) != null;
	}

	public void a(axw axw, aom aom, apl apl) {
		apl.a(axw, aom);
	}

	public void a(Path path) throws IOException {
		yp yp3 = this.i().a;
		Writer writer4 = Files.newBufferedWriter(path.resolve("stats.txt"));
		Throwable path5 = null;

		try {
			writer4.write(String.format("spawning_chunks: %d\n", yp3.e().b()));
			bqj.d d6 = this.i().k();
			if (d6 != null) {
				for (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry<apa> entry8 : d6.b().object2IntEntrySet()) {
					writer4.write(String.format("spawn_count.%s: %d\n", ((apa)entry8.getKey()).b(), entry8.getIntValue()));
				}
			}

			writer4.write(String.format("entities: %d\n", this.y.size()));
			writer4.write(String.format("block_entities: %d\n", this.j.size()));
			writer4.write(String.format("block_ticks: %d\n", this.j().a()));
			writer4.write(String.format("fluid_ticks: %d\n", this.k().a()));
			writer4.write("distance_manager: " + yp3.e().c() + "\n");
			writer4.write(String.format("pending_tasks: %d\n", this.i().f()));
		} catch (Throwable var121) {
			path5 = var121;
			throw var121;
		} finally {
			if (writer4 != null) {
				if (path5 != null) {
					try {
						writer4.close();
					} catch (Throwable var112) {
						path5.addSuppressed(var112);
					}
				} else {
					writer4.close();
				}
			}
		}

		j j4 = new j("Level dump", new Exception("dummy"));
		this.a(j4);
		Writer writer5 = Files.newBufferedWriter(path.resolve("example_crash.txt"));
		Throwable var126 = null;

		try {
			writer5.write(j4.e());
		} catch (Throwable var116) {
			var126 = var116;
			throw var116;
		} finally {
			if (writer5 != null) {
				if (var126 != null) {
					try {
						writer5.close();
					} catch (Throwable var111) {
						var126.addSuppressed(var111);
					}
				} else {
					writer5.close();
				}
			}
		}

		Path path5x = path.resolve("chunks.csv");
		Writer writer6 = Files.newBufferedWriter(path5x);
		Throwable var129 = null;

		try {
			yp3.a(writer6);
		} catch (Throwable var115) {
			var129 = var115;
			throw var115;
		} finally {
			if (writer6 != null) {
				if (var129 != null) {
					try {
						writer6.close();
					} catch (Throwable var110) {
						var129.addSuppressed(var110);
					}
				} else {
					writer6.close();
				}
			}
		}

		Path path6 = path.resolve("entities.csv");
		Writer writer7 = Files.newBufferedWriter(path6);
		Throwable var132 = null;

		try {
			a(writer7, this.y.values());
		} catch (Throwable var114) {
			var132 = var114;
			throw var114;
		} finally {
			if (writer7 != null) {
				if (var132 != null) {
					try {
						writer7.close();
					} catch (Throwable var109) {
						var132.addSuppressed(var109);
					}
				} else {
					writer7.close();
				}
			}
		}

		Path path7 = path.resolve("block_entities.csv");
		Writer writer8 = Files.newBufferedWriter(path7);
		Throwable var8 = null;

		try {
			this.a(writer8);
		} catch (Throwable var113) {
			var8 = var113;
			throw var113;
		} finally {
			if (writer8 != null) {
				if (var8 != null) {
					try {
						writer8.close();
					} catch (Throwable var108) {
						var8.addSuppressed(var108);
					}
				} else {
					writer8.close();
				}
			}
		}
	}

	private static void a(Writer writer, Iterable<aom> iterable) throws IOException {
		ado ado3 = ado.a().a("x").a("y").a("z").a("uuid").a("type").a("alive").a("display_name").a("custom_name").a(writer);

		for (aom aom5 : iterable) {
			mr mr6 = aom5.R();
			mr mr7 = aom5.d();
			ado3.a(aom5.cC(), aom5.cD(), aom5.cG(), aom5.bR(), gl.al.b(aom5.U()), aom5.aU(), mr7.getString(), mr6 != null ? mr6.getString() : null);
		}
	}

	private void a(Writer writer) throws IOException {
		ado ado3 = ado.a().a("x").a("y").a("z").a("type").a(writer);

		for (cdl cdl5 : this.j) {
			fu fu6 = cdl5.o();
			ado3.a(fu6.u(), fu6.v(), fu6.w(), gl.aC.b(cdl5.u()));
		}
	}

	@VisibleForTesting
	public void a(ctd ctd) {
		this.L.removeIf(bpf -> ctd.b(bpf.a()));
	}

	@Override
	public void a(fu fu, bvr bvr) {
		if (!this.Z()) {
			this.b(fu, bvr);
		}
	}

	public Iterable<aom> z() {
		return Iterables.unmodifiableIterable(this.y.values());
	}

	public String toString() {
		return "ServerLevel[" + this.E.f() + "]";
	}

	public boolean A() {
		return this.D.aV().z().i();
	}

	@Override
	public long B() {
		return this.D.aV().z().b();
	}

	@Nullable
	public cii C() {
		return this.O;
	}

	public static void a(zd zd) {
		fu fu2 = a;
		int integer3 = fu2.u();
		int integer4 = fu2.v() - 2;
		int integer5 = fu2.w();
		fu.b(integer3 - 2, integer4 + 1, integer5 - 2, integer3 + 2, integer4 + 3, integer5 + 2).forEach(fu -> zd.a(fu, bvs.a.n()));
		fu.b(integer3 - 2, integer4, integer5 - 2, integer3 + 2, integer4, integer5 + 2).forEach(fu -> zd.a(fu, bvs.bK.n()));
	}
}

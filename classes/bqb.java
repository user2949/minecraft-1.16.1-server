import bre.f;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class bqb implements bqc, AutoCloseable {
	protected static final Logger e = LogManager.getLogger();
	public static final Codec<ug<bqb>> f = uh.a.xmap(ug.a(gl.ae), ug::a);
	public static final ug<bqb> g = ug.a(gl.ae, new uh("overworld"));
	public static final ug<bqb> h = ug.a(gl.ae, new uh("the_nether"));
	public static final ug<bqb> i = ug.a(gl.ae, new uh("the_end"));
	private static final fz[] a = fz.values();
	public final List<cdl> j = Lists.<cdl>newArrayList();
	public final List<cdl> k = Lists.<cdl>newArrayList();
	protected final List<cdl> l = Lists.<cdl>newArrayList();
	protected final List<cdl> m = Lists.<cdl>newArrayList();
	private final Thread b;
	private final boolean c;
	private int d;
	protected int n = new Random().nextInt();
	protected final int o = 1013904223;
	protected float p;
	protected float q;
	protected float r;
	protected float s;
	public final Random t = new Random();
	private final cif x;
	protected final dam u;
	private final Supplier<ami> y;
	public final boolean v;
	protected boolean w;
	private final cgw z;
	private final brg A;
	private final ug<bqb> B;
	private final ug<cif> C;

	protected bqb(dam dam, ug<bqb> ug2, ug<cif> ug3, cif cif, Supplier<ami> supplier, boolean boolean6, boolean boolean7, long long8) {
		this.y = supplier;
		this.u = dam;
		this.x = cif;
		this.B = ug2;
		this.C = ug3;
		this.v = boolean6;
		if (cif.h()) {
			this.z = new cgw() {
				@Override
				public double a() {
					return super.a() / 8.0;
				}

				@Override
				public double b() {
					return super.b() / 8.0;
				}
			};
		} else {
			this.z = new cgw();
		}

		this.b = Thread.currentThread();
		this.A = new brg(this, long8, cif.o());
		this.c = boolean7;
	}

	@Override
	public boolean s_() {
		return this.v;
	}

	@Nullable
	public MinecraftServer l() {
		return null;
	}

	public static boolean j(fu fu) {
		return !l(fu) && e(fu);
	}

	public static boolean k(fu fu) {
		return !d(fu.v()) && e(fu);
	}

	private static boolean e(fu fu) {
		return fu.u() >= -30000000 && fu.w() >= -30000000 && fu.u() < 30000000 && fu.w() < 30000000;
	}

	private static boolean d(int integer) {
		return integer < -20000000 || integer >= 20000000;
	}

	public static boolean l(fu fu) {
		return b(fu.v());
	}

	public static boolean b(int integer) {
		return integer < 0 || integer >= 256;
	}

	public double m(fu fu) {
		return this.c(fu, cfj -> false);
	}

	public double c(fu fu, Predicate<cfj> predicate) {
		cfj cfj4 = this.d_(fu);
		dfg dfg5 = predicate.test(cfj4) ? dfd.a() : cfj4.k(this, fu);
		if (dfg5.b()) {
			fu fu6 = fu.c();
			cfj cfj7 = this.d_(fu6);
			dfg dfg8 = predicate.test(cfj7) ? dfd.a() : cfj7.k(this, fu6);
			double double9 = dfg8.c(fz.a.Y);
			return double9 >= 1.0 ? double9 - 1.0 : Double.NEGATIVE_INFINITY;
		} else {
			return dfg5.c(fz.a.Y);
		}
	}

	public double a(fu fu, double double2) {
		fu.a a5 = fu.i();
		int integer6 = aec.f(double2);
		int integer7 = 0;

		while (integer7 < integer6) {
			dfg dfg8 = this.d_(a5).k(this, a5);
			if (!dfg8.b()) {
				return (double)integer7 + dfg8.b(fz.a.Y);
			}

			integer7++;
			a5.c(fz.UP);
		}

		return Double.POSITIVE_INFINITY;
	}

	public chj n(fu fu) {
		return this.d(fu.u() >> 4, fu.w() >> 4);
	}

	public chj a(int integer1, int integer2) {
		return (chj)this.a(integer1, integer2, chc.m);
	}

	@Override
	public cgy a(int integer1, int integer2, chc chc, boolean boolean4) {
		cgy cgy6 = this.E().a(integer1, integer2, chc, boolean4);
		if (cgy6 == null && boolean4) {
			throw new IllegalStateException("Should always be able to create a chunk!");
		} else {
			return cgy6;
		}
	}

	@Override
	public boolean a(fu fu, cfj cfj, int integer) {
		return this.a(fu, cfj, integer, 512);
	}

	@Override
	public boolean a(fu fu, cfj cfj, int integer3, int integer4) {
		if (l(fu)) {
			return false;
		} else if (!this.v && this.Z()) {
			return false;
		} else {
			chj chj6 = this.n(fu);
			bvr bvr7 = cfj.b();
			cfj cfj8 = chj6.a(fu, cfj, (integer3 & 64) != 0);
			if (cfj8 == null) {
				return false;
			} else {
				cfj cfj9 = this.d_(fu);
				if (cfj9 != cfj8 && (cfj9.b((bpg)this, fu) != cfj8.b((bpg)this, fu) || cfj9.f() != cfj8.f() || cfj9.e() || cfj8.e())) {
					this.X().a("queueCheckLight");
					this.E().l().a(fu);
					this.X().c();
				}

				if (cfj9 == cfj) {
					if (cfj8 != cfj9) {
						this.b(fu, cfj8, cfj9);
					}

					if ((integer3 & 2) != 0 && (!this.v || (integer3 & 4) == 0) && (this.v || chj6.u() != null && chj6.u().a(yo.b.TICKING))) {
						this.a(fu, cfj8, cfj, integer3);
					}

					if ((integer3 & 1) != 0) {
						this.a(fu, cfj8.b());
						if (!this.v && cfj.j()) {
							this.c(fu, bvr7);
						}
					}

					if ((integer3 & 16) == 0 && integer4 > 0) {
						int integer10 = integer3 & -34;
						cfj8.b(this, fu, integer10, integer4 - 1);
						cfj.a((bqc)this, fu, integer10, integer4 - 1);
						cfj.b(this, fu, integer10, integer4 - 1);
					}

					this.a(fu, cfj8, cfj9);
				}

				return true;
			}
		}
	}

	public void a(fu fu, cfj cfj2, cfj cfj3) {
	}

	@Override
	public boolean a(fu fu, boolean boolean2) {
		cxa cxa4 = this.b(fu);
		return this.a(fu, cxa4.g(), 3 | (boolean2 ? 64 : 0));
	}

	@Override
	public boolean a(fu fu, boolean boolean2, @Nullable aom aom, int integer) {
		cfj cfj6 = this.d_(fu);
		if (cfj6.g()) {
			return false;
		} else {
			cxa cxa7 = this.b(fu);
			if (!(cfj6.b() instanceof bvh)) {
				this.c(2001, fu, bvr.i(cfj6));
			}

			if (boolean2) {
				cdl cdl8 = cfj6.b().q() ? this.c(fu) : null;
				bvr.a(cfj6, this, fu, cdl8, aom, bki.b);
			}

			return this.a(fu, cxa7.g(), 3, integer);
		}
	}

	public boolean a(fu fu, cfj cfj) {
		return this.a(fu, cfj, 3);
	}

	public abstract void a(fu fu, cfj cfj2, cfj cfj3, int integer);

	public void b(fu fu, cfj cfj2, cfj cfj3) {
	}

	public void b(fu fu, bvr bvr) {
		this.a(fu.f(), bvr, fu);
		this.a(fu.g(), bvr, fu);
		this.a(fu.c(), bvr, fu);
		this.a(fu.b(), bvr, fu);
		this.a(fu.d(), bvr, fu);
		this.a(fu.e(), bvr, fu);
	}

	public void a(fu fu, bvr bvr, fz fz) {
		if (fz != fz.WEST) {
			this.a(fu.f(), bvr, fu);
		}

		if (fz != fz.EAST) {
			this.a(fu.g(), bvr, fu);
		}

		if (fz != fz.DOWN) {
			this.a(fu.c(), bvr, fu);
		}

		if (fz != fz.UP) {
			this.a(fu.b(), bvr, fu);
		}

		if (fz != fz.NORTH) {
			this.a(fu.d(), bvr, fu);
		}

		if (fz != fz.SOUTH) {
			this.a(fu.e(), bvr, fu);
		}
	}

	public void a(fu fu1, bvr bvr, fu fu3) {
		if (!this.v) {
			cfj cfj5 = this.d_(fu1);

			try {
				cfj5.a(this, fu1, bvr, fu3, false);
			} catch (Throwable var8) {
				j j7 = j.a(var8, "Exception while updating neighbours");
				k k8 = j7.a("Block being updated");
				k8.a("Source block type", (l<String>)(() -> {
					try {
						return String.format("ID #%s (%s // %s)", gl.aj.b(bvr), bvr.i(), bvr.getClass().getCanonicalName());
					} catch (Throwable var2) {
						return "ID #" + gl.aj.b(bvr);
					}
				}));
				k.a(k8, fu1, cfj5);
				throw new s(j7);
			}
		}
	}

	@Override
	public int a(cio.a a, int integer2, int integer3) {
		int integer5;
		if (integer2 >= -30000000 && integer3 >= -30000000 && integer2 < 30000000 && integer3 < 30000000) {
			if (this.b(integer2 >> 4, integer3 >> 4)) {
				integer5 = this.d(integer2 >> 4, integer3 >> 4).a(a, integer2 & 15, integer3 & 15) + 1;
			} else {
				integer5 = 0;
			}
		} else {
			integer5 = this.t_() + 1;
		}

		return integer5;
	}

	@Override
	public cwr e() {
		return this.E().l();
	}

	@Override
	public cfj d_(fu fu) {
		if (l(fu)) {
			return bvs.la.n();
		} else {
			chj chj3 = this.d(fu.u() >> 4, fu.w() >> 4);
			return chj3.d_(fu);
		}
	}

	@Override
	public cxa b(fu fu) {
		if (l(fu)) {
			return cxb.a.h();
		} else {
			chj chj3 = this.n(fu);
			return chj3.b(fu);
		}
	}

	public boolean J() {
		return !this.m().p() && this.d < 4;
	}

	public boolean K() {
		return !this.m().p() && !this.J();
	}

	@Override
	public void a(@Nullable bec bec, fu fu, ack ack, acm acm, float float5, float float6) {
		this.a(bec, (double)fu.u() + 0.5, (double)fu.v() + 0.5, (double)fu.w() + 0.5, ack, acm, float5, float6);
	}

	public abstract void a(@Nullable bec bec, double double2, double double3, double double4, ack ack, acm acm, float float7, float float8);

	public abstract void a(@Nullable bec bec, aom aom, ack ack, acm acm, float float5, float float6);

	public void a(double double1, double double2, double double3, ack ack, acm acm, float float6, float float7, boolean boolean8) {
	}

	@Override
	public void a(hf hf, double double2, double double3, double double4, double double5, double double6, double double7) {
	}

	public void b(hf hf, double double2, double double3, double double4, double double5, double double6, double double7) {
	}

	public void b(hf hf, boolean boolean2, double double3, double double4, double double5, double double6, double double7, double double8) {
	}

	public float a(float float1) {
		float float3 = this.f(float1);
		return float3 * (float) (Math.PI * 2);
	}

	public boolean a(cdl cdl) {
		if (this.w) {
			e.error("Adding block entity while ticking: {} @ {}", () -> gl.aC.b(cdl.u()), cdl::o);
		}

		boolean boolean3 = this.j.add(cdl);
		if (boolean3 && cdl instanceof ceo) {
			this.k.add(cdl);
		}

		if (this.v) {
			fu fu4 = cdl.o();
			cfj cfj5 = this.d_(fu4);
			this.a(fu4, cfj5, cfj5, 2);
		}

		return boolean3;
	}

	public void a(Collection<cdl> collection) {
		if (this.w) {
			this.l.addAll(collection);
		} else {
			for (cdl cdl4 : collection) {
				this.a(cdl4);
			}
		}
	}

	public void L() {
		ami ami2 = this.X();
		ami2.a("blockEntities");
		if (!this.m.isEmpty()) {
			this.k.removeAll(this.m);
			this.j.removeAll(this.m);
			this.m.clear();
		}

		this.w = true;
		Iterator<cdl> iterator3 = this.k.iterator();

		while (iterator3.hasNext()) {
			cdl cdl4 = (cdl)iterator3.next();
			if (!cdl4.q() && cdl4.n()) {
				fu fu5 = cdl4.o();
				if (this.E().a(fu5) && this.f().a(fu5)) {
					try {
						ami2.a((Supplier<String>)(() -> String.valueOf(cdm.a(cdl4.u()))));
						if (cdl4.u().a(this.d_(fu5).b())) {
							((ceo)cdl4).al_();
						} else {
							cdl4.w();
						}

						ami2.c();
					} catch (Throwable var8) {
						j j7 = j.a(var8, "Ticking block entity");
						k k8 = j7.a("Block entity being ticked");
						cdl4.a(k8);
						throw new s(j7);
					}
				}
			}

			if (cdl4.q()) {
				iterator3.remove();
				this.j.remove(cdl4);
				if (this.C(cdl4.o())) {
					this.n(cdl4.o()).d(cdl4.o());
				}
			}
		}

		this.w = false;
		ami2.b("pendingBlockEntities");
		if (!this.l.isEmpty()) {
			for (int integer4 = 0; integer4 < this.l.size(); integer4++) {
				cdl cdl5 = (cdl)this.l.get(integer4);
				if (!cdl5.q()) {
					if (!this.j.contains(cdl5)) {
						this.a(cdl5);
					}

					if (this.C(cdl5.o())) {
						chj chj6 = this.n(cdl5.o());
						cfj cfj7 = chj6.d_(cdl5.o());
						chj6.a(cdl5.o(), cdl5);
						this.a(cdl5.o(), cfj7, cfj7, 3);
					}
				}
			}

			this.l.clear();
		}

		ami2.c();
	}

	public void a(Consumer<aom> consumer, aom aom) {
		try {
			consumer.accept(aom);
		} catch (Throwable var6) {
			j j5 = j.a(var6, "Ticking entity");
			k k6 = j5.a("Entity being ticked");
			aom.a(k6);
			throw new s(j5);
		}
	}

	public bpt a(@Nullable aom aom, double double2, double double3, double double4, float float5, bpt.a a) {
		return this.a(aom, null, null, double2, double3, double4, float5, false, a);
	}

	public bpt a(@Nullable aom aom, double double2, double double3, double double4, float float5, boolean boolean6, bpt.a a) {
		return this.a(aom, null, null, double2, double3, double4, float5, boolean6, a);
	}

	public bpt a(@Nullable aom aom, @Nullable anw anw, @Nullable bpu bpu, double double4, double double5, double double6, float float7, boolean boolean8, bpt.a a) {
		bpt bpt14 = new bpt(this, aom, anw, bpu, double4, double5, double6, float7, boolean8, a);
		bpt14.a();
		bpt14.a(true);
		return bpt14;
	}

	@Nullable
	@Override
	public cdl c(fu fu) {
		if (l(fu)) {
			return null;
		} else if (!this.v && Thread.currentThread() != this.b) {
			return null;
		} else {
			cdl cdl3 = null;
			if (this.w) {
				cdl3 = this.D(fu);
			}

			if (cdl3 == null) {
				cdl3 = this.n(fu).a(fu, chj.a.IMMEDIATE);
			}

			if (cdl3 == null) {
				cdl3 = this.D(fu);
			}

			return cdl3;
		}
	}

	@Nullable
	private cdl D(fu fu) {
		for (int integer3 = 0; integer3 < this.l.size(); integer3++) {
			cdl cdl4 = (cdl)this.l.get(integer3);
			if (!cdl4.q() && cdl4.o().equals(fu)) {
				return cdl4;
			}
		}

		return null;
	}

	public void a(fu fu, @Nullable cdl cdl) {
		if (!l(fu)) {
			if (cdl != null && !cdl.q()) {
				if (this.w) {
					cdl.a(this, fu);
					Iterator<cdl> iterator4 = this.l.iterator();

					while (iterator4.hasNext()) {
						cdl cdl5 = (cdl)iterator4.next();
						if (cdl5.o().equals(fu)) {
							cdl5.an_();
							iterator4.remove();
						}
					}

					this.l.add(cdl);
				} else {
					this.n(fu).a(fu, cdl);
					this.a(cdl);
				}
			}
		}
	}

	public void o(fu fu) {
		cdl cdl3 = this.c(fu);
		if (cdl3 != null && this.w) {
			cdl3.an_();
			this.l.remove(cdl3);
		} else {
			if (cdl3 != null) {
				this.l.remove(cdl3);
				this.j.remove(cdl3);
				this.k.remove(cdl3);
			}

			this.n(fu).d(fu);
		}
	}

	public boolean p(fu fu) {
		return l(fu) ? false : this.E().b(fu.u() >> 4, fu.w() >> 4);
	}

	public boolean a(fu fu, aom aom, fz fz) {
		if (l(fu)) {
			return false;
		} else {
			cgy cgy5 = this.a(fu.u() >> 4, fu.w() >> 4, chc.m, false);
			return cgy5 == null ? false : cgy5.d_(fu).a(this, fu, aom, fz);
		}
	}

	public boolean a(fu fu, aom aom) {
		return this.a(fu, aom, fz.UP);
	}

	public void N() {
		double double2 = 1.0 - (double)(this.d(1.0F) * 5.0F) / 16.0;
		double double4 = 1.0 - (double)(this.b(1.0F) * 5.0F) / 16.0;
		double double6 = 0.5 + 2.0 * aec.a((double)aec.b(this.f(1.0F) * (float) (Math.PI * 2)), -0.25, 0.25);
		this.d = (int)((1.0 - double6 * double2 * double4) * 11.0);
	}

	public void b(boolean boolean1, boolean boolean2) {
		this.E().a(boolean1, boolean2);
	}

	protected void O() {
		if (this.u.j()) {
			this.q = 1.0F;
			if (this.u.h()) {
				this.s = 1.0F;
			}
		}
	}

	public void close() throws IOException {
		this.E().close();
	}

	@Nullable
	@Override
	public bpg c(int integer1, int integer2) {
		return this.a(integer1, integer2, chc.m, false);
	}

	@Override
	public List<aom> a(@Nullable aom aom, deg deg, @Nullable Predicate<? super aom> predicate) {
		this.X().c("getEntities");
		List<aom> list5 = Lists.<aom>newArrayList();
		int integer6 = aec.c((deg.a - 2.0) / 16.0);
		int integer7 = aec.c((deg.d + 2.0) / 16.0);
		int integer8 = aec.c((deg.c - 2.0) / 16.0);
		int integer9 = aec.c((deg.f + 2.0) / 16.0);
		chb chb10 = this.E();

		for (int integer11 = integer6; integer11 <= integer7; integer11++) {
			for (int integer12 = integer8; integer12 <= integer9; integer12++) {
				chj chj13 = chb10.a(integer11, integer12, false);
				if (chj13 != null) {
					chj13.a(aom, deg, list5, predicate);
				}
			}
		}

		return list5;
	}

	public <T extends aom> List<T> a(@Nullable aoq<T> aoq, deg deg, Predicate<? super T> predicate) {
		this.X().c("getEntities");
		int integer5 = aec.c((deg.a - 2.0) / 16.0);
		int integer6 = aec.f((deg.d + 2.0) / 16.0);
		int integer7 = aec.c((deg.c - 2.0) / 16.0);
		int integer8 = aec.f((deg.f + 2.0) / 16.0);
		List<T> list9 = Lists.<T>newArrayList();

		for (int integer10 = integer5; integer10 < integer6; integer10++) {
			for (int integer11 = integer7; integer11 < integer8; integer11++) {
				chj chj12 = this.E().a(integer10, integer11, false);
				if (chj12 != null) {
					chj12.a(aoq, deg, list9, predicate);
				}
			}
		}

		return list9;
	}

	@Override
	public <T extends aom> List<T> a(Class<? extends T> class1, deg deg, @Nullable Predicate<? super T> predicate) {
		this.X().c("getEntities");
		int integer5 = aec.c((deg.a - 2.0) / 16.0);
		int integer6 = aec.f((deg.d + 2.0) / 16.0);
		int integer7 = aec.c((deg.c - 2.0) / 16.0);
		int integer8 = aec.f((deg.f + 2.0) / 16.0);
		List<T> list9 = Lists.<T>newArrayList();
		chb chb10 = this.E();

		for (int integer11 = integer5; integer11 < integer6; integer11++) {
			for (int integer12 = integer7; integer12 < integer8; integer12++) {
				chj chj13 = chb10.a(integer11, integer12, false);
				if (chj13 != null) {
					chj13.a(class1, deg, list9, predicate);
				}
			}
		}

		return list9;
	}

	@Override
	public <T extends aom> List<T> b(Class<? extends T> class1, deg deg, @Nullable Predicate<? super T> predicate) {
		this.X().c("getLoadedEntities");
		int integer5 = aec.c((deg.a - 2.0) / 16.0);
		int integer6 = aec.f((deg.d + 2.0) / 16.0);
		int integer7 = aec.c((deg.c - 2.0) / 16.0);
		int integer8 = aec.f((deg.f + 2.0) / 16.0);
		List<T> list9 = Lists.<T>newArrayList();
		chb chb10 = this.E();

		for (int integer11 = integer5; integer11 < integer6; integer11++) {
			for (int integer12 = integer7; integer12 < integer8; integer12++) {
				chj chj13 = chb10.a(integer11, integer12);
				if (chj13 != null) {
					chj13.a(class1, deg, list9, predicate);
				}
			}
		}

		return list9;
	}

	@Nullable
	public abstract aom a(int integer);

	public void b(fu fu, cdl cdl) {
		if (this.C(fu)) {
			this.n(fu).s();
		}
	}

	@Override
	public int t_() {
		return 63;
	}

	@Override
	public bqb n() {
		return this;
	}

	public int q(fu fu) {
		int integer3 = 0;
		integer3 = Math.max(integer3, this.c(fu.c(), fz.DOWN));
		if (integer3 >= 15) {
			return integer3;
		} else {
			integer3 = Math.max(integer3, this.c(fu.b(), fz.UP));
			if (integer3 >= 15) {
				return integer3;
			} else {
				integer3 = Math.max(integer3, this.c(fu.d(), fz.NORTH));
				if (integer3 >= 15) {
					return integer3;
				} else {
					integer3 = Math.max(integer3, this.c(fu.e(), fz.SOUTH));
					if (integer3 >= 15) {
						return integer3;
					} else {
						integer3 = Math.max(integer3, this.c(fu.f(), fz.WEST));
						if (integer3 >= 15) {
							return integer3;
						} else {
							integer3 = Math.max(integer3, this.c(fu.g(), fz.EAST));
							return integer3 >= 15 ? integer3 : integer3;
						}
					}
				}
			}
		}
	}

	public boolean a(fu fu, fz fz) {
		return this.b(fu, fz) > 0;
	}

	public int b(fu fu, fz fz) {
		cfj cfj4 = this.d_(fu);
		int integer5 = cfj4.b(this, fu, fz);
		return cfj4.g(this, fu) ? Math.max(integer5, this.q(fu)) : integer5;
	}

	public boolean r(fu fu) {
		if (this.b(fu.c(), fz.DOWN) > 0) {
			return true;
		} else if (this.b(fu.b(), fz.UP) > 0) {
			return true;
		} else if (this.b(fu.d(), fz.NORTH) > 0) {
			return true;
		} else if (this.b(fu.e(), fz.SOUTH) > 0) {
			return true;
		} else {
			return this.b(fu.f(), fz.WEST) > 0 ? true : this.b(fu.g(), fz.EAST) > 0;
		}
	}

	public int s(fu fu) {
		int integer3 = 0;

		for (fz fz7 : a) {
			int integer8 = this.b(fu.a(fz7), fz7);
			if (integer8 >= 15) {
				return 15;
			}

			if (integer8 > integer3) {
				integer3 = integer8;
			}
		}

		return integer3;
	}

	public long Q() {
		return this.u.d();
	}

	public long R() {
		return this.u.e();
	}

	public boolean a(bec bec, fu fu) {
		return true;
	}

	public void a(aom aom, byte byte2) {
	}

	public void a(fu fu, bvr bvr, int integer3, int integer4) {
		this.d_(fu).a(this, fu, integer3, integer4);
	}

	@Override
	public dab u_() {
		return this.u;
	}

	public bpx S() {
		return this.u.p();
	}

	public float b(float float1) {
		return aec.g(float1, this.r, this.s) * this.d(float1);
	}

	public float d(float float1) {
		return aec.g(float1, this.p, this.q);
	}

	public boolean T() {
		return this.m().d() && !this.m().e() ? (double)this.b(1.0F) > 0.9 : false;
	}

	public boolean U() {
		return (double)this.d(1.0F) > 0.2;
	}

	public boolean t(fu fu) {
		if (!this.U()) {
			return false;
		} else if (!this.f(fu)) {
			return false;
		} else if (this.a(cio.a.MOTION_BLOCKING, fu).v() > fu.v()) {
			return false;
		} else {
			bre bre3 = this.v(fu);
			return bre3.d() == bre.f.RAIN && bre3.b(fu) >= 0.15F;
		}
	}

	public boolean u(fu fu) {
		bre bre3 = this.v(fu);
		return bre3.e();
	}

	@Nullable
	public abstract czv a(String string);

	public abstract void a(czv czv);

	public abstract int t();

	public void b(int integer1, fu fu, int integer3) {
	}

	public k a(j j) {
		k k3 = j.a("Affected level", 1);
		k3.a("All players", (l<String>)(() -> this.w().size() + " total; " + this.w()));
		k3.a("Chunk stats", this.E()::e);
		k3.a("Level dimension", (l<String>)(() -> this.W().a().toString()));

		try {
			this.u.a(k3);
		} catch (Throwable var4) {
			k3.a("Level Data Unobtainable", var4);
		}

		return k3;
	}

	public abstract void a(int integer1, fu fu, int integer3);

	public abstract dfm D();

	public void c(fu fu, bvr bvr) {
		for (fz fz5 : fz.c.HORIZONTAL) {
			fu fu6 = fu.a(fz5);
			if (this.C(fu6)) {
				cfj cfj7 = this.d_(fu6);
				if (cfj7.a(bvs.fu)) {
					cfj7.a(this, fu6, bvr, fu, false);
				} else if (cfj7.g(this, fu6)) {
					fu6 = fu6.a(fz5);
					cfj7 = this.d_(fu6);
					if (cfj7.a(bvs.fu)) {
						cfj7.a(this, fu6, bvr, fu, false);
					}
				}
			}
		}
	}

	@Override
	public ane d(fu fu) {
		long long3 = 0L;
		float float5 = 0.0F;
		if (this.C(fu)) {
			float5 = this.aa();
			long3 = this.n(fu).q();
		}

		return new ane(this.ac(), this.R(), long3, float5);
	}

	@Override
	public int c() {
		return this.d;
	}

	public void c(int integer) {
	}

	@Override
	public cgw f() {
		return this.z;
	}

	public void a(ni<?> ni) {
		throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
	}

	@Override
	public cif m() {
		return this.x;
	}

	public ug<cif> V() {
		return this.C;
	}

	public ug<bqb> W() {
		return this.B;
	}

	@Override
	public Random v_() {
		return this.t;
	}

	@Override
	public boolean a(fu fu, Predicate<cfj> predicate) {
		return predicate.test(this.d_(fu));
	}

	public abstract bmv o();

	public abstract adh p();

	public fu a(int integer1, int integer2, int integer3, int integer4) {
		this.n = this.n * 3 + 1013904223;
		int integer6 = this.n >> 2;
		return new fu(integer1 + (integer6 & 15), integer2 + (integer6 >> 16 & integer4), integer3 + (integer6 >> 8 & 15));
	}

	public boolean q() {
		return false;
	}

	public ami X() {
		return (ami)this.y.get();
	}

	public Supplier<ami> Y() {
		return this.y;
	}

	@Override
	public brg d() {
		return this.A;
	}

	public final boolean Z() {
		return this.c;
	}
}

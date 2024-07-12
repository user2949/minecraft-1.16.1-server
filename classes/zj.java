import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zj implements bqu {
	private static final Logger a = LogManager.getLogger();
	private final List<cgy> b;
	private final int c;
	private final int d;
	private final int e;
	private final zd f;
	private final long g;
	private final dab h;
	private final Random i;
	private final cif j;
	private final bqr<bvr> k = new zk<>(fu -> this.z(fu).n());
	private final bqr<cwz> l = new zk<>(fu -> this.z(fu).o());
	private final brg m;
	private final bph n;
	private final bph o;

	public zj(zd zd, List<cgy> list) {
		int integer4 = aec.c(Math.sqrt((double)list.size()));
		if (integer4 * integer4 != list.size()) {
			throw (IllegalStateException)v.c(new IllegalStateException("Cache size is not a square."));
		} else {
			bph bph5 = ((cgy)list.get(list.size() / 2)).g();
			this.b = list;
			this.c = bph5.b;
			this.d = bph5.c;
			this.e = integer4;
			this.f = zd;
			this.g = zd.B();
			this.h = zd.u_();
			this.i = zd.v_();
			this.j = zd.m();
			this.m = new brg(this, brg.a(this.g), zd.m().o());
			this.n = ((cgy)list.get(0)).g();
			this.o = ((cgy)list.get(list.size() - 1)).g();
		}
	}

	public int a() {
		return this.c;
	}

	public int b() {
		return this.d;
	}

	@Override
	public cgy a(int integer1, int integer2) {
		return this.a(integer1, integer2, chc.a);
	}

	@Nullable
	@Override
	public cgy a(int integer1, int integer2, chc chc, boolean boolean4) {
		cgy cgy6;
		if (this.b(integer1, integer2)) {
			int integer7 = integer1 - this.n.b;
			int integer8 = integer2 - this.n.c;
			cgy6 = (cgy)this.b.get(integer7 + integer8 * this.e);
			if (cgy6.k().b(chc)) {
				return cgy6;
			}
		} else {
			cgy6 = null;
		}

		if (!boolean4) {
			return null;
		} else {
			a.error("Requested chunk : {} {}", integer1, integer2);
			a.error("Region bounds : {} {} | {} {}", this.n.b, this.n.c, this.o.b, this.o.c);
			if (cgy6 != null) {
				throw (RuntimeException)v.c(
					new RuntimeException(String.format("Chunk is not of correct status. Expecting %s, got %s | %s %s", chc, cgy6.k(), integer1, integer2))
				);
			} else {
				throw (RuntimeException)v.c(new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", integer1, integer2)));
			}
		}
	}

	@Override
	public boolean b(int integer1, int integer2) {
		return integer1 >= this.n.b && integer1 <= this.o.b && integer2 >= this.n.c && integer2 <= this.o.c;
	}

	@Override
	public cfj d_(fu fu) {
		return this.a(fu.u() >> 4, fu.w() >> 4).d_(fu);
	}

	@Override
	public cxa b(fu fu) {
		return this.z(fu).b(fu);
	}

	@Nullable
	@Override
	public bec a(double double1, double double2, double double3, double double4, Predicate<aom> predicate) {
		return null;
	}

	@Override
	public int c() {
		return 0;
	}

	@Override
	public brg d() {
		return this.m;
	}

	@Override
	public bre a(int integer1, int integer2, int integer3) {
		return this.f.a(integer1, integer2, integer3);
	}

	@Override
	public cwr e() {
		return this.f.e();
	}

	@Override
	public boolean a(fu fu, boolean boolean2, @Nullable aom aom, int integer) {
		cfj cfj6 = this.d_(fu);
		if (cfj6.g()) {
			return false;
		} else {
			if (boolean2) {
				cdl cdl7 = cfj6.b().q() ? this.c(fu) : null;
				bvr.a(cfj6, (bqb)this.f, fu, cdl7, aom, bki.b);
			}

			return this.a(fu, bvs.a.n(), 3, integer);
		}
	}

	@Nullable
	@Override
	public cdl c(fu fu) {
		cgy cgy3 = this.z(fu);
		cdl cdl4 = cgy3.c(fu);
		if (cdl4 != null) {
			return cdl4;
		} else {
			le le5 = cgy3.f(fu);
			cfj cfj6 = cgy3.d_(fu);
			if (le5 != null) {
				if ("DUMMY".equals(le5.l("id"))) {
					bvr bvr7 = cfj6.b();
					if (!(bvr7 instanceof bxp)) {
						return null;
					}

					cdl4 = ((bxp)bvr7).a(this.f);
				} else {
					cdl4 = cdl.b(cfj6, le5);
				}

				if (cdl4 != null) {
					cgy3.a(fu, cdl4);
					return cdl4;
				}
			}

			if (cfj6.b() instanceof bxp) {
				a.warn("Tried to access a block entity before it was created. {}", fu);
			}

			return null;
		}
	}

	@Override
	public boolean a(fu fu, cfj cfj, int integer3, int integer4) {
		cgy cgy6 = this.z(fu);
		cfj cfj7 = cgy6.a(fu, cfj, false);
		if (cfj7 != null) {
			this.f.a(fu, cfj7, cfj);
		}

		bvr bvr8 = cfj.b();
		if (bvr8.q()) {
			if (cgy6.k().g() == chc.a.LEVELCHUNK) {
				cgy6.a(fu, ((bxp)bvr8).a(this));
			} else {
				le le9 = new le();
				le9.b("x", fu.u());
				le9.b("y", fu.v());
				le9.b("z", fu.w());
				le9.a("id", "DUMMY");
				cgy6.a(le9);
			}
		} else if (cfj7 != null && cfj7.b().q()) {
			cgy6.d(fu);
		}

		if (cfj.q(this, fu)) {
			this.e(fu);
		}

		return true;
	}

	private void e(fu fu) {
		this.z(fu).e(fu);
	}

	@Override
	public boolean c(aom aom) {
		int integer3 = aec.c(aom.cC() / 16.0);
		int integer4 = aec.c(aom.cG() / 16.0);
		this.a(integer3, integer4).a(aom);
		return true;
	}

	@Override
	public boolean a(fu fu, boolean boolean2) {
		return this.a(fu, bvs.a.n(), 3);
	}

	@Override
	public cgw f() {
		return this.f.f();
	}

	@Override
	public boolean s_() {
		return false;
	}

	@Deprecated
	public zd n() {
		return this.f;
	}

	@Override
	public dab u_() {
		return this.h;
	}

	@Override
	public ane d(fu fu) {
		if (!this.b(fu.u() >> 4, fu.w() >> 4)) {
			throw new RuntimeException("We are asking a region for a chunk out of bound");
		} else {
			return new ane(this.f.ac(), this.f.R(), 0L, this.f.aa());
		}
	}

	@Override
	public chb E() {
		return this.f.i();
	}

	@Override
	public long B() {
		return this.g;
	}

	@Override
	public bqr<bvr> G() {
		return this.k;
	}

	@Override
	public bqr<cwz> F() {
		return this.l;
	}

	@Override
	public int t_() {
		return this.f.t_();
	}

	@Override
	public Random v_() {
		return this.i;
	}

	@Override
	public int a(cio.a a, int integer2, int integer3) {
		return this.a(integer2 >> 4, integer3 >> 4).a(a, integer2 & 15, integer3 & 15) + 1;
	}

	@Override
	public void a(@Nullable bec bec, fu fu, ack ack, acm acm, float float5, float float6) {
	}

	@Override
	public void a(hf hf, double double2, double double3, double double4, double double5, double double6, double double7) {
	}

	@Override
	public void a(@Nullable bec bec, int integer2, fu fu, int integer4) {
	}

	@Override
	public cif m() {
		return this.j;
	}

	@Override
	public boolean a(fu fu, Predicate<cfj> predicate) {
		return predicate.test(this.d_(fu));
	}

	@Override
	public <T extends aom> List<T> a(Class<? extends T> class1, deg deg, @Nullable Predicate<? super T> predicate) {
		return Collections.emptyList();
	}

	@Override
	public List<aom> a(@Nullable aom aom, deg deg, @Nullable Predicate<? super aom> predicate) {
		return Collections.emptyList();
	}

	@Override
	public List<bec> w() {
		return Collections.emptyList();
	}
}

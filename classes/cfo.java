import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cfo {
	private final Predicate<cfn>[][][] a;
	private final int b;
	private final int c;
	private final int d;

	public cfo(Predicate<cfn>[][][] arr) {
		this.a = arr;
		this.b = arr.length;
		if (this.b > 0) {
			this.c = arr[0].length;
			if (this.c > 0) {
				this.d = arr[0][0].length;
			} else {
				this.d = 0;
			}
		} else {
			this.c = 0;
			this.d = 0;
		}
	}

	public int a() {
		return this.b;
	}

	public int b() {
		return this.c;
	}

	public int c() {
		return this.d;
	}

	@Nullable
	private cfo.b a(fu fu, fz fz2, fz fz3, LoadingCache<fu, cfn> loadingCache) {
		for (int integer6 = 0; integer6 < this.d; integer6++) {
			for (int integer7 = 0; integer7 < this.c; integer7++) {
				for (int integer8 = 0; integer8 < this.b; integer8++) {
					if (!this.a[integer8][integer7][integer6].test(loadingCache.getUnchecked(a(fu, fz2, fz3, integer6, integer7, integer8)))) {
						return null;
					}
				}
			}
		}

		return new cfo.b(fu, fz2, fz3, loadingCache, this.d, this.c, this.b);
	}

	@Nullable
	public cfo.b a(bqd bqd, fu fu) {
		LoadingCache<fu, cfn> loadingCache4 = a(bqd, false);
		int integer5 = Math.max(Math.max(this.d, this.c), this.b);

		for (fu fu7 : fu.a(fu, fu.b(integer5 - 1, integer5 - 1, integer5 - 1))) {
			for (fz fz11 : fz.values()) {
				for (fz fz15 : fz.values()) {
					if (fz15 != fz11 && fz15 != fz11.f()) {
						cfo.b b16 = this.a(fu7, fz11, fz15, loadingCache4);
						if (b16 != null) {
							return b16;
						}
					}
				}
			}
		}

		return null;
	}

	public static LoadingCache<fu, cfn> a(bqd bqd, boolean boolean2) {
		return CacheBuilder.newBuilder().build(new cfo.a(bqd, boolean2));
	}

	protected static fu a(fu fu, fz fz2, fz fz3, int integer4, int integer5, int integer6) {
		if (fz2 != fz3 && fz2 != fz3.f()) {
			gr gr7 = new gr(fz2.i(), fz2.j(), fz2.k());
			gr gr8 = new gr(fz3.i(), fz3.j(), fz3.k());
			gr gr9 = gr7.d(gr8);
			return fu.b(
				gr8.u() * -integer5 + gr9.u() * integer4 + gr7.u() * integer6,
				gr8.v() * -integer5 + gr9.v() * integer4 + gr7.v() * integer6,
				gr8.w() * -integer5 + gr9.w() * integer4 + gr7.w() * integer6
			);
		} else {
			throw new IllegalArgumentException("Invalid forwards & up combination");
		}
	}

	static class a extends CacheLoader<fu, cfn> {
		private final bqd a;
		private final boolean b;

		public a(bqd bqd, boolean boolean2) {
			this.a = bqd;
			this.b = boolean2;
		}

		public cfn load(fu fu) throws Exception {
			return new cfn(this.a, fu, this.b);
		}
	}

	public static class b {
		private final fu a;
		private final fz b;
		private final fz c;
		private final LoadingCache<fu, cfn> d;
		private final int e;
		private final int f;
		private final int g;

		public b(fu fu, fz fz2, fz fz3, LoadingCache<fu, cfn> loadingCache, int integer5, int integer6, int integer7) {
			this.a = fu;
			this.b = fz2;
			this.c = fz3;
			this.d = loadingCache;
			this.e = integer5;
			this.f = integer6;
			this.g = integer7;
		}

		public fu a() {
			return this.a;
		}

		public fz b() {
			return this.b;
		}

		public fz c() {
			return this.c;
		}

		public int d() {
			return this.e;
		}

		public int e() {
			return this.f;
		}

		public cfn a(int integer1, int integer2, int integer3) {
			return this.d.getUnchecked(cfo.a(this.a, this.b(), this.c(), integer1, integer2, integer3));
		}

		public String toString() {
			return MoreObjects.toStringHelper(this).add("up", this.c).add("forwards", this.b).add("frontTopLeft", this.a).toString();
		}

		public cfo.c a(fz fz, fu fu, double double3, dem dem, double double5) {
			fz fz9 = this.b();
			fz fz10 = fz9.g();
			double double13 = (double)(this.a().v() + 1) - double3 * (double)this.e();
			double double11;
			double double15;
			if (fz10 == fz.NORTH) {
				double11 = (double)fu.u() + 0.5;
				double15 = (double)(this.a().w() + 1) - (1.0 - double5) * (double)this.d();
			} else if (fz10 == fz.SOUTH) {
				double11 = (double)fu.u() + 0.5;
				double15 = (double)this.a().w() + (1.0 - double5) * (double)this.d();
			} else if (fz10 == fz.WEST) {
				double11 = (double)(this.a().u() + 1) - (1.0 - double5) * (double)this.d();
				double15 = (double)fu.w() + 0.5;
			} else {
				double11 = (double)this.a().u() + (1.0 - double5) * (double)this.d();
				double15 = (double)fu.w() + 0.5;
			}

			double double17;
			double double19;
			if (fz9.f() == fz) {
				double17 = dem.b;
				double19 = dem.d;
			} else if (fz9.f() == fz.f()) {
				double17 = -dem.b;
				double19 = -dem.d;
			} else if (fz9.f() == fz.g()) {
				double17 = -dem.d;
				double19 = dem.b;
			} else {
				double17 = dem.d;
				double19 = -dem.b;
			}

			int integer21 = (fz9.d() - fz.f().d()) * 90;
			return new cfo.c(new dem(double11, double13, double15), new dem(double17, dem.c, double19), integer21);
		}
	}

	public static class c {
		public final dem a;
		public final dem b;
		public final int c;

		public c(dem dem1, dem dem2, int integer) {
			this.a = dem1;
			this.b = dem2;
			this.c = integer;
		}
	}
}

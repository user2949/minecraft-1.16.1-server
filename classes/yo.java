import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class yo {
	public static final Either<cgy, yo.a> a = Either.right(yo.a.b);
	public static final CompletableFuture<Either<cgy, yo.a>> b = CompletableFuture.completedFuture(a);
	public static final Either<chj, yo.a> c = Either.right(yo.a.b);
	private static final CompletableFuture<Either<chj, yo.a>> d = CompletableFuture.completedFuture(c);
	private static final List<chc> e = chc.a();
	private static final yo.b[] f = yo.b.values();
	private final AtomicReferenceArray<CompletableFuture<Either<cgy, yo.a>>> g = new AtomicReferenceArray(e.size());
	private volatile CompletableFuture<Either<chj, yo.a>> h = d;
	private volatile CompletableFuture<Either<chj, yo.a>> i = d;
	private volatile CompletableFuture<Either<chj, yo.a>> j = d;
	private CompletableFuture<cgy> k = CompletableFuture.completedFuture(null);
	private int l;
	private int m;
	private int n;
	private final bph o;
	private final short[] p = new short[64];
	private int q;
	private int r;
	private int s;
	private int t;
	private final cwr u;
	private final yo.c v;
	private final yo.d w;
	private boolean x;

	public yo(bph bph, int integer, cwr cwr, yo.c c, yo.d d) {
		this.o = bph;
		this.u = cwr;
		this.v = c;
		this.w = d;
		this.l = yp.a + 1;
		this.m = this.l;
		this.n = this.l;
		this.a(integer);
	}

	public CompletableFuture<Either<cgy, yo.a>> a(chc chc) {
		CompletableFuture<Either<cgy, yo.a>> completableFuture3 = (CompletableFuture<Either<cgy, yo.a>>)this.g.get(chc.c());
		return completableFuture3 == null ? b : completableFuture3;
	}

	public CompletableFuture<Either<cgy, yo.a>> b(chc chc) {
		return b(this.m).b(chc) ? this.a(chc) : b;
	}

	public CompletableFuture<Either<chj, yo.a>> a() {
		return this.i;
	}

	public CompletableFuture<Either<chj, yo.a>> b() {
		return this.j;
	}

	public CompletableFuture<Either<chj, yo.a>> c() {
		return this.h;
	}

	@Nullable
	public chj d() {
		CompletableFuture<Either<chj, yo.a>> completableFuture2 = this.a();
		Either<chj, yo.a> either3 = (Either<chj, yo.a>)completableFuture2.getNow(null);
		return either3 == null ? null : (chj)either3.left().orElse(null);
	}

	@Nullable
	public cgy f() {
		for (int integer2 = e.size() - 1; integer2 >= 0; integer2--) {
			chc chc3 = (chc)e.get(integer2);
			CompletableFuture<Either<cgy, yo.a>> completableFuture4 = this.a(chc3);
			if (!completableFuture4.isCompletedExceptionally()) {
				Optional<cgy> optional5 = ((Either)completableFuture4.getNow(a)).left();
				if (optional5.isPresent()) {
					return (cgy)optional5.get();
				}
			}
		}

		return null;
	}

	public CompletableFuture<cgy> g() {
		return this.k;
	}

	public void a(int integer1, int integer2, int integer3) {
		chj chj5 = this.d();
		if (chj5 != null) {
			this.r |= 1 << (integer2 >> 4);
			if (this.q < 64) {
				short short6 = (short)(integer1 << 12 | integer3 << 8 | integer2);

				for (int integer7 = 0; integer7 < this.q; integer7++) {
					if (this.p[integer7] == short6) {
						return;
					}
				}

				this.p[this.q++] = short6;
			}
		}
	}

	public void a(bqi bqi, int integer) {
		chj chj4 = this.d();
		if (chj4 != null) {
			chj4.a(true);
			if (bqi == bqi.SKY) {
				this.t |= 1 << integer - -1;
			} else {
				this.s |= 1 << integer - -1;
			}
		}
	}

	public void a(chj chj) {
		if (this.q != 0 || this.t != 0 || this.s != 0) {
			bqb bqb3 = chj.x();
			if (this.q < 64 && (this.t != 0 || this.s != 0)) {
				this.a(new ow(chj.g(), this.u, this.t, this.s, false), true);
				this.t = 0;
				this.s = 0;
			}

			if (this.q == 1) {
				int integer4 = (this.p[0] >> 12 & 15) + this.o.b * 16;
				int integer5 = this.p[0] & 255;
				int integer6 = (this.p[0] >> 8 & 15) + this.o.c * 16;
				fu fu7 = new fu(integer4, integer5, integer6);
				this.a(new nx(bqb3, fu7), false);
				if (bqb3.d_(fu7).b().q()) {
					this.a(bqb3, fu7);
				}
			} else if (this.q == 64) {
				this.a(new ot(chj, this.r, false), false);
			} else if (this.q != 0) {
				this.a(new ob(this.q, this.p, chj), false);

				for (int integer4 = 0; integer4 < this.q; integer4++) {
					int integer5 = (this.p[integer4] >> 12 & 15) + this.o.b * 16;
					int integer6 = this.p[integer4] & 255;
					int integer7 = (this.p[integer4] >> 8 & 15) + this.o.c * 16;
					fu fu8 = new fu(integer5, integer6, integer7);
					if (bqb3.d_(fu8).b().q()) {
						this.a(bqb3, fu8);
					}
				}
			}

			this.q = 0;
			this.r = 0;
		}
	}

	private void a(bqb bqb, fu fu) {
		cdl cdl4 = bqb.c(fu);
		if (cdl4 != null) {
			nv nv5 = cdl4.a();
			if (nv5 != null) {
				this.a(nv5, false);
			}
		}
	}

	private void a(ni<?> ni, boolean boolean2) {
		this.w.a(this.o, boolean2).forEach(ze -> ze.b.a(ni));
	}

	public CompletableFuture<Either<cgy, yo.a>> a(chc chc, yp yp) {
		int integer4 = chc.c();
		CompletableFuture<Either<cgy, yo.a>> completableFuture5 = (CompletableFuture<Either<cgy, yo.a>>)this.g.get(integer4);
		if (completableFuture5 != null) {
			Either<cgy, yo.a> either6 = (Either<cgy, yo.a>)completableFuture5.getNow(null);
			if (either6 == null || either6.left().isPresent()) {
				return completableFuture5;
			}
		}

		if (b(this.m).b(chc)) {
			CompletableFuture<Either<cgy, yo.a>> completableFuture6 = yp.a(this, chc);
			this.a(completableFuture6);
			this.g.set(integer4, completableFuture6);
			return completableFuture6;
		} else {
			return completableFuture5 == null ? b : completableFuture5;
		}
	}

	private void a(CompletableFuture<? extends Either<? extends cgy, yo.a>> completableFuture) {
		this.k = this.k.thenCombine(completableFuture, (cgy, either) -> either.map(cgyx -> cgyx, a -> cgy));
	}

	public bph i() {
		return this.o;
	}

	public int j() {
		return this.m;
	}

	public int k() {
		return this.n;
	}

	private void d(int integer) {
		this.n = integer;
	}

	public void a(int integer) {
		this.m = integer;
	}

	protected void a(yp yp) {
		chc chc3 = b(this.l);
		chc chc4 = b(this.m);
		boolean boolean5 = this.l <= yp.a;
		boolean boolean6 = this.m <= yp.a;
		yo.b b7 = c(this.l);
		yo.b b8 = c(this.m);
		if (boolean5) {
			Either<cgy, yo.a> either9 = Either.right(new yo.a() {
				public String toString() {
					return "Unloaded ticket level " + yo.this.o.toString();
				}
			});

			for (int integer10 = boolean6 ? chc4.c() + 1 : 0; integer10 <= chc3.c(); integer10++) {
				CompletableFuture<Either<cgy, yo.a>> completableFuture11 = (CompletableFuture<Either<cgy, yo.a>>)this.g.get(integer10);
				if (completableFuture11 != null) {
					completableFuture11.complete(either9);
				} else {
					this.g.set(integer10, CompletableFuture.completedFuture(either9));
				}
			}
		}

		boolean boolean9 = b7.a(yo.b.BORDER);
		boolean boolean10 = b8.a(yo.b.BORDER);
		this.x |= boolean10;
		if (!boolean9 && boolean10) {
			this.h = yp.b(this);
			this.a(this.h);
		}

		if (boolean9 && !boolean10) {
			CompletableFuture<Either<chj, yo.a>> completableFuture11 = this.h;
			this.h = d;
			this.a(completableFuture11.thenApply(either -> either.ifLeft(yp::a)));
		}

		boolean boolean11 = b7.a(yo.b.TICKING);
		boolean boolean12 = b8.a(yo.b.TICKING);
		if (!boolean11 && boolean12) {
			this.i = yp.a(this);
			this.a(this.i);
		}

		if (boolean11 && !boolean12) {
			this.i.complete(c);
			this.i = d;
		}

		boolean boolean13 = b7.a(yo.b.ENTITY_TICKING);
		boolean boolean14 = b8.a(yo.b.ENTITY_TICKING);
		if (!boolean13 && boolean14) {
			if (this.j != d) {
				throw (IllegalStateException)v.c(new IllegalStateException());
			}

			this.j = yp.b(this.o);
			this.a(this.j);
		}

		if (boolean13 && !boolean14) {
			this.j.complete(c);
			this.j = d;
		}

		this.v.a(this.o, this::k, this.m, this::d);
		this.l = this.m;
	}

	public static chc b(int integer) {
		return integer < 33 ? chc.m : chc.a(integer - 33);
	}

	public static yo.b c(int integer) {
		return f[aec.a(33 - integer + 1, 0, f.length - 1)];
	}

	public boolean l() {
		return this.x;
	}

	public void m() {
		this.x = c(this.m).a(yo.b.BORDER);
	}

	public void a(chi chi) {
		for (int integer3 = 0; integer3 < this.g.length(); integer3++) {
			CompletableFuture<Either<cgy, yo.a>> completableFuture4 = (CompletableFuture<Either<cgy, yo.a>>)this.g.get(integer3);
			if (completableFuture4 != null) {
				Optional<cgy> optional5 = ((Either)completableFuture4.getNow(a)).left();
				if (optional5.isPresent() && optional5.get() instanceof chr) {
					this.g.set(integer3, CompletableFuture.completedFuture(Either.left(chi)));
				}
			}
		}

		this.a(CompletableFuture.completedFuture(Either.left(chi.u())));
	}

	public interface a {
		yo.a b = new yo.a() {
			public String toString() {
				return "UNLOADED";
			}
		};
	}

	public static enum b {
		INACCESSIBLE,
		BORDER,
		TICKING,
		ENTITY_TICKING;

		public boolean a(yo.b b) {
			return this.ordinal() >= b.ordinal();
		}
	}

	public interface c {
		void a(bph bph, IntSupplier intSupplier, int integer, IntConsumer intConsumer);
	}

	public interface d {
		Stream<ze> a(bph bph, boolean boolean2);
	}
}

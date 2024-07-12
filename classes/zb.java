import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class zb extends chb {
	private static final List<chc> b = chc.a();
	private final yv c;
	private final cha d;
	private final zd e;
	private final Thread f;
	private final zg g;
	private final zb.a h;
	public final yp a;
	private final daa i;
	private long j;
	private boolean k = true;
	private boolean l = true;
	private final long[] m = new long[4];
	private final chc[] n = new chc[4];
	private final cgy[] o = new cgy[4];
	@Nullable
	private bqj.d p;

	public zb(zd zd, dae.a a, DataFixer dataFixer, cva cva, Executor executor, cha cha, int integer, boolean boolean8, zm zm, Supplier<daa> supplier) {
		this.e = zd;
		this.h = new zb.a(zd);
		this.d = cha;
		this.f = Thread.currentThread();
		File file12 = a.a(zd.W());
		File file13 = new File(file12, "data");
		file13.mkdirs();
		this.i = new daa(file13, dataFixer);
		this.a = new yp(zd, a, dataFixer, cva, executor, this.h, this, this.g(), zm, supplier, integer, boolean8);
		this.g = this.a.a();
		this.c = this.a.e();
		this.n();
	}

	public zg l() {
		return this.g;
	}

	@Nullable
	private yo a(long long1) {
		return this.a.b(long1);
	}

	public int b() {
		return this.a.c();
	}

	private void a(long long1, cgy cgy, chc chc) {
		for (int integer6 = 3; integer6 > 0; integer6--) {
			this.m[integer6] = this.m[integer6 - 1];
			this.n[integer6] = this.n[integer6 - 1];
			this.o[integer6] = this.o[integer6 - 1];
		}

		this.m[0] = long1;
		this.n[0] = chc;
		this.o[0] = cgy;
	}

	@Nullable
	@Override
	public cgy a(int integer1, int integer2, chc chc, boolean boolean4) {
		if (Thread.currentThread() != this.f) {
			return (cgy)CompletableFuture.supplyAsync(() -> this.a(integer1, integer2, chc, boolean4), this.h).join();
		} else {
			ami ami6 = this.e.X();
			ami6.c("getChunk");
			long long7 = bph.a(integer1, integer2);

			for (int integer9 = 0; integer9 < 4; integer9++) {
				if (long7 == this.m[integer9] && chc == this.n[integer9]) {
					cgy cgy10 = this.o[integer9];
					if (cgy10 != null || !boolean4) {
						return cgy10;
					}
				}
			}

			ami6.c("getChunkCacheMiss");
			CompletableFuture<Either<cgy, yo.a>> completableFuture9 = this.c(integer1, integer2, chc, boolean4);
			this.h.c(completableFuture9::isDone);
			cgy cgy10 = ((Either)completableFuture9.join()).map(cgy -> cgy, a -> {
				if (boolean4) {
					throw (IllegalStateException)v.c(new IllegalStateException("Chunk not there when requested: " + a));
				} else {
					return null;
				}
			});
			this.a(long7, cgy10, chc);
			return cgy10;
		}
	}

	@Nullable
	@Override
	public chj a(int integer1, int integer2) {
		if (Thread.currentThread() != this.f) {
			return null;
		} else {
			this.e.X().c("getChunkNow");
			long long4 = bph.a(integer1, integer2);

			for (int integer6 = 0; integer6 < 4; integer6++) {
				if (long4 == this.m[integer6] && this.n[integer6] == chc.m) {
					cgy cgy7 = this.o[integer6];
					return cgy7 instanceof chj ? (chj)cgy7 : null;
				}
			}

			yo yo6 = this.a(long4);
			if (yo6 == null) {
				return null;
			} else {
				Either<cgy, yo.a> either7 = (Either<cgy, yo.a>)yo6.b(chc.m).getNow(null);
				if (either7 == null) {
					return null;
				} else {
					cgy cgy8 = (cgy)either7.left().orElse(null);
					if (cgy8 != null) {
						this.a(long4, cgy8, chc.m);
						if (cgy8 instanceof chj) {
							return (chj)cgy8;
						}
					}

					return null;
				}
			}
		}
	}

	private void n() {
		Arrays.fill(this.m, bph.a);
		Arrays.fill(this.n, null);
		Arrays.fill(this.o, null);
	}

	private CompletableFuture<Either<cgy, yo.a>> c(int integer1, int integer2, chc chc, boolean boolean4) {
		bph bph6 = new bph(integer1, integer2);
		long long7 = bph6.a();
		int integer9 = 33 + chc.a(chc);
		yo yo10 = this.a(long7);
		if (boolean4) {
			this.c.a(zi.h, bph6, integer9, bph6);
			if (this.a(yo10, integer9)) {
				ami ami11 = this.e.X();
				ami11.a("chunkLoad");
				this.o();
				yo10 = this.a(long7);
				ami11.c();
				if (this.a(yo10, integer9)) {
					throw (IllegalStateException)v.c(new IllegalStateException("No chunk holder after ticket has been added"));
				}
			}
		}

		return this.a(yo10, integer9) ? yo.b : yo10.a(chc, this.a);
	}

	private boolean a(@Nullable yo yo, int integer) {
		return yo == null || yo.j() > integer;
	}

	@Override
	public boolean b(int integer1, int integer2) {
		yo yo4 = this.a(new bph(integer1, integer2).a());
		int integer5 = 33 + chc.a(chc.m);
		return !this.a(yo4, integer5);
	}

	@Override
	public bpg c(int integer1, int integer2) {
		long long4 = bph.a(integer1, integer2);
		yo yo6 = this.a(long4);
		if (yo6 == null) {
			return null;
		} else {
			int integer7 = b.size() - 1;

			while (true) {
				chc chc8 = (chc)b.get(integer7);
				Optional<cgy> optional9 = ((Either)yo6.a(chc8).getNow(yo.a)).left();
				if (optional9.isPresent()) {
					return (bpg)optional9.get();
				}

				if (chc8 == chc.j.e()) {
					return null;
				}

				integer7--;
			}
		}
	}

	public bqb m() {
		return this.e;
	}

	public boolean d() {
		return this.h.x();
	}

	private boolean o() {
		boolean boolean2 = this.c.a(this.a);
		boolean boolean3 = this.a.b();
		if (!boolean2 && !boolean3) {
			return false;
		} else {
			this.n();
			return true;
		}
	}

	@Override
	public boolean a(aom aom) {
		long long3 = bph.a(aec.c(aom.cC()) >> 4, aec.c(aom.cG()) >> 4);
		return this.a(long3, yo::b);
	}

	@Override
	public boolean a(bph bph) {
		return this.a(bph.a(), yo::b);
	}

	@Override
	public boolean a(fu fu) {
		long long3 = bph.a(fu.u() >> 4, fu.w() >> 4);
		return this.a(long3, yo::a);
	}

	private boolean a(long long1, Function<yo, CompletableFuture<Either<chj, yo.a>>> function) {
		yo yo5 = this.a(long1);
		if (yo5 == null) {
			return false;
		} else {
			Either<chj, yo.a> either6 = (Either<chj, yo.a>)((CompletableFuture)function.apply(yo5)).getNow(yo.c);
			return either6.left().isPresent();
		}
	}

	public void a(boolean boolean1) {
		this.o();
		this.a.a(boolean1);
	}

	@Override
	public void close() throws IOException {
		this.a(true);
		this.g.close();
		this.a.close();
	}

	public void a(BooleanSupplier booleanSupplier) {
		this.e.X().a("purge");
		this.c.a();
		this.o();
		this.e.X().b("chunks");
		this.p();
		this.e.X().b("unload");
		this.a.a(booleanSupplier);
		this.e.X().c();
		this.n();
	}

	private void p() {
		long long2 = this.e.Q();
		long long4 = long2 - this.j;
		this.j = long2;
		dab dab6 = this.e.u_();
		boolean boolean7 = this.e.Z();
		boolean boolean8 = this.e.S().b(bpx.d);
		if (!boolean7) {
			this.e.X().a("pollingChunks");
			int integer9 = this.e.S().c(bpx.m);
			boolean boolean10 = dab6.d() % 400L == 0L;
			this.e.X().a("naturalSpawnCount");
			int integer11 = this.c.b();
			bqj.d d12 = bqj.a(integer11, this.e.z(), this::a);
			this.p = d12;
			this.e.X().c();
			List<yo> list13 = Lists.<yo>newArrayList(this.a.f());
			Collections.shuffle(list13);
			list13.forEach(yo -> {
				Optional<chj> optional9 = ((Either)yo.a().getNow(yo.c)).left();
				if (optional9.isPresent()) {
					this.e.X().a("broadcast");
					yo.a((chj)optional9.get());
					this.e.X().c();
					Optional<chj> optional10 = ((Either)yo.b().getNow(yo.c)).left();
					if (optional10.isPresent()) {
						chj chj11 = (chj)optional10.get();
						bph bph12 = yo.i();
						if (!this.a.d(bph12)) {
							chj11.b(chj11.q() + long4);
							if (boolean8 && (this.k || this.l) && this.e.f().a(chj11.g())) {
								bqj.a(this.e, chj11, d12, this.l, this.k, boolean10);
							}

							this.e.a(chj11, integer9);
						}
					}
				}
			});
			this.e.X().a("customSpawners");
			if (boolean8) {
				this.e.a(this.k, this.l);
			}

			this.e.X().c();
			this.e.X().c();
		}

		this.a.g();
	}

	private void a(long long1, Consumer<chj> consumer) {
		yo yo5 = this.a(long1);
		if (yo5 != null) {
			((Either)yo5.c().getNow(yo.c)).left().ifPresent(consumer);
		}
	}

	@Override
	public String e() {
		return "ServerChunkCache: " + this.h();
	}

	@VisibleForTesting
	public int f() {
		return this.h.bg();
	}

	public cha g() {
		return this.d;
	}

	public int h() {
		return this.a.d();
	}

	public void b(fu fu) {
		int integer3 = fu.u() >> 4;
		int integer4 = fu.w() >> 4;
		yo yo5 = this.a(bph.a(integer3, integer4));
		if (yo5 != null) {
			yo5.a(fu.u() & 15, fu.v(), fu.w() & 15);
		}
	}

	@Override
	public void a(bqi bqi, go go) {
		this.h.execute(() -> {
			yo yo4 = this.a(go.r().a());
			if (yo4 != null) {
				yo4.a(bqi, go.b());
			}
		});
	}

	public <T> void a(zi<T> zi, bph bph, int integer, T object) {
		this.c.c(zi, bph, integer, object);
	}

	public <T> void b(zi<T> zi, bph bph, int integer, T object) {
		this.c.d(zi, bph, integer, object);
	}

	@Override
	public void a(bph bph, boolean boolean2) {
		this.c.a(bph, boolean2);
	}

	public void a(ze ze) {
		this.a.a(ze);
	}

	public void b(aom aom) {
		this.a.b(aom);
	}

	public void c(aom aom) {
		this.a.a(aom);
	}

	public void a(aom aom, ni<?> ni) {
		this.a.b(aom, ni);
	}

	public void b(aom aom, ni<?> ni) {
		this.a.a(aom, ni);
	}

	public void a(int integer) {
		this.a.a(integer);
	}

	@Override
	public void a(boolean boolean1, boolean boolean2) {
		this.k = boolean1;
		this.l = boolean2;
	}

	public daa i() {
		return this.i;
	}

	public axz j() {
		return this.a.h();
	}

	@Nullable
	public bqj.d k() {
		return this.p;
	}

	final class a extends amn<Runnable> {
		private a(bqb bqb) {
			super("Chunk source main thread executor for " + bqb.W().a());
		}

		@Override
		protected Runnable e(Runnable runnable) {
			return runnable;
		}

		@Override
		protected boolean d(Runnable runnable) {
			return true;
		}

		@Override
		protected boolean at() {
			return true;
		}

		@Override
		protected Thread au() {
			return zb.this.f;
		}

		@Override
		protected void c(Runnable runnable) {
			zb.this.e.X().c("runTask");
			super.c(runnable);
		}

		@Override
		protected boolean x() {
			if (zb.this.o()) {
				return true;
			} else {
				zb.this.g.A_();
				return super.x();
			}
		}
	}
}

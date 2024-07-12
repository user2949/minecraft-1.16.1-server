import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class yp extends chw implements yo.d {
	private static final Logger c = LogManager.getLogger();
	public static final int a = 33 + chc.b();
	private final Long2ObjectLinkedOpenHashMap<yo> d = new Long2ObjectLinkedOpenHashMap<>();
	private volatile Long2ObjectLinkedOpenHashMap<yo> e = this.d.clone();
	private final Long2ObjectLinkedOpenHashMap<yo> f = new Long2ObjectLinkedOpenHashMap<>();
	private final LongSet g = new LongOpenHashSet();
	private final zd h;
	private final zg i;
	private final amn<Runnable> j;
	private final cha k;
	private final Supplier<daa> l;
	private final axz m;
	private final LongSet n = new LongOpenHashSet();
	private boolean o;
	private final yr p;
	private final amp<yr.a<Runnable>> q;
	private final amp<yr.a<Runnable>> r;
	private final zm s;
	private final yp.a t;
	private final AtomicInteger u = new AtomicInteger();
	private final cva v;
	private final File w;
	private final yx x = new yx();
	private final Int2ObjectMap<yp.b> y = new Int2ObjectOpenHashMap<>();
	private final Long2ByteMap z = new Long2ByteOpenHashMap();
	private final Queue<Runnable> A = Queues.<Runnable>newConcurrentLinkedQueue();
	private int B;

	public yp(
		zd zd,
		dae.a a,
		DataFixer dataFixer,
		cva cva,
		Executor executor,
		amn<Runnable> amn,
		chl chl,
		cha cha,
		zm zm,
		Supplier<daa> supplier,
		int integer,
		boolean boolean12
	) {
		super(new File(a.a(zd.W()), "region"), dataFixer, boolean12);
		this.v = cva;
		this.w = a.a(zd.W());
		this.h = zd;
		this.k = cha;
		this.j = amn;
		amq<Runnable> amq14 = amq.a(executor, "worldgen");
		amp<Runnable> amp15 = amp.a("main", amn::h);
		this.s = zm;
		amq<Runnable> amq16 = amq.a(executor, "light");
		this.p = new yr(ImmutableList.of(amq14, amp15, amq16), executor, Integer.MAX_VALUE);
		this.q = this.p.a(amq14, false);
		this.r = this.p.a(amp15, false);
		this.i = new zg(chl, this, this.h.m().d(), amq16, this.p.a(amq16, false));
		this.t = new yp.a(executor, amn);
		this.l = supplier;
		this.m = new axz(new File(this.w, "poi"), dataFixer, boolean12);
		this.a(integer);
	}

	private static double a(bph bph, aom aom) {
		double double3 = (double)(bph.b * 16 + 8);
		double double5 = (double)(bph.c * 16 + 8);
		double double7 = double3 - aom.cC();
		double double9 = double5 - aom.cG();
		return double7 * double7 + double9 * double9;
	}

	private static int b(bph bph, ze ze, boolean boolean3) {
		int integer4;
		int integer5;
		if (boolean3) {
			go go6 = ze.N();
			integer4 = go6.a();
			integer5 = go6.c();
		} else {
			integer4 = aec.c(ze.cC() / 16.0);
			integer5 = aec.c(ze.cG() / 16.0);
		}

		return a(bph, integer4, integer5);
	}

	private static int a(bph bph, int integer2, int integer3) {
		int integer4 = bph.b - integer2;
		int integer5 = bph.c - integer3;
		return Math.max(Math.abs(integer4), Math.abs(integer5));
	}

	protected zg a() {
		return this.i;
	}

	@Nullable
	protected yo a(long long1) {
		return this.d.get(long1);
	}

	@Nullable
	protected yo b(long long1) {
		return this.e.get(long1);
	}

	protected IntSupplier c(long long1) {
		return () -> {
			yo yo4 = this.b(long1);
			return yo4 == null ? yq.a - 1 : Math.min(yo4.k(), yq.a - 1);
		};
	}

	private CompletableFuture<Either<List<cgy>, yo.a>> a(bph bph, int integer, IntFunction<chc> intFunction) {
		List<CompletableFuture<Either<cgy, yo.a>>> list5 = Lists.<CompletableFuture<Either<cgy, yo.a>>>newArrayList();
		int integer6 = bph.b;
		int integer7 = bph.c;

		for (int integer8 = -integer; integer8 <= integer; integer8++) {
			for (int integer9 = -integer; integer9 <= integer; integer9++) {
				int integer10 = Math.max(Math.abs(integer9), Math.abs(integer8));
				final bph bph11 = new bph(integer6 + integer9, integer7 + integer8);
				long long12 = bph11.a();
				yo yo14 = this.a(long12);
				if (yo14 == null) {
					return CompletableFuture.completedFuture(Either.right(new yo.a() {
						public String toString() {
							return "Unloaded " + bph11.toString();
						}
					}));
				}

				chc chc15 = (chc)intFunction.apply(integer10);
				CompletableFuture<Either<cgy, yo.a>> completableFuture16 = yo14.a(chc15, this);
				list5.add(completableFuture16);
			}
		}

		CompletableFuture<List<Either<cgy, yo.a>>> completableFuture8 = v.b(list5);
		return completableFuture8.thenApply(
			list -> {
				List<cgy> list6 = Lists.<cgy>newArrayList();
				int integer7x = 0;
	
				for (final Either<cgy, yo.a> either9 : list) {
					Optional<cgy> optional10 = either9.left();
					if (!optional10.isPresent()) {
						final int integer11 = integer7x;
						return Either.right(
							new yo.a() {
								public String toString() {
									return "Unloaded "
										+ new bph(integer6 + integer11 % (integer * 2 + 1), integer7 + integer11 / (integer * 2 + 1))
										+ " "
										+ ((yo.a)either9.right().get()).toString();
								}
							}
						);
					}
	
					list6.add(optional10.get());
					integer7x++;
				}
	
				return Either.left(list6);
			}
		);
	}

	public CompletableFuture<Either<chj, yo.a>> b(bph bph) {
		return this.a(bph, 2, integer -> chc.m).thenApplyAsync(either -> either.mapLeft(list -> (chj)list.get(list.size() / 2)), this.j);
	}

	@Nullable
	private yo a(long long1, int integer2, @Nullable yo yo, int integer4) {
		if (integer4 > a && integer2 > a) {
			return yo;
		} else {
			if (yo != null) {
				yo.a(integer2);
			}

			if (yo != null) {
				if (integer2 > a) {
					this.n.add(long1);
				} else {
					this.n.remove(long1);
				}
			}

			if (integer2 <= a && yo == null) {
				yo = this.f.remove(long1);
				if (yo != null) {
					yo.a(integer2);
				} else {
					yo = new yo(new bph(long1), integer2, this.i, this.p, this);
				}

				this.d.put(long1, yo);
				this.o = true;
			}

			return yo;
		}
	}

	@Override
	public void close() throws IOException {
		try {
			this.p.close();
			this.m.close();
		} finally {
			super.close();
		}
	}

	protected void a(boolean boolean1) {
		if (boolean1) {
			List<yo> list3 = (List<yo>)this.e.values().stream().filter(yo::l).peek(yo::m).collect(Collectors.toList());
			MutableBoolean mutableBoolean4 = new MutableBoolean();

			do {
				mutableBoolean4.setFalse();
				list3.stream().map(yo -> {
					CompletableFuture<cgy> completableFuture3;
					do {
						completableFuture3 = yo.g();
						this.j.c(completableFuture3::isDone);
					} while (completableFuture3 != yo.g());

					return (cgy)completableFuture3.join();
				}).filter(cgy -> cgy instanceof chi || cgy instanceof chj).filter(this::a).forEach(cgy -> mutableBoolean4.setTrue());
			} while (mutableBoolean4.isTrue());

			this.b((BooleanSupplier)(() -> true));
			this.i();
			c.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", this.w.getName());
		} else {
			this.e.values().stream().filter(yo::l).forEach(yo -> {
				cgy cgy3 = (cgy)yo.g().getNow(null);
				if (cgy3 instanceof chi || cgy3 instanceof chj) {
					this.a(cgy3);
					yo.m();
				}
			});
		}
	}

	protected void a(BooleanSupplier booleanSupplier) {
		ami ami3 = this.h.X();
		ami3.a("poi");
		this.m.a(booleanSupplier);
		ami3.b("chunk_unload");
		if (!this.h.q()) {
			this.b(booleanSupplier);
		}

		ami3.c();
	}

	private void b(BooleanSupplier booleanSupplier) {
		LongIterator longIterator3 = this.n.iterator();

		for (int integer4 = 0; longIterator3.hasNext() && (booleanSupplier.getAsBoolean() || integer4 < 200 || this.n.size() > 2000); longIterator3.remove()) {
			long long5 = longIterator3.nextLong();
			yo yo7 = this.d.remove(long5);
			if (yo7 != null) {
				this.f.put(long5, yo7);
				this.o = true;
				integer4++;
				this.a(long5, yo7);
			}
		}

		Runnable runnable5;
		while ((booleanSupplier.getAsBoolean() || this.A.size() > 2000) && (runnable5 = (Runnable)this.A.poll()) != null) {
			runnable5.run();
		}
	}

	private void a(long long1, yo yo) {
		CompletableFuture<cgy> completableFuture5 = yo.g();
		completableFuture5.thenAcceptAsync(cgy -> {
			CompletableFuture<cgy> completableFuture7 = yo.g();
			if (completableFuture7 != completableFuture5) {
				this.a(long1, yo);
			} else {
				if (this.f.remove(long1, yo) && cgy != null) {
					if (cgy instanceof chj) {
						((chj)cgy).c(false);
					}

					this.a(cgy);
					if (this.g.remove(long1) && cgy instanceof chj) {
						chj chj8 = (chj)cgy;
						this.h.a(chj8);
					}

					this.i.a(cgy.g());
					this.i.A_();
					this.s.a(cgy.g(), null);
				}
			}
		}, this.A::add).whenComplete((void2, throwable) -> {
			if (throwable != null) {
				c.error("Failed to save chunk " + yo.i(), throwable);
			}
		});
	}

	protected boolean b() {
		if (!this.o) {
			return false;
		} else {
			this.e = this.d.clone();
			this.o = false;
			return true;
		}
	}

	public CompletableFuture<Either<cgy, yo.a>> a(yo yo, chc chc) {
		bph bph4 = yo.i();
		if (chc == chc.a) {
			return this.f(bph4);
		} else {
			CompletableFuture<Either<cgy, yo.a>> completableFuture5 = yo.a(chc.e(), this);
			return completableFuture5.thenComposeAsync(either -> {
				Optional<cgy> optional6 = either.left();
				if (!optional6.isPresent()) {
					return CompletableFuture.completedFuture(either);
				} else {
					if (chc == chc.j) {
						this.t.a(zi.e, bph4, 33 + chc.a(chc.i), bph4);
					}

					cgy cgy7 = (cgy)optional6.get();
					if (cgy7.k().b(chc)) {
						CompletableFuture<Either<cgy, yo.a>> completableFuture8;
						if (chc == chc.j) {
							completableFuture8 = this.b(yo, chc);
						} else {
							completableFuture8 = chc.a(this.h, this.v, this.i, cgy -> this.c(yo), cgy7);
						}

						this.s.a(bph4, chc);
						return completableFuture8;
					} else {
						return this.b(yo, chc);
					}
				}
			}, this.j);
		}
	}

	private CompletableFuture<Either<cgy, yo.a>> f(bph bph) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				this.h.X().c("chunkLoad");
				le le3 = this.i(bph);
				if (le3 != null) {
					boolean boolean4 = le3.c("Level", 10) && le3.p("Level").c("Status", 8);
					if (boolean4) {
						cgy cgy5 = chv.a(this.h, this.v, this.m, bph, le3);
						cgy5.a(this.h.Q());
						this.a(bph, cgy5.k().g());
						return Either.left(cgy5);
					}

					c.error("Chunk file at {} is missing level data, skipping", bph);
				}
			} catch (s var5) {
				Throwable throwable4 = var5.getCause();
				if (!(throwable4 instanceof IOException)) {
					this.g(bph);
					throw var5;
				}

				c.error("Couldn't load chunk {}", bph, throwable4);
			} catch (Exception var6) {
				c.error("Couldn't load chunk {}", bph, var6);
			}

			this.g(bph);
			return Either.left(new chr(bph, cht.a));
		}, this.j);
	}

	private void g(bph bph) {
		this.z.put(bph.a(), (byte)-1);
	}

	private byte a(bph bph, chc.a a) {
		return this.z.put(bph.a(), (byte)(a == chc.a.PROTOCHUNK ? -1 : 1));
	}

	private CompletableFuture<Either<cgy, yo.a>> b(yo yo, chc chc) {
		bph bph4 = yo.i();
		CompletableFuture<Either<List<cgy>, yo.a>> completableFuture5 = this.a(bph4, chc.f(), integer -> this.a(chc, integer));
		this.h.X().c((Supplier<String>)(() -> "chunkGenerate " + chc.d()));
		return completableFuture5.thenComposeAsync(either -> (CompletableFuture)either.map(list -> {
				try {
					CompletableFuture<Either<cgy, yo.a>> completableFuture6 = chc.a(this.h, this.k, this.v, this.i, cgy -> this.c(yo), list);
					this.s.a(bph4, chc);
					return completableFuture6;
				} catch (Exception var8) {
					j j7 = j.a(var8, "Exception generating new chunk");
					k k8 = j7.a("Chunk to be generated");
					k8.a("Location", String.format("%d,%d", bph4.b, bph4.c));
					k8.a("Position hash", bph.a(bph4.b, bph4.c));
					k8.a("Generator", this.k);
					throw new s(j7);
				}
			}, a -> {
				this.c(bph4);
				return CompletableFuture.completedFuture(Either.right(a));
			}), runnable -> this.q.a(yr.a(yo, runnable)));
	}

	protected void c(bph bph) {
		this.j.h(v.a((Runnable)(() -> this.t.b(zi.e, bph, 33 + chc.a(chc.i), bph)), (Supplier<String>)(() -> "release light ticket " + bph)));
	}

	private chc a(chc chc, int integer) {
		chc chc4;
		if (integer == 0) {
			chc4 = chc.e();
		} else {
			chc4 = chc.a(chc.a(chc) + integer);
		}

		return chc4;
	}

	private CompletableFuture<Either<cgy, yo.a>> c(yo yo) {
		CompletableFuture<Either<cgy, yo.a>> completableFuture3 = yo.a(chc.m.e());
		return completableFuture3.thenApplyAsync(either -> {
			chc chc4 = yo.b(yo.j());
			return !chc4.b(chc.m) ? yo.a : either.mapLeft(cgy -> {
				bph bph4 = yo.i();
				chj chj5;
				if (cgy instanceof chi) {
					chj5 = ((chi)cgy).u();
				} else {
					chj5 = new chj(this.h, (chr)cgy);
					yo.a(new chi(chj5));
				}

				chj5.a((Supplier<yo.b>)(() -> yo.c(yo.j())));
				chj5.w();
				if (this.g.add(bph4.a())) {
					chj5.c(true);
					this.h.a(chj5.y().values());
					List<aom> list6 = null;
					adk[] var6 = chj5.z();
					int var7 = var6.length;

					for (int var8 = 0; var8 < var7; var8++) {
						for (aom aom12 : var6[var8]) {
							if (!(aom12 instanceof bec) && !this.h.f(aom12)) {
								if (list6 == null) {
									list6 = Lists.<aom>newArrayList(aom12);
								} else {
									list6.add(aom12);
								}
							}
						}
					}

					if (list6 != null) {
						list6.forEach(chj5::b);
					}
				}

				return chj5;
			});
		}, runnable -> this.r.a(yr.a(runnable, yo.i().a(), yo::j)));
	}

	public CompletableFuture<Either<chj, yo.a>> a(yo yo) {
		bph bph3 = yo.i();
		CompletableFuture<Either<List<cgy>, yo.a>> completableFuture4 = this.a(bph3, 1, integer -> chc.m);
		CompletableFuture<Either<chj, yo.a>> completableFuture5 = completableFuture4.thenApplyAsync(either -> either.flatMap(list -> {
				chj chj2 = (chj)list.get(list.size() / 2);
				chj2.A();
				return Either.left(chj2);
			}), runnable -> this.r.a(yr.a(yo, runnable)));
		completableFuture5.thenAcceptAsync(either -> either.mapLeft(chj -> {
				this.u.getAndIncrement();
				ni<?>[] arr4 = new ni[2];
				this.a(bph3, false).forEach(ze -> this.a(ze, arr4, chj));
				return Either.left(chj);
			}), runnable -> this.r.a(yr.a(yo, runnable)));
		return completableFuture5;
	}

	public CompletableFuture<Either<chj, yo.a>> b(yo yo) {
		return yo.a(chc.m, this).thenApplyAsync(either -> either.mapLeft(cgy -> {
				chj chj2 = (chj)cgy;
				chj2.B();
				return chj2;
			}), runnable -> this.r.a(yr.a(yo, runnable)));
	}

	public int c() {
		return this.u.get();
	}

	private boolean a(cgy cgy) {
		this.m.a(cgy.g());
		if (!cgy.j()) {
			return false;
		} else {
			cgy.a(this.h.Q());
			cgy.a(false);
			bph bph3 = cgy.g();

			try {
				chc chc4 = cgy.k();
				if (chc4.g() != chc.a.LEVELCHUNK) {
					if (this.h(bph3)) {
						return false;
					}

					if (chc4 == chc.a && cgy.h().values().stream().noneMatch(ctz::e)) {
						return false;
					}
				}

				this.h.X().c("chunkSave");
				le le5 = chv.a(this.h, cgy);
				this.a(bph3, le5);
				this.a(bph3, chc4.g());
				return true;
			} catch (Exception var5) {
				c.error("Failed to save chunk {},{}", bph3.b, bph3.c, var5);
				return false;
			}
		}
	}

	private boolean h(bph bph) {
		byte byte3 = this.z.get(bph.a());
		if (byte3 != 0) {
			return byte3 == 1;
		} else {
			le le4;
			try {
				le4 = this.i(bph);
				if (le4 == null) {
					this.g(bph);
					return false;
				}
			} catch (Exception var5) {
				c.error("Failed to read chunk {}", bph, var5);
				this.g(bph);
				return false;
			}

			chc.a a5 = chv.a(le4);
			return this.a(bph, a5) == 1;
		}
	}

	protected void a(int integer) {
		int integer3 = aec.a(integer + 1, 3, 33);
		if (integer3 != this.B) {
			int integer4 = this.B;
			this.B = integer3;
			this.t.a(this.B);

			for (yo yo6 : this.d.values()) {
				bph bph7 = yo6.i();
				ni<?>[] arr8 = new ni[2];
				this.a(bph7, false).forEach(ze -> {
					int integer6 = b(bph7, ze, true);
					boolean boolean7 = integer6 <= integer4;
					boolean boolean8 = integer6 <= this.B;
					this.a(ze, bph7, arr8, boolean7, boolean8);
				});
			}
		}
	}

	protected void a(ze ze, bph bph, ni<?>[] arr, boolean boolean4, boolean boolean5) {
		if (ze.l == this.h) {
			if (boolean5 && !boolean4) {
				yo yo7 = this.b(bph.a());
				if (yo7 != null) {
					chj chj8 = yo7.d();
					if (chj8 != null) {
						this.a(ze, arr, chj8);
					}

					qy.a(this.h, bph);
				}
			}

			if (!boolean5 && boolean4) {
				ze.a(bph);
			}
		}
	}

	public int d() {
		return this.e.size();
	}

	protected yp.a e() {
		return this.t;
	}

	protected Iterable<yo> f() {
		return Iterables.unmodifiableIterable(this.e.values());
	}

	void a(Writer writer) throws IOException {
		ado ado3 = ado.a()
			.a("x")
			.a("z")
			.a("level")
			.a("in_memory")
			.a("status")
			.a("full_status")
			.a("accessible_ready")
			.a("ticking_ready")
			.a("entity_ticking_ready")
			.a("ticket")
			.a("spawning")
			.a("entity_count")
			.a("block_entity_count")
			.a(writer);

		for (Entry<yo> entry5 : this.e.long2ObjectEntrySet()) {
			bph bph6 = new bph(entry5.getLongKey());
			yo yo7 = (yo)entry5.getValue();
			Optional<cgy> optional8 = Optional.ofNullable(yo7.f());
			Optional<chj> optional9 = optional8.flatMap(cgy -> cgy instanceof chj ? Optional.of((chj)cgy) : Optional.empty());
			ado3.a(
				bph6.b,
				bph6.c,
				yo7.j(),
				optional8.isPresent(),
				optional8.map(cgy::k).orElse(null),
				optional9.map(chj::u).orElse(null),
				a(yo7.c()),
				a(yo7.a()),
				a(yo7.b()),
				this.t.c(entry5.getLongKey()),
				!this.d(bph6),
				optional9.map(chj -> Stream.of(chj.z()).mapToInt(adk::size).sum()).orElse(0),
				optional9.map(chj -> chj.y().size()).orElse(0)
			);
		}
	}

	private static String a(CompletableFuture<Either<chj, yo.a>> completableFuture) {
		try {
			Either<chj, yo.a> either2 = (Either<chj, yo.a>)completableFuture.getNow(null);
			return either2 != null ? either2.map(chj -> "done", a -> "unloaded") : "not completed";
		} catch (CompletionException var2) {
			return "failed " + var2.getCause().getMessage();
		} catch (CancellationException var3) {
			return "cancelled";
		}
	}

	@Nullable
	private le i(bph bph) throws IOException {
		le le3 = this.e(bph);
		return le3 == null ? null : this.a(this.h.W(), this.l, le3);
	}

	boolean d(bph bph) {
		long long3 = bph.a();
		return !this.t.d(long3) ? true : this.x.a(long3).noneMatch(ze -> !ze.a_() && a(bph, (aom)ze) < 16384.0);
	}

	private boolean b(ze ze) {
		return ze.a_() && !this.h.S().b(bpx.p);
	}

	void a(ze ze, boolean boolean2) {
		boolean boolean4 = this.b(ze);
		boolean boolean5 = this.x.c(ze);
		int integer6 = aec.c(ze.cC()) >> 4;
		int integer7 = aec.c(ze.cG()) >> 4;
		if (boolean2) {
			this.x.a(bph.a(integer6, integer7), ze, boolean4);
			this.c(ze);
			if (!boolean4) {
				this.t.a(go.a(ze), ze);
			}
		} else {
			go go8 = ze.N();
			this.x.a(go8.r().a(), ze);
			if (!boolean5) {
				this.t.b(go8, ze);
			}
		}

		for (int integer8 = integer6 - this.B; integer8 <= integer6 + this.B; integer8++) {
			for (int integer9 = integer7 - this.B; integer9 <= integer7 + this.B; integer9++) {
				bph bph10 = new bph(integer8, integer9);
				this.a(ze, bph10, new ni[2], !boolean2, boolean2);
			}
		}
	}

	private go c(ze ze) {
		go go3 = go.a(ze);
		ze.a(go3);
		ze.b.a(new pv(go3.a(), go3.c()));
		return go3;
	}

	public void a(ze ze) {
		for (yp.b b4 : this.y.values()) {
			if (b4.c == ze) {
				b4.a(this.h.w());
			} else {
				b4.b(ze);
			}
		}

		int integer3 = aec.c(ze.cC()) >> 4;
		int integer4 = aec.c(ze.cG()) >> 4;
		go go5 = ze.N();
		go go6 = go.a(ze);
		long long7 = go5.r().a();
		long long9 = go6.r().a();
		boolean boolean11 = this.x.d(ze);
		boolean boolean12 = this.b(ze);
		boolean boolean13 = go5.s() != go6.s();
		if (boolean13 || boolean11 != boolean12) {
			this.c(ze);
			if (!boolean11) {
				this.t.b(go5, ze);
			}

			if (!boolean12) {
				this.t.a(go6, ze);
			}

			if (!boolean11 && boolean12) {
				this.x.a(ze);
			}

			if (boolean11 && !boolean12) {
				this.x.b(ze);
			}

			if (long7 != long9) {
				this.x.a(long7, long9, ze);
			}
		}

		int integer14 = go5.a();
		int integer15 = go5.c();
		if (Math.abs(integer14 - integer3) <= this.B * 2 && Math.abs(integer15 - integer4) <= this.B * 2) {
			int integer16 = Math.min(integer3, integer14) - this.B;
			int integer17 = Math.min(integer4, integer15) - this.B;
			int integer18 = Math.max(integer3, integer14) + this.B;
			int integer19 = Math.max(integer4, integer15) + this.B;

			for (int integer20 = integer16; integer20 <= integer18; integer20++) {
				for (int integer21 = integer17; integer21 <= integer19; integer21++) {
					bph bph22 = new bph(integer20, integer21);
					boolean boolean23 = a(bph22, integer14, integer15) <= this.B;
					boolean boolean24 = a(bph22, integer3, integer4) <= this.B;
					this.a(ze, bph22, new ni[2], boolean23, boolean24);
				}
			}
		} else {
			for (int integer16 = integer14 - this.B; integer16 <= integer14 + this.B; integer16++) {
				for (int integer17 = integer15 - this.B; integer17 <= integer15 + this.B; integer17++) {
					bph bph18 = new bph(integer16, integer17);
					boolean boolean19 = true;
					boolean boolean20 = false;
					this.a(ze, bph18, new ni[2], true, false);
				}
			}

			for (int integer16 = integer3 - this.B; integer16 <= integer3 + this.B; integer16++) {
				for (int integer17 = integer4 - this.B; integer17 <= integer4 + this.B; integer17++) {
					bph bph18 = new bph(integer16, integer17);
					boolean boolean19 = false;
					boolean boolean20 = true;
					this.a(ze, bph18, new ni[2], false, true);
				}
			}
		}
	}

	@Override
	public Stream<ze> a(bph bph, boolean boolean2) {
		return this.x.a(bph.a()).filter(ze -> {
			int integer5 = b(bph, ze, true);
			return integer5 > this.B ? false : !boolean2 || integer5 == this.B;
		});
	}

	protected void a(aom aom) {
		if (!(aom instanceof baa)) {
			aoq<?> aoq3 = aom.U();
			int integer4 = aoq3.m() * 16;
			int integer5 = aoq3.n();
			if (this.y.containsKey(aom.V())) {
				throw (IllegalStateException)v.c(new IllegalStateException("Entity is already tracked!"));
			} else {
				yp.b b6 = new yp.b(aom, integer4, integer5, aoq3.o());
				this.y.put(aom.V(), b6);
				b6.a(this.h.w());
				if (aom instanceof ze) {
					ze ze7 = (ze)aom;
					this.a(ze7, true);

					for (yp.b b9 : this.y.values()) {
						if (b9.c != ze7) {
							b9.b(ze7);
						}
					}
				}
			}
		}
	}

	protected void b(aom aom) {
		if (aom instanceof ze) {
			ze ze3 = (ze)aom;
			this.a(ze3, false);

			for (yp.b b5 : this.y.values()) {
				b5.a(ze3);
			}
		}

		yp.b b3 = this.y.remove(aom.V());
		if (b3 != null) {
			b3.a();
		}
	}

	protected void g() {
		List<ze> list2 = Lists.<ze>newArrayList();
		List<ze> list3 = this.h.w();

		for (yp.b b5 : this.y.values()) {
			go go6 = b5.e;
			go go7 = go.a(b5.c);
			if (!Objects.equals(go6, go7)) {
				b5.a(list3);
				aom aom8 = b5.c;
				if (aom8 instanceof ze) {
					list2.add((ze)aom8);
				}

				b5.e = go7;
			}

			b5.b.a();
		}

		if (!list2.isEmpty()) {
			for (yp.b b5 : this.y.values()) {
				b5.a(list2);
			}
		}
	}

	protected void a(aom aom, ni<?> ni) {
		yp.b b4 = this.y.get(aom.V());
		if (b4 != null) {
			b4.a(ni);
		}
	}

	protected void b(aom aom, ni<?> ni) {
		yp.b b4 = this.y.get(aom.V());
		if (b4 != null) {
			b4.b(ni);
		}
	}

	private void a(ze ze, ni<?>[] arr, chj chj) {
		if (arr[0] == null) {
			arr[0] = new ot(chj, 65535, true);
			arr[1] = new ow(chj.g(), this.i, true);
		}

		ze.a(chj.g(), arr[0], arr[1]);
		qy.a(this.h, chj.g());
		List<aom> list5 = Lists.<aom>newArrayList();
		List<aom> list6 = Lists.<aom>newArrayList();

		for (yp.b b8 : this.y.values()) {
			aom aom9 = b8.c;
			if (aom9 != ze && aom9.W == chj.g().b && aom9.Y == chj.g().c) {
				b8.b(ze);
				if (aom9 instanceof aoz && ((aoz)aom9).eD() != null) {
					list5.add(aom9);
				}

				if (!aom9.cm().isEmpty()) {
					list6.add(aom9);
				}
			}
		}

		if (!list5.isEmpty()) {
			for (aom aom8 : list5) {
				ze.b.a(new qa(aom8, ((aoz)aom8).eD()));
			}
		}

		if (!list6.isEmpty()) {
			for (aom aom8 : list6) {
				ze.b.a(new qg(aom8));
			}
		}
	}

	protected axz h() {
		return this.m;
	}

	public CompletableFuture<Void> a(chj chj) {
		return this.j.f(() -> chj.a(this.h));
	}

	class a extends yv {
		protected a(Executor executor2, Executor executor3) {
			super(executor2, executor3);
		}

		@Override
		protected boolean a(long long1) {
			return yp.this.n.contains(long1);
		}

		@Nullable
		@Override
		protected yo b(long long1) {
			return yp.this.a(long1);
		}

		@Nullable
		@Override
		protected yo a(long long1, int integer2, @Nullable yo yo, int integer4) {
			return yp.this.a(long1, integer2, yo, integer4);
		}
	}

	class b {
		private final zc b;
		private final aom c;
		private final int d;
		private go e;
		private final Set<ze> f = Sets.<ze>newHashSet();

		public b(aom aom, int integer3, int integer4, boolean boolean5) {
			this.b = new zc(yp.this.h, aom, integer4, boolean5, this::a);
			this.c = aom;
			this.d = integer3;
			this.e = go.a(aom);
		}

		public boolean equals(Object object) {
			return object instanceof yp.b ? ((yp.b)object).c.V() == this.c.V() : false;
		}

		public int hashCode() {
			return this.c.V();
		}

		public void a(ni<?> ni) {
			for (ze ze4 : this.f) {
				ze4.b.a(ni);
			}
		}

		public void b(ni<?> ni) {
			this.a(ni);
			if (this.c instanceof ze) {
				((ze)this.c).b.a(ni);
			}
		}

		public void a() {
			for (ze ze3 : this.f) {
				this.b.a(ze3);
			}
		}

		public void a(ze ze) {
			if (this.f.remove(ze)) {
				this.b.a(ze);
			}
		}

		public void b(ze ze) {
			if (ze != this.c) {
				dem dem3 = ze.cz().d(this.b.b());
				int integer4 = Math.min(this.b(), (yp.this.B - 1) * 16);
				boolean boolean5 = dem3.b >= (double)(-integer4)
					&& dem3.b <= (double)integer4
					&& dem3.d >= (double)(-integer4)
					&& dem3.d <= (double)integer4
					&& this.c.a(ze);
				if (boolean5) {
					boolean boolean6 = this.c.k;
					if (!boolean6) {
						bph bph7 = new bph(this.c.W, this.c.Y);
						yo yo8 = yp.this.b(bph7.a());
						if (yo8 != null && yo8.d() != null) {
							boolean6 = yp.b(bph7, ze, false) <= yp.this.B;
						}
					}

					if (boolean6 && this.f.add(ze)) {
						this.b.b(ze);
					}
				} else if (this.f.remove(ze)) {
					this.b.a(ze);
				}
			}
		}

		private int a(int integer) {
			return yp.this.h.l().b(integer);
		}

		private int b() {
			Collection<aom> collection2 = this.c.cn();
			int integer3 = this.d;

			for (aom aom5 : collection2) {
				int integer6 = aom5.U().m() * 16;
				if (integer6 > integer3) {
					integer3 = integer6;
				}
			}

			return this.a(integer3);
		}

		public void a(List<ze> list) {
			for (ze ze4 : list) {
				this.b(ze4);
			}
		}
	}
}

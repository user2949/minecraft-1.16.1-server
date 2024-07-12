import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class axz extends cid<ayb> {
	private final axz.a a;
	private final LongSet b = new LongOpenHashSet();

	public axz(File file, DataFixer dataFixer, boolean boolean3) {
		super(file, ayb::a, ayb::new, dataFixer, aeo.POI_CHUNK, boolean3);
		this.a = new axz.a();
	}

	public void a(fu fu, ayc ayc) {
		this.e(go.a(fu).s()).a(fu, ayc);
	}

	public void a(fu fu) {
		this.e(go.a(fu).s()).a(fu);
	}

	public long a(Predicate<ayc> predicate, fu fu, int integer, axz.b b) {
		return this.c(predicate, fu, integer, b).count();
	}

	public boolean a(ayc ayc, fu fu) {
		Optional<ayc> optional4 = this.e(go.a(fu).s()).d(fu);
		return optional4.isPresent() && ((ayc)optional4.get()).equals(ayc);
	}

	public Stream<aya> b(Predicate<ayc> predicate, fu fu, int integer, axz.b b) {
		int integer6 = Math.floorDiv(integer, 16) + 1;
		return bph.a(new bph(fu), integer6).flatMap(bph -> this.a(predicate, bph, b));
	}

	public Stream<aya> c(Predicate<ayc> predicate, fu fu, int integer, axz.b b) {
		int integer6 = integer * integer;
		return this.b(predicate, fu, integer, b).filter(aya -> aya.f().j(fu) <= (double)integer6);
	}

	public Stream<aya> a(Predicate<ayc> predicate, bph bph, axz.b b) {
		return IntStream.range(0, 16)
			.boxed()
			.map(integer -> this.d(go.a(bph, integer).s()))
			.filter(Optional::isPresent)
			.flatMap(optional -> ((ayb)optional.get()).a(predicate, b));
	}

	public Stream<fu> a(Predicate<ayc> predicate1, Predicate<fu> predicate2, fu fu, int integer, axz.b b) {
		return this.c(predicate1, fu, integer, b).map(aya::f).filter(predicate2);
	}

	public Optional<fu> b(Predicate<ayc> predicate1, Predicate<fu> predicate2, fu fu, int integer, axz.b b) {
		return this.a(predicate1, predicate2, fu, integer, b).findFirst();
	}

	public Optional<fu> d(Predicate<ayc> predicate, fu fu, int integer, axz.b b) {
		return this.c(predicate, fu, integer, b).map(aya::f).min(Comparator.comparingDouble(fu2 -> fu2.j(fu)));
	}

	public Optional<fu> a(Predicate<ayc> predicate1, Predicate<fu> predicate2, fu fu, int integer) {
		return this.c(predicate1, fu, integer, axz.b.HAS_SPACE).filter(aya -> predicate2.test(aya.f())).findFirst().map(aya -> {
			aya.b();
			return aya.f();
		});
	}

	public Optional<fu> a(Predicate<ayc> predicate1, Predicate<fu> predicate2, axz.b b, fu fu, int integer, Random random) {
		List<aya> list8 = (List<aya>)this.c(predicate1, fu, integer, b).collect(Collectors.toList());
		Collections.shuffle(list8, random);
		return list8.stream().filter(aya -> predicate2.test(aya.f())).findFirst().map(aya::f);
	}

	public boolean b(fu fu) {
		return this.e(go.a(fu).s()).c(fu);
	}

	public boolean a(fu fu, Predicate<ayc> predicate) {
		return (Boolean)this.d(go.a(fu).s()).map(ayb -> ayb.a(fu, predicate)).orElse(false);
	}

	public Optional<ayc> c(fu fu) {
		ayb ayb3 = this.e(go.a(fu).s());
		return ayb3.d(fu);
	}

	public int a(go go) {
		this.a.a();
		return this.a.c(go.s());
	}

	private boolean f(long long1) {
		Optional<ayb> optional4 = this.c(long1);
		return optional4 == null ? false : (Boolean)optional4.map(ayb -> ayb.a(ayc.b, axz.b.IS_OCCUPIED).count() > 0L).orElse(false);
	}

	@Override
	public void a(BooleanSupplier booleanSupplier) {
		super.a(booleanSupplier);
		this.a.a();
	}

	@Override
	protected void a(long long1) {
		super.a(long1);
		this.a.b(long1, this.a.b(long1), false);
	}

	@Override
	protected void b(long long1) {
		this.a.b(long1, this.a.b(long1), false);
	}

	public void a(bph bph, chk chk) {
		go go4 = go.a(bph, chk.g() >> 4);
		v.a(this.d(go4.s()), ayb -> ayb.a((Consumer<BiConsumer<fu, ayc>>)(biConsumer -> {
				if (a(chk)) {
					this.a(chk, go4, biConsumer);
				}
			})), () -> {
			if (a(chk)) {
				ayb ayb4 = this.e(go4.s());
				this.a(chk, go4, ayb4::a);
			}
		});
	}

	private static boolean a(chk chk) {
		return chk.a(ayc.x::contains);
	}

	private void a(chk chk, go go, BiConsumer<fu, ayc> biConsumer) {
		go.t().forEach(fu -> {
			cfj cfj4 = chk.a(go.b(fu.u()), go.b(fu.v()), go.b(fu.w()));
			ayc.b(cfj4).ifPresent(ayc -> biConsumer.accept(fu, ayc));
		});
	}

	public void a(bqd bqd, fu fu, int integer) {
		go.b(new bph(fu), Math.floorDiv(integer, 16))
			.map(go -> Pair.of(go, this.d(go.s())))
			.filter(pair -> !(Boolean)((Optional)pair.getSecond()).map(ayb::a).orElse(false))
			.map(pair -> ((go)pair.getFirst()).r())
			.filter(bph -> this.b.add(bph.a()))
			.forEach(bph -> bqd.a(bph.b, bph.c, chc.a));
	}

	final class a extends yz {
		private final Long2ByteMap b = new Long2ByteOpenHashMap();

		protected a() {
			super(7, 16, 256);
			this.b.defaultReturnValue((byte)7);
		}

		@Override
		protected int b(long long1) {
			return axz.this.f(long1) ? 0 : 7;
		}

		@Override
		protected int c(long long1) {
			return this.b.get(long1);
		}

		@Override
		protected void a(long long1, int integer) {
			if (integer > 6) {
				this.b.remove(long1);
			} else {
				this.b.put(long1, (byte)integer);
			}
		}

		public void a() {
			super.b(Integer.MAX_VALUE);
		}
	}

	public static enum b {
		HAS_SPACE(aya::d),
		IS_OCCUPIED(aya::e),
		ANY(aya -> true);

		private final Predicate<? super aya> d;

		private b(Predicate<? super aya> predicate) {
			this.d = predicate;
		}

		public Predicate<? super aya> a() {
			return this.d;
		}
	}
}

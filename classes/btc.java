import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class btc extends brh {
	public static final MapCodec<btc> e = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
					Codec.LONG.fieldOf("seed").forGetter(btc -> btc.m),
					RecordCodecBuilder.create(
							instancex -> instancex.group(bre.d.a.fieldOf("parameters").forGetter(Pair::getFirst), gl.as.fieldOf("biome").forGetter(Pair::getSecond))
									.apply(instancex, Pair::of)
						)
						.listOf()
						.fieldOf("biomes")
						.forGetter(btc -> btc.k)
				)
				.apply(instance, btc::new)
	);
	public static final Codec<btc> f = Codec.mapEither(btc.a.a, e)
		.<btc>xmap(
			either -> either.map(pair -> ((btc.a)pair.getFirst()).a((Long)pair.getSecond()), Function.identity()),
			btc -> (Either)btc.n.map(a -> Either.left(Pair.of(a, btc.m))).orElseGet(() -> Either.right(btc))
		)
		.codec();
	private final cwd g;
	private final cwd h;
	private final cwd i;
	private final cwd j;
	private final List<Pair<bre.d, bre>> k;
	private final boolean l;
	private final long m;
	private final Optional<btc.a> n;

	private btc(long long1, List<Pair<bre.d, bre>> list) {
		this(long1, list, Optional.empty());
	}

	public btc(long long1, List<Pair<bre.d, bre>> list, Optional<btc.a> optional) {
		super((List<bre>)list.stream().map(Pair::getSecond).collect(Collectors.toList()));
		this.m = long1;
		this.n = optional;
		IntStream intStream6 = IntStream.rangeClosed(-7, -6);
		IntStream intStream7 = IntStream.rangeClosed(-7, -6);
		IntStream intStream8 = IntStream.rangeClosed(-7, -6);
		IntStream intStream9 = IntStream.rangeClosed(-7, -6);
		this.g = new cwd(new ciy(long1), intStream6);
		this.h = new cwd(new ciy(long1 + 1L), intStream7);
		this.i = new cwd(new ciy(long1 + 2L), intStream8);
		this.j = new cwd(new ciy(long1 + 3L), intStream9);
		this.k = list;
		this.l = false;
	}

	private static btc d(long long1) {
		ImmutableList<bre> immutableList3 = ImmutableList.of(brk.j, brk.ay, brk.az, brk.aA, brk.aB);
		return new btc(
			long1,
			(List<Pair<bre.d, bre>>)immutableList3.stream().flatMap(bre -> bre.B().map(d -> Pair.of(d, bre))).collect(ImmutableList.toImmutableList()),
			Optional.of(btc.a.b)
		);
	}

	@Override
	protected Codec<? extends brh> a() {
		return f;
	}

	@Override
	public bre b(int integer1, int integer2, int integer3) {
		int integer5 = this.l ? integer2 : 0;
		bre.d d6 = new bre.d(
			(float)this.g.a((double)integer1, (double)integer5, (double)integer3),
			(float)this.h.a((double)integer1, (double)integer5, (double)integer3),
			(float)this.i.a((double)integer1, (double)integer5, (double)integer3),
			(float)this.j.a((double)integer1, (double)integer5, (double)integer3),
			0.0F
		);
		return (bre)this.k.stream().min(Comparator.comparing(pair -> ((bre.d)pair.getFirst()).a(d6))).map(Pair::getSecond).orElse(brk.aa);
	}

	public boolean b(long long1) {
		return this.m == long1 && Objects.equals(this.n, Optional.of(btc.a.b));
	}

	public static class a {
		private static final Map<uh, btc.a> c = Maps.<uh, btc.a>newHashMap();
		public static final MapCodec<Pair<btc.a, Long>> a = Codec.mapPair(
				uh.a
					.<btc.a>flatXmap(
						uh -> (DataResult)Optional.ofNullable(c.get(uh)).map(DataResult::success).orElseGet(() -> DataResult.error("Unknown preset: " + uh)),
						a -> DataResult.success(a.d)
					)
					.fieldOf("preset"),
				Codec.LONG.fieldOf("seed")
			)
			.stable();
		public static final btc.a b = new btc.a(new uh("nether"), long1 -> btc.d(long1));
		private final uh d;
		private final LongFunction<btc> e;

		public a(uh uh, LongFunction<btc> longFunction) {
			this.d = uh;
			this.e = longFunction;
			c.put(uh, this);
		}

		public btc a(long long1) {
			return (btc)this.e.apply(long1);
		}
	}
}

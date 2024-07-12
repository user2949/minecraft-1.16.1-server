import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class ciq {
	public static final Codec<ciq> a = RecordCodecBuilder.create(
		instance -> instance.group(
					ciw.a.fieldOf("structures").forGetter(ciq::a),
					cis.a.fieldOf("noise").forGetter(ciq::b),
					cfj.b.fieldOf("default_block").forGetter(ciq::c),
					cfj.b.fieldOf("default_fluid").forGetter(ciq::d),
					adl.a(-20, 276).fieldOf("bedrock_roof_position").forGetter(ciq::e),
					adl.a(-20, 276).fieldOf("bedrock_floor_position").forGetter(ciq::f),
					adl.a(0, 255).fieldOf("sea_level").forGetter(ciq::g),
					Codec.BOOL.fieldOf("disable_mob_generation").forGetter(ciq::h)
				)
				.apply(instance, ciq::new)
	);
	public static final Codec<ciq> b = Codec.either(ciq.a.a, a)
		.xmap(either -> either.map(ciq.a::b, Function.identity()), ciq -> (Either)ciq.k.map(Either::left).orElseGet(() -> Either.right(ciq)));
	private final ciw c;
	private final cis d;
	private final cfj e;
	private final cfj f;
	private final int g;
	private final int h;
	private final int i;
	private final boolean j;
	private final Optional<ciq.a> k;

	private ciq(ciw ciw, cis cis, cfj cfj3, cfj cfj4, int integer5, int integer6, int integer7, boolean boolean8) {
		this(ciw, cis, cfj3, cfj4, integer5, integer6, integer7, boolean8, Optional.empty());
	}

	private ciq(ciw ciw, cis cis, cfj cfj3, cfj cfj4, int integer5, int integer6, int integer7, boolean boolean8, Optional<ciq.a> optional) {
		this.c = ciw;
		this.d = cis;
		this.e = cfj3;
		this.f = cfj4;
		this.g = integer5;
		this.h = integer6;
		this.i = integer7;
		this.j = boolean8;
		this.k = optional;
	}

	public ciw a() {
		return this.c;
	}

	public cis b() {
		return this.d;
	}

	public cfj c() {
		return this.e;
	}

	public cfj d() {
		return this.f;
	}

	public int e() {
		return this.g;
	}

	public int f() {
		return this.h;
	}

	public int g() {
		return this.i;
	}

	@Deprecated
	protected boolean h() {
		return this.j;
	}

	public boolean a(ciq.a a) {
		return Objects.equals(this.k, Optional.of(a));
	}

	public static class a {
		private static final Map<uh, ciq.a> h = Maps.<uh, ciq.a>newHashMap();
		public static final Codec<ciq.a> a = uh.a
			.<ciq.a>flatXmap(
				uh -> (DataResult)Optional.ofNullable(h.get(uh)).map(DataResult::success).orElseGet(() -> DataResult.error("Unknown preset: " + uh)),
				a -> DataResult.success(a.j)
			)
			.stable();
		public static final ciq.a b = new ciq.a("overworld", a -> a(new ciw(true), false, a));
		public static final ciq.a c = new ciq.a("amplified", a -> a(new ciw(true), true, a));
		public static final ciq.a d = new ciq.a("nether", a -> a(new ciw(false), bvs.cL.n(), bvs.B.n(), a));
		public static final ciq.a e = new ciq.a("end", a -> a(new ciw(false), bvs.ee.n(), bvs.a.n(), a, true, true));
		public static final ciq.a f = new ciq.a("caves", a -> a(new ciw(false), bvs.b.n(), bvs.A.n(), a));
		public static final ciq.a g = new ciq.a("floating_islands", a -> a(new ciw(false), bvs.b.n(), bvs.A.n(), a, false, false));
		private final mr i;
		private final uh j;
		private final ciq k;

		public a(String string, Function<ciq.a, ciq> function) {
			this.j = new uh(string);
			this.i = new ne("generator.noise." + string);
			this.k = (ciq)function.apply(this);
			h.put(this.j, this);
		}

		public ciq b() {
			return this.k;
		}

		private static ciq a(ciw ciw, cfj cfj2, cfj cfj3, ciq.a a, boolean boolean5, boolean boolean6) {
			return new ciq(
				ciw,
				new cis(128, new cir(2.0, 1.0, 80.0, 160.0), new cit(-3000, 64, -46), new cit(-30, 7, 1), 2, 1, 0.0, 0.0, true, false, boolean6, false),
				cfj2,
				cfj3,
				-10,
				-10,
				0,
				boolean5,
				Optional.of(a)
			);
		}

		private static ciq a(ciw ciw, cfj cfj2, cfj cfj3, ciq.a a) {
			Map<cml<?>, cot> map5 = Maps.<cml<?>, cot>newHashMap(ciw.b);
			map5.put(cml.h, new cot(25, 10, 34222645));
			return new ciq(
				new ciw(Optional.ofNullable(ciw.b()), map5),
				new cis(128, new cir(1.0, 3.0, 80.0, 60.0), new cit(120, 3, 0), new cit(320, 4, -1), 1, 2, 0.0, 0.019921875, false, false, false, false),
				cfj2,
				cfj3,
				0,
				0,
				32,
				false,
				Optional.of(a)
			);
		}

		private static ciq a(ciw ciw, boolean boolean2, ciq.a a) {
			double double4 = 0.9999999814507745;
			return new ciq(
				ciw,
				new cis(
					256,
					new cir(0.9999999814507745, 0.9999999814507745, 80.0, 160.0),
					new cit(-10, 3, 0),
					new cit(-30, 0, 0),
					1,
					2,
					1.0,
					-0.46875,
					true,
					true,
					false,
					boolean2
				),
				bvs.b.n(),
				bvs.A.n(),
				-10,
				0,
				63,
				false,
				Optional.of(a)
			);
		}
	}
}

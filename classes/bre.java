import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bre {
	public static final Logger a = LogManager.getLogger();
	public static final Codec<bre> b = RecordCodecBuilder.create(
		instance -> instance.group(
					bre.f.d.fieldOf("precipitation").forGetter(bre -> bre.o),
					bre.b.r.fieldOf("category").forGetter(bre -> bre.n),
					Codec.FLOAT.fieldOf("depth").forGetter(bre -> bre.h),
					Codec.FLOAT.fieldOf("scale").forGetter(bre -> bre.i),
					Codec.FLOAT.fieldOf("temperature").forGetter(bre -> bre.j),
					Codec.FLOAT.fieldOf("downfall").forGetter(bre -> bre.k),
					bri.a.fieldOf("effects").forGetter(bre -> bre.p),
					Codec.INT.fieldOf("sky_color").forGetter(bre -> bre.t),
					cvj.a.fieldOf("surface_builder").forGetter(bre -> bre.m),
					Codec.simpleMap(cin.a.c, cjc.a.listOf().promotePartial(v.a("Carver: ", a::error)), aeh.a(cin.a.values())).fieldOf("carvers").forGetter(bre -> bre.q),
					Codec.simpleMap(cin.b.k, ckb.b.listOf().promotePartial(v.a("Feature: ", a::error)), aeh.a(cin.b.values())).fieldOf("features").forGetter(bre -> bre.r),
					ckc.a
						.listOf()
						.promotePartial(v.a("Structure start: ", a::error))
						.fieldOf("starts")
						.forGetter(bre -> (List)bre.u.values().stream().sorted(Comparator.comparing(ckc -> gl.aG.b(ckc.b))).collect(Collectors.toList())),
					Codec.simpleMap(apa.g, bre.g.b.listOf().promotePartial(v.a("Spawn data: ", a::error)), aeh.a(apa.values())).fieldOf("spawners").forGetter(bre -> bre.v),
					bre.d.a.listOf().fieldOf("climate_parameters").forGetter(bre -> bre.x),
					Codec.STRING.optionalFieldOf("parent").forGetter(bre -> Optional.ofNullable(bre.l))
				)
				.apply(instance, bre::new)
	);
	public static final Set<bre> c = Sets.<bre>newHashSet();
	public static final ge<bre> d = new ge<>();
	protected static final cwf e = new cwf(new ciy(1234L), ImmutableList.of(0));
	public static final cwf f = new cwf(new ciy(2345L), ImmutableList.of(0));
	@Nullable
	protected String g;
	protected final float h;
	protected final float i;
	protected final float j;
	protected final float k;
	private final int t;
	@Nullable
	protected final String l;
	protected final cvj<?> m;
	protected final bre.b n;
	protected final bre.f o;
	protected final bri p;
	protected final Map<cin.a, List<cjc<?>>> q;
	protected final Map<cin.b, List<ckb<?, ?>>> r;
	protected final List<ckb<?, ?>> s = Lists.<ckb<?, ?>>newArrayList();
	private final Map<cml<?>, ckc<?, ?>> u;
	private final Map<apa, List<bre.g>> v;
	private final Map<aoq<?>, bre.e> w = Maps.<aoq<?>, bre.e>newHashMap();
	private final List<bre.d> x;
	private final ThreadLocal<Long2FloatLinkedOpenHashMap> y = ThreadLocal.withInitial(() -> v.a((Supplier<Long2FloatLinkedOpenHashMap>)(() -> {
			Long2FloatLinkedOpenHashMap long2FloatLinkedOpenHashMap2 = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
				@Override
				protected void rehash(int integer) {
				}
			};
			long2FloatLinkedOpenHashMap2.defaultReturnValue(Float.NaN);
			return long2FloatLinkedOpenHashMap2;
		})));

	@Nullable
	public static bre a(bre bre) {
		return d.a(gl.as.a(bre));
	}

	public static <C extends cja> cjc<C> a(cjh<C> cjh, C cja) {
		return new cjc<>(cjh, cja);
	}

	protected bre(bre.a a) {
		if (a.a != null && a.b != null && a.c != null && a.d != null && a.e != null && a.f != null && a.g != null && a.j != null) {
			this.m = a.a;
			this.o = a.b;
			this.n = a.c;
			this.h = a.d;
			this.i = a.e;
			this.j = a.f;
			this.k = a.g;
			this.t = this.D();
			this.l = a.h;
			this.x = (List<bre.d>)(a.i != null ? a.i : ImmutableList.of());
			this.p = a.j;
			this.q = Maps.<cin.a, List<cjc<?>>>newHashMap();
			this.u = Maps.<cml<?>, ckc<?, ?>>newHashMap();
			this.r = Maps.<cin.b, List<ckb<?, ?>>>newHashMap();

			for (cin.b b6 : cin.b.values()) {
				this.r.put(b6, Lists.newArrayList());
			}

			this.v = Maps.<apa, List<bre.g>>newHashMap();

			for (apa apa6 : apa.values()) {
				this.v.put(apa6, Lists.newArrayList());
			}
		} else {
			throw new IllegalStateException("You are missing parameters to build a proper biome for " + this.getClass().getSimpleName() + "\n" + a);
		}
	}

	private bre(
		bre.f f,
		bre.b b,
		float float3,
		float float4,
		float float5,
		float float6,
		bri bri,
		int integer,
		cvj<?> cvj,
		Map<cin.a, List<cjc<?>>> map10,
		Map<cin.b, List<ckb<?, ?>>> map11,
		List<ckc<?, ?>> list12,
		Map<apa, List<bre.g>> map13,
		List<bre.d> list14,
		Optional<String> optional
	) {
		this.o = f;
		this.n = b;
		this.h = float3;
		this.i = float4;
		this.j = float5;
		this.k = float6;
		this.p = bri;
		this.t = integer;
		this.m = cvj;
		this.q = map10;
		this.r = map11;
		this.u = (Map<cml<?>, ckc<?, ?>>)list12.stream().collect(Collectors.toMap(ckc -> ckc.b, Function.identity()));
		this.v = map13;
		this.x = list14;
		this.l = (String)optional.orElse(null);
		map11.values().stream().flatMap(Collection::stream).filter(ckb -> ckb.d == ckt.ab).forEach(this.s::add);
	}

	public boolean b() {
		return this.l != null;
	}

	private int D() {
		float float2 = this.j;
		float2 /= 3.0F;
		float2 = aec.a(float2, -1.0F, 1.0F);
		return aec.f(0.62222224F - float2 * 0.05F, 0.5F + float2 * 0.1F, 1.0F);
	}

	protected void a(apa apa, bre.g g) {
		((List)this.v.get(apa)).add(g);
	}

	protected void a(aoq<?> aoq, double double2, double double3) {
		this.w.put(aoq, new bre.e(double3, double2));
	}

	public List<bre.g> a(apa apa) {
		return (List<bre.g>)this.v.get(apa);
	}

	@Nullable
	public bre.e a(aoq<?> aoq) {
		return (bre.e)this.w.get(aoq);
	}

	public bre.f d() {
		return this.o;
	}

	public boolean e() {
		return this.l() > 0.85F;
	}

	public float f() {
		return 0.1F;
	}

	protected float a(fu fu) {
		if (fu.v() > 64) {
			float float3 = (float)(e.a((double)((float)fu.u() / 8.0F), (double)((float)fu.w() / 8.0F), false) * 4.0);
			return this.p() - (float3 + (float)fu.v() - 64.0F) * 0.05F / 30.0F;
		} else {
			return this.p();
		}
	}

	public final float b(fu fu) {
		long long3 = fu.a();
		Long2FloatLinkedOpenHashMap long2FloatLinkedOpenHashMap5 = (Long2FloatLinkedOpenHashMap)this.y.get();
		float float6 = long2FloatLinkedOpenHashMap5.get(long3);
		if (!Float.isNaN(float6)) {
			return float6;
		} else {
			float float7 = this.a(fu);
			if (long2FloatLinkedOpenHashMap5.size() == 1024) {
				long2FloatLinkedOpenHashMap5.removeFirstFloat();
			}

			long2FloatLinkedOpenHashMap5.put(long3, float7);
			return float7;
		}
	}

	public boolean a(bqd bqd, fu fu) {
		return this.a(bqd, fu, true);
	}

	public boolean a(bqd bqd, fu fu, boolean boolean3) {
		if (this.b(fu) >= 0.15F) {
			return false;
		} else {
			if (fu.v() >= 0 && fu.v() < 256 && bqd.a(bqi.BLOCK, fu) < 10) {
				cfj cfj5 = bqd.d_(fu);
				cxa cxa6 = bqd.b(fu);
				if (cxa6.a() == cxb.c && cfj5.b() instanceof bze) {
					if (!boolean3) {
						return true;
					}

					boolean boolean7 = bqd.A(fu.f()) && bqd.A(fu.g()) && bqd.A(fu.d()) && bqd.A(fu.e());
					if (!boolean7) {
						return true;
					}
				}
			}

			return false;
		}
	}

	public boolean b(bqd bqd, fu fu) {
		if (this.b(fu) >= 0.15F) {
			return false;
		} else {
			if (fu.v() >= 0 && fu.v() < 256 && bqd.a(bqi.BLOCK, fu) < 10) {
				cfj cfj4 = bqd.d_(fu);
				if (cfj4.g() && bvs.cC.n().a(bqd, fu)) {
					return true;
				}
			}

			return false;
		}
	}

	public void a(cin.b b, ckb<?, ?> ckb) {
		if (ckb.d == ckt.ab) {
			this.s.add(ckb);
		}

		((List)this.r.get(b)).add(ckb);
	}

	public <C extends cja> void a(cin.a a, cjc<C> cjc) {
		((List)this.q.computeIfAbsent(a, ax -> Lists.newArrayList())).add(cjc);
	}

	public List<cjc<?>> a(cin.a a) {
		return (List<cjc<?>>)this.q.computeIfAbsent(a, ax -> Lists.newArrayList());
	}

	public void a(ckc<?, ?> ckc) {
		this.u.put(ckc.b, ckc);
	}

	public boolean a(cml<?> cml) {
		return this.u.containsKey(cml);
	}

	public Iterable<ckc<?, ?>> g() {
		return this.u.values();
	}

	public ckc<?, ?> b(ckc<?, ?> ckc) {
		return (ckc<?, ?>)this.u.getOrDefault(ckc.b, ckc);
	}

	public List<ckb<?, ?>> h() {
		return this.s;
	}

	public List<ckb<?, ?>> a(cin.b b) {
		return (List<ckb<?, ?>>)this.r.get(b);
	}

	public void a(cin.b b, bqq bqq, cha cha, bqu bqu, long long5, ciy ciy, fu fu) {
		int integer10 = 0;
		if (bqq.a()) {
			for (cml<?> cml12 : gl.aG) {
				if (cml12.f() == b) {
					ciy.b(long5, integer10, b.ordinal());
					int integer13 = fu.u() >> 4;
					int integer14 = fu.w() >> 4;
					int integer15 = integer13 << 4;
					int integer16 = integer14 << 4;

					try {
						bqq.a(go.a(fu), cml12)
							.forEach(ctz -> ctz.a(bqu, bqq, cha, ciy, new ctd(integer15, integer16, integer15 + 15, integer16 + 15), new bph(integer13, integer14)));
					} catch (Exception var19) {
						j j18 = j.a(var19, "Feature placement");
						j18.a("Feature").a("Id", gl.aG.b(cml12)).a("Description", (l<String>)(() -> cml12.toString()));
						throw new s(j18);
					}

					integer10++;
				}
			}
		}

		for (ckb<?, ?> ckb12 : (List)this.r.get(b)) {
			ciy.b(long5, integer10, b.ordinal());

			try {
				ckb12.a(bqu, bqq, cha, ciy, fu);
			} catch (Exception var18) {
				j j14 = j.a(var18, "Feature placement");
				j14.a("Feature").a("Id", gl.aq.b(ckb12.d)).a("Config", ckb12.e).a("Description", (l<String>)(() -> ckb12.d.toString()));
				throw new s(j14);
			}

			integer10++;
		}
	}

	public void a(Random random, cgy cgy, int integer3, int integer4, int integer5, double double6, cfj cfj7, cfj cfj8, int integer9, long long10) {
		this.m.a(long10);
		this.m.a(random, cgy, this, integer3, integer4, integer5, double6, cfj7, cfj8, integer9, long10);
	}

	public bre.c j() {
		if (this.n == bre.b.OCEAN) {
			return bre.c.OCEAN;
		} else if ((double)this.p() < 0.2) {
			return bre.c.COLD;
		} else {
			return (double)this.p() < 1.0 ? bre.c.MEDIUM : bre.c.WARM;
		}
	}

	public final float k() {
		return this.h;
	}

	public final float l() {
		return this.k;
	}

	public String n() {
		if (this.g == null) {
			this.g = v.a("biome", gl.as.b(this));
		}

		return this.g;
	}

	public final float o() {
		return this.i;
	}

	public final float p() {
		return this.j;
	}

	public bri q() {
		return this.p;
	}

	public final bre.b y() {
		return this.n;
	}

	public cvj<?> z() {
		return this.m;
	}

	public cvy A() {
		return this.m.a();
	}

	public Stream<bre.d> B() {
		return this.x.stream();
	}

	@Nullable
	public String C() {
		return this.l;
	}

	public static class a {
		@Nullable
		private cvj<?> a;
		@Nullable
		private bre.f b;
		@Nullable
		private bre.b c;
		@Nullable
		private Float d;
		@Nullable
		private Float e;
		@Nullable
		private Float f;
		@Nullable
		private Float g;
		@Nullable
		private String h;
		@Nullable
		private List<bre.d> i;
		@Nullable
		private bri j;

		public <SC extends cvy> bre.a a(cvw<SC> cvw, SC cvy) {
			this.a = new cvj<>(cvw, cvy);
			return this;
		}

		public bre.a a(cvj<?> cvj) {
			this.a = cvj;
			return this;
		}

		public bre.a a(bre.f f) {
			this.b = f;
			return this;
		}

		public bre.a a(bre.b b) {
			this.c = b;
			return this;
		}

		public bre.a a(float float1) {
			this.d = float1;
			return this;
		}

		public bre.a b(float float1) {
			this.e = float1;
			return this;
		}

		public bre.a c(float float1) {
			this.f = float1;
			return this;
		}

		public bre.a d(float float1) {
			this.g = float1;
			return this;
		}

		public bre.a a(@Nullable String string) {
			this.h = string;
			return this;
		}

		public bre.a a(List<bre.d> list) {
			this.i = list;
			return this;
		}

		public bre.a a(bri bri) {
			this.j = bri;
			return this;
		}

		public String toString() {
			return "BiomeBuilder{\nsurfaceBuilder="
				+ this.a
				+ ",\nprecipitation="
				+ this.b
				+ ",\nbiomeCategory="
				+ this.c
				+ ",\ndepth="
				+ this.d
				+ ",\nscale="
				+ this.e
				+ ",\ntemperature="
				+ this.f
				+ ",\ndownfall="
				+ this.g
				+ ",\nspecialEffects="
				+ this.j
				+ ",\nparent='"
				+ this.h
				+ '\''
				+ "\n"
				+ '}';
		}
	}

	public static enum b implements aeh {
		NONE("none"),
		TAIGA("taiga"),
		EXTREME_HILLS("extreme_hills"),
		JUNGLE("jungle"),
		MESA("mesa"),
		PLAINS("plains"),
		SAVANNA("savanna"),
		ICY("icy"),
		THEEND("the_end"),
		BEACH("beach"),
		FOREST("forest"),
		OCEAN("ocean"),
		DESERT("desert"),
		RIVER("river"),
		SWAMP("swamp"),
		MUSHROOM("mushroom"),
		NETHER("nether");

		public static final Codec<bre.b> r = aeh.a(bre.b::values, bre.b::a);
		private static final Map<String, bre.b> s = (Map<String, bre.b>)Arrays.stream(values()).collect(Collectors.toMap(bre.b::b, b -> b));
		private final String t;

		private b(String string3) {
			this.t = string3;
		}

		public String b() {
			return this.t;
		}

		public static bre.b a(String string) {
			return (bre.b)s.get(string);
		}

		@Override
		public String a() {
			return this.t;
		}
	}

	public static enum c {
		OCEAN("ocean"),
		COLD("cold"),
		MEDIUM("medium"),
		WARM("warm");

		private static final Map<String, bre.c> e = (Map<String, bre.c>)Arrays.stream(values()).collect(Collectors.toMap(bre.c::a, c -> c));
		private final String f;

		private c(String string3) {
			this.f = string3;
		}

		public String a() {
			return this.f;
		}
	}

	public static class d {
		public static final Codec<bre.d> a = RecordCodecBuilder.create(
			instance -> instance.group(
						Codec.FLOAT.fieldOf("temperature").forGetter(d -> d.b),
						Codec.FLOAT.fieldOf("humidity").forGetter(d -> d.c),
						Codec.FLOAT.fieldOf("altitude").forGetter(d -> d.d),
						Codec.FLOAT.fieldOf("weirdness").forGetter(d -> d.e),
						Codec.FLOAT.fieldOf("offset").forGetter(d -> d.f)
					)
					.apply(instance, bre.d::new)
		);
		private final float b;
		private final float c;
		private final float d;
		private final float e;
		private final float f;

		public d(float float1, float float2, float float3, float float4, float float5) {
			this.b = float1;
			this.c = float2;
			this.d = float3;
			this.e = float4;
			this.f = float5;
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			} else if (object != null && this.getClass() == object.getClass()) {
				bre.d d3 = (bre.d)object;
				if (Float.compare(d3.b, this.b) != 0) {
					return false;
				} else if (Float.compare(d3.c, this.c) != 0) {
					return false;
				} else {
					return Float.compare(d3.d, this.d) != 0 ? false : Float.compare(d3.e, this.e) == 0;
				}
			} else {
				return false;
			}
		}

		public int hashCode() {
			int integer2 = this.b != 0.0F ? Float.floatToIntBits(this.b) : 0;
			integer2 = 31 * integer2 + (this.c != 0.0F ? Float.floatToIntBits(this.c) : 0);
			integer2 = 31 * integer2 + (this.d != 0.0F ? Float.floatToIntBits(this.d) : 0);
			return 31 * integer2 + (this.e != 0.0F ? Float.floatToIntBits(this.e) : 0);
		}

		public float a(bre.d d) {
			return (this.b - d.b) * (this.b - d.b)
				+ (this.c - d.c) * (this.c - d.c)
				+ (this.d - d.d) * (this.d - d.d)
				+ (this.e - d.e) * (this.e - d.e)
				+ (this.f - d.f) * (this.f - d.f);
		}
	}

	public static class e {
		private final double a;
		private final double b;

		public e(double double1, double double2) {
			this.a = double1;
			this.b = double2;
		}

		public double a() {
			return this.a;
		}

		public double b() {
			return this.b;
		}
	}

	public static enum f implements aeh {
		NONE("none"),
		RAIN("rain"),
		SNOW("snow");

		public static final Codec<bre.f> d = aeh.a(bre.f::values, bre.f::a);
		private static final Map<String, bre.f> e = (Map<String, bre.f>)Arrays.stream(values()).collect(Collectors.toMap(bre.f::b, f -> f));
		private final String f;

		private f(String string3) {
			this.f = string3;
		}

		public String b() {
			return this.f;
		}

		public static bre.f a(String string) {
			return (bre.f)e.get(string);
		}

		@Override
		public String a() {
			return this.f;
		}
	}

	public static class g extends aen.a {
		public static final Codec<bre.g> b = RecordCodecBuilder.create(
			instance -> instance.group(
						gl.al.fieldOf("type").forGetter(g -> g.c),
						Codec.INT.fieldOf("weight").forGetter(g -> g.a),
						Codec.INT.fieldOf("minCount").forGetter(g -> g.d),
						Codec.INT.fieldOf("maxCount").forGetter(g -> g.e)
					)
					.apply(instance, bre.g::new)
		);
		public final aoq<?> c;
		public final int d;
		public final int e;

		public g(aoq<?> aoq, int integer2, int integer3, int integer4) {
			super(integer2);
			this.c = aoq.e() == apa.MISC ? aoq.ah : aoq;
			this.d = integer3;
			this.e = integer4;
		}

		public String toString() {
			return aoq.a(this.c) + "*(" + this.d + "-" + this.e + "):" + this.a;
		}
	}
}

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.File;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Supplier;

public class cif {
	public static final MapCodec<cif> a = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
					Codec.LONG
						.optionalFieldOf("fixed_time")
						.xmap(
							optional -> (OptionalLong)optional.map(OptionalLong::of).orElseGet(OptionalLong::empty),
							optionalLong -> optionalLong.isPresent() ? Optional.of(optionalLong.getAsLong()) : Optional.empty()
						)
						.forGetter(cif -> cif.l),
					Codec.BOOL.fieldOf("has_skylight").forGetter(cif::d),
					Codec.BOOL.fieldOf("has_ceiling").forGetter(cif::e),
					Codec.BOOL.fieldOf("ultrawarm").forGetter(cif::f),
					Codec.BOOL.fieldOf("natural").forGetter(cif::g),
					Codec.BOOL.fieldOf("shrunk").forGetter(cif::h),
					Codec.BOOL.fieldOf("piglin_safe").forGetter(cif::i),
					Codec.BOOL.fieldOf("bed_works").forGetter(cif::j),
					Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(cif::k),
					Codec.BOOL.fieldOf("has_raids").forGetter(cif::l),
					adl.a(0, 256).fieldOf("logical_height").forGetter(cif::m),
					uh.a.fieldOf("infiniburn").forGetter(cif -> cif.y),
					Codec.FLOAT.fieldOf("ambient_light").forGetter(cif -> cif.z)
				)
				.apply(instance, cif::new)
	);
	public static final float[] b = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
	public static final ug<cif> c = ug.a(gl.ad, new uh("overworld"));
	public static final ug<cif> d = ug.a(gl.ad, new uh("the_nether"));
	public static final ug<cif> e = ug.a(gl.ad, new uh("the_end"));
	protected static final cif f = new cif(
		OptionalLong.empty(), true, false, false, true, false, false, false, true, false, true, 256, bsk.INSTANCE, acx.aC.a(), 0.0F
	);
	protected static final cif g = new cif(
		OptionalLong.of(18000L), false, true, true, false, true, false, true, false, true, false, 128, bsj.INSTANCE, acx.aD.a(), 0.1F
	);
	protected static final cif h = new cif(
		OptionalLong.of(6000L), false, false, false, false, false, true, false, false, false, true, 256, bsj.INSTANCE, acx.aE.a(), 0.0F
	);
	public static final ug<cif> i = ug.a(gl.ad, new uh("overworld_caves"));
	protected static final cif j = new cif(
		OptionalLong.empty(), true, true, false, true, false, false, false, true, false, true, 256, bsk.INSTANCE, acx.aC.a(), 0.0F
	);
	public static final Codec<Supplier<cif>> k = ud.a(gl.ad, a);
	private final OptionalLong l;
	private final boolean m;
	private final boolean n;
	private final boolean o;
	private final boolean p;
	private final boolean q;
	private final boolean r;
	private final boolean s;
	private final boolean t;
	private final boolean u;
	private final boolean v;
	private final int w;
	private final brj x;
	private final uh y;
	private final float z;
	private final transient float[] A;

	public static cif a() {
		return f;
	}

	protected cif(
		OptionalLong optionalLong,
		boolean boolean2,
		boolean boolean3,
		boolean boolean4,
		boolean boolean5,
		boolean boolean6,
		boolean boolean7,
		boolean boolean8,
		boolean boolean9,
		boolean boolean10,
		int integer,
		uh uh,
		float float13
	) {
		this(optionalLong, boolean2, boolean3, boolean4, boolean5, boolean6, false, boolean7, boolean8, boolean9, boolean10, integer, bsj.INSTANCE, uh, float13);
	}

	protected cif(
		OptionalLong optionalLong,
		boolean boolean2,
		boolean boolean3,
		boolean boolean4,
		boolean boolean5,
		boolean boolean6,
		boolean boolean7,
		boolean boolean8,
		boolean boolean9,
		boolean boolean10,
		boolean boolean11,
		int integer,
		brj brj,
		uh uh,
		float float15
	) {
		this.l = optionalLong;
		this.m = boolean2;
		this.n = boolean3;
		this.o = boolean4;
		this.p = boolean5;
		this.q = boolean6;
		this.r = boolean7;
		this.s = boolean8;
		this.t = boolean9;
		this.u = boolean10;
		this.v = boolean11;
		this.w = integer;
		this.x = brj;
		this.y = uh;
		this.z = float15;
		this.A = a(float15);
	}

	private static float[] a(float float1) {
		float[] arr2 = new float[16];

		for (int integer3 = 0; integer3 <= 15; integer3++) {
			float float4 = (float)integer3 / 15.0F;
			float float5 = float4 / (4.0F - 3.0F * float4);
			arr2[integer3] = aec.g(float1, float5, 1.0F);
		}

		return arr2;
	}

	@Deprecated
	public static DataResult<ug<bqb>> a(Dynamic<?> dynamic) {
		Optional<Number> optional2 = dynamic.asNumber().result();
		if (optional2.isPresent()) {
			int integer3 = ((Number)optional2.get()).intValue();
			if (integer3 == -1) {
				return DataResult.success(bqb.h);
			}

			if (integer3 == 0) {
				return DataResult.success(bqb.g);
			}

			if (integer3 == 1) {
				return DataResult.success(bqb.i);
			}
		}

		return bqb.f.parse(dynamic);
	}

	public static gm.a a(gm.a a) {
		a.a(c, f);
		a.a(i, j);
		a.a(d, g);
		a.a(e, h);
		return a;
	}

	private static cha d(long long1) {
		return new cip(new buh(long1), long1, ciq.a.e.b());
	}

	private static cha e(long long1) {
		return new cip(btc.a.b.a(long1), long1, ciq.a.d.b());
	}

	public static gh<cig> a(long long1) {
		gh<cig> gh3 = new gh<>(gl.af, Lifecycle.experimental());
		gh3.a(cig.c, new cig(() -> g, e(long1)));
		gh3.a(cig.d, new cig(() -> h, d(long1)));
		gh3.d(cig.c);
		gh3.d(cig.d);
		return gh3;
	}

	@Deprecated
	public String c() {
		return this == h ? "_end" : "";
	}

	public static File a(ug<bqb> ug, File file) {
		if (ug == bqb.g) {
			return file;
		} else if (ug == bqb.i) {
			return new File(file, "DIM1");
		} else {
			return ug == bqb.h ? new File(file, "DIM-1") : new File(file, "dimensions/" + ug.a().b() + "/" + ug.a().a());
		}
	}

	public boolean d() {
		return this.m;
	}

	public boolean e() {
		return this.n;
	}

	public boolean f() {
		return this.o;
	}

	public boolean g() {
		return this.p;
	}

	public boolean h() {
		return this.q;
	}

	public boolean i() {
		return this.s;
	}

	public boolean j() {
		return this.t;
	}

	public boolean k() {
		return this.u;
	}

	public boolean l() {
		return this.v;
	}

	public int m() {
		return this.w;
	}

	public boolean n() {
		return this.r;
	}

	public brj o() {
		return this.x;
	}

	public boolean p() {
		return this.l.isPresent();
	}

	public float b(long long1) {
		double double4 = aec.h((double)this.l.orElse(long1) / 24000.0 - 0.25);
		double double6 = 0.5 - Math.cos(double4 * Math.PI) / 2.0;
		return (float)(double4 * 2.0 + double6) / 3.0F;
	}

	public int c(long long1) {
		return (int)(long1 / 24000L % 8L + 8L) % 8;
	}

	public float a(int integer) {
		return this.A[integer];
	}

	public adf<bvr> q() {
		adf<bvr> adf2 = acx.b().a(this.y);
		return (adf<bvr>)(adf2 != null ? adf2 : acx.aC);
	}
}

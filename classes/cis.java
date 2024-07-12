import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cis {
	public static final Codec<cis> a = RecordCodecBuilder.create(
		instance -> instance.group(
					adl.a(0, 256).fieldOf("height").forGetter(cis::a),
					cir.a.fieldOf("sampling").forGetter(cis::b),
					cit.a.fieldOf("top_slide").forGetter(cis::c),
					cit.a.fieldOf("bottom_slide").forGetter(cis::d),
					adl.a(1, 4).fieldOf("size_horizontal").forGetter(cis::e),
					adl.a(1, 4).fieldOf("size_vertical").forGetter(cis::f),
					Codec.DOUBLE.fieldOf("density_factor").forGetter(cis::g),
					Codec.DOUBLE.fieldOf("density_offset").forGetter(cis::h),
					Codec.BOOL.fieldOf("simplex_surface_noise").forGetter(cis::i),
					Codec.BOOL.optionalFieldOf("random_density_offset", Boolean.valueOf(false), Lifecycle.experimental()).forGetter(cis::j),
					Codec.BOOL.optionalFieldOf("island_noise_override", Boolean.valueOf(false), Lifecycle.experimental()).forGetter(cis::k),
					Codec.BOOL.optionalFieldOf("amplified", Boolean.valueOf(false), Lifecycle.experimental()).forGetter(cis::l)
				)
				.apply(instance, cis::new)
	);
	private final int b;
	private final cir c;
	private final cit d;
	private final cit e;
	private final int f;
	private final int g;
	private final double h;
	private final double i;
	private final boolean j;
	private final boolean k;
	private final boolean l;
	private final boolean m;

	public cis(
		int integer1,
		cir cir,
		cit cit3,
		cit cit4,
		int integer5,
		int integer6,
		double double7,
		double double8,
		boolean boolean9,
		boolean boolean10,
		boolean boolean11,
		boolean boolean12
	) {
		this.b = integer1;
		this.c = cir;
		this.d = cit3;
		this.e = cit4;
		this.f = integer5;
		this.g = integer6;
		this.h = double7;
		this.i = double8;
		this.j = boolean9;
		this.k = boolean10;
		this.l = boolean11;
		this.m = boolean12;
	}

	public int a() {
		return this.b;
	}

	public cir b() {
		return this.c;
	}

	public cit c() {
		return this.d;
	}

	public cit d() {
		return this.e;
	}

	public int e() {
		return this.f;
	}

	public int f() {
		return this.g;
	}

	public double g() {
		return this.h;
	}

	public double h() {
		return this.i;
	}

	@Deprecated
	public boolean i() {
		return this.j;
	}

	@Deprecated
	public boolean j() {
		return this.k;
	}

	@Deprecated
	public boolean k() {
		return this.l;
	}

	@Deprecated
	public boolean l() {
		return this.m;
	}
}

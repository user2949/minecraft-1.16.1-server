import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cir {
	private static final Codec<Double> b = adl.a(0.001, 1000.0);
	public static final Codec<cir> a = RecordCodecBuilder.create(
		instance -> instance.group(
					b.fieldOf("xz_scale").forGetter(cir::a),
					b.fieldOf("y_scale").forGetter(cir::b),
					b.fieldOf("xz_factor").forGetter(cir::c),
					b.fieldOf("y_factor").forGetter(cir::d)
				)
				.apply(instance, cir::new)
	);
	private final double c;
	private final double d;
	private final double e;
	private final double f;

	public cir(double double1, double double2, double double3, double double4) {
		this.c = double1;
		this.d = double2;
		this.e = double3;
		this.f = double4;
	}

	public double a() {
		return this.c;
	}

	public double b() {
		return this.d;
	}

	public double c() {
		return this.e;
	}

	public double d() {
		return this.f;
	}
}

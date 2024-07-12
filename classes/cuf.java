import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class cuf extends cus {
	public static final Codec<cuf> a = RecordCodecBuilder.create(
		instance -> instance.group(
					Codec.FLOAT.fieldOf("min_chance").withDefault(0.0F).forGetter(cuf -> cuf.b),
					Codec.FLOAT.fieldOf("max_chance").withDefault(0.0F).forGetter(cuf -> cuf.d),
					Codec.INT.fieldOf("min_dist").withDefault(0).forGetter(cuf -> cuf.e),
					Codec.INT.fieldOf("max_dist").withDefault(0).forGetter(cuf -> cuf.f),
					fz.a.d.fieldOf("axis").withDefault(fz.a.Y).forGetter(cuf -> cuf.g)
				)
				.apply(instance, cuf::new)
	);
	private final float b;
	private final float d;
	private final int e;
	private final int f;
	private final fz.a g;

	public cuf(float float1, float float2, int integer3, int integer4, fz.a a) {
		if (integer3 >= integer4) {
			throw new IllegalArgumentException("Invalid range: [" + integer3 + "," + integer4 + "]");
		} else {
			this.b = float1;
			this.d = float2;
			this.e = integer3;
			this.f = integer4;
			this.g = a;
		}
	}

	@Override
	public boolean a(fu fu1, fu fu2, fu fu3, Random random) {
		fz fz6 = fz.a(fz.b.POSITIVE, this.g);
		float float7 = (float)Math.abs((fu2.u() - fu3.u()) * fz6.i());
		float float8 = (float)Math.abs((fu2.v() - fu3.v()) * fz6.j());
		float float9 = (float)Math.abs((fu2.w() - fu3.w()) * fz6.k());
		int integer10 = (int)(float7 + float8 + float9);
		float float11 = random.nextFloat();
		return (double)float11 <= aec.b((double)this.b, (double)this.d, aec.c((double)integer10, (double)this.e, (double)this.f));
	}

	@Override
	protected cut<?> a() {
		return cut.c;
	}
}

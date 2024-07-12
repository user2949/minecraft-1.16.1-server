import com.mojang.datafixers.Products.P4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.Set;

public abstract class cpg {
	public static final Codec<cpg> d = gl.av.dispatch(cpg::a, cph::a);
	protected final int e;
	protected final int f;
	protected final int g;
	protected final int h;

	protected static <P extends cpg> P4<Mu<P>, Integer, Integer, Integer, Integer> b(Instance<P> instance) {
		return instance.group(
			Codec.INT.fieldOf("radius").forGetter(cpg -> cpg.e),
			Codec.INT.fieldOf("radius_random").forGetter(cpg -> cpg.f),
			Codec.INT.fieldOf("offset").forGetter(cpg -> cpg.g),
			Codec.INT.fieldOf("offset_random").forGetter(cpg -> cpg.h)
		);
	}

	public cpg(int integer1, int integer2, int integer3, int integer4) {
		this.e = integer1;
		this.f = integer2;
		this.g = integer3;
		this.h = integer4;
	}

	protected abstract cph<?> a();

	public void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, ctd ctd) {
		this.a(bqf, random, cou, integer4, b, integer6, integer7, set, this.a(random), ctd);
	}

	protected abstract void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd);

	public abstract int a(Random random, int integer, cou cou);

	public int a(Random random, int integer) {
		return this.e + random.nextInt(this.f + 1);
	}

	private int a(Random random) {
		return this.g + random.nextInt(this.h + 1);
	}

	protected abstract boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6);

	protected boolean b(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		int integer8;
		int integer9;
		if (boolean6) {
			integer8 = Math.min(Math.abs(integer2), Math.abs(integer2 - 1));
			integer9 = Math.min(Math.abs(integer4), Math.abs(integer4 - 1));
		} else {
			integer8 = Math.abs(integer2);
			integer9 = Math.abs(integer4);
		}

		return this.a(random, integer8, integer3, integer9, integer5, boolean6);
	}

	protected void a(bqf bqf, Random random, cou cou, fu fu, int integer5, Set<fu> set, int integer7, boolean boolean8, ctd ctd) {
		int integer11 = boolean8 ? 1 : 0;
		fu.a a12 = new fu.a();

		for (int integer13 = -integer5; integer13 <= integer5 + integer11; integer13++) {
			for (int integer14 = -integer5; integer14 <= integer5 + integer11; integer14++) {
				if (!this.b(random, integer13, integer7, integer14, integer5, boolean8)) {
					a12.a(fu, integer13, integer7, integer14);
					if (cmp.e(bqf, a12)) {
						bqf.a(a12, cou.c.a(random, a12), 19);
						ctd.c(new ctd(a12, a12));
						set.add(a12.h());
					}
				}
			}
		}
	}

	public static final class b {
		private final fu a;
		private final int b;
		private final boolean c;

		public b(fu fu, int integer, boolean boolean3) {
			this.a = fu;
			this.b = integer;
			this.c = boolean3;
		}

		public fu a() {
			return this.a;
		}

		public int b() {
			return this.b;
		}

		public boolean c() {
			return this.c;
		}
	}
}

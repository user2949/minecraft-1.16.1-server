import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class cqw {
	public static final Codec<cqw> c = gl.aw.dispatch(cqw::a, cqx::a);
	protected final int d;
	protected final int e;
	protected final int f;

	protected static <P extends cqw> P3<Mu<P>, Integer, Integer, Integer> a(Instance<P> instance) {
		return instance.group(
			Codec.INT.fieldOf("base_height").forGetter(cqw -> cqw.d),
			Codec.INT.fieldOf("height_rand_a").forGetter(cqw -> cqw.e),
			Codec.INT.fieldOf("height_rand_b").forGetter(cqw -> cqw.f)
		);
	}

	public cqw(int integer1, int integer2, int integer3) {
		this.d = integer1;
		this.e = integer2;
		this.f = integer3;
	}

	protected abstract cqx<?> a();

	public abstract List<cpg.b> a(bqf bqf, Random random, int integer, fu fu, Set<fu> set, ctd ctd, cou cou);

	public int a(Random random) {
		return this.d + random.nextInt(this.e + 1) + random.nextInt(this.f + 1);
	}

	protected static void a(bqh bqh, fu fu, cfj cfj, ctd ctd) {
		cmp.b(bqh, fu, cfj);
		ctd.c(new ctd(fu, fu));
	}

	private static boolean a(bqg bqg, fu fu) {
		return bqg.a(fu, cfj -> {
			bvr bvr2 = cfj.b();
			return ckt.b(bvr2) && !cfj.a(bvs.i) && !cfj.a(bvs.dT);
		});
	}

	protected static void a(bqf bqf, fu fu) {
		if (!a((bqg)bqf, fu)) {
			cmp.b(bqf, fu, bvs.j.n());
		}
	}

	protected static boolean a(bqf bqf, Random random, fu fu, Set<fu> set, ctd ctd, cou cou) {
		if (cmp.e(bqf, fu)) {
			a(bqf, fu, cou.b.a(random, fu), ctd);
			set.add(fu.h());
			return true;
		} else {
			return false;
		}
	}

	protected static void a(bqf bqf, Random random, fu.a a, Set<fu> set, ctd ctd, cou cou) {
		if (cmp.c(bqf, a)) {
			a(bqf, random, (fu)a, set, ctd, cou);
		}
	}
}

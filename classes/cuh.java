import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class cuh extends cvc {
	public static final Codec<cuh> a = Codec.FLOAT.fieldOf("mossiness").<cuh>xmap(cuh::new, cuh -> cuh.b).codec();
	private final float b;

	public cuh(float float1) {
		this.b = float1;
	}

	@Nullable
	@Override
	public cve.c a(bqd bqd, fu fu2, fu fu3, cve.c c4, cve.c c5, cvb cvb) {
		Random random8 = cvb.b(c5.a);
		cfj cfj9 = c5.b;
		fu fu10 = c5.a;
		cfj cfj11 = null;
		if (cfj9.a(bvs.du) || cfj9.a(bvs.b) || cfj9.a(bvs.dx)) {
			cfj11 = this.a(random8);
		} else if (cfj9.a(acx.C)) {
			cfj11 = this.a(random8, c5.b);
		} else if (cfj9.a(acx.D)) {
			cfj11 = this.b(random8);
		} else if (cfj9.a(acx.E)) {
			cfj11 = this.c(random8);
		} else if (cfj9.a(bvs.bK)) {
			cfj11 = this.d(random8);
		}

		return cfj11 != null ? new cve.c(fu10, cfj11, c5.c) : c5;
	}

	@Nullable
	private cfj a(Random random) {
		if (random.nextFloat() >= 0.5F) {
			return null;
		} else {
			cfj[] arr3 = new cfj[]{bvs.dw.n(), a(random, bvs.dS)};
			cfj[] arr4 = new cfj[]{bvs.dv.n(), a(random, bvs.lf)};
			return this.a(random, arr3, arr4);
		}
	}

	@Nullable
	private cfj a(Random random, cfj cfj) {
		fz fz4 = cfj.c(cbn.a);
		cgh cgh5 = cfj.c(cbn.b);
		if (random.nextFloat() >= 0.5F) {
			return null;
		} else {
			cfj[] arr6 = new cfj[]{bvs.hQ.n(), bvs.hX.n()};
			cfj[] arr7 = new cfj[]{bvs.lf.n().a(cbn.a, fz4).a(cbn.b, cgh5), bvs.lt.n()};
			return this.a(random, arr6, arr7);
		}
	}

	@Nullable
	private cfj b(Random random) {
		return random.nextFloat() < this.b ? bvs.lt.n() : null;
	}

	@Nullable
	private cfj c(Random random) {
		return random.nextFloat() < this.b ? bvs.lH.n() : null;
	}

	@Nullable
	private cfj d(Random random) {
		return random.nextFloat() < 0.15F ? bvs.ni.n() : null;
	}

	private static cfj a(Random random, bvr bvr) {
		return bvr.n().a(cbn.a, fz.c.HORIZONTAL.a(random)).a(cbn.b, cgh.values()[random.nextInt(cgh.values().length)]);
	}

	private cfj a(Random random, cfj[] arr2, cfj[] arr3) {
		return random.nextFloat() < this.b ? a(random, arr3) : a(random, arr2);
	}

	private static cfj a(Random random, cfj[] arr) {
		return arr[random.nextInt(arr.length)];
	}

	@Override
	protected cvd<?> a() {
		return cvd.g;
	}
}

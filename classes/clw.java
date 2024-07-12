import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class clw extends ckt<coj> {
	public clw(Codec<coj> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coj coj) {
		bvr bvr8 = coj.b.b();
		fu fu9 = a(bqu, fu.i().a(fz.a.Y, 1, bqu.I() - 1), bvr8);
		if (fu9 == null) {
			return false;
		} else {
			gr gr10 = a(random, coj);
			int integer11 = Math.max(gr10.u(), Math.max(gr10.v(), gr10.w()));
			boolean boolean12 = false;

			for (fu fu14 : fu.a(fu9, gr10.u(), gr10.v(), gr10.w())) {
				if (fu14.k(fu9) > integer11) {
					break;
				}

				cfj cfj15 = bqu.d_(fu14);
				if (cfj15.a(bvr8)) {
					this.a(bqu, fu14, coj.c);
					boolean12 = true;
				}
			}

			return boolean12;
		}
	}

	@Nullable
	private static fu a(bqc bqc, fu.a a, bvr bvr) {
		while (a.v() > 1) {
			cfj cfj4 = bqc.d_(a);
			if (cfj4.a(bvr)) {
				return a;
			}

			a.c(fz.DOWN);
		}

		return null;
	}

	private static gr a(Random random, coj coj) {
		return new gr(
			coj.d.u() + random.nextInt(coj.e.u() - coj.d.u() + 1),
			coj.d.v() + random.nextInt(coj.e.v() - coj.d.v() + 1),
			coj.d.w() + random.nextInt(coj.e.w() - coj.d.w() + 1)
		);
	}
}

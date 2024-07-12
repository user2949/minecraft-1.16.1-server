import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class cjg extends cjb {
	public cjg(Codec<cod> codec) {
		super(codec, 256);
		this.j = ImmutableSet.of(
			bvs.b,
			bvs.c,
			bvs.e,
			bvs.g,
			bvs.j,
			bvs.k,
			bvs.l,
			bvs.i,
			bvs.gR,
			bvs.fF,
			bvs.fG,
			bvs.fH,
			bvs.fI,
			bvs.fJ,
			bvs.fK,
			bvs.fL,
			bvs.fM,
			bvs.fN,
			bvs.fO,
			bvs.fP,
			bvs.fQ,
			bvs.fR,
			bvs.fS,
			bvs.fT,
			bvs.fU,
			bvs.at,
			bvs.hG,
			bvs.dT,
			bvs.cC,
			bvs.C,
			bvs.E,
			bvs.A,
			bvs.B,
			bvs.bK,
			bvs.a,
			bvs.lb,
			bvs.gT
		);
	}

	@Override
	protected boolean a(cgy cgy, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, int integer9) {
		return false;
	}

	@Override
	protected boolean a(
		cgy cgy,
		Function<fu, bre> function,
		BitSet bitSet,
		Random random,
		fu.a a5,
		fu.a a6,
		fu.a a7,
		int integer8,
		int integer9,
		int integer10,
		int integer11,
		int integer12,
		int integer13,
		int integer14,
		int integer15,
		MutableBoolean mutableBoolean
	) {
		return a(this, cgy, bitSet, random, a5, integer8, integer9, integer10, integer11, integer12, integer13, integer14, integer15);
	}

	protected static boolean a(
		cjh<?> cjh,
		cgy cgy,
		BitSet bitSet,
		Random random,
		fu.a a,
		int integer6,
		int integer7,
		int integer8,
		int integer9,
		int integer10,
		int integer11,
		int integer12,
		int integer13
	) {
		if (integer12 >= integer6) {
			return false;
		} else {
			int integer14 = integer11 | integer13 << 4 | integer12 << 8;
			if (bitSet.get(integer14)) {
				return false;
			} else {
				bitSet.set(integer14);
				a.d(integer9, integer12, integer10);
				cfj cfj15 = cgy.d_(a);
				if (!cjh.a(cfj15)) {
					return false;
				} else if (integer12 == 10) {
					float float16 = random.nextFloat();
					if ((double)float16 < 0.25) {
						cgy.a(a, bvs.iJ.n(), false);
						cgy.n().a(a, bvs.iJ, 0);
					} else {
						cgy.a(a, bvs.bK.n(), false);
					}

					return true;
				} else if (integer12 < 10) {
					cgy.a(a, bvs.B.n(), false);
					return false;
				} else {
					boolean boolean16 = false;

					for (fz fz18 : fz.c.HORIZONTAL) {
						int integer19 = integer9 + fz18.i();
						int integer20 = integer10 + fz18.k();
						if (integer19 >> 4 != integer7 || integer20 >> 4 != integer8 || cgy.d_(a.d(integer19, integer12, integer20)).g()) {
							cgy.a(a, h.g(), false);
							cgy.o().a(a, h.a(), 0);
							boolean16 = true;
							break;
						}
					}

					a.d(integer9, integer12, integer10);
					if (!boolean16) {
						cgy.a(a, h.g(), false);
						return true;
					} else {
						return true;
					}
				}
			}
		}
	}
}

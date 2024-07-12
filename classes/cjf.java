import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class cjf extends ciz {
	public cjf(Codec<cod> codec) {
		super(codec);
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
			bvs.lb
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
		return cjg.a(this, cgy, bitSet, random, a5, integer8, integer9, integer10, integer11, integer12, integer13, integer14, integer15);
	}
}

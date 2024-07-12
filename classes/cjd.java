import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class cjd extends cjb {
	public cjd(Codec<cod> codec) {
		super(codec, 128);
		this.j = ImmutableSet.of(bvs.b, bvs.c, bvs.e, bvs.g, bvs.j, bvs.k, bvs.l, bvs.i, bvs.cL, bvs.cM, bvs.cN, bvs.mu, bvs.ml, bvs.iK, bvs.mn, bvs.cO, bvs.np);
		this.k = ImmutableSet.of(cxb.e, cxb.c);
	}

	@Override
	protected int a() {
		return 10;
	}

	@Override
	protected float a(Random random) {
		return (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
	}

	@Override
	protected double b() {
		return 5.0;
	}

	@Override
	protected int b(Random random) {
		return random.nextInt(this.l);
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
		int integer18 = integer13 | integer15 << 4 | integer14 << 8;
		if (bitSet.get(integer18)) {
			return false;
		} else {
			bitSet.set(integer18);
			a5.d(integer11, integer14, integer12);
			if (this.a(cgy.d_(a5))) {
				cfj cfj19;
				if (integer14 <= 31) {
					cfj19 = i.g();
				} else {
					cfj19 = g;
				}

				cgy.a(a5, cfj19, false);
				return true;
			} else {
				return false;
			}
		}
	}
}

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class crd extends csc<cre> {
	public crd(Codec<cre> codec) {
		super(codec);
	}

	public Stream<fu> a(bqc bqc, cha cha, Random random, cre cre, fu fu) {
		cgy cgy7 = bqc.z(fu);
		bph bph8 = cgy7.g();
		BitSet bitSet9 = ((chr)cgy7).a(cre.b);
		return bitSet9 == null
			? Stream.empty()
			: IntStream.range(0, bitSet9.length()).filter(integer -> bitSet9.get(integer) && random.nextFloat() < cre.c).mapToObj(integer -> {
				int integer3 = integer & 15;
				int integer4 = integer >> 4 & 15;
				int integer5 = integer >> 8;
				return new fu(bph8.d() + integer3, integer5, bph8.e() + integer4);
			});
	}
}

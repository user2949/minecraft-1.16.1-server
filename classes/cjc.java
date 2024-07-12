import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class cjc<WC extends cja> {
	public static final Codec<cjc<?>> a = gl.ao.dispatch("name", cjc -> cjc.b, cjh::c);
	public final cjh<WC> b;
	public final WC c;

	public cjc(cjh<WC> cjh, WC cja) {
		this.b = cjh;
		this.c = cja;
	}

	public boolean a(Random random, int integer2, int integer3) {
		return this.b.a(random, integer2, integer3, this.c);
	}

	public boolean a(cgy cgy, Function<fu, bre> function, Random random, int integer4, int integer5, int integer6, int integer7, int integer8, BitSet bitSet) {
		return this.b.a(cgy, function, random, integer4, integer5, integer6, integer7, integer8, bitSet, this.c);
	}
}

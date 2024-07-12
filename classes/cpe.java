import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cpe extends cpg {
	public static final Codec<cpe> a = RecordCodecBuilder.create(instance -> b(instance).apply(instance, cpe::new));

	public cpe(int integer1, int integer2, int integer3, int integer4) {
		super(integer1, integer2, integer3, integer4);
	}

	@Override
	protected cph<?> a() {
		return cph.i;
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		fu fu12 = b.a().b(integer9);
		boolean boolean13 = b.c();
		if (boolean13) {
			this.a(bqf, random, cou, fu12, integer7 + 2, set, -1, boolean13, ctd);
			this.a(bqf, random, cou, fu12, integer7 + 3, set, 0, boolean13, ctd);
			this.a(bqf, random, cou, fu12, integer7 + 2, set, 1, boolean13, ctd);
			if (random.nextBoolean()) {
				this.a(bqf, random, cou, fu12, integer7, set, 2, boolean13, ctd);
			}
		} else {
			this.a(bqf, random, cou, fu12, integer7 + 2, set, -1, boolean13, ctd);
			this.a(bqf, random, cou, fu12, integer7 + 1, set, 0, boolean13, ctd);
		}
	}

	@Override
	public int a(Random random, int integer, cou cou) {
		return 4;
	}

	@Override
	protected boolean b(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return integer3 != 0 || !boolean6 || integer2 != -integer5 && integer2 < integer5 || integer4 != -integer5 && integer4 < integer5
			? super.b(random, integer2, integer3, integer4, integer5, boolean6)
			: true;
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		if (integer3 == -1 && !boolean6) {
			return integer2 == integer5 && integer4 == integer5;
		} else {
			return integer3 == 1 ? integer2 + integer4 > integer5 * 2 - 2 : false;
		}
	}
}

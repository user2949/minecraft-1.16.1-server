import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cpb extends cpg {
	public static final Codec<cpb> a = RecordCodecBuilder.create(instance -> b(instance).apply(instance, cpb::new));

	public cpb(int integer1, int integer2, int integer3, int integer4) {
		super(integer1, integer2, integer3, integer4);
	}

	@Override
	protected cph<?> a() {
		return cph.d;
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		boolean boolean12 = b.c();
		fu fu13 = b.a().b(integer9);
		this.a(bqf, random, cou, fu13, integer7 + b.b(), set, -1 - integer6, boolean12, ctd);
		this.a(bqf, random, cou, fu13, integer7 - 1, set, -integer6, boolean12, ctd);
		this.a(bqf, random, cou, fu13, integer7 + b.b() - 1, set, 0, boolean12, ctd);
	}

	@Override
	public int a(Random random, int integer, cou cou) {
		return 0;
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return integer3 == 0 ? (integer2 > 1 || integer4 > 1) && integer2 != 0 && integer4 != 0 : integer2 == integer5 && integer4 == integer5 && integer5 > 0;
	}
}

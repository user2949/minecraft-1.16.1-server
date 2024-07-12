import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;

public class cpf extends cpc {
	public static final Codec<cpf> c = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cpf::new));

	public cpf(int integer1, int integer2, int integer3, int integer4, int integer5) {
		super(integer1, integer2, integer3, integer4, integer5, cph.f);
	}

	@Override
	protected void a(bqf bqf, Random random, cou cou, int integer4, cpg.b b, int integer6, int integer7, Set<fu> set, int integer9, ctd ctd) {
		for (int integer12 = integer9; integer12 >= integer9 - integer6; integer12--) {
			int integer13 = integer7 + (integer12 != integer9 && integer12 != integer9 - integer6 ? 1 : 0);
			this.a(bqf, random, cou, b.a(), integer13, set, integer12, b.c(), ctd);
		}
	}

	@Override
	protected boolean a(Random random, int integer2, int integer3, int integer4, int integer5, boolean boolean6) {
		return aec.k((float)integer2 + 0.5F) + aec.k((float)integer4 + 0.5F) > (float)(integer5 * integer5);
	}
}

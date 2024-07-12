import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cqk extends cqm {
	public static final Codec<cqk> a = Codec.FLOAT.fieldOf("probability").<cqk>xmap(cqk::new, cqk -> cqk.b).codec();
	private final float b;

	public cqk(float float1) {
		this.b = float1;
	}

	@Override
	protected cqn<?> a() {
		return cqn.c;
	}

	@Override
	public void a(bqc bqc, Random random, List<fu> list3, List<fu> list4, Set<fu> set, ctd ctd) {
		if (!(random.nextFloat() >= this.b)) {
			int integer8 = ((fu)list3.get(0)).v();
			list3.stream().filter(fu -> fu.v() - integer8 <= 2).forEach(fu -> {
				for (fz fz8 : fz.c.HORIZONTAL) {
					if (random.nextFloat() <= 0.25F) {
						fz fz9 = fz8.f();
						fu fu10 = fu.b(fz9.i(), 0, fz9.k());
						if (ckt.b(bqc, fu10)) {
							cfj cfj11 = bvs.eh.n().a(bwk.a, Integer.valueOf(random.nextInt(3))).a(bwk.aq, fz8);
							this.a(bqc, fu10, cfj11, set, ctd);
						}
					}
				}
			});
		}
	}
}

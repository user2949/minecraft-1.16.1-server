import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cqi extends cqm {
	public static final Codec<cqi> a = cpo.a.fieldOf("provider").<cqi>xmap(cqi::new, cqi -> cqi.b).codec();
	private final cpo b;

	public cqi(cpo cpo) {
		this.b = cpo;
	}

	@Override
	protected cqn<?> a() {
		return cqn.e;
	}

	@Override
	public void a(bqc bqc, Random random, List<fu> list3, List<fu> list4, Set<fu> set, ctd ctd) {
		int integer8 = ((fu)list3.get(0)).v();
		list3.stream().filter(fu -> fu.v() == integer8).forEach(fu -> {
			this.a((bqf)bqc, random, fu.f().d());
			this.a((bqf)bqc, random, fu.g(2).d());
			this.a((bqf)bqc, random, fu.f().e(2));
			this.a((bqf)bqc, random, fu.g(2).e(2));

			for (int integer5 = 0; integer5 < 5; integer5++) {
				int integer6 = random.nextInt(64);
				int integer7 = integer6 % 8;
				int integer8x = integer6 / 8;
				if (integer7 == 0 || integer7 == 7 || integer8x == 0 || integer8x == 7) {
					this.a((bqf)bqc, random, fu.b(-3 + integer7, 0, -3 + integer8x));
				}
			}
		});
	}

	private void a(bqf bqf, Random random, fu fu) {
		for (int integer5 = -2; integer5 <= 2; integer5++) {
			for (int integer6 = -2; integer6 <= 2; integer6++) {
				if (Math.abs(integer5) != 2 || Math.abs(integer6) != 2) {
					this.b(bqf, random, fu.b(integer5, 0, integer6));
				}
			}
		}
	}

	private void b(bqf bqf, Random random, fu fu) {
		for (int integer5 = 2; integer5 >= -3; integer5--) {
			fu fu6 = fu.b(integer5);
			if (ckt.a(bqf, fu6)) {
				bqf.a(fu6, this.b.a(random, fu), 19);
				break;
			}

			if (!ckt.b(bqf, fu6) && integer5 < 0) {
				break;
			}
		}
	}
}

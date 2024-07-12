import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

public class cql extends cqm {
	public static final Codec<cql> a = Codec.unit((Supplier<cql>)(() -> cql.b));
	public static final cql b = new cql();

	@Override
	protected cqn<?> a() {
		return cqn.b;
	}

	@Override
	public void a(bqc bqc, Random random, List<fu> list3, List<fu> list4, Set<fu> set, ctd ctd) {
		list4.forEach(fu -> {
			if (random.nextInt(4) == 0) {
				fu fu7 = fu.f();
				if (ckt.b(bqc, fu7)) {
					this.a(bqc, fu7, cck.c, set, ctd);
				}
			}

			if (random.nextInt(4) == 0) {
				fu fu7 = fu.g();
				if (ckt.b(bqc, fu7)) {
					this.a(bqc, fu7, cck.e, set, ctd);
				}
			}

			if (random.nextInt(4) == 0) {
				fu fu7 = fu.d();
				if (ckt.b(bqc, fu7)) {
					this.a(bqc, fu7, cck.d, set, ctd);
				}
			}

			if (random.nextInt(4) == 0) {
				fu fu7 = fu.e();
				if (ckt.b(bqc, fu7)) {
					this.a(bqc, fu7, cck.b, set, ctd);
				}
			}
		});
	}

	private void a(bqf bqf, fu fu, cga cga, Set<fu> set, ctd ctd) {
		this.a((bqh)bqf, fu, cga, set, ctd);
		int integer7 = 4;

		for (fu var7 = fu.c(); ckt.b(bqf, var7) && integer7 > 0; integer7--) {
			this.a((bqh)bqf, var7, cga, set, ctd);
			var7 = var7.c();
		}
	}
}

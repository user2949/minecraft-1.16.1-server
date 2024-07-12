import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

public class cqo extends cqm {
	public static final Codec<cqo> a = Codec.unit((Supplier<cqo>)(() -> cqo.b));
	public static final cqo b = new cqo();

	@Override
	protected cqn<?> a() {
		return cqn.a;
	}

	@Override
	public void a(bqc bqc, Random random, List<fu> list3, List<fu> list4, Set<fu> set, ctd ctd) {
		list3.forEach(fu -> {
			if (random.nextInt(3) > 0) {
				fu fu7 = fu.f();
				if (ckt.b(bqc, fu7)) {
					this.a(bqc, fu7, cck.c, set, ctd);
				}
			}

			if (random.nextInt(3) > 0) {
				fu fu7 = fu.g();
				if (ckt.b(bqc, fu7)) {
					this.a(bqc, fu7, cck.e, set, ctd);
				}
			}

			if (random.nextInt(3) > 0) {
				fu fu7 = fu.d();
				if (ckt.b(bqc, fu7)) {
					this.a(bqc, fu7, cck.d, set, ctd);
				}
			}

			if (random.nextInt(3) > 0) {
				fu fu7 = fu.e();
				if (ckt.b(bqc, fu7)) {
					this.a(bqc, fu7, cck.b, set, ctd);
				}
			}
		});
	}
}

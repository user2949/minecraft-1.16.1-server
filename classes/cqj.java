import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class cqj extends cqm {
	public static final Codec<cqj> a = Codec.FLOAT.fieldOf("probability").<cqj>xmap(cqj::new, cqj -> cqj.b).codec();
	private final float b;

	public cqj(float float1) {
		this.b = float1;
	}

	@Override
	protected cqn<?> a() {
		return cqn.d;
	}

	@Override
	public void a(bqc bqc, Random random, List<fu> list3, List<fu> list4, Set<fu> set, ctd ctd) {
		if (!(random.nextFloat() >= this.b)) {
			fz fz8 = bvn.a(random);
			int integer9 = !list4.isEmpty()
				? Math.max(((fu)list4.get(0)).v() - 1, ((fu)list3.get(0)).v())
				: Math.min(((fu)list3.get(0)).v() + 1 + random.nextInt(3), ((fu)list3.get(list3.size() - 1)).v());
			List<fu> list10 = (List<fu>)list3.stream().filter(fu -> fu.v() == integer9).collect(Collectors.toList());
			if (!list10.isEmpty()) {
				fu fu11 = (fu)list10.get(random.nextInt(list10.size()));
				fu fu12 = fu11.a(fz8);
				if (ckt.b(bqc, fu12) && ckt.b(bqc, fu12.a(fz.SOUTH))) {
					cfj cfj13 = bvs.nc.n().a(bvn.a, fz.SOUTH);
					this.a(bqc, fu12, cfj13, set, ctd);
					cdl cdl14 = bqc.c(fu12);
					if (cdl14 instanceof cdi) {
						cdi cdi15 = (cdi)cdl14;
						int integer16 = 2 + random.nextInt(2);

						for (int integer17 = 0; integer17 < integer16; integer17++) {
							ayl ayl18 = new ayl(aoq.e, bqc.n());
							cdi15.a(ayl18, false, random.nextInt(599));
						}
					}
				}
			}
		}
	}
}

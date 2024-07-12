import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;

public class asq extends aqh<bdp> {
	private Set<bke> b = ImmutableSet.of();

	public asq() {
		super(ImmutableMap.of(awp.q, awq.VALUE_PRESENT, awp.h, awq.VALUE_PRESENT));
	}

	protected boolean a(zd zd, bdp bdp) {
		return aqi.a(bdp.cI(), awp.q, aoq.aO);
	}

	protected boolean b(zd zd, bdp bdp, long long3) {
		return this.a(zd, bdp);
	}

	protected void a(zd zd, bdp bdp, long long3) {
		bdp bdp6 = (bdp)bdp.cI().c(awp.q).get();
		aqi.a(bdp, bdp6, 0.5F);
		this.b = a(bdp, bdp6);
	}

	protected void d(zd zd, bdp bdp, long long3) {
		bdp bdp6 = (bdp)bdp.cI().c(awp.q).get();
		if (!(bdp.h(bdp6) > 5.0)) {
			aqi.a(bdp, bdp6, 0.5F);
			bdp.a(bdp6, long3);
			if (bdp.fg() && (bdp.eY().b() == bds.f || bdp6.fh())) {
				a(bdp, bdp.bw.keySet(), bdp6);
			}

			if (bdp6.eY().b() == bds.f && bdp.eU().a(bkk.kW) > bkk.kW.i() / 2) {
				a(bdp, ImmutableSet.of(bkk.kW), bdp6);
			}

			if (!this.b.isEmpty() && bdp.eU().a(this.b)) {
				a(bdp, this.b, bdp6);
			}
		}
	}

	protected void d(zd zd, bdp bdp, long long3) {
		bdp.cI().b(awp.q);
	}

	private static Set<bke> a(bdp bdp1, bdp bdp2) {
		ImmutableSet<bke> immutableSet3 = bdp2.eY().b().c();
		ImmutableSet<bke> immutableSet4 = bdp1.eY().b().c();
		return (Set<bke>)immutableSet3.stream().filter(bke -> !immutableSet4.contains(bke)).collect(Collectors.toSet());
	}

	private static void a(bdp bdp, Set<bke> set, aoy aoy) {
		anm anm4 = bdp.eU();
		bki bki5 = bki.b;
		int integer6 = 0;

		while (integer6 < anm4.ab_()) {
			bki bki7;
			bke bke8;
			int integer9;
			label28: {
				bki7 = anm4.a(integer6);
				if (!bki7.a()) {
					bke8 = bki7.b();
					if (set.contains(bke8)) {
						if (bki7.E() > bki7.c() / 2) {
							integer9 = bki7.E() / 2;
							break label28;
						}

						if (bki7.E() > 24) {
							integer9 = bki7.E() - 24;
							break label28;
						}
					}
				}

				integer6++;
				continue;
			}

			bki7.g(integer9);
			bki5 = new bki(bke8, integer9);
			break;
		}

		if (!bki5.a()) {
			aqi.a(bdp, bki5, aoy.cz());
		}
	}
}

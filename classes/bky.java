public class bky extends bke {
	public bky(bke.a a) {
		super(a);
	}

	@Override
	public ang a(bki bki, bec bec, aoy aoy, anf anf) {
		if (aoy instanceof apm && aoy.aU()) {
			apm apm6 = (apm)aoy;
			if (!apm6.N_() && apm6.M_()) {
				if (!bec.l.v) {
					apm6.a(acm.NEUTRAL);
					bki.g(1);
				}

				return ang.a(bec.l.v);
			}
		}

		return ang.PASS;
	}
}

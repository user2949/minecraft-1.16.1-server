public class bkr extends bke {
	public bkr(bke.a a) {
		super(a);
	}

	@Override
	public ang a(bki bki, bec bec, aoy aoy, anf anf) {
		if (bki.t() && !(aoy instanceof bec)) {
			if (!bec.l.v && aoy.aU()) {
				aoy.a(bki.r());
				if (aoy instanceof aoz) {
					((aoz)aoy).et();
				}

				bki.g(1);
			}

			return ang.a(bec.l.v);
		} else {
			return ang.PASS;
		}
	}
}

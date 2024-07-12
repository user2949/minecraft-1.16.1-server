public class biv extends bke {
	public biv(bke.a a) {
		super(a);
	}

	@Override
	public bki a(bki bki, bqb bqb, aoy aoy) {
		bki bki5 = super.a(bki, bqb, aoy);
		if (!bqb.v) {
			double double6 = aoy.cC();
			double double8 = aoy.cD();
			double double10 = aoy.cG();

			for (int integer12 = 0; integer12 < 16; integer12++) {
				double double13 = aoy.cC() + (aoy.cX().nextDouble() - 0.5) * 16.0;
				double double15 = aec.a(aoy.cD() + (double)(aoy.cX().nextInt(16) - 8), 0.0, (double)(bqb.ad() - 1));
				double double17 = aoy.cG() + (aoy.cX().nextDouble() - 0.5) * 16.0;
				if (aoy.bn()) {
					aoy.l();
				}

				if (aoy.a(double13, double15, double17, true)) {
					ack ack19 = aoy instanceof ays ? acl.ez : acl.bO;
					bqb.a(null, double6, double8, double10, ack19, acm.PLAYERS, 1.0F, 1.0F);
					aoy.a(ack19, 1.0F, 1.0F);
					break;
				}
			}

			if (aoy instanceof bec) {
				((bec)aoy).eT().a(this, 20);
			}
		}

		return bki5;
	}
}

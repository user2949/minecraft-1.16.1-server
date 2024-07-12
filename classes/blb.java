public class blb extends bke {
	public blb(bke.a a) {
		super(a);
	}

	@Override
	public boolean a(bki bki, bqb bqb, cfj cfj, fu fu, aoy aoy) {
		if (!bqb.v && !cfj.b().a(acx.am)) {
			bki.a(1, aoy, aoyx -> aoyx.c(aor.MAINHAND));
		}

		return !cfj.a(acx.H) && !cfj.a(bvs.aQ) && !cfj.a(bvs.aR) && !cfj.a(bvs.aS) && !cfj.a(bvs.aT) && !cfj.a(bvs.dP) && !cfj.a(bvs.em) && !cfj.a(acx.a)
			? super.a(bki, bqb, cfj, fu, aoy)
			: true;
	}

	@Override
	public boolean b(cfj cfj) {
		return cfj.a(bvs.aQ) || cfj.a(bvs.bS) || cfj.a(bvs.em);
	}

	@Override
	public float a(bki bki, cfj cfj) {
		if (cfj.a(bvs.aQ) || cfj.a(acx.H)) {
			return 15.0F;
		} else {
			return cfj.a(acx.a) ? 5.0F : super.a(bki, cfj);
		}
	}
}

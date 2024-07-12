public class bjx extends bke {
	public bjx(bke.a a) {
		super(a);
	}

	@Override
	public ang a(blv blv) {
		bec bec3 = blv.m();
		bqc bqc4 = blv.o();
		fu fu5 = blv.a();
		cfj cfj6 = bqc4.d_(fu5);
		if (bwb.h(cfj6)) {
			bqc4.a(bec3, fu5, acl.eo, acm.BLOCKS, 1.0F, h.nextFloat() * 0.4F + 0.8F);
			bqc4.a(fu5, cfj6.a(cfz.r, Boolean.valueOf(true)), 11);
			if (bec3 != null) {
				blv.l().a(1, bec3, bec -> bec.d(blv.n()));
			}

			return ang.a(bqc4.s_());
		} else {
			fu fu7 = fu5.a(blv.i());
			if (bvh.a(bqc4, fu7)) {
				bqc4.a(bec3, fu7, acl.eo, acm.BLOCKS, 1.0F, h.nextFloat() * 0.4F + 0.8F);
				cfj cfj8 = bvh.a((bpg)bqc4, fu7);
				bqc4.a(fu7, cfj8, 11);
				bki bki9 = blv.l();
				if (bec3 instanceof ze) {
					aa.y.a((ze)bec3, fu7, bki9);
					bki9.a(1, bec3, bec -> bec.d(blv.n()));
				}

				return ang.a(bqc4.s_());
			} else {
				return ang.FAIL;
			}
		}
	}
}

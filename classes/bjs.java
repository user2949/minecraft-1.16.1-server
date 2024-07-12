public class bjs extends bke {
	public bjs(bke.a a) {
		super(a);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		cfj cfj5 = bqb3.d_(fu4);
		boolean boolean6 = false;
		if (bwb.h(cfj5)) {
			this.a(bqb3, fu4);
			bqb3.a(fu4, cfj5.a(bwb.b, Boolean.valueOf(true)));
			boolean6 = true;
		} else {
			fu4 = fu4.a(blv.i());
			if (bvh.a((bqc)bqb3, fu4)) {
				this.a(bqb3, fu4);
				bqb3.a(fu4, bvh.a((bpg)bqb3, fu4));
				boolean6 = true;
			}
		}

		if (boolean6) {
			blv.l().g(1);
			return ang.a(bqb3.v);
		} else {
			return ang.FAIL;
		}
	}

	private void a(bqb bqb, fu fu) {
		bqb.a(null, fu, acl.dZ, acm.BLOCKS, 1.0F, (h.nextFloat() - h.nextFloat()) * 0.2F + 1.0F);
	}
}

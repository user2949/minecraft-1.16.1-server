public class bjy<T extends aom & aow> extends bke {
	private final aoq<T> a;
	private final int b;

	public bjy(bke.a a, aoq<T> aoq, int integer) {
		super(a);
		this.a = aoq;
		this.b = integer;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		bki bki5 = bec.b(anf);
		if (bqb.v) {
			return anh.c(bki5);
		} else {
			aom aom6 = bec.cs();
			if (bec.bn() && aom6 instanceof aow && aom6.U() == this.a) {
				aow aow7 = (aow)aom6;
				if (aow7.P_()) {
					bki5.a(this.b, bec, becx -> becx.d(anf));
					if (bki5.a()) {
						bki bki8 = new bki(bkk.mi);
						bki8.c(bki5.o());
						return anh.a(bki8);
					}

					return anh.a(bki5);
				}
			}

			bec.b(acu.c.b(this));
			return anh.c(bki5);
		}
	}
}

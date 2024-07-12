import javax.annotation.Nullable;

public class bbm extends bcm {
	public bbm(aoq<? extends bbm> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public static apw.a m() {
		return bcm.eL().a(apx.a, 12.0);
	}

	@Override
	public boolean B(aom aom) {
		if (super.B(aom)) {
			if (aom instanceof aoy) {
				int integer3 = 0;
				if (this.l.ac() == and.NORMAL) {
					integer3 = 7;
				} else if (this.l.ac() == and.HARD) {
					integer3 = 15;
				}

				if (integer3 > 0) {
					((aoy)aom).c(new aog(aoi.s, integer3 * 20, 0));
				}
			}

			return true;
		} else {
			return false;
		}
	}

	@Nullable
	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		return apo;
	}

	@Override
	protected float b(apj apj, aon aon) {
		return 0.45F;
	}
}

import java.util.Random;

public class bcn extends bbk {
	public bcn(aoq<? extends bcn> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public static boolean b(aoq<bcn> aoq, bqc bqc, apb apb, fu fu, Random random) {
		return c(aoq, bqc, apb, fu, random) && (apb == apb.SPAWNER || bqc.f(fu));
	}

	@Override
	protected ack I() {
		return acl.oM;
	}

	@Override
	protected ack e(anw anw) {
		return acl.oO;
	}

	@Override
	protected ack dp() {
		return acl.oN;
	}

	@Override
	ack eL() {
		return acl.oP;
	}

	@Override
	protected beg b(bki bki, float float2) {
		beg beg4 = super.b(bki, float2);
		if (beg4 instanceof bei) {
			((bei)beg4).a(new aog(aoi.b, 600));
		}

		return beg4;
	}
}

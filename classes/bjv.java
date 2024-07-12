import javax.annotation.Nullable;

public class bjv extends biu {
	private final aoq<?> a;

	public bjv(aoq<?> aoq, cwz cwz, bke.a a) {
		super(cwz, a);
		this.a = aoq;
	}

	@Override
	public void a(bqb bqb, bki bki, fu fu) {
		if (!bqb.v) {
			this.b(bqb, bki, fu);
		}
	}

	@Override
	protected void a(@Nullable bec bec, bqc bqc, fu fu) {
		bqc.a(bec, fu, acl.bk, acm.NEUTRAL, 1.0F, 1.0F);
	}

	private void b(bqb bqb, bki bki, fu fu) {
		aom aom5 = this.a.a(bqb, bki, null, fu, apb.BUCKET, true, false);
		if (aom5 != null) {
			((ayh)aom5).t(true);
		}
	}
}

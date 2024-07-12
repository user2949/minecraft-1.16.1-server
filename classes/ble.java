import javax.annotation.Nullable;

public class ble extends blk {
	public ble(bke.a a, bvr bvr2, bvr bvr3) {
		super(bvr2, bvr3, a);
	}

	@Override
	protected boolean a(fu fu, bqb bqb, @Nullable bec bec, bki bki, cfj cfj) {
		boolean boolean7 = super.a(fu, bqb, bec, bki, cfj);
		if (!bqb.v && !boolean7 && bec != null) {
			bec.a((ceh)bqb.c(fu));
		}

		return boolean7;
	}
}

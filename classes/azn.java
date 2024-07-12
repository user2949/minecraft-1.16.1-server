import javax.annotation.Nullable;

public class azn extends azl {
	public azn(aoq<? extends azn> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	protected ack I() {
		super.I();
		return acl.cN;
	}

	@Override
	protected ack fi() {
		super.fi();
		return acl.cO;
	}

	@Override
	protected ack dp() {
		super.dp();
		return acl.cQ;
	}

	@Nullable
	@Override
	protected ack fh() {
		return acl.cR;
	}

	@Override
	protected ack e(anw anw) {
		super.e(anw);
		return acl.cS;
	}

	@Override
	public boolean a(ayk ayk) {
		if (ayk == this) {
			return false;
		} else {
			return !(ayk instanceof azn) && !(ayk instanceof azo) ? false : this.fp() && ((azm)ayk).fp();
		}
	}

	@Override
	public aok a(aok aok) {
		aoq<? extends azm> aoq3 = aok instanceof azo ? aoq.aa : aoq.o;
		azm azm4 = aoq3.a(this.l);
		this.a(aok, azm4);
		return azm4;
	}
}

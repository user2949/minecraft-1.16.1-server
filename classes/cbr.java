public class cbr extends bvy {
	protected cbr(cfi.c c) {
		super(false, c);
	}

	@Override
	protected ack a(boolean boolean1) {
		return boolean1 ? acl.oF : acl.oE;
	}
}

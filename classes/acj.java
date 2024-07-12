public class acj {
	public static final aci a = new aci(acl.in, 20, 600, true);
	public static final aci b = new aci(acl.hV, 12000, 24000, false);
	public static final aci c = new aci(acl.hW, 0, 0, true);
	public static final aci d = new aci(acl.ik, 0, 0, true);
	public static final aci e = new aci(acl.il, 6000, 24000, false);
	public static final aci f = a(acl.it);
	public static final aci g = a(acl.im);

	public static aci a(ack ack) {
		return new aci(ack, 12000, 24000, false);
	}
}

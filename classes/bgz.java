public class bgz extends bhw {
	private final bgj a;

	public bgz(bgj bgj, amz amz, int integer3, int integer4, int integer5) {
		super(amz, integer3, integer4, integer5);
		this.a = bgj;
	}

	@Override
	public boolean a(bki bki) {
		return this.a.b(bki) || c_(bki);
	}

	@Override
	public int b(bki bki) {
		return c_(bki) ? 1 : super.b(bki);
	}

	public static boolean c_(bki bki) {
		return bki.b() == bkk.lK;
	}
}

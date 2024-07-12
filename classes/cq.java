import com.google.gson.JsonObject;

public class cq extends ci<cq.a> {
	private static final uh a = new uh("used_ender_eye");

	@Override
	public uh a() {
		return a;
	}

	public cq.a b(JsonObject jsonObject, be.b b, av av) {
		bx.c c5 = bx.c.a(jsonObject.get("distance"));
		return new cq.a(b, c5);
	}

	public void a(ze ze, fu fu) {
		double double4 = ze.cC() - (double)fu.u();
		double double6 = ze.cG() - (double)fu.w();
		double double8 = double4 * double4 + double6 * double6;
		this.a(ze, a -> a.a(double8));
	}

	public static class a extends aj {
		private final bx.c a;

		public a(be.b b, bx.c c) {
			super(cq.a, b);
			this.a = c;
		}

		public boolean a(double double1) {
			return this.a.a(double1);
		}
	}
}

public class iv {
	@Deprecated
	public static uh a(String string) {
		return new uh("minecraft", "block/" + string);
	}

	public static uh b(String string) {
		return new uh("minecraft", "item/" + string);
	}

	public static uh a(bvr bvr, String string) {
		uh uh3 = gl.aj.b(bvr);
		return new uh(uh3.b(), "block/" + uh3.a() + string);
	}

	public static uh a(bvr bvr) {
		uh uh2 = gl.aj.b(bvr);
		return new uh(uh2.b(), "block/" + uh2.a());
	}

	public static uh a(bke bke) {
		uh uh2 = gl.am.b(bke);
		return new uh(uh2.b(), "item/" + uh2.a());
	}

	public static uh a(bke bke, String string) {
		uh uh3 = gl.am.b(bke);
		return new uh(uh3.b(), "item/" + uh3.a() + string);
	}
}

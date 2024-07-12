import com.google.gson.JsonPrimitive;

public class ir {
	public static final is<ir.a> a = new is<>("x", a -> new JsonPrimitive(a.e));
	public static final is<ir.a> b = new is<>("y", a -> new JsonPrimitive(a.e));
	public static final is<uh> c = new is<>("model", uh -> new JsonPrimitive(uh.toString()));
	public static final is<Boolean> d = new is<>("uvlock", JsonPrimitive::new);
	public static final is<Integer> e = new is<>("weight", JsonPrimitive::new);

	public static enum a {
		R0(0),
		R90(90),
		R180(180),
		R270(270);

		private final int e;

		private a(int integer3) {
			this.e = integer3;
		}
	}
}

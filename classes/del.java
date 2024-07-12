public class del {
	public static final del a = new del(0.0F, 0.0F);
	public static final del b = new del(1.0F, 1.0F);
	public static final del c = new del(1.0F, 0.0F);
	public static final del d = new del(-1.0F, 0.0F);
	public static final del e = new del(0.0F, 1.0F);
	public static final del f = new del(0.0F, -1.0F);
	public static final del g = new del(Float.MAX_VALUE, Float.MAX_VALUE);
	public static final del h = new del(Float.MIN_VALUE, Float.MIN_VALUE);
	public final float i;
	public final float j;

	public del(float float1, float float2) {
		this.i = float1;
		this.j = float2;
	}

	public boolean c(del del) {
		return this.i == del.i && this.j == del.j;
	}
}

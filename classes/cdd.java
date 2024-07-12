import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public enum cdd {
	BASE("base", "b", false),
	SQUARE_BOTTOM_LEFT("square_bottom_left", "bl"),
	SQUARE_BOTTOM_RIGHT("square_bottom_right", "br"),
	SQUARE_TOP_LEFT("square_top_left", "tl"),
	SQUARE_TOP_RIGHT("square_top_right", "tr"),
	STRIPE_BOTTOM("stripe_bottom", "bs"),
	STRIPE_TOP("stripe_top", "ts"),
	STRIPE_LEFT("stripe_left", "ls"),
	STRIPE_RIGHT("stripe_right", "rs"),
	STRIPE_CENTER("stripe_center", "cs"),
	STRIPE_MIDDLE("stripe_middle", "ms"),
	STRIPE_DOWNRIGHT("stripe_downright", "drs"),
	STRIPE_DOWNLEFT("stripe_downleft", "dls"),
	STRIPE_SMALL("small_stripes", "ss"),
	CROSS("cross", "cr"),
	STRAIGHT_CROSS("straight_cross", "sc"),
	TRIANGLE_BOTTOM("triangle_bottom", "bt"),
	TRIANGLE_TOP("triangle_top", "tt"),
	TRIANGLES_BOTTOM("triangles_bottom", "bts"),
	TRIANGLES_TOP("triangles_top", "tts"),
	DIAGONAL_LEFT("diagonal_left", "ld"),
	DIAGONAL_RIGHT("diagonal_up_right", "rd"),
	DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud"),
	DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud"),
	CIRCLE_MIDDLE("circle", "mc"),
	RHOMBUS_MIDDLE("rhombus", "mr"),
	HALF_VERTICAL("half_vertical", "vh"),
	HALF_HORIZONTAL("half_horizontal", "hh"),
	HALF_VERTICAL_MIRROR("half_vertical_right", "vhr"),
	HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb"),
	BORDER("border", "bo"),
	CURLY_BORDER("curly_border", "cbo"),
	GRADIENT("gradient", "gra"),
	GRADIENT_UP("gradient_up", "gru"),
	BRICKS("bricks", "bri"),
	GLOBE("globe", "glb", true),
	CREEPER("creeper", "cre", true),
	SKULL("skull", "sku", true),
	FLOWER("flower", "flo", true),
	MOJANG("mojang", "moj", true),
	PIGLIN("piglin", "pig", true);

	private static final cdd[] S = values();
	public static final int P = S.length;
	public static final int Q = (int)Arrays.stream(S).filter(cdd -> cdd.T).count();
	public static final int R = P - Q - 1;
	private final boolean T;
	private final String U;
	private final String V;

	private cdd(String string3, String string4) {
		this(string3, string4, false);
	}

	private cdd(String string3, String string4, boolean boolean5) {
		this.U = string3;
		this.V = string4;
		this.T = boolean5;
	}

	public String b() {
		return this.V;
	}

	public static class a {
		private final List<Pair<cdd, bje>> a = Lists.<Pair<cdd, bje>>newArrayList();

		public cdd.a a(cdd cdd, bje bje) {
			this.a.add(Pair.of(cdd, bje));
			return this;
		}

		public lk a() {
			lk lk2 = new lk();

			for (Pair<cdd, bje> pair4 : this.a) {
				le le5 = new le();
				le5.a("Pattern", pair4.getLeft().V);
				le5.b("Color", pair4.getRight().b());
				lk2.add(le5);
			}

			return lk2;
		}
	}
}

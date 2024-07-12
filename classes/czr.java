import java.util.Objects;
import javax.annotation.Nullable;

public class czr {
	private final fu a;
	private final bje b;
	@Nullable
	private final mr c;

	public czr(fu fu, bje bje, @Nullable mr mr) {
		this.a = fu;
		this.b = bje;
		this.c = mr;
	}

	public static czr a(le le) {
		fu fu2 = lq.b(le.p("Pos"));
		bje bje3 = bje.a(le.l("Color"), bje.WHITE);
		mr mr4 = le.e("Name") ? mr.a.a(le.l("Name")) : null;
		return new czr(fu2, bje3, mr4);
	}

	@Nullable
	public static czr a(bpg bpg, fu fu) {
		cdl cdl3 = bpg.c(fu);
		if (cdl3 instanceof cdc) {
			cdc cdc4 = (cdc)cdl3;
			bje bje5 = cdc4.a(() -> bpg.d_(fu));
			mr mr6 = cdc4.Q() ? cdc4.R() : null;
			return new czr(fu, bje5, mr6);
		} else {
			return null;
		}
	}

	public fu a() {
		return this.a;
	}

	public czs.a c() {
		switch (this.b) {
			case WHITE:
				return czs.a.BANNER_WHITE;
			case ORANGE:
				return czs.a.BANNER_ORANGE;
			case MAGENTA:
				return czs.a.BANNER_MAGENTA;
			case LIGHT_BLUE:
				return czs.a.BANNER_LIGHT_BLUE;
			case YELLOW:
				return czs.a.BANNER_YELLOW;
			case LIME:
				return czs.a.BANNER_LIME;
			case PINK:
				return czs.a.BANNER_PINK;
			case GRAY:
				return czs.a.BANNER_GRAY;
			case LIGHT_GRAY:
				return czs.a.BANNER_LIGHT_GRAY;
			case CYAN:
				return czs.a.BANNER_CYAN;
			case PURPLE:
				return czs.a.BANNER_PURPLE;
			case BLUE:
				return czs.a.BANNER_BLUE;
			case BROWN:
				return czs.a.BANNER_BROWN;
			case GREEN:
				return czs.a.BANNER_GREEN;
			case RED:
				return czs.a.BANNER_RED;
			case BLACK:
			default:
				return czs.a.BANNER_BLACK;
		}
	}

	@Nullable
	public mr d() {
		return this.c;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			czr czr3 = (czr)object;
			return Objects.equals(this.a, czr3.a) && this.b == czr3.b && Objects.equals(this.c, czr3.c);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return Objects.hash(new Object[]{this.a, this.b, this.c});
	}

	public le e() {
		le le2 = new le();
		le2.a("Pos", lq.a(this.a));
		le2.a("Color", this.b.c());
		if (this.c != null) {
			le2.a("Name", mr.a.a(this.c));
		}

		return le2;
	}

	public String f() {
		return "banner-" + this.a.u() + "," + this.a.v() + "," + this.a.w();
	}
}

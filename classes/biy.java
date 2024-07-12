import javax.annotation.Nullable;

public abstract class biy {
	public static final biy[] a = new biy[12];
	public static final biy b = (new biy(0, "buildingBlocks") {
	}).b("building_blocks");
	public static final biy c = new biy(1, "decorations") {
	};
	public static final biy d = new biy(2, "redstone") {
	};
	public static final biy e = new biy(3, "transportation") {
	};
	public static final biy f = new biy(6, "misc") {
	};
	public static final biy g = (new biy(5, "search") {
	}).a("item_search.png");
	public static final biy h = new biy(7, "food") {
	};
	public static final biy i = (new biy(8, "tools") {
	}).a(new bnx[]{bnx.VANISHABLE, bnx.DIGGER, bnx.FISHING_ROD, bnx.BREAKABLE});
	public static final biy j = (new biy(9, "combat") {
		})
		.a(
			new bnx[]{
				bnx.VANISHABLE,
				bnx.ARMOR,
				bnx.ARMOR_FEET,
				bnx.ARMOR_HEAD,
				bnx.ARMOR_LEGS,
				bnx.ARMOR_CHEST,
				bnx.BOW,
				bnx.WEAPON,
				bnx.WEARABLE,
				bnx.BREAKABLE,
				bnx.TRIDENT,
				bnx.CROSSBOW
			}
		);
	public static final biy k = new biy(10, "brewing") {
	};
	public static final biy l = f;
	public static final biy m = new biy(4, "hotbar") {
	};
	public static final biy n = (new biy(11, "inventory") {
	}).a("inventory.png").k().i();
	private final int o;
	private final String p;
	private String q;
	private String r = "items.png";
	private boolean s = true;
	private boolean t = true;
	private bnx[] u = new bnx[0];
	private bki v;

	public biy(int integer, String string) {
		this.o = integer;
		this.p = string;
		this.v = bki.b;
		a[integer] = this;
	}

	public String c() {
		return this.q == null ? this.p : this.q;
	}

	public biy a(String string) {
		this.r = string;
		return this;
	}

	public biy b(String string) {
		this.q = string;
		return this;
	}

	public biy i() {
		this.t = false;
		return this;
	}

	public biy k() {
		this.s = false;
		return this;
	}

	public bnx[] o() {
		return this.u;
	}

	public biy a(bnx... arr) {
		this.u = arr;
		return this;
	}

	public boolean a(@Nullable bnx bnx) {
		if (bnx != null) {
			for (bnx bnx6 : this.u) {
				if (bnx6 == bnx) {
					return true;
				}
			}
		}

		return false;
	}
}

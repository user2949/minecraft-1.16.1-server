import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum gb implements aeh {
	DOWN_EAST("down_east", fz.DOWN, fz.EAST),
	DOWN_NORTH("down_north", fz.DOWN, fz.NORTH),
	DOWN_SOUTH("down_south", fz.DOWN, fz.SOUTH),
	DOWN_WEST("down_west", fz.DOWN, fz.WEST),
	UP_EAST("up_east", fz.UP, fz.EAST),
	UP_NORTH("up_north", fz.UP, fz.NORTH),
	UP_SOUTH("up_south", fz.UP, fz.SOUTH),
	UP_WEST("up_west", fz.UP, fz.WEST),
	WEST_UP("west_up", fz.WEST, fz.UP),
	EAST_UP("east_up", fz.EAST, fz.UP),
	NORTH_UP("north_up", fz.NORTH, fz.UP),
	SOUTH_UP("south_up", fz.SOUTH, fz.UP);

	private static final Int2ObjectMap<gb> m = new Int2ObjectOpenHashMap<>(values().length);
	private final String n;
	private final fz o;
	private final fz p;

	private static int b(fz fz1, fz fz2) {
		return fz1.ordinal() << 3 | fz2.ordinal();
	}

	private gb(String string3, fz fz4, fz fz5) {
		this.n = string3;
		this.p = fz4;
		this.o = fz5;
	}

	@Override
	public String a() {
		return this.n;
	}

	public static gb a(fz fz1, fz fz2) {
		int integer3 = b(fz2, fz1);
		return m.get(integer3);
	}

	public fz b() {
		return this.p;
	}

	public fz c() {
		return this.o;
	}

	static {
		for (gb gb4 : values()) {
			m.put(b(gb4.o, gb4.p), gb4);
		}
	}
}

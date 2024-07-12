import java.io.IOException;

public class ok implements ni<nl> {
	public static final uh a = new uh("brand");
	public static final uh b = new uh("debug/path");
	public static final uh c = new uh("debug/neighbors_update");
	public static final uh d = new uh("debug/caves");
	public static final uh e = new uh("debug/structures");
	public static final uh f = new uh("debug/worldgen_attempt");
	public static final uh g = new uh("debug/poi_ticket_count");
	public static final uh h = new uh("debug/poi_added");
	public static final uh i = new uh("debug/poi_removed");
	public static final uh j = new uh("debug/village_sections");
	public static final uh k = new uh("debug/goal_selector");
	public static final uh l = new uh("debug/brain");
	public static final uh m = new uh("debug/bee");
	public static final uh n = new uh("debug/hive");
	public static final uh o = new uh("debug/game_test_add_marker");
	public static final uh p = new uh("debug/game_test_clear");
	public static final uh q = new uh("debug/raids");
	private uh r;
	private mg s;

	public ok() {
	}

	public ok(uh uh, mg mg) {
		this.r = uh;
		this.s = mg;
		if (mg.writerIndex() > 1048576) {
			throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
		}
	}

	@Override
	public void a(mg mg) throws IOException {
		this.r = mg.o();
		int integer3 = mg.readableBytes();
		if (integer3 >= 0 && integer3 <= 1048576) {
			this.s = new mg(mg.readBytes(integer3));
		} else {
			throw new IOException("Payload may not be larger than 1048576 bytes");
		}
	}

	@Override
	public void b(mg mg) throws IOException {
		mg.a(this.r);
		mg.writeBytes(this.s.copy());
	}

	public void a(nl nl) {
		nl.a(this);
	}
}

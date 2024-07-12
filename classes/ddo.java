import java.util.function.Predicate;

public class ddo {
	public static final ddn a = a("inverted", new ddj.a());
	public static final ddn b = a("alternative", new ddc.b());
	public static final ddn c = a("random_chance", new ddr.a());
	public static final ddn d = a("random_chance_with_looting", new dds.a());
	public static final ddn e = a("entity_properties", new ddp.a());
	public static final ddn f = a("killed_by_player", new ddq.a());
	public static final ddn g = a("entity_scores", new ddh.b());
	public static final ddn h = a("block_state_property", new ddl.b());
	public static final ddn i = a("match_tool", new ddt.a());
	public static final ddn j = a("table_bonus", new ddd.a());
	public static final ddn k = a("survives_explosion", new ddi.a());
	public static final ddn l = a("damage_source_properties", new ddg.a());
	public static final ddn m = a("location_check", new ddk.a());
	public static final ddn n = a("weather_check", new ddv.b());
	public static final ddn o = a("reference", new dde.a());
	public static final ddn p = a("time_check", new ddu.b());

	private static ddn a(String string, dbc<? extends ddm> dbc) {
		return gl.a(gl.ba, new uh(string), new ddn(dbc));
	}

	public static Object a() {
		return dar.<ddm, ddn>a(gl.ba, "condition", "condition", ddm::b).a();
	}

	public static <T> Predicate<T> a(Predicate<T>[] arr) {
		switch (arr.length) {
			case 0:
				return object -> true;
			case 1:
				return arr[0];
			case 2:
				return arr[0].and(arr[1]);
			default:
				return object -> {
					for (Predicate<T> predicate6 : arr) {
						if (!predicate6.test(object)) {
							return false;
						}
					}

					return true;
				};
		}
	}

	public static <T> Predicate<T> b(Predicate<T>[] arr) {
		switch (arr.length) {
			case 0:
				return object -> false;
			case 1:
				return arr[0];
			case 2:
				return arr[0].or(arr[1]);
			default:
				return object -> {
					for (Predicate<T> predicate6 : arr) {
						if (predicate6.test(object)) {
							return true;
						}
					}

					return false;
				};
		}
	}
}

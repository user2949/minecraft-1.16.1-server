import java.util.function.BiFunction;

public class dcj {
	public static final BiFunction<bki, dat, bki> a = (bki, dat) -> bki;
	public static final dci b = a("set_count", new dco.a());
	public static final dci c = a("enchant_with_levels", new dcb.b());
	public static final dci d = a("enchant_randomly", new dca.b());
	public static final dci e = a("set_nbt", new dcs.a());
	public static final dci f = a("furnace_smelt", new dcu.a());
	public static final dci g = a("looting_enchant", new dck.b());
	public static final dci h = a("set_damage", new dcp.a());
	public static final dci i = a("set_attributes", new dcl.d());
	public static final dci j = a("set_name", new dcr.a());
	public static final dci k = a("exploration_map", new dcc.b());
	public static final dci l = a("set_stew_effect", new dct.b());
	public static final dci m = a("copy_name", new dby.b());
	public static final dci n = a("set_contents", new dcm.b());
	public static final dci o = a("limit_count", new dcf.a());
	public static final dci p = a("apply_bonus", new dbv.e());
	public static final dci q = a("set_loot_table", new dcn.a());
	public static final dci r = a("explosion_decay", new dbw.a());
	public static final dci s = a("set_lore", new dcq.b());
	public static final dci t = a("fill_player_head", new dcd.a());
	public static final dci u = a("copy_nbt", new dbz.e());
	public static final dci v = a("copy_state", new dbx.b());

	private static dci a(String string, dbc<? extends dch> dbc) {
		return gl.a(gl.aZ, new uh(string), new dci(dbc));
	}

	public static Object a() {
		return dar.<dch, dci>a(gl.aZ, "function", "function", dch::b).a();
	}

	public static BiFunction<bki, dat, bki> a(BiFunction<bki, dat, bki>[] arr) {
		switch (arr.length) {
			case 0:
				return a;
			case 1:
				return arr[0];
			case 2:
				BiFunction<bki, dat, bki> biFunction2 = arr[0];
				BiFunction<bki, dat, bki> biFunction3 = arr[1];
				return (bki, dat) -> (bki)biFunction3.apply(biFunction2.apply(bki, dat), dat);
			default:
				return (bki, dat) -> {
					for (BiFunction<bki, dat, bki> biFunction7 : arr) {
						bki = (bki)biFunction7.apply(bki, dat);
					}

					return bki;
				};
		}
	}
}

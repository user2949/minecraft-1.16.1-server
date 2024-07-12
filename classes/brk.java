import java.util.Collections;

public abstract class brk {
	public static final bre a = a(0, "ocean", new bth());
	public static final bre b = a;
	public static final bre c = a(1, "plains", new btj());
	public static final bre d = a(2, "desert", new brx());
	public static final bre e = a(3, "mountains", new bta());
	public static final bre f = a(4, "forest", new bsf());
	public static final bre g = a(5, "taiga", new bub());
	public static final bre h = a(6, "swamp", new btz());
	public static final bre i = a(7, "river", new btk());
	public static final bre j = a(8, "nether_wastes", new btg());
	public static final bre k = a(9, "the_end", new bug());
	public static final bre l = a(10, "frozen_ocean", new bsh());
	public static final bre m = a(11, "frozen_river", new bsi());
	public static final bre n = a(12, "snowy_tundra", new btv());
	public static final bre o = a(13, "snowy_mountains", new btr());
	public static final bre p = a(14, "mushroom_fields", new btd());
	public static final bre q = a(15, "mushroom_field_shore", new bte());
	public static final bre r = a(16, "beach", new brd());
	public static final bre s = a(17, "desert_hills", new bry());
	public static final bre t = a(18, "wooded_hills", new bum());
	public static final bre u = a(19, "taiga_hills", new buc());
	public static final bre v = a(20, "mountain_edge", new btb());
	public static final bre w = a(21, "jungle", new bsr());
	public static final bre x = a(22, "jungle_hills", new bst());
	public static final bre y = a(23, "jungle_edge", new bss());
	public static final bre z = a(24, "deep_ocean", new brv());
	public static final bre A = a(25, "stone_shore", new btx());
	public static final bre B = a(26, "snowy_beach", new btq());
	public static final bre C = a(27, "birch_forest", new brl());
	public static final bre D = a(28, "birch_forest_hills", new brm());
	public static final bre E = a(29, "dark_forest", new brq());
	public static final bre F = a(30, "snowy_taiga", new bts());
	public static final bre G = a(31, "snowy_taiga_hills", new btt());
	public static final bre H = a(32, "giant_tree_taiga", new bsn());
	public static final bre I = a(33, "giant_tree_taiga_hills", new bso());
	public static final bre J = a(34, "wooded_mountains", new bun());
	public static final bre K = a(35, "savanna", new btl());
	public static final bre L = a(36, "savanna_plateau", new btm());
	public static final bre M = a(37, "badlands", new bqy());
	public static final bre N = a(38, "wooded_badlands_plateau", new bul());
	public static final bre O = a(39, "badlands_plateau", new bqz());
	public static final bre P = a(40, "small_end_islands", new btp());
	public static final bre Q = a(41, "end_midlands", new bsc());
	public static final bre R = a(42, "end_highlands", new bsb());
	public static final bre S = a(43, "end_barrens", new bsa());
	public static final bre T = a(44, "warm_ocean", new buj());
	public static final bre U = a(45, "lukewarm_ocean", new bsu());
	public static final bre V = a(46, "cold_ocean", new bro());
	public static final bre W = a(47, "deep_warm_ocean", new brw());
	public static final bre X = a(48, "deep_lukewarm_ocean", new bru());
	public static final bre Y = a(49, "deep_cold_ocean", new brs());
	public static final bre Z = a(50, "deep_frozen_ocean", new brt());
	public static final bre aa = a(127, "the_void", new bui());
	public static final bre ab = a(129, "sunflower_plains", new bty());
	public static final bre ac = a(130, "desert_lakes", new brz());
	public static final bre ad = a(131, "gravelly_mountains", new bsp());
	public static final bre ae = a(132, "flower_forest", new bsg());
	public static final bre af = a(133, "taiga_mountains", new bud());
	public static final bre ag = a(134, "swamp_hills", new bua());
	public static final bre ah = a(140, "ice_spikes", new bsq());
	public static final bre ai = a(149, "modified_jungle", new bsx());
	public static final bre aj = a(151, "modified_jungle_edge", new bsy());
	public static final bre ak = a(155, "tall_birch_forest", new bue());
	public static final bre al = a(156, "tall_birch_hills", new buf());
	public static final bre am = a(157, "dark_forest_hills", new brr());
	public static final bre an = a(158, "snowy_taiga_mountains", new btu());
	public static final bre ao = a(160, "giant_spruce_taiga", new bsl());
	public static final bre ap = a(161, "giant_spruce_taiga_hills", new bsm());
	public static final bre aq = a(162, "modified_gravelly_mountains", new bsw());
	public static final bre ar = a(163, "shattered_savanna", new btn());
	public static final bre as = a(164, "shattered_savanna_plateau", new bto());
	public static final bre at = a(165, "eroded_badlands", new bsd());
	public static final bre au = a(166, "modified_wooded_badlands_plateau", new bsz());
	public static final bre av = a(167, "modified_badlands_plateau", new bsv());
	public static final bre aw = a(168, "bamboo_jungle", new bra());
	public static final bre ax = a(169, "bamboo_jungle_hills", new brb());
	public static final bre ay = a(170, "soul_sand_valley", new btw());
	public static final bre az = a(171, "crimson_forest", new brp());
	public static final bre aA = a(172, "warped_forest", new buk());
	public static final bre aB = a(173, "basalt_deltas", new brc());

	private static bre a(int integer, String string, bre bre) {
		gl.a(gl.as, integer, string, bre);
		if (bre.b()) {
			bre.d.a(bre, gl.as.a(gl.as.a(new uh(bre.l))));
		}

		return bre;
	}

	static {
		Collections.addAll(bre.c, new bre[]{a, c, d, e, f, g, h, i, m, n, o, p, q, r, s, t, u, w, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O});
	}
}

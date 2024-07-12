public class boa {
	private static final aor[] M = new aor[]{aor.HEAD, aor.CHEST, aor.LEGS, aor.FEET};
	public static final bnw a = a("protection", new boj(bnw.a.COMMON, boj.a.ALL, M));
	public static final bnw b = a("fire_protection", new boj(bnw.a.UNCOMMON, boj.a.FIRE, M));
	public static final bnw c = a("feather_falling", new boj(bnw.a.UNCOMMON, boj.a.FALL, M));
	public static final bnw d = a("blast_protection", new boj(bnw.a.RARE, boj.a.EXPLOSION, M));
	public static final bnw e = a("projectile_protection", new boj(bnw.a.UNCOMMON, boj.a.PROJECTILE, M));
	public static final bnw f = a("respiration", new boi(bnw.a.RARE, M));
	public static final bnw g = a("aqua_affinity", new bov(bnw.a.RARE, M));
	public static final bnw h = a("thorns", new bon(bnw.a.VERY_RARE, M));
	public static final bnw i = a("depth_strider", new bou(bnw.a.RARE, M));
	public static final bnw j = a("frost_walker", new bod(bnw.a.RARE, aor.FEET));
	public static final bnw k = a("binding_curse", new bns(bnw.a.VERY_RARE, M));
	public static final bnw l = a("soul_speed", new bol(bnw.a.VERY_RARE, aor.FEET));
	public static final bnw m = a("sharpness", new bnt(bnw.a.COMMON, 0, aor.MAINHAND));
	public static final bnw n = a("smite", new bnt(bnw.a.UNCOMMON, 1, aor.MAINHAND));
	public static final bnw o = a("bane_of_arthropods", new bnt(bnw.a.UNCOMMON, 2, aor.MAINHAND));
	public static final bnw p = a("knockback", new boe(bnw.a.UNCOMMON, aor.MAINHAND));
	public static final bnw q = a("fire_aspect", new bob(bnw.a.RARE, aor.MAINHAND));
	public static final bnw r = a("looting", new bof(bnw.a.RARE, bnx.WEAPON, aor.MAINHAND));
	public static final bnw s = a("sweeping", new bom(bnw.a.RARE, aor.MAINHAND));
	public static final bnw t = a("efficiency", new bnv(bnw.a.COMMON, aor.MAINHAND));
	public static final bnw u = a("silk_touch", new bos(bnw.a.VERY_RARE, aor.MAINHAND));
	public static final bnw v = a("unbreaking", new bnu(bnw.a.UNCOMMON, aor.MAINHAND));
	public static final bnw w = a("fortune", new bof(bnw.a.RARE, bnx.DIGGER, aor.MAINHAND));
	public static final bnw x = a("power", new bnn(bnw.a.COMMON, aor.MAINHAND));
	public static final bnw y = a("punch", new bnq(bnw.a.RARE, aor.MAINHAND));
	public static final bnw z = a("flame", new bno(bnw.a.RARE, aor.MAINHAND));
	public static final bnw A = a("infinity", new bnp(bnw.a.VERY_RARE, aor.MAINHAND));
	public static final bnw B = a("luck_of_the_sea", new bof(bnw.a.RARE, bnx.FISHING_ROD, aor.MAINHAND));
	public static final bnw C = a("lure", new boc(bnw.a.RARE, bnx.FISHING_ROD, aor.MAINHAND));
	public static final bnw D = a("loyalty", new boq(bnw.a.UNCOMMON, aor.MAINHAND));
	public static final bnw E = a("impaling", new bop(bnw.a.RARE, aor.MAINHAND));
	public static final bnw F = a("riptide", new bor(bnw.a.RARE, aor.MAINHAND));
	public static final bnw G = a("channeling", new boo(bnw.a.VERY_RARE, aor.MAINHAND));
	public static final bnw H = a("multishot", new boh(bnw.a.RARE, aor.MAINHAND));
	public static final bnw I = a("quick_charge", new bok(bnw.a.UNCOMMON, aor.MAINHAND));
	public static final bnw J = a("piercing", new bnr(bnw.a.COMMON, aor.MAINHAND));
	public static final bnw K = a("mending", new bog(bnw.a.RARE, aor.values()));
	public static final bnw L = a("vanishing_curse", new bot(bnw.a.VERY_RARE, aor.values()));

	private static bnw a(String string, bnw bnw) {
		return gl.a(gl.ak, string, bnw);
	}
}

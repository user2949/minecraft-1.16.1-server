public class aoi {
	public static final aoe a = a(1, "speed", new aoe(aof.BENEFICIAL, 8171462).a(apx.d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2F, apv.a.MULTIPLY_TOTAL));
	public static final aoe b = a(2, "slowness", new aoe(aof.HARMFUL, 5926017).a(apx.d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, apv.a.MULTIPLY_TOTAL));
	public static final aoe c = a(3, "haste", new aoe(aof.BENEFICIAL, 14270531).a(apx.h, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.1F, apv.a.MULTIPLY_TOTAL));
	public static final aoe d = a(4, "mining_fatigue", new aoe(aof.HARMFUL, 4866583).a(apx.h, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1F, apv.a.MULTIPLY_TOTAL));
	public static final aoe e = a(5, "strength", new aob(aof.BENEFICIAL, 9643043, 3.0).a(apx.f, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0, apv.a.ADDITION));
	public static final aoe f = a(6, "instant_health", new aod(aof.BENEFICIAL, 16262179));
	public static final aoe g = a(7, "instant_damage", new aod(aof.HARMFUL, 4393481));
	public static final aoe h = a(8, "jump_boost", new aoe(aof.BENEFICIAL, 2293580));
	public static final aoe i = a(9, "nausea", new aoe(aof.HARMFUL, 5578058));
	public static final aoe j = a(10, "regeneration", new aoe(aof.BENEFICIAL, 13458603));
	public static final aoe k = a(11, "resistance", new aoe(aof.BENEFICIAL, 10044730));
	public static final aoe l = a(12, "fire_resistance", new aoe(aof.BENEFICIAL, 14981690));
	public static final aoe m = a(13, "water_breathing", new aoe(aof.BENEFICIAL, 3035801));
	public static final aoe n = a(14, "invisibility", new aoe(aof.BENEFICIAL, 8356754));
	public static final aoe o = a(15, "blindness", new aoe(aof.HARMFUL, 2039587));
	public static final aoe p = a(16, "night_vision", new aoe(aof.BENEFICIAL, 2039713));
	public static final aoe q = a(17, "hunger", new aoe(aof.HARMFUL, 5797459));
	public static final aoe r = a(18, "weakness", new aob(aof.HARMFUL, 4738376, -4.0).a(apx.f, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0, apv.a.ADDITION));
	public static final aoe s = a(19, "poison", new aoe(aof.HARMFUL, 5149489));
	public static final aoe t = a(20, "wither", new aoe(aof.HARMFUL, 3484199));
	public static final aoe u = a(21, "health_boost", new aoc(aof.BENEFICIAL, 16284963).a(apx.a, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, apv.a.ADDITION));
	public static final aoe v = a(22, "absorption", new aoa(aof.BENEFICIAL, 2445989));
	public static final aoe w = a(23, "saturation", new aod(aof.BENEFICIAL, 16262179));
	public static final aoe x = a(24, "glowing", new aoe(aof.NEUTRAL, 9740385));
	public static final aoe y = a(25, "levitation", new aoe(aof.HARMFUL, 13565951));
	public static final aoe z = a(26, "luck", new aoe(aof.BENEFICIAL, 3381504).a(apx.k, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0, apv.a.ADDITION));
	public static final aoe A = a(27, "unluck", new aoe(aof.HARMFUL, 12624973).a(apx.k, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0, apv.a.ADDITION));
	public static final aoe B = a(28, "slow_falling", new aoe(aof.BENEFICIAL, 16773073));
	public static final aoe C = a(29, "conduit_power", new aoe(aof.BENEFICIAL, 1950417));
	public static final aoe D = a(30, "dolphins_grace", new aoe(aof.BENEFICIAL, 8954814));
	public static final aoe E = a(31, "bad_omen", new aoe(aof.NEUTRAL, 745784) {
		@Override
		public boolean a(int integer1, int integer2) {
			return true;
		}

		@Override
		public void a(aoy aoy, int integer) {
			if (aoy instanceof ze && !aoy.a_()) {
				ze ze4 = (ze)aoy;
				zd zd5 = ze4.u();
				if (zd5.ac() == and.PEACEFUL) {
					return;
				}

				if (zd5.b_(aoy.cA())) {
					zd5.y().a(ze4);
				}
			}
		}
	});
	public static final aoe F = a(32, "hero_of_the_village", new aoe(aof.BENEFICIAL, 4521796));

	private static aoe a(int integer, String string, aoe aoe) {
		return gl.a(gl.ai, integer, string, aoe);
	}
}

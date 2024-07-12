public class bme {
	public static final bmb a = a("empty", new bmb());
	public static final bmb b = a("water", new bmb());
	public static final bmb c = a("mundane", new bmb());
	public static final bmb d = a("thick", new bmb());
	public static final bmb e = a("awkward", new bmb());
	public static final bmb f = a("night_vision", new bmb(new aog(aoi.p, 3600)));
	public static final bmb g = a("long_night_vision", new bmb("night_vision", new aog(aoi.p, 9600)));
	public static final bmb h = a("invisibility", new bmb(new aog(aoi.n, 3600)));
	public static final bmb i = a("long_invisibility", new bmb("invisibility", new aog(aoi.n, 9600)));
	public static final bmb j = a("leaping", new bmb(new aog(aoi.h, 3600)));
	public static final bmb k = a("long_leaping", new bmb("leaping", new aog(aoi.h, 9600)));
	public static final bmb l = a("strong_leaping", new bmb("leaping", new aog(aoi.h, 1800, 1)));
	public static final bmb m = a("fire_resistance", new bmb(new aog(aoi.l, 3600)));
	public static final bmb n = a("long_fire_resistance", new bmb("fire_resistance", new aog(aoi.l, 9600)));
	public static final bmb o = a("swiftness", new bmb(new aog(aoi.a, 3600)));
	public static final bmb p = a("long_swiftness", new bmb("swiftness", new aog(aoi.a, 9600)));
	public static final bmb q = a("strong_swiftness", new bmb("swiftness", new aog(aoi.a, 1800, 1)));
	public static final bmb r = a("slowness", new bmb(new aog(aoi.b, 1800)));
	public static final bmb s = a("long_slowness", new bmb("slowness", new aog(aoi.b, 4800)));
	public static final bmb t = a("strong_slowness", new bmb("slowness", new aog(aoi.b, 400, 3)));
	public static final bmb u = a("turtle_master", new bmb("turtle_master", new aog(aoi.b, 400, 3), new aog(aoi.k, 400, 2)));
	public static final bmb v = a("long_turtle_master", new bmb("turtle_master", new aog(aoi.b, 800, 3), new aog(aoi.k, 800, 2)));
	public static final bmb w = a("strong_turtle_master", new bmb("turtle_master", new aog(aoi.b, 400, 5), new aog(aoi.k, 400, 3)));
	public static final bmb x = a("water_breathing", new bmb(new aog(aoi.m, 3600)));
	public static final bmb y = a("long_water_breathing", new bmb("water_breathing", new aog(aoi.m, 9600)));
	public static final bmb z = a("healing", new bmb(new aog(aoi.f, 1)));
	public static final bmb A = a("strong_healing", new bmb("healing", new aog(aoi.f, 1, 1)));
	public static final bmb B = a("harming", new bmb(new aog(aoi.g, 1)));
	public static final bmb C = a("strong_harming", new bmb("harming", new aog(aoi.g, 1, 1)));
	public static final bmb D = a("poison", new bmb(new aog(aoi.s, 900)));
	public static final bmb E = a("long_poison", new bmb("poison", new aog(aoi.s, 1800)));
	public static final bmb F = a("strong_poison", new bmb("poison", new aog(aoi.s, 432, 1)));
	public static final bmb G = a("regeneration", new bmb(new aog(aoi.j, 900)));
	public static final bmb H = a("long_regeneration", new bmb("regeneration", new aog(aoi.j, 1800)));
	public static final bmb I = a("strong_regeneration", new bmb("regeneration", new aog(aoi.j, 450, 1)));
	public static final bmb J = a("strength", new bmb(new aog(aoi.e, 3600)));
	public static final bmb K = a("long_strength", new bmb("strength", new aog(aoi.e, 9600)));
	public static final bmb L = a("strong_strength", new bmb("strength", new aog(aoi.e, 1800, 1)));
	public static final bmb M = a("weakness", new bmb(new aog(aoi.r, 1800)));
	public static final bmb N = a("long_weakness", new bmb("weakness", new aog(aoi.r, 4800)));
	public static final bmb O = a("luck", new bmb("luck", new aog(aoi.z, 6000)));
	public static final bmb P = a("slow_falling", new bmb(new aog(aoi.B, 1800)));
	public static final bmb Q = a("long_slow_falling", new bmb("slow_falling", new aog(aoi.B, 4800)));

	private static bmb a(String string, bmb bmb) {
		return gl.a(gl.an, string, bmb);
	}
}

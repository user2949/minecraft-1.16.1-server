import com.mojang.serialization.Codec;
import java.util.function.Function;

public class hh {
	public static final hi a = a("ambient_entity_effect", false);
	public static final hi b = a("angry_villager", false);
	public static final hi c = a("barrier", false);
	public static final hg<hc> d = a("block", hc.a, hc::a);
	public static final hi e = a("bubble", false);
	public static final hi f = a("cloud", false);
	public static final hi g = a("crit", false);
	public static final hi h = a("damage_indicator", true);
	public static final hi i = a("dragon_breath", false);
	public static final hi j = a("dripping_lava", false);
	public static final hi k = a("falling_lava", false);
	public static final hi l = a("landing_lava", false);
	public static final hi m = a("dripping_water", false);
	public static final hi n = a("falling_water", false);
	public static final hg<hd> o = a("dust", hd.c, hg -> hd.b);
	public static final hi p = a("effect", false);
	public static final hi q = a("elder_guardian", true);
	public static final hi r = a("enchanted_hit", false);
	public static final hi s = a("enchant", false);
	public static final hi t = a("end_rod", false);
	public static final hi u = a("entity_effect", false);
	public static final hi v = a("explosion_emitter", true);
	public static final hi w = a("explosion", true);
	public static final hg<hc> x = a("falling_dust", hc.a, hc::a);
	public static final hi y = a("firework", false);
	public static final hi z = a("fishing", false);
	public static final hi A = a("flame", false);
	public static final hi B = a("soul_fire_flame", false);
	public static final hi C = a("soul", false);
	public static final hi D = a("flash", false);
	public static final hi E = a("happy_villager", false);
	public static final hi F = a("composter", false);
	public static final hi G = a("heart", false);
	public static final hi H = a("instant_effect", false);
	public static final hg<he> I = a("item", he.a, he::a);
	public static final hi J = a("item_slime", false);
	public static final hi K = a("item_snowball", false);
	public static final hi L = a("large_smoke", false);
	public static final hi M = a("lava", false);
	public static final hi N = a("mycelium", false);
	public static final hi O = a("note", false);
	public static final hi P = a("poof", true);
	public static final hi Q = a("portal", false);
	public static final hi R = a("rain", false);
	public static final hi S = a("smoke", false);
	public static final hi T = a("sneeze", false);
	public static final hi U = a("spit", true);
	public static final hi V = a("squid_ink", true);
	public static final hi W = a("sweep_attack", true);
	public static final hi X = a("totem_of_undying", false);
	public static final hi Y = a("underwater", false);
	public static final hi Z = a("splash", false);
	public static final hi aa = a("witch", false);
	public static final hi ab = a("bubble_pop", false);
	public static final hi ac = a("current_down", false);
	public static final hi ad = a("bubble_column_up", false);
	public static final hi ae = a("nautilus", false);
	public static final hi af = a("dolphin", false);
	public static final hi ag = a("campfire_cosy_smoke", true);
	public static final hi ah = a("campfire_signal_smoke", true);
	public static final hi ai = a("dripping_honey", false);
	public static final hi aj = a("falling_honey", false);
	public static final hi ak = a("landing_honey", false);
	public static final hi al = a("falling_nectar", false);
	public static final hi am = a("ash", false);
	public static final hi an = a("crimson_spore", false);
	public static final hi ao = a("warped_spore", false);
	public static final hi ap = a("dripping_obsidian_tear", false);
	public static final hi aq = a("falling_obsidian_tear", false);
	public static final hi ar = a("landing_obsidian_tear", false);
	public static final hi as = a("reverse_portal", false);
	public static final hi at = a("white_ash", false);
	public static final Codec<hf> au = gl.az.dispatch("type", hf::b, hg::e);

	private static hi a(String string, boolean boolean2) {
		return gl.a(gl.az, string, new hi(boolean2));
	}

	private static <T extends hf> hg<T> a(String string, hf.a<T> a, Function<hg<T>, Codec<T>> function) {
		return gl.a(gl.az, string, new hg<T>(false, a) {
			@Override
			public Codec<T> e() {
				return (Codec<T>)function.apply(this);
			}
		});
	}
}

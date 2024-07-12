import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class aa {
	private static final Map<uh, ad<?>> Q = Maps.<uh, ad<?>>newHashMap();
	public static final bk a = a(new bk());
	public static final br b = a(new br(new uh("player_killed_entity")));
	public static final br c = a(new br(new uh("entity_killed_player")));
	public static final ba d = a(new ba());
	public static final bl e = a(new bl());
	public static final cf f = a(new cf());
	public static final cc g = a(new cc());
	public static final bd h = a(new bd());
	public static final ay i = a(new ay());
	public static final bg j = a(new bg());
	public static final an k = a(new an());
	public static final aq l = a(new aq());
	public static final cq m = a(new cq());
	public static final cl n = a(new cl());
	public static final am o = a(new am());
	public static final bv p = a(new bv(new uh("location")));
	public static final bv q = a(new bv(new uh("slept_in_bed")));
	public static final as r = a(new as());
	public static final cp s = a(new cp());
	public static final bm t = a(new bm());
	public static final bs u = a(new bs());
	public static final ao v = a(new ao());
	public static final co w = a(new co());
	public static final cm x = a(new cm());
	public static final cb y = a(new cb());
	public static final ar z = a(new ar());
	public static final ax A = a(new ax());
	public static final cr B = a(new cr());
	public static final ca C = a(new ca());
	public static final bi D = a(new bi());
	public static final ap E = a(new ap());
	public static final ch F = a(new ch());
	public static final bq G = a(new bq());
	public static final bv H = a(new bv(new uh("hero_of_the_village")));
	public static final bv I = a(new bv(new uh("voluntary_exile")));
	public static final cj J = a(new cj());
	public static final ak K = a(new ak());
	public static final cn L = a(new cn());
	public static final bp M = a(new bp());
	public static final bw N = a(new bw());
	public static final bn O = a(new bn());
	public static final cd P = a(new cd());

	private static <T extends ad<?>> T a(T ad) {
		if (Q.containsKey(ad.a())) {
			throw new IllegalArgumentException("Duplicate criterion id " + ad.a());
		} else {
			Q.put(ad.a(), ad);
			return ad;
		}
	}

	@Nullable
	public static <T extends ae> ad<T> a(uh uh) {
		return (ad<T>)Q.get(uh);
	}

	public static Iterable<? extends ad<?>> a() {
		return Q.values();
	}
}

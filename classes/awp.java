import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class awp<U> {
	public static final awp<Void> a = a("dummy");
	public static final awp<gc> b = a("home", gc.a);
	public static final awp<gc> c = a("job_site", gc.a);
	public static final awp<gc> d = a("potential_job_site", gc.a);
	public static final awp<gc> e = a("meeting_point", gc.a);
	public static final awp<List<gc>> f = a("secondary_job_site");
	public static final awp<List<aoy>> g = a("mobs");
	public static final awp<List<aoy>> h = a("visible_mobs");
	public static final awp<List<aoy>> i = a("visible_villager_babies");
	public static final awp<List<bec>> j = a("nearest_players");
	public static final awp<bec> k = a("nearest_visible_player");
	public static final awp<bec> l = a("nearest_visible_targetable_player");
	public static final awp<awr> m = a("walk_target");
	public static final awp<arn> n = a("look_target");
	public static final awp<aoy> o = a("attack_target");
	public static final awp<Boolean> p = a("attack_cooling_down");
	public static final awp<aoy> q = a("interaction_target");
	public static final awp<aok> r = a("breed_target");
	public static final awp<aom> s = a("ride_target");
	public static final awp<czf> t = a("path");
	public static final awp<List<gc>> u = a("interactable_doors");
	public static final awp<Set<gc>> v = a("opened_doors");
	public static final awp<fu> w = a("nearest_bed");
	public static final awp<anw> x = a("hurt_by");
	public static final awp<aoy> y = a("hurt_by_entity");
	public static final awp<aoy> z = a("avoid_target");
	public static final awp<aoy> A = a("nearest_hostile");
	public static final awp<gc> B = a("hiding_place");
	public static final awp<Long> C = a("heard_bell_time");
	public static final awp<Long> D = a("cant_reach_walk_target_since");
	public static final awp<Long> E = a("golem_last_seen_time");
	public static final awp<Long> F = a("last_slept", Codec.LONG);
	public static final awp<Long> G = a("last_woken", Codec.LONG);
	public static final awp<Long> H = a("last_worked_at_poi", Codec.LONG);
	public static final awp<aok> I = a("nearest_visible_adult");
	public static final awp<bbg> J = a("nearest_visible_wanted_item");
	public static final awp<aoz> K = a("nearest_visible_nemesis");
	public static final awp<UUID> L = a("angry_at", gp.a);
	public static final awp<Boolean> M = a("universal_anger", Codec.BOOL);
	public static final awp<Boolean> N = a("admiring_item", Codec.BOOL);
	public static final awp<Boolean> O = a("admiring_disabled", Codec.BOOL);
	public static final awp<Boolean> P = a("hunted_recently", Codec.BOOL);
	public static final awp<fu> Q = a("celebrate_location");
	public static final awp<Boolean> R = a("dancing");
	public static final awp<bcx> S = a("nearest_visible_huntable_hoglin");
	public static final awp<bcx> T = a("nearest_visible_baby_hoglin");
	public static final awp<bdc> U = a("nearest_visible_baby_piglin");
	public static final awp<bec> V = a("nearest_targetable_player_not_wearing_gold");
	public static final awp<List<bdc>> W = a("nearest_adult_piglins");
	public static final awp<List<bdc>> X = a("nearest_visible_adult_piglins");
	public static final awp<List<bcx>> Y = a("nearest_visible_adult_hoglins");
	public static final awp<bdc> Z = a("nearest_visible_adult_piglin");
	public static final awp<aoy> aa = a("nearest_visible_zombified");
	public static final awp<Integer> ab = a("visible_adult_piglin_count");
	public static final awp<Integer> ac = a("visible_adult_hoglin_count");
	public static final awp<bec> ad = a("nearest_player_holding_wanted_item");
	public static final awp<Boolean> ae = a("ate_recently");
	public static final awp<fu> af = a("nearest_repellent");
	public static final awp<Boolean> ag = a("pacified");
	private final Optional<Codec<awo<U>>> ah;

	private awp(Optional<Codec<U>> optional) {
		this.ah = optional.map(awo::a);
	}

	public String toString() {
		return gl.aU.b(this).toString();
	}

	public Optional<Codec<awo<U>>> a() {
		return this.ah;
	}

	private static <U> awp<U> a(String string, Codec<U> codec) {
		return gl.a(gl.aU, new uh(string), new awp<>(Optional.of(codec)));
	}

	private static <U> awp<U> a(String string) {
		return gl.a(gl.aU, new uh(string), new awp<>(Optional.empty()));
	}
}

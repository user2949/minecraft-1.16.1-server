import java.util.function.Supplier;

public class axo<U extends axn<?>> {
	public static final axo<axb> a = a("dummy", axb::new);
	public static final axo<axh> b = a("nearest_items", axh::new);
	public static final axo<axi> c = a("nearest_living_entities", axi::new);
	public static final axo<axk> d = a("nearest_players", axk::new);
	public static final axo<axf> e = a("interactable_doors", axf::new);
	public static final axo<axg> f = a("nearest_bed", axg::new);
	public static final axo<axe> g = a("hurt_by", axe::new);
	public static final axo<axq> h = a("villager_hostiles", axq::new);
	public static final axo<axp> i = a("villager_babies", axp::new);
	public static final axo<axl> j = a("secondary_pois", axl::new);
	public static final axo<axc> k = a("golem_last_seen", axc::new);
	public static final axo<axj> l = a("piglin_specific_sensor", axj::new);
	public static final axo<axd> m = a("hoglin_specific_sensor", axd::new);
	public static final axo<axa> n = a("nearest_adult", axa::new);
	private final Supplier<U> o;

	private axo(Supplier<U> supplier) {
		this.o = supplier;
	}

	public U a() {
		return (U)this.o.get();
	}

	private static <U extends axn<?>> axo<U> a(String string, Supplier<U> supplier) {
		return gl.a(gl.aV, new uh(string), new axo<>(supplier));
	}
}

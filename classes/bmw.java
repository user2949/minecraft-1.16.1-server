import com.google.gson.JsonObject;

public interface bmw<T extends bmu<?>> {
	bmw<bmz> a = a("crafting_shaped", new bmz.a());
	bmw<bna> b = a("crafting_shapeless", new bna.a());
	bne<bmh> c = a("crafting_special_armordye", new bne<>(bmh::new));
	bne<bmk> d = a("crafting_special_bookcloning", new bne<>(bmk::new));
	bne<bms> e = a("crafting_special_mapcloning", new bne<>(bms::new));
	bne<bmt> f = a("crafting_special_mapextending", new bne<>(bmt::new));
	bne<bmo> g = a("crafting_special_firework_rocket", new bne<>(bmo::new));
	bne<bmq> h = a("crafting_special_firework_star", new bne<>(bmq::new));
	bne<bmp> i = a("crafting_special_firework_star_fade", new bne<>(bmp::new));
	bne<bnk> j = a("crafting_special_tippedarrow", new bne<>(bnk::new));
	bne<bmi> k = a("crafting_special_bannerduplicate", new bne<>(bmi::new));
	bne<bnb> l = a("crafting_special_shielddecoration", new bne<>(bnb::new));
	bne<bnc> m = a("crafting_special_shulkerboxcoloring", new bne<>(bnc::new));
	bne<bnj> n = a("crafting_special_suspiciousstew", new bne<>(bnj::new));
	bne<bmy> o = a("crafting_special_repairitem", new bne<>(bmy::new));
	bnd<bng> p = a("smelting", new bnd<>(bng::new, 200));
	bnd<bmj> q = a("blasting", new bnd<>(bmj::new, 100));
	bnd<bnh> r = a("smoking", new bnd<>(bnh::new, 100));
	bnd<bml> s = a("campfire_cooking", new bnd<>(bml::new, 100));
	bmw<bni> t = a("stonecutting", new bnf.a<>(bni::new));
	bmw<bnl> u = a("smithing", new bnl.a());

	T a(uh uh, JsonObject jsonObject);

	T a(uh uh, mg mg);

	void a(mg mg, T bmu);

	static <S extends bmw<T>, T extends bmu<?>> S a(String string, S bmw) {
		return gl.a(gl.aO, string, bmw);
	}
}

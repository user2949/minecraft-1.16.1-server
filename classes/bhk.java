public class bhk<T extends bgi> {
	public static final bhk<bgp> a = a("generic_9x1", bgp::a);
	public static final bhk<bgp> b = a("generic_9x2", bgp::b);
	public static final bhk<bgp> c = a("generic_9x3", bgp::c);
	public static final bhk<bgp> d = a("generic_9x4", bgp::d);
	public static final bhk<bgp> e = a("generic_9x5", bgp::e);
	public static final bhk<bgp> f = a("generic_9x6", bgp::f);
	public static final bhk<bgx> g = a("generic_3x3", bgx::new);
	public static final bhk<bgk> h = a("anvil", bgk::new);
	public static final bhk<bgl> i = a("beacon", bgl::new);
	public static final bhk<bgm> j = a("blast_furnace", bgm::new);
	public static final bhk<bgn> k = a("brewing_stand", bgn::new);
	public static final bhk<bgv> l = a("crafting", bgv::new);
	public static final bhk<bgy> m = a("enchantment", bgy::new);
	public static final bhk<bha> n = a("furnace", bha::new);
	public static final bhk<bhc> o = a("grindstone", bhc::new);
	public static final bhk<bhd> p = a("hopper", bhd::new);
	public static final bhk<bhh> q = a("lectern", (integer, beb) -> new bhh(integer));
	public static final bhk<bhi> r = a("loom", bhi::new);
	public static final bhk<bhm> s = a("merchant", bhm::new);
	public static final bhk<bht> t = a("shulker_box", bht::new);
	public static final bhk<bhx> u = a("smithing", bhx::new);
	public static final bhk<bhy> v = a("smoker", bhy::new);
	public static final bhk<bgo> w = a("cartography_table", bgo::new);
	public static final bhk<bia> x = a("stonecutter", bia::new);
	private final bhk.a<T> y;

	private static <T extends bgi> bhk<T> a(String string, bhk.a<T> a) {
		return gl.a(gl.aM, string, new bhk<>(a));
	}

	private bhk(bhk.a<T> a) {
		this.y = a;
	}

	interface a<T extends bgi> {
	}
}

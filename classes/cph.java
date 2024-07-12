import com.mojang.serialization.Codec;

public class cph<P extends cpg> {
	public static final cph<cpc> a = a("blob_foliage_placer", cpc.a);
	public static final cph<cpl> b = a("spruce_foliage_placer", cpl.a);
	public static final cph<cpk> c = a("pine_foliage_placer", cpk.a);
	public static final cph<cpb> d = a("acacia_foliage_placer", cpb.a);
	public static final cph<cpd> e = a("bush_foliage_placer", cpd.c);
	public static final cph<cpf> f = a("fancy_foliage_placer", cpf.c);
	public static final cph<cpi> g = a("jungle_foliage_placer", cpi.a);
	public static final cph<cpj> h = a("mega_pine_foliage_placer", cpj.a);
	public static final cph<cpe> i = a("dark_oak_foliage_placer", cpe.a);
	private final Codec<P> j;

	private static <P extends cpg> cph<P> a(String string, Codec<P> codec) {
		return gl.a(gl.av, string, new cph<>(codec));
	}

	private cph(Codec<P> codec) {
		this.j = codec;
	}

	public Codec<P> a() {
		return this.j;
	}
}

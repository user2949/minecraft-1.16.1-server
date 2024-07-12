import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class csc<DC extends cnn> {
	public static final csc<cnz> a = a("nope", new cso(cnz.a));
	public static final csc<csf> b = a("count_heightmap", new crt(csf.a));
	public static final csc<csf> c = a("count_top_solid", new cru(csf.a));
	public static final csc<csf> d = a("count_heightmap_32", new crs(csf.a));
	public static final csc<csf> e = a("count_heightmap_double", new crq(csf.a));
	public static final csc<csf> f = a("count_height_64", new crr(csf.a));
	public static final csc<cny> g = a("noise_heightmap_32", new csm(cny.a));
	public static final csc<cny> h = a("noise_heightmap_double", new csn(cny.a));
	public static final csc<crf> i = a("chance_heightmap", new crg(crf.a));
	public static final csc<crf> j = a("chance_heightmap_double", new crh(crf.a));
	public static final csc<crf> k = a("chance_passthrough", new cri(crf.a));
	public static final csc<crf> l = a("chance_top_solid_heightmap", new crj(crf.a));
	public static final csc<csg> m = a("count_extra_heightmap", new crw(csg.a));
	public static final csc<cnl> n = a("count_range", new csv(cnl.a));
	public static final csc<cnl> o = a("count_biased_range", new crm(cnl.a));
	public static final csc<cnl> p = a("count_very_biased_range", new crv(cnl.a));
	public static final csc<cnl> q = a("random_count_range", new csz(cnl.a));
	public static final csc<cni> r = a("chance_range", new csu(cni.a));
	public static final csc<cse> s = a("count_chance_heightmap", new crn(cse.a));
	public static final csc<cse> t = a("count_chance_heightmap_double", new cro(cse.a));
	public static final csc<cry> u = a("count_depth_average", new crp(cry.a));
	public static final csc<cnz> v = a("top_solid_heightmap", new csr(cnz.a));
	public static final csc<csp> w = a("top_solid_heightmap_range", new cst(csp.a));
	public static final csc<csl> x = a("top_solid_heightmap_noise_biased", new css(csl.a));
	public static final csc<cre> y = a("carving_mask", new crd(cre.a));
	public static final csc<csf> z = a("forest_rock", new csd(csf.a));
	public static final csc<csf> A = a("fire", new csw(csf.a));
	public static final csc<csf> B = a("magma", new csy(csf.a));
	public static final csc<cnz> C = a("emerald_ore", new crz(cnz.a));
	public static final csc<crf> D = a("lava_lake", new csi(crf.a));
	public static final csc<crf> E = a("water_lake", new csj(crf.a));
	public static final csc<crf> F = a("dungeons", new csk(crf.a));
	public static final csc<cnz> G = a("dark_oak_tree", new crx(cnz.a));
	public static final csc<crf> H = a("iceberg", new csh(crf.a));
	public static final csc<csf> I = a("light_gem_chance", new csx(csf.a));
	public static final csc<cnz> J = a("end_island", new csb(cnz.a));
	public static final csc<cnz> K = a("chorus_plant", new crk(cnz.a));
	public static final csc<cnz> L = a("end_gateway", new csa(cnz.a));
	private final Codec<crl<DC>> M;

	private static <T extends cnn, G extends csc<T>> G a(String string, G csc) {
		return gl.a(gl.ar, string, csc);
	}

	public csc(Codec<DC> codec) {
		this.M = codec.fieldOf("config").<crl<DC>>xmap(cnn -> new crl<>(this, (DC)cnn), crl -> crl.c).codec();
	}

	public crl<DC> a(DC cnn) {
		return new crl<>(this, cnn);
	}

	public Codec<crl<DC>> a() {
		return this.M;
	}

	protected <FC extends cnr, F extends ckt<FC>> boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, DC cnn, ckb<FC, F> ckb) {
		MutableBoolean mutableBoolean9 = new MutableBoolean();
		this.a(bqu, cha, random, cnn, fu).forEach(fux -> {
			if (ckb.a(bqu, bqq, cha, random, fux)) {
				mutableBoolean9.setTrue();
			}
		});
		return mutableBoolean9.isTrue();
	}

	public abstract Stream<fu> a(bqc bqc, cha cha, Random random, DC cnn, fu fu);

	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
	}
}

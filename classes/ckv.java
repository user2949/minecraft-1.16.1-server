import com.mojang.serialization.Codec;
import java.util.Random;

public class ckv extends ckt<coa> {
	private static final uh a = new uh("fossil/spine_1");
	private static final uh ac = new uh("fossil/spine_2");
	private static final uh ad = new uh("fossil/spine_3");
	private static final uh ae = new uh("fossil/spine_4");
	private static final uh af = new uh("fossil/spine_1_coal");
	private static final uh ag = new uh("fossil/spine_2_coal");
	private static final uh ah = new uh("fossil/spine_3_coal");
	private static final uh ai = new uh("fossil/spine_4_coal");
	private static final uh aj = new uh("fossil/skull_1");
	private static final uh ak = new uh("fossil/skull_2");
	private static final uh al = new uh("fossil/skull_3");
	private static final uh am = new uh("fossil/skull_4");
	private static final uh an = new uh("fossil/skull_1_coal");
	private static final uh ao = new uh("fossil/skull_2_coal");
	private static final uh ap = new uh("fossil/skull_3_coal");
	private static final uh aq = new uh("fossil/skull_4_coal");
	private static final uh[] ar = new uh[]{a, ac, ad, ae, aj, ak, al, am};
	private static final uh[] as = new uh[]{af, ag, ah, ai, an, ao, ap, aq};

	public ckv(Codec<coa> codec) {
		super(codec);
	}

	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, fu fu, coa coa) {
		cap cap8 = cap.a(random);
		int integer9 = random.nextInt(ar.length);
		cva cva10 = ((zd)bqu.n()).l().aU();
		cve cve11 = cva10.a(ar[integer9]);
		cve cve12 = cva10.a(as[integer9]);
		bph bph13 = new bph(fu);
		ctd ctd14 = new ctd(bph13.d(), 0, bph13.e(), bph13.f(), 256, bph13.g());
		cvb cvb15 = new cvb().a(cap8).a(ctd14).a(random).a(cui.d);
		fu fu16 = cve11.a(cap8);
		int integer17 = random.nextInt(16 - fu16.u());
		int integer18 = random.nextInt(16 - fu16.w());
		int integer19 = 256;

		for (int integer20 = 0; integer20 < fu16.u(); integer20++) {
			for (int integer21 = 0; integer21 < fu16.w(); integer21++) {
				integer19 = Math.min(integer19, bqu.a(cio.a.OCEAN_FLOOR_WG, fu.u() + integer20 + integer17, fu.w() + integer21 + integer18));
			}
		}

		int integer20 = Math.max(integer19 - 15 - random.nextInt(10), 10);
		fu fu21 = cve11.a(fu.b(integer17, integer20, integer18), bzj.NONE, cap8);
		cuk cuk22 = new cuk(0.9F);
		cvb15.b().a(cuk22);
		cve11.a(bqu, fu21, fu21, cvb15, random, 4);
		cvb15.b(cuk22);
		cuk cuk23 = new cuk(0.1F);
		cvb15.b().a(cuk23);
		cve12.a(bqu, fu21, fu21, cvb15, random, 4);
		return true;
	}
}

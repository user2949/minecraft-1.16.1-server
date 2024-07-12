import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class cjh<C extends cja> {
	public static final cjh<cod> a = a("cave", new cjb(cod.b, 256));
	public static final cjh<cod> b = a("nether_cave", new cjd(cod.b));
	public static final cjh<cod> c = a("canyon", new ciz(cod.b));
	public static final cjh<cod> d = a("underwater_canyon", new cjf(cod.b));
	public static final cjh<cod> e = a("underwater_cave", new cjg(cod.b));
	protected static final cfj f = bvs.a.n();
	protected static final cfj g = bvs.lb.n();
	protected static final cxa h = cxb.c.h();
	protected static final cxa i = cxb.e.h();
	protected Set<bvr> j = ImmutableSet.of(
		bvs.b,
		bvs.c,
		bvs.e,
		bvs.g,
		bvs.j,
		bvs.k,
		bvs.l,
		bvs.i,
		bvs.gR,
		bvs.fF,
		bvs.fG,
		bvs.fH,
		bvs.fI,
		bvs.fJ,
		bvs.fK,
		bvs.fL,
		bvs.fM,
		bvs.fN,
		bvs.fO,
		bvs.fP,
		bvs.fQ,
		bvs.fR,
		bvs.fS,
		bvs.fT,
		bvs.fU,
		bvs.at,
		bvs.hG,
		bvs.dT,
		bvs.cC,
		bvs.gT
	);
	protected Set<cwz> k = ImmutableSet.of(cxb.c);
	private final Codec<cjc<C>> m;
	protected final int l;

	private static <C extends cja, F extends cjh<C>> F a(String string, F cjh) {
		return gl.a(gl.ao, string, cjh);
	}

	public cjh(Codec<C> codec, int integer) {
		this.l = integer;
		this.m = codec.fieldOf("config").<cjc<C>>xmap(cja -> new cjc<>(this, (C)cja), cjc -> cjc.c).codec();
	}

	public Codec<cjc<C>> c() {
		return this.m;
	}

	public int d() {
		return 4;
	}

	protected boolean a(
		cgy cgy,
		Function<fu, bre> function,
		long long3,
		int integer4,
		int integer5,
		int integer6,
		double double7,
		double double8,
		double double9,
		double double10,
		double double11,
		BitSet bitSet
	) {
		Random random20 = new Random(long3 + (long)integer5 + (long)integer6);
		double double21 = (double)(integer5 * 16 + 8);
		double double23 = (double)(integer6 * 16 + 8);
		if (!(double7 < double21 - 16.0 - double10 * 2.0)
			&& !(double9 < double23 - 16.0 - double10 * 2.0)
			&& !(double7 > double21 + 16.0 + double10 * 2.0)
			&& !(double9 > double23 + 16.0 + double10 * 2.0)) {
			int integer25 = Math.max(aec.c(double7 - double10) - integer5 * 16 - 1, 0);
			int integer26 = Math.min(aec.c(double7 + double10) - integer5 * 16 + 1, 16);
			int integer27 = Math.max(aec.c(double8 - double11) - 1, 1);
			int integer28 = Math.min(aec.c(double8 + double11) + 1, this.l - 8);
			int integer29 = Math.max(aec.c(double9 - double10) - integer6 * 16 - 1, 0);
			int integer30 = Math.min(aec.c(double9 + double10) - integer6 * 16 + 1, 16);
			if (this.a(cgy, integer5, integer6, integer25, integer26, integer27, integer28, integer29, integer30)) {
				return false;
			} else {
				boolean boolean31 = false;
				fu.a a32 = new fu.a();
				fu.a a33 = new fu.a();
				fu.a a34 = new fu.a();

				for (int integer35 = integer25; integer35 < integer26; integer35++) {
					int integer36 = integer35 + integer5 * 16;
					double double37 = ((double)integer36 + 0.5 - double7) / double10;

					for (int integer39 = integer29; integer39 < integer30; integer39++) {
						int integer40 = integer39 + integer6 * 16;
						double double41 = ((double)integer40 + 0.5 - double9) / double10;
						if (!(double37 * double37 + double41 * double41 >= 1.0)) {
							MutableBoolean mutableBoolean43 = new MutableBoolean(false);

							for (int integer44 = integer28; integer44 > integer27; integer44--) {
								double double45 = ((double)integer44 - 0.5 - double8) / double11;
								if (!this.a(double37, double45, double41, integer44)) {
									boolean31 |= this.a(
										cgy, function, bitSet, random20, a32, a33, a34, integer4, integer5, integer6, integer36, integer40, integer35, integer44, integer39, mutableBoolean43
									);
								}
							}
						}
					}
				}

				return boolean31;
			}
		} else {
			return false;
		}
	}

	protected boolean a(
		cgy cgy,
		Function<fu, bre> function,
		BitSet bitSet,
		Random random,
		fu.a a5,
		fu.a a6,
		fu.a a7,
		int integer8,
		int integer9,
		int integer10,
		int integer11,
		int integer12,
		int integer13,
		int integer14,
		int integer15,
		MutableBoolean mutableBoolean
	) {
		int integer18 = integer13 | integer15 << 4 | integer14 << 8;
		if (bitSet.get(integer18)) {
			return false;
		} else {
			bitSet.set(integer18);
			a5.d(integer11, integer14, integer12);
			cfj cfj19 = cgy.d_(a5);
			cfj cfj20 = cgy.d_(a6.a(a5, fz.UP));
			if (cfj19.a(bvs.i) || cfj19.a(bvs.dT)) {
				mutableBoolean.setTrue();
			}

			if (!this.a(cfj19, cfj20)) {
				return false;
			} else {
				if (integer14 < 11) {
					cgy.a(a5, i.g(), false);
				} else {
					cgy.a(a5, g, false);
					if (mutableBoolean.isTrue()) {
						a7.a(a5, fz.DOWN);
						if (cgy.d_(a7).a(bvs.j)) {
							cgy.a(a7, ((bre)function.apply(a5)).A().a(), false);
						}
					}
				}

				return true;
			}
		}
	}

	public abstract boolean a(
		cgy cgy, Function<fu, bre> function, Random random, int integer4, int integer5, int integer6, int integer7, int integer8, BitSet bitSet, C cja
	);

	public abstract boolean a(Random random, int integer2, int integer3, C cja);

	protected boolean a(cfj cfj) {
		return this.j.contains(cfj.b());
	}

	protected boolean a(cfj cfj1, cfj cfj2) {
		return this.a(cfj1) || (cfj1.a(bvs.C) || cfj1.a(bvs.E)) && !cfj2.m().a(acz.a);
	}

	protected boolean a(cgy cgy, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, int integer9) {
		fu.a a11 = new fu.a();

		for (int integer12 = integer4; integer12 < integer5; integer12++) {
			for (int integer13 = integer8; integer13 < integer9; integer13++) {
				for (int integer14 = integer6 - 1; integer14 <= integer7 + 1; integer14++) {
					if (this.k.contains(cgy.b(a11.d(integer12 + integer2 * 16, integer14, integer13 + integer3 * 16)).a())) {
						return true;
					}

					if (integer14 != integer7 + 1 && !this.a(integer4, integer5, integer8, integer9, integer12, integer13)) {
						integer14 = integer7;
					}
				}
			}
		}

		return false;
	}

	private boolean a(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
		return integer5 == integer1 || integer5 == integer2 - 1 || integer6 == integer3 || integer6 == integer4 - 1;
	}

	protected boolean a(int integer1, int integer2, double double3, double double4, int integer5, int integer6, float float7) {
		double double11 = (double)(integer1 * 16 + 8);
		double double13 = (double)(integer2 * 16 + 8);
		double double15 = double3 - double11;
		double double17 = double4 - double13;
		double double19 = (double)(integer6 - integer5);
		double double21 = (double)(float7 + 2.0F + 16.0F);
		return double15 * double15 + double17 * double17 - double19 * double19 <= double21 * double21;
	}

	protected abstract boolean a(double double1, double double2, double double3, int integer);
}

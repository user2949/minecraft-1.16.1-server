import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class cqr extends cqw {
	public static final Codec<cqr> a = RecordCodecBuilder.create(instance -> a(instance).apply(instance, cqr::new));

	public cqr(int integer1, int integer2, int integer3) {
		super(integer1, integer2, integer3);
	}

	@Override
	protected cqx<?> a() {
		return cqx.f;
	}

	@Override
	public List<cpg.b> a(bqf bqf, Random random, int integer, fu fu, Set<fu> set, ctd ctd, cou cou) {
		int integer9 = 5;
		int integer10 = integer + 2;
		int integer11 = aec.c((double)integer10 * 0.618);
		if (!cou.e) {
			a(bqf, fu.c());
		}

		double double12 = 1.0;
		int integer14 = Math.min(1, aec.c(1.382 + Math.pow(1.0 * (double)integer10 / 13.0, 2.0)));
		int integer15 = fu.v() + integer11;
		int integer16 = integer10 - 5;
		List<cqr.a> list17 = Lists.<cqr.a>newArrayList();
		list17.add(new cqr.a(fu.b(integer16), integer15));

		for (; integer16 >= 0; integer16--) {
			float float18 = this.b(integer10, integer16);
			if (!(float18 < 0.0F)) {
				for (int integer19 = 0; integer19 < integer14; integer19++) {
					double double20 = 1.0;
					double double22 = 1.0 * (double)float18 * ((double)random.nextFloat() + 0.328);
					double double24 = (double)(random.nextFloat() * 2.0F) * Math.PI;
					double double26 = double22 * Math.sin(double24) + 0.5;
					double double28 = double22 * Math.cos(double24) + 0.5;
					fu fu30 = fu.a(double26, (double)(integer16 - 1), double28);
					fu fu31 = fu30.b(5);
					if (this.a(bqf, random, fu30, fu31, false, set, ctd, cou)) {
						int integer32 = fu.u() - fu30.u();
						int integer33 = fu.w() - fu30.w();
						double double34 = (double)fu30.v() - Math.sqrt((double)(integer32 * integer32 + integer33 * integer33)) * 0.381;
						int integer36 = double34 > (double)integer15 ? integer15 : (int)double34;
						fu fu37 = new fu(fu.u(), integer36, fu.w());
						if (this.a(bqf, random, fu37, fu30, false, set, ctd, cou)) {
							list17.add(new cqr.a(fu30, fu37.v()));
						}
					}
				}
			}
		}

		this.a(bqf, random, fu, fu.b(integer11), true, set, ctd, cou);
		this.a(bqf, random, integer10, fu, list17, set, ctd, cou);
		List<cpg.b> list18 = Lists.<cpg.b>newArrayList();

		for (cqr.a a20 : list17) {
			if (this.a(integer10, a20.a() - fu.v())) {
				list18.add(a20.a);
			}
		}

		return list18;
	}

	private boolean a(bqf bqf, Random random, fu fu3, fu fu4, boolean boolean5, Set<fu> set, ctd ctd, cou cou) {
		if (!boolean5 && Objects.equals(fu3, fu4)) {
			return true;
		} else {
			fu fu10 = fu4.b(-fu3.u(), -fu3.v(), -fu3.w());
			int integer11 = this.a(fu10);
			float float12 = (float)fu10.u() / (float)integer11;
			float float13 = (float)fu10.v() / (float)integer11;
			float float14 = (float)fu10.w() / (float)integer11;

			for (int integer15 = 0; integer15 <= integer11; integer15++) {
				fu fu16 = fu3.a((double)(0.5F + (float)integer15 * float12), (double)(0.5F + (float)integer15 * float13), (double)(0.5F + (float)integer15 * float14));
				if (boolean5) {
					a(bqf, fu16, cou.b.a(random, fu16).a(cao.a, this.a(fu3, fu16)), ctd);
					set.add(fu16.h());
				} else if (!cmp.c(bqf, fu16)) {
					return false;
				}
			}

			return true;
		}
	}

	private int a(fu fu) {
		int integer3 = aec.a(fu.u());
		int integer4 = aec.a(fu.v());
		int integer5 = aec.a(fu.w());
		return Math.max(integer3, Math.max(integer4, integer5));
	}

	private fz.a a(fu fu1, fu fu2) {
		fz.a a4 = fz.a.Y;
		int integer5 = Math.abs(fu2.u() - fu1.u());
		int integer6 = Math.abs(fu2.w() - fu1.w());
		int integer7 = Math.max(integer5, integer6);
		if (integer7 > 0) {
			if (integer5 == integer7) {
				a4 = fz.a.X;
			} else {
				a4 = fz.a.Z;
			}
		}

		return a4;
	}

	private boolean a(int integer1, int integer2) {
		return (double)integer2 >= (double)integer1 * 0.2;
	}

	private void a(bqf bqf, Random random, int integer, fu fu, List<cqr.a> list, Set<fu> set, ctd ctd, cou cou) {
		for (cqr.a a11 : list) {
			int integer12 = a11.a();
			fu fu13 = new fu(fu.u(), integer12, fu.w());
			if (!fu13.equals(a11.a.a()) && this.a(integer, integer12 - fu.v())) {
				this.a(bqf, random, fu13, a11.a.a(), true, set, ctd, cou);
			}
		}
	}

	private float b(int integer1, int integer2) {
		if ((float)integer2 < (float)integer1 * 0.3F) {
			return -1.0F;
		} else {
			float float4 = (float)integer1 / 2.0F;
			float float5 = float4 - (float)integer2;
			float float6 = aec.c(float4 * float4 - float5 * float5);
			if (float5 == 0.0F) {
				float6 = float4;
			} else if (Math.abs(float5) >= float4) {
				return 0.0F;
			}

			return float6 * 0.5F;
		}
	}

	static class a {
		private final cpg.b a;
		private final int b;

		public a(fu fu, int integer) {
			this.a = new cpg.b(fu, 0, false);
			this.b = integer;
		}

		public int a() {
			return this.b;
		}
	}
}

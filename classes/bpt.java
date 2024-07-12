import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public class bpt {
	private final boolean a;
	private final bpt.a b;
	private final Random c = new Random();
	private final bqb d;
	private final double e;
	private final double f;
	private final double g;
	@Nullable
	private final aom h;
	private final float i;
	private final anw j;
	private final bpu k;
	private final List<fu> l = Lists.<fu>newArrayList();
	private final Map<bec, dem> m = Maps.<bec, dem>newHashMap();

	public bpt(
		bqb bqb, @Nullable aom aom, @Nullable anw anw, @Nullable bpu bpu, double double5, double double6, double double7, float float8, boolean boolean9, bpt.a a
	) {
		this.d = bqb;
		this.h = aom;
		this.i = float8;
		this.e = double5;
		this.f = double6;
		this.g = double7;
		this.a = boolean9;
		this.b = a;
		this.j = anw == null ? anw.a(this) : anw;
		this.k = bpu == null ? this.a(aom) : bpu;
	}

	private bpu a(@Nullable aom aom) {
		return (bpu)(aom == null ? bpo.INSTANCE : new bpr(aom));
	}

	public static float a(dem dem, aom aom) {
		deg deg3 = aom.cb();
		double double4 = 1.0 / ((deg3.d - deg3.a) * 2.0 + 1.0);
		double double6 = 1.0 / ((deg3.e - deg3.b) * 2.0 + 1.0);
		double double8 = 1.0 / ((deg3.f - deg3.c) * 2.0 + 1.0);
		double double10 = (1.0 - Math.floor(1.0 / double4) * double4) / 2.0;
		double double12 = (1.0 - Math.floor(1.0 / double8) * double8) / 2.0;
		if (!(double4 < 0.0) && !(double6 < 0.0) && !(double8 < 0.0)) {
			int integer14 = 0;
			int integer15 = 0;

			for (float float16 = 0.0F; float16 <= 1.0F; float16 = (float)((double)float16 + double4)) {
				for (float float17 = 0.0F; float17 <= 1.0F; float17 = (float)((double)float17 + double6)) {
					for (float float18 = 0.0F; float18 <= 1.0F; float18 = (float)((double)float18 + double8)) {
						double double19 = aec.d((double)float16, deg3.a, deg3.d);
						double double21 = aec.d((double)float17, deg3.b, deg3.e);
						double double23 = aec.d((double)float18, deg3.c, deg3.f);
						dem dem25 = new dem(double19 + double10, double21, double23 + double12);
						if (aom.l.a(new bpj(dem25, dem, bpj.a.COLLIDER, bpj.b.NONE, aom)).c() == dej.a.MISS) {
							integer14++;
						}

						integer15++;
					}
				}
			}

			return (float)integer14 / (float)integer15;
		} else {
			return 0.0F;
		}
	}

	public void a() {
		Set<fu> set2 = Sets.<fu>newHashSet();
		int integer3 = 16;

		for (int integer4 = 0; integer4 < 16; integer4++) {
			for (int integer5 = 0; integer5 < 16; integer5++) {
				for (int integer6 = 0; integer6 < 16; integer6++) {
					if (integer4 == 0 || integer4 == 15 || integer5 == 0 || integer5 == 15 || integer6 == 0 || integer6 == 15) {
						double double7 = (double)((float)integer4 / 15.0F * 2.0F - 1.0F);
						double double9 = (double)((float)integer5 / 15.0F * 2.0F - 1.0F);
						double double11 = (double)((float)integer6 / 15.0F * 2.0F - 1.0F);
						double double13 = Math.sqrt(double7 * double7 + double9 * double9 + double11 * double11);
						double7 /= double13;
						double9 /= double13;
						double11 /= double13;
						float float15 = this.i * (0.7F + this.d.t.nextFloat() * 0.6F);
						double double16 = this.e;
						double double18 = this.f;
						double double20 = this.g;

						for (float float22 = 0.3F; float15 > 0.0F; float15 -= 0.22500001F) {
							fu fu23 = new fu(double16, double18, double20);
							cfj cfj24 = this.d.d_(fu23);
							cxa cxa25 = this.d.b(fu23);
							Optional<Float> optional26 = this.k.a(this, this.d, fu23, cfj24, cxa25);
							if (optional26.isPresent()) {
								float15 -= (optional26.get() + 0.3F) * 0.3F;
							}

							if (float15 > 0.0F && this.k.a(this, this.d, fu23, cfj24, float15)) {
								set2.add(fu23);
							}

							double16 += double7 * 0.3F;
							double18 += double9 * 0.3F;
							double20 += double11 * 0.3F;
						}
					}
				}
			}
		}

		this.l.addAll(set2);
		float float4 = this.i * 2.0F;
		int integer5 = aec.c(this.e - (double)float4 - 1.0);
		int integer6x = aec.c(this.e + (double)float4 + 1.0);
		int integer7 = aec.c(this.f - (double)float4 - 1.0);
		int integer8 = aec.c(this.f + (double)float4 + 1.0);
		int integer9 = aec.c(this.g - (double)float4 - 1.0);
		int integer10 = aec.c(this.g + (double)float4 + 1.0);
		List<aom> list11 = this.d.a(this.h, new deg((double)integer5, (double)integer7, (double)integer9, (double)integer6x, (double)integer8, (double)integer10));
		dem dem12 = new dem(this.e, this.f, this.g);

		for (int integer13 = 0; integer13 < list11.size(); integer13++) {
			aom aom14 = (aom)list11.get(integer13);
			if (!aom14.ch()) {
				double double15 = (double)(aec.a(aom14.d(dem12)) / float4);
				if (double15 <= 1.0) {
					double double17 = aom14.cC() - this.e;
					double double19 = (aom14 instanceof bbh ? aom14.cD() : aom14.cF()) - this.f;
					double double21 = aom14.cG() - this.g;
					double double23 = (double)aec.a(double17 * double17 + double19 * double19 + double21 * double21);
					if (double23 != 0.0) {
						double17 /= double23;
						double19 /= double23;
						double21 /= double23;
						double double25 = (double)a(dem12, aom14);
						double double27 = (1.0 - double15) * double25;
						aom14.a(this.b(), (float)((int)((double27 * double27 + double27) / 2.0 * 7.0 * (double)float4 + 1.0)));
						double double29 = double27;
						if (aom14 instanceof aoy) {
							double29 = boj.a((aoy)aom14, double27);
						}

						aom14.e(aom14.cB().b(double17 * double29, double19 * double29, double21 * double29));
						if (aom14 instanceof bec) {
							bec bec31 = (bec)aom14;
							if (!bec31.a_() && (!bec31.b_() || !bec31.bJ.b)) {
								this.m.put(bec31, new dem(double17 * double27, double19 * double27, double21 * double27));
							}
						}
					}
				}
			}
		}
	}

	public void a(boolean boolean1) {
		if (this.d.v) {
			this.d.a(this.e, this.f, this.g, acl.eL, acm.BLOCKS, 4.0F, (1.0F + (this.d.t.nextFloat() - this.d.t.nextFloat()) * 0.2F) * 0.7F, false);
		}

		boolean boolean3 = this.b != bpt.a.NONE;
		if (boolean1) {
			if (!(this.i < 2.0F) && boolean3) {
				this.d.a(hh.v, this.e, this.f, this.g, 1.0, 0.0, 0.0);
			} else {
				this.d.a(hh.w, this.e, this.f, this.g, 1.0, 0.0, 0.0);
			}
		}

		if (boolean3) {
			ObjectArrayList<Pair<bki, fu>> objectArrayList4 = new ObjectArrayList<>();
			Collections.shuffle(this.l, this.d.t);

			for (fu fu6 : this.l) {
				cfj cfj7 = this.d.d_(fu6);
				bvr bvr8 = cfj7.b();
				if (!cfj7.g()) {
					fu fu9 = fu6.h();
					this.d.X().a("explosion_blocks");
					if (bvr8.a(this) && this.d instanceof zd) {
						cdl cdl10 = bvr8.q() ? this.d.c(fu6) : null;
						dat.a a11 = new dat.a((zd)this.d).a(this.d.t).a(dda.f, fu6).a(dda.j, bki.b).b(dda.i, cdl10).b(dda.a, this.h);
						if (this.b == bpt.a.DESTROY) {
							a11.a(dda.k, this.i);
						}

						cfj7.a(a11).forEach(bki -> a(objectArrayList4, bki, fu9));
					}

					this.d.a(fu6, bvs.a.n(), 3);
					bvr8.a(this.d, fu6, this);
					this.d.X().c();
				}
			}

			for (Pair<bki, fu> pair6 : objectArrayList4) {
				bvr.a(this.d, pair6.getSecond(), pair6.getFirst());
			}
		}

		if (this.a) {
			for (fu fu5 : this.l) {
				if (this.c.nextInt(3) == 0 && this.d.d_(fu5).g() && this.d.d_(fu5.c()).i(this.d, fu5.c())) {
					this.d.a(fu5, bvh.a((bpg)this.d, fu5));
				}
			}
		}
	}

	private static void a(ObjectArrayList<Pair<bki, fu>> objectArrayList, bki bki, fu fu) {
		int integer4 = objectArrayList.size();

		for (int integer5 = 0; integer5 < integer4; integer5++) {
			Pair<bki, fu> pair6 = objectArrayList.get(integer5);
			bki bki7 = pair6.getFirst();
			if (bbg.a(bki7, bki)) {
				bki bki8 = bbg.a(bki7, bki, 16);
				objectArrayList.set(integer5, Pair.of(bki8, pair6.getSecond()));
				if (bki.a()) {
					return;
				}
			}
		}

		objectArrayList.add(Pair.of(bki, fu));
	}

	public anw b() {
		return this.j;
	}

	public Map<bec, dem> c() {
		return this.m;
	}

	@Nullable
	public aoy d() {
		if (this.h == null) {
			return null;
		} else if (this.h instanceof bbh) {
			return ((bbh)this.h).g();
		} else if (this.h instanceof aoy) {
			return (aoy)this.h;
		} else {
			if (this.h instanceof bes) {
				aom aom2 = ((bes)this.h).v();
				if (aom2 instanceof aoy) {
					return (aoy)aom2;
				}
			}

			return null;
		}
	}

	public void e() {
		this.l.clear();
	}

	public List<fu> f() {
		return this.l;
	}

	public static enum a {
		NONE,
		BREAK,
		DESTROY;
	}
}

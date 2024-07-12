import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ctt extends cub {
	private static final Logger d = LogManager.getLogger();
	private final uh e;
	private final cap f;
	private final bzj g;
	private final ctt.b h;
	private final ctt.a i;

	public ctt(fu fu1, ctt.b b, ctt.a a, uh uh, cve cve, cap cap, bzj bzj, fu fu8) {
		super(cmm.L, 0);
		this.c = fu1;
		this.e = uh;
		this.f = cap;
		this.g = bzj;
		this.h = b;
		this.i = a;
		this.a(cve, fu8);
	}

	public ctt(cva cva, le le) {
		super(cmm.L, le);
		this.e = new uh(le.l("Template"));
		this.f = cap.valueOf(le.l("Rotation"));
		this.g = bzj.valueOf(le.l("Mirror"));
		this.h = ctt.b.a(le.l("VerticalPlacement"));
		this.i = ctt.a.a.parse(new Dynamic<>(lp.a, le.c("Properties"))).getOrThrow(true, d::error);
		cve cve4 = cva.a(this.e);
		this.a(cve4, new fu(cve4.a().u() / 2, 0, cve4.a().w() / 2));
	}

	@Override
	protected void a(le le) {
		super.a(le);
		le.a("Template", this.e.toString());
		le.a("Rotation", this.f.name());
		le.a("Mirror", this.g.name());
		le.a("VerticalPlacement", this.h.a());
		ctt.a.a.encodeStart(lp.a, this.i).resultOrPartial(d::error).ifPresent(lu -> le.a("Properties", lu));
	}

	private void a(cve cve, fu fu) {
		cui cui4 = this.i.d ? cui.b : cui.d;
		List<cuu> list5 = Lists.<cuu>newArrayList();
		list5.add(a(bvs.bE, 0.3F, bvs.a));
		list5.add(this.c());
		if (!this.i.b) {
			list5.add(a(bvs.cL, 0.07F, bvs.iJ));
		}

		cvb cvb6 = new cvb().a(this.f).a(this.g).a(fu).a(cui4).a(new cux(list5)).a(new cuh(this.i.c)).a(new cuo());
		if (this.i.g) {
			cvb6.a(cug.b);
		}

		this.a(cve, this.c, cvb6);
	}

	private cuu c() {
		if (this.h == ctt.b.ON_OCEAN_FLOOR) {
			return a(bvs.B, bvs.iJ);
		} else {
			return this.i.b ? a(bvs.B, bvs.cL) : a(bvs.B, 0.2F, bvs.iJ);
		}
	}

	@Override
	public boolean a(bqu bqu, bqq bqq, cha cha, Random random, ctd ctd, bph bph, fu fu) {
		if (!ctd.b(this.c)) {
			return true;
		} else {
			ctd.c(this.a.b(this.b, this.c));
			boolean boolean9 = super.a(bqu, bqq, cha, random, ctd, bph, fu);
			this.b(random, bqu);
			this.a(random, bqu);
			if (this.i.f || this.i.e) {
				fu.a(this.g()).forEach(fux -> {
					if (this.i.f) {
						this.a(random, (bqc)bqu, fux);
					}

					if (this.i.e) {
						this.b(random, bqu, fux);
					}
				});
			}

			return boolean9;
		}
	}

	@Override
	protected void a(String string, fu fu, bqc bqc, Random random, ctd ctd) {
	}

	private void a(Random random, bqc bqc, fu fu) {
		cfj cfj5 = bqc.d_(fu);
		if (!cfj5.g() && !cfj5.a(bvs.dP)) {
			fz fz6 = fz.c.HORIZONTAL.a(random);
			fu fu7 = fu.a(fz6);
			cfj cfj8 = bqc.d_(fu7);
			if (cfj8.g()) {
				if (bvr.a(cfj5.k(bqc, fu), fz6)) {
					cga cga9 = cck.a(fz6.f());
					bqc.a(fu7, bvs.dP.n().a(cga9, Boolean.valueOf(true)), 3);
				}
			}
		}
	}

	private void b(Random random, bqc bqc, fu fu) {
		if (random.nextFloat() < 0.5F && bqc.d_(fu).a(bvs.cL) && bqc.d_(fu.b()).g()) {
			bqc.a(fu.b(), bvs.ak.n().a(bza.b, Boolean.valueOf(true)), 3);
		}
	}

	private void a(Random random, bqc bqc) {
		for (int integer4 = this.n.a + 1; integer4 < this.n.d; integer4++) {
			for (int integer5 = this.n.c + 1; integer5 < this.n.f; integer5++) {
				fu fu6 = new fu(integer4, this.n.b, integer5);
				if (bqc.d_(fu6).a(bvs.cL)) {
					this.c(random, bqc, fu6.c());
				}
			}
		}
	}

	private void c(Random random, bqc bqc, fu fu) {
		fu.a a5 = fu.i();
		this.d(random, bqc, a5);
		int integer6 = 8;

		while (integer6 > 0 && random.nextFloat() < 0.5F) {
			a5.c(fz.DOWN);
			integer6--;
			this.d(random, bqc, a5);
		}
	}

	private void b(Random random, bqc bqc) {
		boolean boolean4 = this.h == ctt.b.ON_LAND_SURFACE || this.h == ctt.b.ON_OCEAN_FLOOR;
		gr gr5 = this.n.g();
		int integer6 = gr5.u();
		int integer7 = gr5.w();
		float[] arr8 = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F};
		int integer9 = arr8.length;
		int integer10 = (this.n.d() + this.n.f()) / 2;
		int integer11 = random.nextInt(Math.max(1, 8 - integer10 / 2));
		int integer12 = 3;
		fu.a a13 = fu.b.i();

		for (int integer14 = integer6 - integer9; integer14 <= integer6 + integer9; integer14++) {
			for (int integer15 = integer7 - integer9; integer15 <= integer7 + integer9; integer15++) {
				int integer16 = Math.abs(integer14 - integer6) + Math.abs(integer15 - integer7);
				int integer17 = Math.max(0, integer16 + integer11);
				if (integer17 < integer9) {
					float float18 = arr8[integer17];
					if (random.nextDouble() < (double)float18) {
						int integer19 = a(bqc, integer14, integer15, this.h);
						int integer20 = boolean4 ? integer19 : Math.min(this.n.b, integer19);
						a13.d(integer14, integer20, integer15);
						if (Math.abs(integer20 - this.n.b) <= 3 && this.a(bqc, a13)) {
							this.d(random, bqc, a13);
							if (this.i.e) {
								this.b(random, bqc, a13);
							}

							this.c(random, bqc, a13.c());
						}
					}
				}
			}
		}
	}

	private boolean a(bqc bqc, fu fu) {
		cfj cfj4 = bqc.d_(fu);
		return !cfj4.a(bvs.a) && !cfj4.a(bvs.bK) && (this.h == ctt.b.IN_NETHER || !cfj4.a(bvs.B));
	}

	private void d(Random random, bqc bqc, fu fu) {
		if (!this.i.b && random.nextFloat() < 0.07F) {
			bqc.a(fu, bvs.iJ.n(), 3);
		} else {
			bqc.a(fu, bvs.cL.n(), 3);
		}
	}

	private static int a(bqc bqc, int integer2, int integer3, ctt.b b) {
		return bqc.a(a(b), integer2, integer3) - 1;
	}

	public static cio.a a(ctt.b b) {
		return b == ctt.b.ON_OCEAN_FLOOR ? cio.a.OCEAN_FLOOR_WG : cio.a.WORLD_SURFACE_WG;
	}

	private static cuu a(bvr bvr1, float float2, bvr bvr3) {
		return new cuu(new cuv(bvr1, float2), cue.b, bvr3.n());
	}

	private static cuu a(bvr bvr1, bvr bvr2) {
		return new cuu(new cuj(bvr1), cue.b, bvr2.n());
	}

	public static class a {
		public static final Codec<ctt.a> a = RecordCodecBuilder.create(
			instance -> instance.group(
						Codec.BOOL.fieldOf("cold").forGetter(a -> a.b),
						Codec.FLOAT.fieldOf("mossiness").forGetter(a -> a.c),
						Codec.BOOL.fieldOf("air_pocket").forGetter(a -> a.d),
						Codec.BOOL.fieldOf("overgrown").forGetter(a -> a.e),
						Codec.BOOL.fieldOf("vines").forGetter(a -> a.f),
						Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(a -> a.g)
					)
					.apply(instance, ctt.a::new)
		);
		public boolean b;
		public float c = 0.2F;
		public boolean d;
		public boolean e;
		public boolean f;
		public boolean g;

		public a() {
		}

		public <T> a(boolean boolean1, float float2, boolean boolean3, boolean boolean4, boolean boolean5, boolean boolean6) {
			this.b = boolean1;
			this.c = float2;
			this.d = boolean3;
			this.e = boolean4;
			this.f = boolean5;
			this.g = boolean6;
		}
	}

	public static enum b {
		ON_LAND_SURFACE("on_land_surface"),
		PARTLY_BURIED("partly_buried"),
		ON_OCEAN_FLOOR("on_ocean_floor"),
		IN_MOUNTAIN("in_mountain"),
		UNDERGROUND("underground"),
		IN_NETHER("in_nether");

		private static final Map<String, ctt.b> g = (Map<String, ctt.b>)Arrays.stream(values()).collect(Collectors.toMap(ctt.b::a, b -> b));
		private final String h;

		private b(String string3) {
			this.h = string3;
		}

		public String a() {
			return this.h;
		}

		public static ctt.b a(String string) {
			return (ctt.b)g.get(string);
		}
	}
}

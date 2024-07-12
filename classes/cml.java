import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class cml<C extends cnr> {
	public static final BiMap<String, cml<?>> a = HashBiMap.create();
	private static final Map<cml<?>, cin.b> u = Maps.<cml<?>, cin.b>newHashMap();
	private static final Logger v = LogManager.getLogger();
	public static final cml<coa> b = a("Pillager_Outpost", new clq(coa.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<cnw> c = a("Mineshaft", new cli(cnw.a), cin.b.UNDERGROUND_STRUCTURES);
	public static final cml<coa> d = a("Mansion", new cmx(coa.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<coa> e = a("Jungle_Pyramid", new clf(coa.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<coa> f = a("Desert_Pyramid", new ckl(coa.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<coa> g = a("Igloo", new cle(coa.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<cok> h = a("Ruined_Portal", new cly(cok.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<com> i = a("Shipwreck", new cmc(com.a), cin.b.SURFACE_STRUCTURES);
	public static final cmn j = a("Swamp_Hut", new cmn(coa.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<coa> k = a("Stronghold", new cmk(coa.a), cin.b.STRONGHOLDS);
	public static final cml<coa> l = a("Monument", new clo(coa.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<cob> m = a("Ocean_Ruin", new ctp(cob.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<coa> n = a("Fortress", new cll(coa.a), cin.b.UNDERGROUND_DECORATION);
	public static final cml<coa> o = a("EndCity", new ckp(coa.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<cnh> p = a("Buried_Treasure", new cjz(cnh.a), cin.b.UNDERGROUND_STRUCTURES);
	public static final cml<cnu> q = a("Village", new cmr(cnu.a), cin.b.SURFACE_STRUCTURES);
	public static final cml<coa> r = a("Nether_Fossil", new ctm(coa.a), cin.b.UNDERGROUND_DECORATION);
	public static final cml<cnx> s = a("Bastion_Remnant", new cjp(cnx.a), cin.b.SURFACE_STRUCTURES);
	public static final List<cml<?>> t = ImmutableList.of(b, q, r);
	private final Codec<ckc<C, cml<C>>> w;

	private static <F extends cml<?>> F a(String string, F cml, cin.b b) {
		a.put(string.toLowerCase(Locale.ROOT), cml);
		u.put(cml, b);
		return gl.a(gl.aG, string.toLowerCase(Locale.ROOT), cml);
	}

	public cml(Codec<C> codec) {
		this.w = codec.fieldOf("config").<ckc<C, cml<C>>>xmap(cnr -> new ckc<>(this, (C)cnr), ckc -> ckc.c).codec();
	}

	public cin.b f() {
		return (cin.b)u.get(this);
	}

	public static void g() {
	}

	@Nullable
	public static ctz<?> a(cva cva, le le, long long3) {
		String string5 = le.l("id");
		if ("INVALID".equals(string5)) {
			return ctz.a;
		} else {
			cml<?> cml6 = gl.aG.a(new uh(string5.toLowerCase(Locale.ROOT)));
			if (cml6 == null) {
				v.error("Unknown feature id: {}", string5);
				return null;
			} else {
				int integer7 = le.h("ChunkX");
				int integer8 = le.h("ChunkZ");
				int integer9 = le.h("references");
				ctd ctd10 = le.e("BB") ? new ctd(le.n("BB")) : ctd.a();
				lk lk11 = le.d("Children", 10);

				try {
					ctz<?> ctz12 = cml6.a(integer7, integer8, ctd10, integer9, long3);

					for (int integer13 = 0; integer13 < lk11.size(); integer13++) {
						le le14 = lk11.a(integer13);
						String string15 = le14.l("id");
						cmm cmm16 = gl.aH.a(new uh(string15.toLowerCase(Locale.ROOT)));
						if (cmm16 == null) {
							v.error("Unknown structure piece id: {}", string15);
						} else {
							try {
								cty cty17 = cmm16.load(cva, le14);
								ctz12.d().add(cty17);
							} catch (Exception var17) {
								v.error("Exception loading structure piece with id {}", string15, var17);
							}
						}
					}

					return ctz12;
				} catch (Exception var18) {
					v.error("Failed Start with id {}", string5, var18);
					return null;
				}
			}
		}
	}

	public Codec<ckc<C, cml<C>>> h() {
		return this.w;
	}

	public ckc<C, ? extends cml<C>> a(C cnr) {
		return new ckc<>(this, cnr);
	}

	@Nullable
	public fu a(bqd bqd, bqq bqq, fu fu, int integer, boolean boolean5, long long6, cot cot) {
		int integer10 = cot.a();
		int integer11 = fu.u() >> 4;
		int integer12 = fu.w() >> 4;
		int integer13 = 0;

		for (ciy ciy14 = new ciy(); integer13 <= integer; integer13++) {
			for (int integer15 = -integer13; integer15 <= integer13; integer15++) {
				boolean boolean16 = integer15 == -integer13 || integer15 == integer13;

				for (int integer17 = -integer13; integer17 <= integer13; integer17++) {
					boolean boolean18 = integer17 == -integer13 || integer17 == integer13;
					if (boolean16 || boolean18) {
						int integer19 = integer11 + integer10 * integer15;
						int integer20 = integer12 + integer10 * integer17;
						bph bph21 = this.a(cot, long6, ciy14, integer19, integer20);
						cgy cgy22 = bqd.a(bph21.b, bph21.c, chc.b);
						ctz<?> ctz23 = bqq.a(go.a(cgy22.g(), 0), this, cgy22);
						if (ctz23 != null && ctz23.e()) {
							if (boolean5 && ctz23.h()) {
								ctz23.i();
								return ctz23.a();
							}

							if (!boolean5) {
								return ctz23.a();
							}
						}

						if (integer13 == 0) {
							break;
						}
					}
				}

				if (integer13 == 0) {
					break;
				}
			}
		}

		return null;
	}

	protected boolean b() {
		return true;
	}

	public final bph a(cot cot, long long2, ciy ciy, int integer4, int integer5) {
		int integer8 = cot.a();
		int integer9 = cot.b();
		int integer10 = Math.floorDiv(integer4, integer8);
		int integer11 = Math.floorDiv(integer5, integer8);
		ciy.a(long2, integer10, integer11, cot.c());
		int integer12;
		int integer13;
		if (this.b()) {
			integer12 = ciy.nextInt(integer8 - integer9);
			integer13 = ciy.nextInt(integer8 - integer9);
		} else {
			integer12 = (ciy.nextInt(integer8 - integer9) + ciy.nextInt(integer8 - integer9)) / 2;
			integer13 = (ciy.nextInt(integer8 - integer9) + ciy.nextInt(integer8 - integer9)) / 2;
		}

		return new bph(integer10 * integer8 + integer12, integer11 * integer8 + integer13);
	}

	protected boolean a(cha cha, brh brh, long long3, ciy ciy, int integer5, int integer6, bre bre, bph bph, C cnr) {
		return true;
	}

	private ctz<C> a(int integer1, int integer2, ctd ctd, int integer4, long long5) {
		return this.a().create(this, integer1, integer2, ctd, integer4, long5);
	}

	public ctz<?> a(cha cha, brh brh, cva cva, long long4, bph bph, bre bre, int integer, ciy ciy, cot cot, C cnr) {
		bph bph13 = this.a(cot, long4, ciy, bph.b, bph.c);
		if (bph.b == bph13.b && bph.c == bph13.c && this.a(cha, brh, long4, ciy, bph.b, bph.c, bre, bph13, cnr)) {
			ctz<C> ctz14 = this.a(bph.b, bph.c, ctd.a(), integer, long4);
			ctz14.a(cha, cva, bph.b, bph.c, bre, cnr);
			if (ctz14.e()) {
				return ctz14;
			}
		}

		return ctz.a;
	}

	public abstract cml.a<C> a();

	public String i() {
		return (String)a.inverse().get(this);
	}

	public List<bre.g> c() {
		return Collections.emptyList();
	}

	public List<bre.g> j() {
		return Collections.emptyList();
	}

	public interface a<C extends cnr> {
		ctz<C> create(cml<C> cml, int integer2, int integer3, ctd ctd, int integer5, long long6);
	}
}

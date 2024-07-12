import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

public class bmd {
	public static List<aog> a(bki bki) {
		return a(bki.o());
	}

	public static List<aog> a(bmb bmb, Collection<aog> collection) {
		List<aog> list3 = Lists.<aog>newArrayList();
		list3.addAll(bmb.a());
		list3.addAll(collection);
		return list3;
	}

	public static List<aog> a(@Nullable le le) {
		List<aog> list2 = Lists.<aog>newArrayList();
		list2.addAll(c(le).a());
		a(le, list2);
		return list2;
	}

	public static List<aog> b(bki bki) {
		return b(bki.o());
	}

	public static List<aog> b(@Nullable le le) {
		List<aog> list2 = Lists.<aog>newArrayList();
		a(le, list2);
		return list2;
	}

	public static void a(@Nullable le le, List<aog> list) {
		if (le != null && le.c("CustomPotionEffects", 9)) {
			lk lk3 = le.d("CustomPotionEffects", 10);

			for (int integer4 = 0; integer4 < lk3.size(); integer4++) {
				le le5 = lk3.a(integer4);
				aog aog6 = aog.b(le5);
				if (aog6 != null) {
					list.add(aog6);
				}
			}
		}
	}

	public static int c(bki bki) {
		le le2 = bki.o();
		if (le2 != null && le2.c("CustomPotionColor", 99)) {
			return le2.h("CustomPotionColor");
		} else {
			return d(bki) == bme.a ? 16253176 : a(a(bki));
		}
	}

	public static int a(bmb bmb) {
		return bmb == bme.a ? 16253176 : a(bmb.a());
	}

	public static int a(Collection<aog> collection) {
		int integer2 = 3694022;
		if (collection.isEmpty()) {
			return 3694022;
		} else {
			float float3 = 0.0F;
			float float4 = 0.0F;
			float float5 = 0.0F;
			int integer6 = 0;

			for (aog aog8 : collection) {
				if (aog8.e()) {
					int integer9 = aog8.a().f();
					int integer10 = aog8.c() + 1;
					float3 += (float)(integer10 * (integer9 >> 16 & 0xFF)) / 255.0F;
					float4 += (float)(integer10 * (integer9 >> 8 & 0xFF)) / 255.0F;
					float5 += (float)(integer10 * (integer9 >> 0 & 0xFF)) / 255.0F;
					integer6 += integer10;
				}
			}

			if (integer6 == 0) {
				return 0;
			} else {
				float3 = float3 / (float)integer6 * 255.0F;
				float4 = float4 / (float)integer6 * 255.0F;
				float5 = float5 / (float)integer6 * 255.0F;
				return (int)float3 << 16 | (int)float4 << 8 | (int)float5;
			}
		}
	}

	public static bmb d(bki bki) {
		return c(bki.o());
	}

	public static bmb c(@Nullable le le) {
		return le == null ? bme.a : bmb.a(le.l("Potion"));
	}

	public static bki a(bki bki, bmb bmb) {
		uh uh3 = gl.an.b(bmb);
		if (bmb == bme.a) {
			bki.c("Potion");
		} else {
			bki.p().a("Potion", uh3.toString());
		}

		return bki;
	}

	public static bki a(bki bki, Collection<aog> collection) {
		if (collection.isEmpty()) {
			return bki;
		} else {
			le le3 = bki.p();
			lk lk4 = le3.d("CustomPotionEffects", 9);

			for (aog aog6 : collection) {
				lk4.add(aog6.a(new le()));
			}

			le3.a("CustomPotionEffects", lk4);
			return bki;
		}
	}
}
